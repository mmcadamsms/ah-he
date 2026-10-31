# Actor and Beneficiary Operation Context

**Status:** Design refinement requested by the user; implementation remains
gated on agreement of the unified platform design.

## Problem

Business operations are not explained by a resource ID or generic system actor.
A customer-provisioned user, employee, device or service performs work in some
authorized capacity, while a customer, order or the business itself receives
the intended benefit. Conflating these concepts loses accountability, hides
shared shop work, and can incorrectly grant customer access or financial authority.

## Objective

Give every business operation an attributable actor and explicit beneficiary
context across commands, changes, approvals, observations, queued work and
external effects. Keep representation, authorization, affected resources,
ownership and financial allocation distinct.

The receiving side is called **beneficiary** here; it does not necessarily fund
the work. This captures the user's “benefactor” examples of customer, customer
order and shop/business as the party or purpose served.

## Terminology

- **Actor/principal:** Identified human, service or device accountable for a
  particular step. Credentials authenticate a principal; they are not the
  permanent business identity.
- **Affiliation:** The business/customer organization membership or service/
  device ownership associated with a principal.
- **Represented party:** Organization/customer on whose behalf an actor acts,
  supported by an applicable membership, delegation or service authority.
- **Beneficiary:** Intended recipient or purpose of the operation: operating
  business, customer, customer order, order line or job.
- **Subject:** The affected item, asset, measurement, package, order or other
  resource; not necessarily the actor's represented party or beneficiary.
- **Operation context:** Versioned record linking actor roles, authority,
  business scope, beneficiaries, subjects and causation.
- **Allocation:** Separately approved accounting/resource attribution; naming
  a beneficiary alone does not allocate cost or create payment liability.

## Inputs

Verified principal and credential source; current affiliation/membership,
customer provisioning authority, role/delegation/service grants and their
versions/expiry; operating business; represented party where applicable;
requested action and subjects; beneficiary references and purpose; order/job
parent relationships; initiating/causing operation; applicable approval policy;
optional approved allocation, correction and imported-evidence provenance.

## Rules

1. **OC-01 - Required shared context.** Every business action/read, domain
   command, change, approval, observation, scheduled task and external intent
   has an actor and explicit beneficiary classification. Read/search context
   expresses the business/customer purpose and authorized subjects; it does
   not require creating a mutation for every row read.
2. **OC-02 - Actor attribution.** Preserve stable principals and separate
   requester, performer, confirmer, approver and executing service/device.
   An employee serving a customer retains employee identity. Do not impersonate
   that customer's user or use a generic `SYSTEM` actor without a stable identity.
3. **OC-03 - Customer-provisioned users.** Customer administrators may invite/
   provision users and bounded roles for their verified customer account only.
   Validate the provisioner's grant. Customer admins cannot mint internal staff
   roles, self-approve financial authority, or grant another customer's access.
   Store authenticated identity mapping separately from editable display data.
4. **OC-04 - Representation is explicit.** Bind on-behalf-of actions to the
   represented party and a valid authority reference. An internal employee can
   serve a customer under business-granted authority without pretending the
   customer delegated every internal step. Never infer authority from a
   beneficiary field, email domain, label or trace ID.
5. **OC-05 - Beneficiary model.** Record a primary beneficiary with typed stable
   reference, plus additional beneficiaries when appropriate and permitted.
   Supported initial kinds are business, customer, order, order line and job.
   Validate parent organization/customer relationships and operation purpose.
   Benefit need not imply exclusive benefit or measured financial gain.
6. **OC-06 - Shared business work.** Business-level care, housekeeping,
   purchasing, backup and maintenance can benefit the operating business/shared
   capability without inventing an order or enumerating every customer.
   Record the business beneficiary and reason for shared/unallocated work.
7. **OC-07 - Incomplete association.** An unidentified receipt can have the
   real operating business as beneficiary of controlled receiving/quarantine,
   with customer/order association explicitly unresolved. Do not assign a
   guessed customer or silently use business benefit to bypass the identity
   requirements of a refund, customer release or order allocation.
8. **OC-08 - Independent security and economics.** Beneficiary, represented
   party, subject ownership, payer and economic allocation are distinct fields.
   A beneficiary is not a read grant, customer consent, allowed refund method,
   or billing instruction. Optional allocations state basis/units/currency/
   coverage and require applicable approval; do not assume equal shares or
   charge all costs to the primary beneficiary.
9. **OC-09 - Validate at execution.** Validate credentials, current grants,
   scope, linked customer/order/job relationships and purpose for each protected
   action. Guard authority and relationship revisions that affect a committing
   decision in the same operational transaction as its effects. Revocation or
   reassignment cannot be ignored because an old view or queued envelope exists.
   Local authority heads participate in commit guards; external credential/
   revocation checks have a separately specified freshness/session policy, not
   an instant atomic identity-provider/Table transaction.
10. **OC-10 - Work propagation.** Committed operation context survives outbox/
    queue retries, notifications and external intents. The worker records its
    own executing principal plus original requester/causation and rechecks the
    applicable execution authority. A service's authority is not an inherited
    blanket human approval; valid service-granted follow-up work can continue
    only under its explicit policy.
11. **OC-11 - Durable history and idempotency.** Commit validated context with
    the business change/history/receipt/outbox. Include beneficiary and
    representation intent in the command digest. Reusing a command key with
    a different beneficiary/represented party is a conflict, not a silent edit.
12. **OC-12 - Corrections and imports.** Changes in association, affiliation or
    beneficiary create attributable new context/correction history without
    rewriting previous actions. Preserve then-current authority references and
    versions; current authorization governs new actions. Imported/reported
    performer claims keep source and verification status, separate from the
    authenticated importing actor; do not fabricate authenticated history.
13. **OC-13 - Visibility and telemetry.** Provide access-controlled views by
    beneficiary, subject, actor and operation. Shared multi-customer work does
    not expose other customers to each participant. Diagnostics use safe opaque
    context/operation references, not delegation credentials, personal identity
    details or unbounded beneficiary lists; business history remains authoritative.

## Assumptions

- One business operates the initial pilot but may serve many customer
  organizations, each with provisioned users. Business tenancy and customer
  affiliation are not equivalent.
- Service/device principals perform scheduled work on a documented business
  mandate even when no human request triggered that execution.
- Beneficiary attribution records intent/purpose; measured economic outcomes
  and cost accounting require their own evidence and policy.
- These are shared contracts for the five workflows, not a complete invitation,
  identity-provider, organization hierarchy or general delegation product.

## Constraints

- No beneficiary label grants access, permits physical work, or authorizes money.
- Clients cannot supply trusted actor identity or provision unrestricted roles.
- Multi-beneficiary work stays inside the permitted organizational transaction/
  data boundary; no cross-organization atomicity claim is introduced.
- Retention/privacy policies govern principal history and displays; historical
  provenance must remain accountable without indiscriminate personal-data logging.

## Acceptance Criteria

| ID | Observable outcome after implementation approval |
|----|--------------------------------------------------|
| OC-AC-01 | A customer-admin-provisioned user submits a return for their customer's exact order; another customer's order or an internal staff-role grant is denied. |
| OC-AC-02 | An employee submits/handles work for a customer order with their own principal, internal affiliation and valid authority; customer identity is not impersonated. |
| OC-AC-03 | Scheduled compressor maintenance records the named executing service/maintainer, business-wide beneficiary and purpose without inventing a customer order. |
| OC-AC-04 | A stock split or measured checkpoint links actor, actual subject and validated order/job beneficiary, while ownership and payer remain distinct. |
| OC-AC-05 | Shared multi-customer work permits authorized internal attribution but customer A cannot view customer B's identity, evidence or allocations. |
| OC-AC-06 | A forged on-behalf-of or beneficiary reference, concurrent grant revocation or changed order/customer relationship cannot authorize a stale action. |
| OC-AC-07 | Replay after restart preserves initiating context and records the worker principal; changed beneficiary/representation under the same idempotency key conflicts. |
| OC-AC-08 | An unidentified package remains quarantined with business receiving purpose and unresolved customer association; that context cannot trigger refund/release. |
| OC-AC-09 | A beneficiary correction retains original context, correction authority and history; a reported performer remains distinguishable from the authenticated importer. |
| OC-AC-10 | Adding a beneficiary does not grant access, approve a refund or automatically allocate cost; telemetry does not expose credentials/personal context. |

## Failure Behavior

Reject unauthorized provisioning/representation and beneficiary/subject
relationships. Expose missing purpose, unresolved association, stale/revoked
authority or inconsistent context as explicit denied, review-required or blocked
states. Preserve safe receiving/observation under a verified business purpose
without fabricating customer association. Queue failures do not erase origin
context or upgrade service authority. Untrusted attempted identifiers are not
recorded as validated actors/beneficiaries in business history.

## Out of Scope

Runtime implementation before design agreement; universal cost-allocation
formula; treating intended benefit as actual profit; complete customer identity
onboarding UI or general delegated-approval engine; identity-provider selection;
physical control authority; bypassing established return/quality/financial rules.

## References

- [Unified platform design](../../../docs/architecture/unified-operational-platform-design.md#actor-representation-and-beneficiary-context).
- [Shared storage/logging requirement](unified-operations-storage-and-logging.md).
- [Proposed ADR 0009](../../../docs/architecture/decisions/0009-azure-first-portable-operational-platform.md).
- [Design/review prompt](../../prompts/deploying/20261031-unified-operations-storage-design.md).
- [User-management scope](../user-management/README.md).
- [Inventory actor/custody rules](../manufacturing-automation/inventory-tooling-and-equipment-lifecycle.md#responsibility-chain-of-custody-and-work-in-process-rules).
- [Machine care](../manufacturing-automation/machine-maintenance-and-care.md).
- [Measured inspection](../manufacturing-automation/in-process-inspection-and-progressive-verification.md).
- [Owned benchmark quotes](../commercial-operations/external-market-quote-benchmarking.md).
- [Customer returns](../commercial-operations/return-authorization-and-refund-workflow.md).
