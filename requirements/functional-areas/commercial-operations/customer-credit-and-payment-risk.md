# Customer Credit and Payment Risk

## Problem

Custom manufacturing commits material, engineering, machine capacity, outside
services, labor, and finished goods before full payment. A customer who pays
late or fails to pay can turn profitable work into a cash-flow or bad-debt
loss. Payment controls must remain fair and must distinguish true delinquency
from valid disputes or errors caused by the business.

## Objective

Use objective customer payment history and approved external business-credit
evidence to set credit limits, deposits, milestone payments, order-release
conditions, shipment holds, and collections actions while preserving audit,
privacy, correction, and human-override controls.

## Terminology

- **Payment history:** Recorded invoices, due dates, payments, disputes,
  promises, failures, credits, and write-offs.
- **Credit exposure:** Unpaid receivables plus committed material, work in
  process, finished goods, tooling, outside services, and other unrecovered
  cost.
- **Credit limit:** Maximum approved unsecured exposure.
- **Credit hold:** A restriction on defined commercial or production actions
  because approved credit conditions are not met.
- **Valid dispute:** A documented disagreement requiring review, such as
  billing error, nonconformance, missing documentation, or unauthorized work.

## Inputs

- customer legal entity and related entities;
- internal invoice and payment history;
- open receivables and aging;
- promises to pay;
- disputes and resolution;
- failed or returned payments;
- write-offs;
- order value and payment schedule;
- material, tooling, outside-service, and WIP exposure;
- available credit and current utilization;
- approved external business-credit information;
- guarantees or security where lawful;
- customer concentration;
- payment terms;
- approved overrides; and
- applicable contract and legal requirements.

## Rules

1. Base decisions on documented payment and credit evidence, not derogatory
   labels or unrelated customer characteristics.
2. Separate valid disputes and business-caused billing errors from customer
   delinquency.
3. New customers receive defined default terms until sufficient history or
   approved external evidence supports unsecured credit.
4. A credit limit applies to total exposure, not invoices alone.
5. Deposits and milestones should cover noncancelable material, special
   tooling, outside services, long-duration work, and other difficult-to-recover
   commitments.
6. The system evaluates credit before quote approval, material purchase,
   production release, major milestones, and shipment.
7. Exceeding a limit or delinquency threshold creates an explicit hold or
   approval requirement rather than a silent warning.
8. Overrides require authorized approval, amount, duration, reason, and
   compensating controls.
9. Introductory or learning-job pricing does not waive payment controls.
10. Customer payment behavior contributes to customer profitability and risk
    analysis.
11. Credit terms can improve after demonstrated payment performance and
    deteriorate after adverse evidence.
12. Do not expose credit information outside authorized roles.
13. Customers can challenge incorrect internal records and receive documented
    correction.
14. Handling of customer-owned property during nonpayment follows contract and
    law; the system must not invent lien or disposal rights.

## Assumptions

- Early customers may have no internal history.
- Payment terms vary by customer, order, material exposure, and strategic
  relationship.
- External business-credit data may be incomplete or stale.
- Sole proprietors and personal guarantees can trigger additional consumer and
  privacy law.

## Constraints

- Comply with applicable contract, collection, privacy, credit-reporting,
  antidiscrimination, bankruptcy, lien, and consumer-protection law.
- Keep customer financial and personal information encrypted and
  role-restricted.
- Preserve accounting records and audit history.
- Do not automatically reject or punish a customer solely because an invoice
  is disputed.
- Do not hold safety work or customer property in a way prohibited by law.

## Credit Decision Output

Provide:

- approved credit limit;
- current exposure;
- available credit;
- required deposit;
- milestone schedule;
- payment method;
- production and shipment conditions;
- review date;
- evidence and policy used;
- unresolved disputes;
- hold state;
- authorized overrides; and
- next required action.

## Failure Behavior

Block or escalate when:

- customer legal identity is unresolved;
- exposure cannot be calculated;
- required credit review is expired;
- requested exposure exceeds the limit;
- a required deposit or milestone is unpaid;
- a payment failed;
- a material dispute is unresolved;
- an override lacks authorization or expiration;
- external data is used without an approved basis; or
- customer-owned property rights are unclear.

## Acceptance Criteria

1. The system calculates exposure from receivables and committed unrecovered
   cost.
2. Payment performance includes due date, actual date, amount, dispute, and
   promise history.
3. Valid disputes can pause delinquency treatment while remaining visibly open.
4. Credit terms and limit changes are effective-dated and auditable.
5. An order exceeding approved exposure cannot silently proceed.
6. Deposits and milestones are linked to quote, order, material, production,
   and shipment release.
7. Every override records approver, reason, amount, controls, and expiration.
8. Incorrect records can be corrected without deleting audit history.
9. Customer profitability can include financing, collections, and bad-debt
   cost.
10. Credit information is excluded from unauthorized customer-facing and
    production views.

## Out of Scope

- Personal consumer lending.
- Fully automated legal collection action.
- Using protected characteristics or stereotypes as credit factors.
- Assuming good technical work compensates for unbounded payment risk.
- Creating legal rights to retain or dispose of property.

## References

- [Customer Credit and Payment Risk Questions](../../discovery/topics/customer-credit-and-payment-risk.md)
- [Business and Commercial Strategy Questions](../../discovery/topics/business-and-commercial-strategy.md)
- [Learning-Job Economics and Process Tuning](../manufacturing-automation/learning-job-economics-and-process-tuning.md)
