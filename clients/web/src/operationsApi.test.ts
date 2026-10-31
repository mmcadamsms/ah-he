import { afterEach, expect, test, vi } from "vitest";
import { getAccount, listOperations, parseBeneficiaries, parseExactJson, submitOperation } from "./operationsApi";

afterEach(() => vi.unstubAllGlobals());

test("rejects success-shaped malformed payloads and surfaces denied requests", async () => {
  const fetcher = vi.fn()
    .mockResolvedValueOnce(new Response(JSON.stringify({ data: {} }), { status: 200 }))
    .mockResolvedValueOnce(new Response(JSON.stringify({ error: { message: "Denied." } }), { status: 403 }));
  vi.stubGlobal("fetch", fetcher);
  await expect(getAccount("test-token")).rejects.toThrow("contract");
  await expect(listOperations("test-token", "inventory")).rejects.toThrow("Denied.");
});

test("preserves decimal strings, command identity and verified context in API requests", async () => {
  const fetcher = vi.fn().mockResolvedValue(new Response(JSON.stringify({
    data: { operationId: "operation", subjects: [], committedAt: "2026-10-31T20:00:00Z" },
  }), { status: 200 }));
  vi.stubGlobal("fetch", fetcher);
  await submitOperation("test-token", "reservations", "stable-key", {
    representedCustomerId: "", primaryBeneficiary: { kind: "ORDER_LINE", id: "line" },
    additionalBeneficiaries: [], purpose: "Reserve actual stock",
  }, { quantity: "0.0000001" });
  expect(fetcher).toHaveBeenCalledWith("/api/v1/operations/reservations",
    expect.objectContaining({
      headers: expect.objectContaining({ "Idempotency-Key": "stable-key" }),
      body: expect.stringContaining('"quantity":"0.0000001"'),
    }));
});

test("unrecognized beneficiary kinds cannot be silently treated as business scope", () => {
  expect(() => parseBeneficiaries('[{"kind":"UNKNOWN","id":"shop"}]')).toThrow();
  expect(parseBeneficiaries('[{"kind":"CUSTOMER","id":"customerA"}]'))
    .toEqual([{ kind: "CUSTOMER", id: "customerA" }]);
});

test("nested decimal and oversized JSON numbers cannot be silently rounded", () => {
  expect(() => parseExactJson('{"quantity":0.1}')).toThrow("strings");
  expect(() => parseExactJson('{"quantity":9007199254740993}')).toThrow("strings");
  expect(parseExactJson('{"quantity":"0.100000000001","revision":2}'))
    .toEqual({ quantity: "0.100000000001", revision: 2 });
});
