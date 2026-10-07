# Historical-Part Analog Estimating

## Problem

Quoting a new custom part without using prior production evidence repeats past
estimating work and ignores the difference between planned and actual
manufacturing performance. Raw historical prices are also misleading when
material, labor, energy, machine capability, quantity, process, or market
conditions have changed.

## Objective

Maintain a privacy-controlled digital inventory of previously manufactured
parts and actual production outcomes. Use explainable, normalized historical
analogs to improve cost estimates, lead-time estimates, process planning,
commercial pricing decisions, and confidence ranges for new customer requests.

## Terminology

- **Historical part record:** The revisioned digital record of a prior
  manufactured part and its planning, execution, cost, quality, and delivery
  outcomes.
- **Analog:** A historical part or job considered comparable to a new request.
- **Similarity evidence:** The specific attributes that make an analog
  relevant.
- **Actual:** Measured outcome rather than quoted, planned, or simulated value.
- **Normalized actual:** A historical actual adjusted to the conditions of the
  new estimate.
- **Manufacturing cost:** Expected economic cost to produce and deliver the
  work.
- **Commercial price:** The amount offered to the customer after applying
  margin, risk, market, relationship, capacity, and strategic decisions.

## Historical Part Record

Where available and permitted, retain:

- customer and project access scope;
- part and drawing revision;
- CAD-derived geometry descriptors;
- units, bounds, volume, mass, and material removal;
- recognized features and complexity;
- tolerances, finish, and inspection requirements;
- material grade and specification;
- raw-stock form and dimensions;
- quantity and batch structure;
- physical setups, orientations, workholding, and work offsets;
- operation sequence;
- machines;
- tools, holders, inserts, fixtures, and consumables;
- NC/G-code metadata, line count, operation count, tool changes, motion, and
  supported complexity measures;
- simulated and actual cycle time;
- attended and unattended labor;
- setup, programming, inspection, packaging, and handling time;
- scrap, rework, downtime, maintenance, and quality results;
- outside processing;
- material, tooling, labor, machine, energy, freight, supplier, and overhead
  cost;
- quoted and actual lead time;
- customer price, discount, and realized margin;
- images, video, logs, and inspection evidence according to retention policy;
- estimate-versus-actual differences; and
- lessons and improvements.

## Inputs for a New Estimate

- submitted CAD and drawings;
- revision and units;
- requested material;
- quantity;
- tolerance, finish, inspection, certification, and delivery requirements;
- customer-provided constraints;
- current material, labor, energy, freight, supplier, and overhead rates;
- available machines, tools, fixtures, people, and capacity; and
- historical part records the requesting user is authorized to use.

## Analog Selection Rules

1. Search exact part and revision history first.
2. Search same-family or parameterized variants next.
3. Search broader analogs using geometry, feature, material, process, size,
   tolerance, quantity, setup, tooling, program, and actual-performance
   attributes.
4. Rank analogs with an explainable similarity score.
5. Show which attributes match, differ, or are missing.
6. A visually similar envelope is insufficient when features, tolerances,
   material, workholding, or process differ materially.
7. Avoid double-counting several records from the same unchanged process as
   independent evidence.
8. Prefer measured actuals over original estimates.
9. Flag analogs affected by abnormal downtime, expedite, scrap, learning,
   customer-supplied material, or unusual commercial terms.
10. Low similarity or poor historical data widens uncertainty and requires
    qualified-human review.

## Normalization Rules

Historical actuals must be adjusted for:

- current material and stock price;
- current labor and burden;
- current energy and consumables;
- current freight;
- current supplier and outside-process rates;
- inflation where direct current rates are unavailable;
- batch quantity and learning effects;
- machine, control, tooling, and fixture differences;
- process improvements;
- current utilization and queue;
- facility and geography;
- current inspection and certification scope;
- changed delivery expectation; and
- known abnormal events.

The estimate must retain both historical actual and normalized value so the
adjustment is auditable.

## Cost and Price Separation

1. Calculate manufacturing cost independently of historical customer price.
2. Treat prior customer price and realized margin as commercial evidence, not
   physical production cost.
3. Do not infer that a low historical price is repeatable or desirable.
4. Do not expose one customer's identity, geometry, price, margin, or
   confidential process information to another customer.
5. Customer-facing explanations use the current job's cost drivers and allowed
   aggregated evidence, not confidential analog details.
6. Strategic discounts and learning investment are represented separately
   under the learning-job policy.

## Privacy and Access Rules

1. Enforce customer and project isolation.
2. Record the contractual and consent basis for retaining and reusing data.
3. Support restrictions on raw-data reuse while permitting only approved
   derived or aggregated metrics.
4. Exclude export-controlled, classified, regulated, or specially restricted
   records unless the new project and user are authorized.
5. Propagate deletion and retention rules to raw records, embeddings, indexes,
   derived features, caches, and backups according to policy.
6. Audit every use of restricted historical records in an estimate.
7. Never train or improve an external model with customer data without the
   required permission and controls.

## Estimate Output

Provide:

- recommended process and assumptions;
- selected analogs;
- similarity explanation;
- historical actual ranges;
- normalization adjustments;
- estimated material, setup, cycle, attended labor, tooling, outside process,
  inspection, freight, and lead time;
- uncertainty range;
- main cost and schedule drivers;
- missing information;
- abnormal historical records excluded or down-weighted;
- required human review;
- manufacturing cost;
- target margin and commercial adjustments; and
- proposed customer price.

## Failure Behavior

Do not present a high-confidence analog estimate when:

- no sufficiently similar records exist;
- geometry or units are invalid;
- critical requirements are missing;
- access to relevant history is prohibited;
- actual-cost data is incomplete;
- historical jobs are dominated by abnormal events;
- normalization cannot be bounded;
- machine or process capability differs materially; or
- customer price is being used as a substitute for cost.

Return an explicit low-confidence or manual-estimate state with the missing
evidence.

## Acceptance Criteria

1. The system retrieves exact prior revisions before broader analogs.
2. Every analog result explains similarity and important differences.
3. Historical actuals and normalized values remain separately visible.
4. Current material and other volatile input rates can replace historical
   rates without rewriting the original record.
5. Estimate uncertainty increases when similarity or actual-data quality
   decreases.
6. Manufacturing cost, margin decision, and customer price are distinct output
   fields.
7. An authorized reviewer can include, exclude, or reweight an analog with a
   recorded reason.
8. Restricted customer information is not exposed across customer boundaries.
9. Completed work feeds actual cost, cycle, labor, quality, and delivery back
   into the historical inventory.
10. Estimate-versus-actual performance can be measured by analog method,
    process, part family, machine, and estimator version.

## Out of Scope

- Assuming geometric resemblance proves manufacturing similarity.
- Copying a prior price without current cost analysis.
- Sharing customer-confidential part geometry or commercial terms.
- Fully automatic binding quotes for unsupported or low-confidence work.
- Treating historical practice as proof that a process is safe or optimal.

## References

- [Draft Business Vision and Phased Plan](../../discovery/business-vision-and-phased-plan.md)
- [Business and Commercial Strategy Questions](../../discovery/topics/business-and-commercial-strategy.md)
- [Data, AI, and Platform Questions](../../discovery/topics/data-ai-and-platform.md)
- [Manufacturing Processes and Quality Questions](../../discovery/topics/manufacturing-processes-and-quality.md)
- [Learning-Job Economics and Process Tuning](learning-job-economics-and-process-tuning.md)
- [Bottleneck-Driven Automation Improvement](bottleneck-driven-automation-improvement.md)
