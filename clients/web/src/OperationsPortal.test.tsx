import { act, render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { beforeEach, expect, test, vi } from "vitest";
import { OperationsPortal } from "./OperationsPortal";
import { getAccount, getAudit, getHistory, listOperations, submitOperation } from "./operationsApi";

vi.mock("./operationsApi", () => ({
  getAccount: vi.fn(),
  listOperations: vi.fn(),
  submitOperation: vi.fn(),
  uploadEvidence: vi.fn(),
  getAssessment: vi.fn(),
  getAudit: vi.fn(),
  getHistory: vi.fn(),
  parseBeneficiaries: (value: string) => JSON.parse(value),
  parseExactJson: (value: string) => JSON.parse(value),
}));

beforeEach(() => {
  vi.clearAllMocks();
  vi.mocked(getAccount).mockResolvedValue({
    businessId: "shop",
    grant: { id: "operator", kind: "HUMAN", customerId: "", roles: ["ADMIN"], expiresAt: "2099-01-01T00:00:00Z" },
  });
  vi.mocked(listOperations).mockResolvedValue({ records: [], continuationToken: "", asOf: "2026-10-31T20:00:00Z" });
});

async function fillReceipt() {
  const user = userEvent.setup();
  render(<OperationsPortal />);
  await user.type(screen.getByLabelText("OIDC API access token"), "test-token");
  await user.click(screen.getByRole("button", { name: "Verify account" }));
  await screen.findByText(/Business: shop/);
  await user.type(screen.getByLabelText("Operation purpose"), "Controlled actual receipt");
  for (const [name, value] of Object.entries({
    owner: "shop", category: "stock", materialLot: "lot-1", quantity: "10",
    unit: "pieces", location: "receiving", custodian: "operator",
  })) {
    await user.type(screen.getByLabelText(name), value);
  }
  return user;
}

test("commits a receipt with explicit beneficiary but no client-forged actor", async () => {
  vi.mocked(submitOperation).mockResolvedValue({
    operationId: "committed-operation", subjects: ["H!inventory!item"], committedAt: "2026-10-31T20:00:00Z",
  });
  const user = await fillReceipt();
  await user.click(screen.getByRole("button", { name: "Commit command" }));
  expect(await screen.findByRole("status")).toHaveTextContent("committed-operation");
  expect(submitOperation).toHaveBeenCalledWith(
    "test-token", "inventory-items", expect.any(String),
    { representedCustomerId: "", primaryBeneficiary: { kind: "BUSINESS", id: "shop" },
      additionalBeneficiaries: [], purpose: "Controlled actual receipt" },
    expect.objectContaining({ quantity: "10", condition: "QUARANTINED", reserved: "0" }),
  );
  expect(screen.getByRole("button", { name: "Commit command" })).toBeDisabled();
  expect(localStorage.getItem("token")).toBeNull();
});

test("unknown outcomes retain request key and input for unchanged retry", async () => {
  vi.mocked(submitOperation)
    .mockRejectedValueOnce(new Error("Storage outcome is unknown; retain the command key."))
    .mockResolvedValueOnce({ operationId: "recovered", subjects: [], committedAt: "2026-10-31T20:00:00Z" });
  const user = await fillReceipt();
  await user.click(screen.getByRole("button", { name: "Commit command" }));
  expect(await screen.findByRole("alert")).toHaveTextContent("outcome is unknown");
  expect(screen.queryByRole("status")).not.toBeInTheDocument();
  await user.click(screen.getByRole("button", { name: "Commit command" }));
  expect(await screen.findByRole("status")).toHaveTextContent("recovered");
  const first = vi.mocked(submitOperation).mock.calls[0];
  const second = vi.mocked(submitOperation).mock.calls[1];
  expect(second[2]).toBe(first[2]);
  expect(second[4]).toEqual(first[4]);
});

test("customer account context is explicit and failed verification exposes no commands", async () => {
  vi.mocked(getAccount).mockRejectedValueOnce(new Error("Access denied."));
  const user = userEvent.setup();
  render(<OperationsPortal />);
  await user.type(screen.getByLabelText("OIDC API access token"), "test-token");
  await user.click(screen.getByRole("button", { name: "Verify account" }));
  expect(await screen.findByRole("alert")).toHaveTextContent("Access denied");
  expect(screen.queryByRole("button", { name: "Commit command" })).not.toBeInTheDocument();
  vi.mocked(getAccount).mockResolvedValueOnce({
    businessId: "shop", grant: { id: "customer-user", kind: "HUMAN", customerId: "customerA",
      roles: ["CUSTOMER"], expiresAt: "2099-01-01T00:00:00Z" },
  });

  await user.click(screen.getByRole("button", { name: "Verify account" }));
  await screen.findByText(/Business: shop/);
  expect(screen.getByLabelText("Primary beneficiary kind")).toHaveValue("CUSTOMER");
  expect(screen.getByLabelText("Primary beneficiary ID")).toHaveValue("customerA");
  expect(screen.getByLabelText("Represented customer ID (if applicable)")).toHaveValue("customerA");
});

test("switching identities clears protected caches and rejects late responses from the former session", async () => {
    let finishAudit: ((value: Record<string, unknown>) => void) | undefined;
    vi.mocked(listOperations).mockResolvedValueOnce({
      records: [{ id: "item", type: "inventory", revision: 1, record: { location: "receiving" } }],
      continuationToken: "", asOf: "2026-10-31T20:00:00Z",
    });
    vi.mocked(getHistory).mockResolvedValueOnce({
      records: [{ id: "item", type: "inventory", revision: 1, record: { operationId: "operation", state: {} } }],
      continuationToken: "", asOf: "2026-10-31T20:00:00Z",
    });
    vi.mocked(getAudit).mockImplementationOnce(() => new Promise((resolve) => { finishAudit = resolve; }));
    const user = await fillReceipt();
    await user.click(screen.getByRole("button", { name: "Load current records" }));
    await user.click(await screen.findByText("inventory · item · revision 1"));
    await user.click(await screen.findByRole("button", { name: "View change history" }));
    await user.click(await screen.findByText("item · revision 1"));
    await user.click(await screen.findByRole("button", { name: "View actor/beneficiary audit context" }));
    expect(finishAudit).toBeDefined();
    await user.clear(screen.getByLabelText("OIDC API access token"));
    await user.type(screen.getByLabelText("OIDC API access token"), "customer-token");
    vi.mocked(getAccount).mockResolvedValueOnce({
      businessId: "shop", grant: { id: "customer-user", kind: "HUMAN", customerId: "customerA",
        roles: ["CUSTOMER"], expiresAt: "2099-01-01T00:00:00Z" },
    });
    await user.click(screen.getByRole("button", { name: "Verify account" }));
    await screen.findByText(/Actor: customer-user/);
    await act(async () => { finishAudit?.({ actor: "former-private-actor" }); });
    expect(screen.queryByText(/former-private-actor/)).not.toBeInTheDocument();
    expect(screen.queryByRole("heading", { name: "Preserved change history" })).not.toBeInTheDocument();
    expect(screen.getByLabelText("owner")).toHaveValue("");
    expect(screen.getByLabelText("Operation purpose")).toHaveValue("");
});
