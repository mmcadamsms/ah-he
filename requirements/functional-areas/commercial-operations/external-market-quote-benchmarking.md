# External Market Quote Benchmarking

## Problem

Digital manufacturing platforms and external shops provide customers with
rapid alternatives. Their quotes can reveal market price and lead-time signals,
but direct comparison is misleading when material, geometry, tolerance,
finish, quantity, inspection, shipping, tax, or acceptance scope differs.
Unauthorized automation or customer-file uploads can also violate terms,
confidentiality, intellectual-property, or export-control obligations.

## Objective

Maintain a controlled library of company-owned benchmark parts, collect public
or authorized external quotes under documented terms, normalize delivered
scope, and use the results as explainable market evidence for estimate review,
commercial pricing, make-versus-buy decisions, and capability investment.

## Inputs

- owned benchmark geometry and revision hash;
- provider and current capability;
- provider terms and permitted access method;
- material, quantity, tolerance, finish, inspection, and documentation;
- destination;
- quote, production lead time, shipping, tax, and expiration;
- design warnings and exclusions;
- current internal estimate;
- internal actual cost where available;
- target margin;
- customer-value and risk factors; and
- actual order and delivery results where intentionally procured.

## Rules

1. Use company-owned synthetic benchmark files by default.
2. Do not upload customer or third-party CAD without explicit authority and
   confirmation that external processing is permitted.
3. Do not upload export-controlled, classified, or specially restricted data
   to an unapproved provider.
4. Follow provider terms, account rules, file licenses, privacy policies, and
   access restrictions.
5. Do not scrape, reverse engineer, or automate a quote interface without
   explicit permission or a documented public API license.
6. Record the exact benchmark revision and quote conditions.
7. Normalize price for material, quantity, tolerance, finish, inspection,
   packaging, shipping, tax, and customer-side work.
8. Distinguish instant, formal, custom, accepted-order, and delivered prices.
9. Record promotions separately and do not assume they recur.
10. Treat an external quote as market evidence, not the provider's production
    cost or proof of delivered capability.
11. Keep internal cost, target margin, strategic adjustment, and external
    benchmark as separate fields.
12. Investigate material deviations rather than automatically matching the
    lowest quote.
13. Benchmarking may support outsourcing analysis but does not bypass customer
    approval, supplier qualification, confidentiality, or quality controls.
14. Do not expose competitor-derived data in customer-facing output unless
    authorized and appropriate.

## Benchmark Part Library

Each benchmark has:

- immutable ID;
- owned source and usage rights;
- CAD revision and hash;
- units;
- geometry and feature summary;
- intended process;
- material;
- quantity set;
- tolerance and finish;
- inspection and documentation;
- packaging and destination;
- expected difficulty; and
- reason it represents a target market.

## Quote Record

Store:

- provider;
- access method;
- account;
- timestamp;
- terms version;
- benchmark ID and revision;
- quote type;
- scope;
- design feedback;
- line and total price;
- nonrecurring charges;
- shipping and tax;
- lead time and ship date;
- expiration;
- promotion;
- evidence artifact where permitted;
- normalization adjustments; and
- reviewer.

## Output

Provide:

- internal expected manufacturing cost;
- internal target price;
- normalized external quote range;
- important scope differences;
- quote age and uncertainty;
- internal-versus-market variance;
- likely cause;
- make, buy, improve, or decline options;
- customer-value differentiators; and
- required review.

## Failure Behavior

Block or mark unusable when:

- file ownership or permission is unresolved;
- provider terms do not permit the intended access;
- quote conditions are incomplete;
- scope cannot be normalized;
- the quote is expired;
- the provider has not accepted the geometry;
- export or confidentiality restrictions apply;
- an automated interface lacks authorization; or
- a promotion dominates the apparent result.

## Acceptance Criteria

1. Every benchmark uses a traceable company-owned or explicitly authorized
   file.
2. Every quote stores material, quantity, tolerance, finish, lead time,
   shipping, tax, and timestamp.
3. External price and internal cost remain separate.
4. A comparison identifies every material scope mismatch.
5. The system can show how normalization changes the raw quote.
6. Unauthorized automated access is blocked.
7. Customer files cannot be sent to an external provider without approved
   authority.
8. Quote age and confidence are visible.
9. Repeated benchmarks can show price and capability changes over time.
10. A market outlier triggers review rather than automatic price matching.

## Out of Scope

- Copying provider software or proprietary functionality.
- Unauthorized scraping or reverse engineering.
- Treating one provider as the entire market.
- Uploading customer IP for curiosity or benchmarking.
- Guaranteeing that an instant quote will become an accepted or delivered
  order.

## References

- [External Pricing and Competitive Benchmarking Questions](../../discovery/topics/external-pricing-and-competitive-benchmarking.md)
- [Business and Commercial Strategy Questions](../../discovery/topics/business-and-commercial-strategy.md)
- [Historical-Part Analog Estimating](../manufacturing-automation/historical-part-analog-estimating.md)
- [Suppliers and Procurement Questions](../../discovery/topics/suppliers-and-procurement.md)
- SendCutSend CNC machining:
  <https://sendcutsend.com/services/cnc-machining/>
- SendCutSend terms:
  <https://store.sendcutsend.com/pages/terms-of-service>
