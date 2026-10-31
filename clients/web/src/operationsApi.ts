export type BeneficiaryKind = "BUSINESS" | "CUSTOMER" | "ORDER_LINE";
export type Context = {
  representedCustomerId: string;
  primaryBeneficiary: { kind: BeneficiaryKind; id: string };
  additionalBeneficiaries: { kind: BeneficiaryKind; id: string }[];
  purpose: string;
};
export type Account = {
  businessId: string;
  grant: { id: string; kind: string; customerId: string; roles: string[]; expiresAt: string };
};
export type View = { id: string; type: string; revision: number; record: Record<string, unknown> };
export type Page = { records: View[]; continuationToken: string; asOf: string };
export type Outcome = { operationId: string; subjects: string[]; committedAt: string };

function object(value: unknown): value is Record<string, unknown> {
  return typeof value === "object" && value !== null && !Array.isArray(value);
}
function strings(value: unknown): value is string[] {
  return Array.isArray(value) && value.every((item: unknown) => typeof item === "string");
}
function account(value: unknown): value is Account {
  return object(value) && typeof value.businessId === "string" && object(value.grant)
    && typeof value.grant.id === "string" && typeof value.grant.kind === "string"
    && typeof value.grant.customerId === "string" && strings(value.grant.roles)
    && typeof value.grant.expiresAt === "string";
}
function view(value: unknown): value is View {
  return object(value) && typeof value.id === "string" && typeof value.type === "string"
    && typeof value.revision === "number" && object(value.record);
}
function page(value: unknown): value is Page {
  return object(value) && Array.isArray(value.records) && value.records.every(view)
    && typeof value.continuationToken === "string" && typeof value.asOf === "string";
}
function outcome(value: unknown): value is Outcome {
  return object(value) && typeof value.operationId === "string" && strings(value.subjects)
    && typeof value.committedAt === "string";
}

async function response<T>(result: Response, valid: (value: unknown) => value is T): Promise<T> {
  const text = await result.text();
  let payload: unknown;
  try {
    payload = text ? JSON.parse(text) : undefined;
  } catch {
    throw new Error(`API returned an invalid response (HTTP ${result.status}).`);
  }
  if (!result.ok) {
    const message = object(payload) && object(payload.error) && typeof payload.error.message === "string"
      ? payload.error.message : `Request denied or unavailable (HTTP ${result.status}).`;
    throw new Error(message);
  }
  if (!object(payload) || !valid(payload.data)) {
    throw new Error("Operational API response does not match its contract.");
  }
  return payload.data;
}

export async function getAccount(token: string): Promise<Account> {
  return response(await fetch("/api/v1/operations/me", {
    headers: { Authorization: `Bearer ${token}` },
  }), account);
}

export async function listOperations(token: string, type: string, cursor = ""): Promise<Page> {
  const params = new URLSearchParams({ continuationToken: cursor, size: "50" });
  return response(await fetch(`/api/v1/operations/records/${encodeURIComponent(type)}?${params}`, {
    headers: { Authorization: `Bearer ${token}` },
  }), page);
}

export async function getHistory(token: string, type: string, id: string, cursor = ""): Promise<Page> {
  const params = new URLSearchParams({ continuationToken: cursor, size: "50" });
  return response(await fetch(`/api/v1/operations/records/${encodeURIComponent(type)}/${encodeURIComponent(id)}/history?${params}`,
    { headers: { Authorization: `Bearer ${token}` } }), page);
}

export async function getAssessment(token: string, id: string): Promise<Record<string, unknown>> {
  return response(await fetch(`/api/v1/operations/assets/${encodeURIComponent(id)}/eligibility`,
    { headers: { Authorization: `Bearer ${token}` } }), object);
}

export async function getAudit(token: string, id: string): Promise<Record<string, unknown>> {
  return response(await fetch(`/api/v1/operations/audit/${encodeURIComponent(id)}`,
    { headers: { Authorization: `Bearer ${token}` } }), object);
}

export async function submitOperation(
  token: string, route: string, key: string, context: Context, input: Record<string, unknown>,
): Promise<Outcome> {
  return response(await fetch(`/api/v1/operations/${route}`, {
    method: "POST",
    headers: { Authorization: `Bearer ${token}`, "Idempotency-Key": key, "Content-Type": "application/json" },
    body: JSON.stringify({ context, input }),
  }), outcome);
}

export async function uploadEvidence(
  token: string, key: string, context: Context, id: string, customerId: string, file: File,
): Promise<Outcome> {
  const form = new FormData();
  form.append("context", new Blob([JSON.stringify(context)], { type: "application/json" }));
  form.append("file", file);
  form.append("id", id);
  form.append("customerId", customerId);
  return response(await fetch("/api/v1/operations/artifacts", {
    method: "POST", headers: { Authorization: `Bearer ${token}`, "Idempotency-Key": key }, body: form,
  }), outcome);
}

export function parseBeneficiaries(value: string): Context["additionalBeneficiaries"] {
  const data: unknown = JSON.parse(value);
  if (!Array.isArray(data) || data.length > 8 || !data.every((entry: unknown) =>
    object(entry) && typeof entry.id === "string"
    && (entry.kind === "BUSINESS" || entry.kind === "CUSTOMER" || entry.kind === "ORDER_LINE"))) {
    throw new Error("Additional beneficiaries must be at most eight typed {kind,id} entries.");
  }
  return data;
}

export function parseExactJson(value: string): unknown {
    const data: unknown = JSON.parse(value);
    const check = (entry: unknown): void => {
      if (typeof entry === "number" && !Number.isSafeInteger(entry)) {
        throw new Error("Use strings for decimal or large numeric values in JSON to preserve exact precision.");
      }
      if (Array.isArray(entry)) entry.forEach(check);
      else if (object(entry)) Object.values(entry).forEach(check);
    };
    check(data);
    return data;
}
