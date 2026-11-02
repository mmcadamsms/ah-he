import { useRef, useState, type FormEvent } from "react";
import { operationCatalog, type CommandSpec } from "./operationCatalog";
import {
  getAccount, getAssessment, getAudit, getHistory, listOperations, parseBeneficiaries, parseExactJson, submitOperation, uploadEvidence,
  type Account, type BeneficiaryKind, type Context, type Outcome, type Page,
} from "./operationsApi";
import "./operations.css";

function initial(spec: CommandSpec): Record<string, string> {
  return Object.fromEntries(spec.fields.map((field) =>
    [field.name, field.initial ?? (field.name === "id" && spec.createsRecord !== false ? crypto.randomUUID() : field.type === "json" ? "[]" : "")]));
}

export function OperationsPortal() {
  const [token, setToken] = useState("");
  const [account, setAccount] = useState<Account>();
  const [spec, setSpec] = useState(operationCatalog[0]);
  const [values, setValues] = useState(() => initial(operationCatalog[0]));
  const [key, setKey] = useState(() => crypto.randomUUID());
  const [kind, setKind] = useState<BeneficiaryKind>("BUSINESS");
  const [beneficiaryId, setBeneficiaryId] = useState("");
  const [represented, setRepresented] = useState("");
  const [additional, setAdditional] = useState("[]");
  const [purpose, setPurpose] = useState("");
  const [page, setPage] = useState<Page>();
  const [pageType, setPageType] = useState(spec.view);
  const [outcome, setOutcome] = useState<Outcome>();
  const [detail, setDetail] = useState<Record<string, unknown>>();
  const [history, setHistory] = useState<Page>();
  const [error, setError] = useState("");
  const [busy, setBusy] = useState(false);
  const [file, setFile] = useState<File>();
  const [artifactId, setArtifactId] = useState<string>(() => crypto.randomUUID());
  const [artifactCustomer, setArtifactCustomer] = useState("");
  const session = useRef(0);
  const [historyTarget, setHistoryTarget] = useState<{ type: string; id: string }>();

  const clearSession = (nextToken: string) => {
    session.current++;
    setToken(nextToken); setAccount(undefined); setOutcome(undefined); setPage(undefined);
    setDetail(undefined); setHistory(undefined); setHistoryTarget(undefined); setError(""); setBusy(false);
    setSpec(operationCatalog[0]); setValues(initial(operationCatalog[0])); setKey(crypto.randomUUID());
    setKind("BUSINESS"); setBeneficiaryId(""); setRepresented(""); setAdditional("[]"); setPurpose("");
    setFile(undefined); setArtifactId(crypto.randomUUID()); setArtifactCustomer("");
  };

  const context = (): Context => {
    if (!purpose.trim() || !beneficiaryId.trim()) {
      throw new Error("Explicit operation purpose and primary beneficiary are required.");
    }
    return { representedCustomerId: represented, primaryBeneficiary: { kind, id: beneficiaryId },
      additionalBeneficiaries: parseBeneficiaries(additional), purpose };
  };
  const connect = async (event: FormEvent) => {
    event.preventDefault();
    const generation = session.current;
    setError(""); setBusy(true);
    try {
      const current = await getAccount(token);
      if (generation !== session.current) return;
      setAccount(current);
      setKind(current.grant.customerId ? "CUSTOMER" : "BUSINESS");
      setBeneficiaryId(current.grant.customerId || current.businessId);
      setRepresented(current.grant.customerId);
      setArtifactCustomer(current.grant.customerId);
    } catch (failure) {
      if (generation !== session.current) return;
      setAccount(undefined);
      setError(failure instanceof Error ? failure.message : "Authentication failed.");
    } finally { if (generation === session.current) setBusy(false); }
  };
  const load = async (cursor = "", type = spec.view) => {
    const generation = session.current;
    try {
      const result = await listOperations(token, type, cursor);
      if (generation !== session.current) return;
      setPage(result);
      setPageType(type);
    } catch (failure) {
      if (generation !== session.current) return;
      setError(failure instanceof Error ? failure.message : "Records are unavailable.");
    }
  };
  const change = (next: CommandSpec) => {
    setSpec(next); setValues(initial(next)); setKey(crypto.randomUUID());
    setOutcome(undefined); setPage(undefined); setError("");
  };
  const submit = async (event: FormEvent) => {
    event.preventDefault(); setError(""); setBusy(true);
    const generation = session.current;
    try {
      const input: Record<string, unknown> = {};
      let route = spec.route;
      for (const field of spec.fields) {
        const value = values[field.name] ?? "";
        if (field.name === spec.pathField) {
          if (!value) throw new Error(`${field.name} is required.`);
          route = route.replace("{id}", encodeURIComponent(value));
          continue;
        }
        if (!field.optional && field.type !== "boolean" && !value.trim()) {
          throw new Error(`${field.name} is required; no operating default is assumed.`);
        }
        if (field.type === "integer") {
          const number = Number(value);
          if (!Number.isSafeInteger(number) || number < 0) throw new Error(`${field.name} must be a nonnegative integer.`);
          input[field.name] = number;
        } else if (field.type === "boolean") {
          if (value !== "true" && value !== "false") throw new Error(`Explicitly select ${field.name}.`);
          input[field.name] = value === "true";
        } else if (field.type === "json") {
          input[field.name] = parseExactJson(value);
        } else {
          input[field.name] = value;
        }
      }
      const result = await submitOperation(token, route, key, context(), input);
      if (generation !== session.current) return;
      setOutcome(result);
      await load();
    } catch (failure) {
      if (generation !== session.current) return;
      setError(failure instanceof Error ? failure.message : "Command failed; retain its request key.");
    } finally { if (generation === session.current) setBusy(false); }
  };
  const upload = async () => {
    setError(""); setBusy(true);
    const generation = session.current;
    try {
      if (!file) throw new Error("Choose actual evidence to upload.");
      const result = await uploadEvidence(token, key, context(), artifactId, artifactCustomer, file);
      if (generation !== session.current) return;
      setOutcome(result);
      await load("", "artifact");
    } catch (failure) {
      if (generation !== session.current) return;
      setError(failure instanceof Error ? failure.message : "Evidence upload failed; retain its request key.");
    } finally { if (generation === session.current) setBusy(false); }
  };
  const inspect = async (type: "eligibility" | "audit" | "history", id: string, recordType = "", cursor = "") => {
    const generation = session.current;
    setError("");
    try {
      if (type === "history") {
        const result = await getHistory(token, recordType, id, cursor);
        if (generation !== session.current) return;
        setHistory(result); setHistoryTarget({ type: recordType, id });
      } else {
        const result = await (type === "audit" ? getAudit(token, id) : getAssessment(token, id));
        if (generation !== session.current) return;
        setDetail(result);
      }
    } catch (failure) {
      if (generation !== session.current) return;
      setError(failure instanceof Error ? failure.message : "Context/eligibility is unavailable; do not infer a release.");
    }
  };

  return <main className="operations-workspace">
    <header><h1>Operations workspace</h1><a href="/">Manufacturing prototype</a></header>
    <p>Manual controlled workflows · No machine control, automated inspection, carrier labels or payment execution.</p>
    <form onSubmit={connect} className="operations-panel">
      <label>OIDC API access token
        <input type="password" autoComplete="off" value={token} onChange={(event) => clearSession(event.target.value)} required />
      </label>
      <p>Token stays in this page's memory, never browser storage. Use the configured issuer/audience.</p>
      <button disabled={busy} type="submit">Verify account</button>
      <button type="button" onClick={() => clearSession("")}>Sign out and clear local data</button>
      {account && <p>Business: {account.businessId} · Actor: {account.grant.id} ({account.grant.kind}) · Roles: {account.grant.roles.join(", ")}</p>}
      {account?.grant.roles.includes("ADMIN") && <p>ADMIN manages business access; it does not implicitly grant inspection, maintenance or financial authority. Assign qualified people explicit roles.</p>}
    </form>
    {error && <p role="alert" className="error-banner">{error}</p>}
    {outcome && <p role="status" className="operations-success">Committed operation {outcome.operationId}.
      External execution may still be unavailable. Start a new command before changing intent.</p>}
    {account && <>
      <section className="operations-panel">
        <h2>Actor and beneficiary context</h2>
        <p>The actor is verified by the API. Beneficiary never grants permission or automatically assigns cost.</p>
        <label>Primary beneficiary kind<select value={kind} onChange={(event) => {
          const value = event.target.value;
          if (value === "BUSINESS" || value === "CUSTOMER" || value === "ORDER_LINE") setKind(value);
        }}><option>BUSINESS</option><option>CUSTOMER</option><option>ORDER_LINE</option></select></label>
        <label>Primary beneficiary ID<input value={beneficiaryId} onChange={(event) => setBeneficiaryId(event.target.value)} /></label>
        <label>Represented customer ID (if applicable)<input value={represented} onChange={(event) => setRepresented(event.target.value)} /></label>
        <label>Additional beneficiaries (JSON)<textarea value={additional} onChange={(event) => setAdditional(event.target.value)} /></label>
        <label>Operation purpose<textarea value={purpose} onChange={(event) => setPurpose(event.target.value)} required /></label>
        <label>Request key<input value={key} readOnly /></label>
        <p>On an unknown outcome, retry the unchanged command/key. Changing intent after a commit requires a new command.</p>
      </section>
      <form onSubmit={submit} className="operations-panel">
        <label>Workflow command<select aria-label="Workflow command" value={spec.name} disabled={busy}
          onChange={(event) => {
            const next = operationCatalog.find((entry) => entry.name === event.target.value);
            if (next) change(next);
          }}>{operationCatalog.map((entry) => <option key={entry.name}>{entry.name}</option>)}</select></label>
        <p>{spec.note}</p>
        <div className="operations-fields">
          {spec.fields.map((field) => <label key={field.name}>{field.name}
            {field.type === "json" ? <textarea value={values[field.name] ?? ""}
              onChange={(event) => setValues({ ...values, [field.name]: event.target.value })} />
              : field.type === "boolean" ? <select value={values[field.name] ?? ""}
                onChange={(event) => setValues({ ...values, [field.name]: event.target.value })}>
                <option value="">Select explicitly</option><option value="true">true</option><option value="false">false</option>
              </select> : <input value={values[field.name] ?? ""}
                onChange={(event) => setValues({ ...values, [field.name]: event.target.value })}
                required={!field.optional} autoComplete="off" />}
          </label>)}
        </div>
        <button disabled={busy || !!outcome} type="submit">Commit command</button>
        <button disabled={busy} type="button" onClick={() => change(spec)}>Start new command</button>
        <button disabled={busy} type="button" onClick={() => { setError(""); void load(); }}>Load current records</button>
      </form>
      <section className="operations-panel">
        <h2>Evidence upload</h2>
        <p>Uploaded files remain unverified. A qualified actor must attest the actual approved scan and hash before they can support release.</p>
        <label>Artifact ID<input value={artifactId} onChange={(event) => setArtifactId(event.target.value)} /></label>
        <label>Evidence customer scope (blank for internal business evidence)<input value={artifactCustomer} onChange={(event) => setArtifactCustomer(event.target.value)} /></label>
        <label>Actual evidence file<input type="file" onChange={(event) => setFile(event.target.files?.[0])} /></label>
        <button disabled={busy || !!outcome} onClick={() => { void upload(); }} type="button">Upload unverified evidence</button>
        <button disabled={busy} type="button" onClick={() => {
          setArtifactId(crypto.randomUUID()); setKey(crypto.randomUUID()); setOutcome(undefined); setFile(undefined);
        }}>Start new evidence upload</button>
      </section>
      {page && <section className="operations-panel">
        <h2>Authoritative record page</h2><p>Read at {page.asOf}. Revisions and eligibility are rechecked when commands commit.</p>
        {page.records.length === 0 && <p>No visible records on this page.</p>}
        {page.records.map((record) => <details key={`${record.type}-${record.id}-${record.revision}`}>
          <summary>{record.type} · {record.id} · revision {record.revision}</summary>
          <pre>{JSON.stringify(record.record, null, 2)}</pre>
          <button type="button" onClick={() => { void inspect("history", record.id, record.type); }}>View change history</button>
          {record.type === "asset" && <><p>qualified is historical qualification; current eligibility is evaluated separately.</p>
            <button type="button" onClick={() => { void inspect("eligibility", record.id); }}>Assess current eligibility</button></>}
        </details>)}
        {page.continuationToken && <button onClick={() => { void load(page.continuationToken, pageType); }}>Next page</button>}
      </section>}
      {history && <section className="operations-panel"><h2>Preserved change history</h2>
        {history.records.map((record) => <details key={`${record.id}-${record.revision}`}>
          <summary>{record.id} · revision {record.revision}</summary><pre>{JSON.stringify(record.record, null, 2)}</pre>
          {account.grant.roles.includes("ADMIN") && typeof record.record.operationId === "string"
            && <button onClick={() => { void inspect("audit", String(record.record.operationId)); }}>View actor/beneficiary audit context</button>}
        </details>)}
        {history.continuationToken && historyTarget && <button type="button" onClick={() => {
          void inspect("history", historyTarget.id, historyTarget.type, history.continuationToken);
        }}>Next history page</button>}
      </section>}
      {detail && <section className="operations-panel"><h2>Current assessment / protected audit context</h2>
        <p>Assessments are time-bound reads, not permission to operate or start machinery.</p><pre>{JSON.stringify(detail, null, 2)}</pre></section>}
    </>}
  </main>;
}
