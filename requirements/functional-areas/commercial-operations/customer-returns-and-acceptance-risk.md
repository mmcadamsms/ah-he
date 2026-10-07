# Customer Returns and Acceptance Risk

## Problem

Returned parts create visible costs such as freight, inspection, rework,
replacement, and refunds, plus hidden costs such as disrupted schedules,
lost capacity, engineering time, customer downtime, and damaged trust. Return
behavior can also reveal that requirements, tolerances, surface finishes,
cosmetic standards, measurement methods, or packaging expectations were not
defined clearly enough before manufacture.

## Objective

Track every customer return by quantity, reason, responsibility, cost, root
cause, disposition, and corrective action. Use relevant history to clarify
future requirements, select inspection and approval controls, and estimate
expected cost without substituting vague customer labels for explicit scope.

## Terminology

- **Return:** A delivered part or lot sent back, rejected, disputed, or
  requiring replacement, rework, refund, or formal review.
- **Acceptance criteria:** Approved dimensional, functional, material,
  cosmetic, finish, inspection, documentation, and packaging conditions.
- **Customer acceptance profile:** Evidence-based history of how a customer
  specifies, measures, documents, and accepts work.
- **Cost of return:** Direct and indirect economic effect of the return.
- **Responsibility classification:** Documented attribution based on evidence,
  including unresolved or shared responsibility.

## Inputs

- customer, part, family, revision, order, and shipment;
- quantity shipped and returned;
- return authorization and dates;
- customer complaint and evidence;
- approved requirements and assumptions;
- drawings, CAD, tolerances, finish, and cosmetic standards;
- inspection plan and results;
- material and outside-process records;
- packaging and freight records;
- customer and business measurement methods;
- root-cause investigation;
- disposition;
- direct and indirect cost;
- corrective action;
- prior comparable returns; and
- customer approval, dispute, and communication history.

## Return Reason and Responsibility

Support at least:

- dimensional nonconformance;
- geometric-tolerance nonconformance;
- material or certification issue;
- surface finish;
- cosmetic appearance;
- burr, edge, cleanliness, or contamination;
- functional or fit failure;
- design error;
- manufacturing-process error;
- outside-supplier error;
- incomplete or ambiguous requirement;
- undocumented expectation;
- approved deviation misunderstood or exceeded;
- incorrect quantity or identity;
- shipping or packaging damage;
- installation or application issue;
- changed operating conditions;
- customer misuse;
- no fault found;
- shared responsibility; and
- unresolved.

Do not force responsibility when evidence is insufficient.

## Cost Capture

Track:

- return freight;
- receiving and quarantine;
- investigation and inspection;
- engineering and customer communication;
- sorting;
- rework;
- remake;
- replacement material;
- tooling and consumables;
- outside processing;
- packaging and reshipment;
- refund or credit;
- customer chargeback;
- scrap and disposal;
- machine and schedule disruption;
- expedite;
- lost productive capacity;
- customer downtime or contractual consequence;
- collections or payment delay;
- corrective action; and
- expected lost future business.

Separate measured cost from estimated consequential impact.

## Customer Acceptance Profile

The profile may record:

- typical tolerance and finish requirements;
- interpretation of unspecified dimensions and surfaces;
- cosmetic sensitivity;
- accepted samples and visual standards;
- edge-break and deburring expectation;
- measurement equipment and method;
- datum and setup practice;
- sampling and reporting;
- first-article or source-inspection expectations;
- deviation approval behavior;
- documentation and traceability;
- packaging;
- return frequency and substantiated reasons; and
- communication and resolution preferences.

The profile is a requirements and process aid, not a personality score.

## Quote and Order Rules

1. Clarify tolerance, finish, cosmetic, inspection, documentation, packaging,
   and acceptance criteria before binding quote or production release.
2. Use customer and part history only when relevant to the new work.
3. Price stricter requirements through explicit operations and controls.
4. Expected return cost may inform contingency and margin, but the quote must
   not hide an unexplained customer-specific penalty.
5. Business-caused historical defects must trigger corrective action and
   process improvement rather than simply higher customer pricing.
6. Repeated ambiguity requires a requirements-review or approval step.
7. Conflicting customer and business measurement methods must be reconciled
   before production.
8. High-risk work may require first article, source inspection, retained sample,
   100-percent inspection, photographs, or signed visual standard.
9. Approved defaults apply only when the customer has accepted them.
10. A customer return does not automatically prove business nonconformance.

## Privacy and Fairness

1. Restrict return and customer-profile data by business need.
2. Use objective job and acceptance evidence.
3. Do not infer quality expectations from protected characteristics,
   geography, industry stereotype, or personal opinion.
4. Allow correction of inaccurate customer history without deleting audit
   evidence.
5. Do not expose another customer's return, pricing, geometry, or acceptance
   data.

## Failure Behavior

Block or escalate quoting or release when:

- critical acceptance criteria are missing;
- tolerance or finish cannot be interpreted;
- cosmetic expectation lacks an approved standard;
- measurement methods materially conflict;
- return history indicates an unresolved systemic issue;
- required first-article or approval evidence is unavailable;
- expected return cost cannot be bounded for a high-risk order; or
- responsibility from a prior return remains disputed and directly affects
  the new order.

## Acceptance Criteria

1. Every return links to customer, order, part, revision, shipment, and quantity.
2. Return reason, responsibility, disposition, direct cost, and status are
   explicit.
3. The system reports return rate by customer, part family, process, machine,
   supplier, and reason without treating all returns as equivalent.
4. Quote review can show relevant prior return evidence and the explicit
   controls added to the new job.
5. Tolerance, finish, cosmetic, measurement, inspection, and packaging
   expectations are independently represented.
6. Business-caused returns link to corrective action and affected similar work.
7. Customer-history corrections preserve who changed what and why.
8. Return cost feeds customer profitability and historical-part actuals.
9. A customer-specific inspection cost can be traced to an accepted requirement
   or relevant evidence.
10. The system distinguishes a stricter requirement from an unsupported
    reputation-based surcharge.

## Out of Scope

- Automatically blaming customers for disputed returns.
- Treating all strict acceptance requirements as unreasonable.
- Raising price instead of correcting recurring business defects.
- Using private customer history outside authorized commercial and quality
  decisions.
- Replacing qualified metrology, quality, engineering, or legal review.

## References

- [Lifecycle and Sustainability Questions](../../discovery/topics/lifecycle-and-sustainability.md)
- [Manufacturing Processes and Quality Questions](../../discovery/topics/manufacturing-processes-and-quality.md)
- [Business and Commercial Strategy Questions](../../discovery/topics/business-and-commercial-strategy.md)
- [Historical-Part Analog Estimating](../manufacturing-automation/historical-part-analog-estimating.md)
- [Customer Credit and Payment Risk](customer-credit-and-payment-risk.md)
