# Return Authorization and Refund Workflow

## Problem

A customer needs a clear way to return a specific part and request a refund,
replacement, repair, or credit. The business must know which order, line item,
part revision, quantity, package, customer, and reason belong to the physical
return. Unidentified or duplicate packages create fraud, safety, accounting,
inventory, and customer-service risk.

## Objective

Provide an application-driven return authorization workflow that validates
eligibility, creates a unique return record, issues a carrier label, QR code,
or freight credential bound to the exact return package, tracks two-way
custody, receives and inspects the item, and issues the approved remedy with a
complete audit trail.

## Terminology

- **Return authorization:** Approved record permitting specified items and
  quantities to be returned under stated conditions.
- **RMA:** Human-readable return merchandise authorization identifier.
- **Return credential:** Carrier label, QR code, pickup number, bill of lading,
  or other issued shipping authorization.
- **Return package:** One physical shipping unit associated with one
  authorization and package sequence.
- **Remedy:** Refund, credit, replacement, repair, rework, rejection, or other
  approved resolution.
- **Unidentified return:** Received package that cannot be confidently linked
  to a valid authorization.

## Customer Return Request

The customer selects:

- order;
- line item;
- part and revision;
- quantity;
- serial or lot where applicable;
- reason;
- requested remedy;
- description;
- photographs or video where requested;
- whether original packaging exists;
- package count and approximate dimensions/weight; and
- preferred eligible return method.

The system displays return policy, deadlines, packaging instructions, evidence
requirements, expected review, and whether return freight is prepaid.

## Eligibility

Evaluate:

- customer and order identity;
- return window;
- warranty and contract;
- custom-made status;
- approved requirements;
- quantity already returned;
- prior remedy;
- alleged defect or damage;
- customer-caused modification, installation, or use;
- hazardous, contaminated, export-controlled, or restricted status;
- payment and chargeback state;
- applicable law; and
- whether inspection is required before approval.

Ineligible or uncertain requests return an explicit reason and review path.

## Return Identity

Every authorization has:

- immutable internal ID;
- human-readable RMA;
- customer;
- order and line;
- part and revision;
- authorized quantity;
- reason;
- remedy requested and provisionally approved;
- package count;
- expiration;
- status;
- evidence;
- payer for freight;
- receiving instructions; and
- audit history.

Every package has:

- RMA;
- package sequence;
- carrier;
- service;
- unique tracking;
- label or QR credential;
- expected contents and quantity;
- dimensions and weight;
- declared value;
- packaging instructions; and
- current custody state.

Do not encode confidential part details in externally visible labels when an
opaque ID is sufficient.

## Carrier Credential Rules

1. Business-paid return shipping requires a credential generated from an
   active authorization.
2. The credential is bound to one package sequence and tracking number.
3. A credential cannot be reused after cancellation, expiration, receipt, or
   completion.
4. Support printable labels and printerless QR codes where the carrier offers
   them.
5. Freight returns may use a bill of lading, pickup number, approved carrier,
   and scheduled collection.
6. Carrier acceptance cannot be universally controlled for customer-paid
   shipments; unidentified arrivals follow the quarantine workflow.
7. Label creation records quoted and actual return-shipping cost.

## Status Model

Support at least:

```text
Requested
Under review
Additional information required
Authorized
Credential issued
Picked up
In transit
Delivered to business
Receiving quarantine
Identified
Inspection in progress
Remedy approved
Refund or credit pending
Replacement or repair in progress
Completed
Rejected
Cancelled
Expired
Unidentified
```

## Receiving and Inspection

1. Scan RMA, label, QR, tracking, or package identifier at receipt.
2. Photograph package condition before opening when required by risk rules.
3. Verify expected package sequence, item, revision, quantity, and serial/lot.
4. Quarantine until identity and safety are confirmed.
5. Capture staged unpacking evidence where damage attribution warrants it.
6. Inspect against approved acceptance criteria and reported reason.
7. Record condition, findings, responsibility, disposition, and costs.
8. Link the return to original production, inspection, packaging, shipment,
   and customer evidence.

## Unidentified Return Workflow

1. Quarantine safely.
2. Record carrier, tracking, sender, labels, dimensions, weight, photographs,
   contents if safe to inspect, and receipt date.
3. Attempt association using tracking, customer, order, serial, part, and
   communication history.
4. Contact likely customer when permitted.
5. Do not automatically refund, discard, use, or reship the item.
6. Apply storage, abandonment, hazardous-material, lien, and disposition rules
   only after legal and policy requirements are met.

## Remedy and Refund

Calculate:

- authorized quantity;
- accepted responsibility;
- item amount;
- tax adjustment;
- original freight treatment;
- return freight;
- discount allocation;
- deposit or milestone allocation;
- prior credit, refund, replacement, or chargeback;
- restocking fee only when contractually and legally allowed;
- payment processor fee treatment; and
- refund method.

Refunds normally return to the original payment method unless policy, law, or
documented exception permits otherwise.

Do not issue a duplicate remedy for the same quantity without authorized
override.

## Customer Experience

The customer can see:

- request status;
- information needed;
- authorization decision;
- RMA;
- packaging instructions;
- label, QR code, or pickup details;
- tracking;
- receiving confirmation;
- inspection status;
- decision and evidence summary;
- approved remedy;
- refund, credit, replacement, or repair status; and
- expected completion.

## Privacy and Security

- Authenticate access to orders and returns.
- Use opaque external identifiers.
- Restrict customer financial, address, part, and evidence data.
- Prevent cross-customer access.
- Audit credential generation, cancellation, status changes, inspection,
  remedy, and override.
- Protect carrier credentials from unauthorized reuse.

## Failure Behavior

Block or escalate when:

- order or item cannot be verified;
- quantity exceeds eligible quantity;
- a duplicate return or remedy exists;
- return credential generation fails;
- package size, weight, value, or contents violate carrier limits;
- hazardous or restricted handling is unresolved;
- customer identity or shipping address is inconsistent;
- received contents do not match authorization;
- inspection cannot determine condition;
- payment dispute or chargeback conflicts with refund; or
- refund calculation or payment method is unresolved.

## Acceptance Criteria

1. A customer can initiate a return from an exact order line in the application.
2. The system prevents authorization above the remaining eligible quantity.
3. Every business-paid package receives a unique return credential and tracking
   linked to the RMA and package sequence.
4. A printerless QR path is available when supported by the selected carrier.
5. Receiving can identify an authorized package by scanning its credential.
6. Unidentified packages enter quarantine and cannot trigger an automatic
   refund.
7. Outbound shipment, customer evidence, return shipment, and inbound
   inspection are linked.
8. The system prevents duplicate refund, credit, replacement, or repair for the
   same quantity without override.
9. Refund calculation itemizes item amount, tax, freight, discounts, fees, and
   prior remedies.
10. Customers can track return and remedy status without seeing internal or
    other-customer data.
11. Every eligibility, inspection, responsibility, and remedy decision is
    auditable.

## Out of Scope

- Guaranteeing that a carrier will refuse every customer-created shipment.
- Automatically disposing of unidentified property.
- Issuing refunds without eligibility and duplicate-remedy controls.
- Replacing applicable consumer, commercial, warranty, transportation,
  hazardous-material, tax, payment, or property law.

## References

- [Lifecycle and Sustainability Questions](../../discovery/topics/lifecycle-and-sustainability.md)
- [Freight, Shipping, and Customer Delivery Questions](../../discovery/topics/freight-shipping-and-customer-delivery.md)
- [Customer Returns and Acceptance Risk](customer-returns-and-acceptance-risk.md)
- [Shipment Evidence and Carrier Performance](shipment-evidence-and-carrier-performance.md)
- [Customer Credit and Payment Risk](customer-credit-and-payment-risk.md)
