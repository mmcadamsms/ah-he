# Suppliers and Procurement Questions

## Purpose

Questions about supplier qualification, subcontracting, purchased components,
outside processes, ownership, and incoming quality.

These are discovery questions, not approved requirements. Question IDs are permanent and canonical in this file.

## Suppliers and External Manufacturing Partners

### SUP-001 — How are suppliers qualified?

**State:** Exploring

**First-pass answer:** Use risk-based qualification: confirm legal identity and
financial viability; review quality, delivery, capacity, security, and
traceability systems; audit critical processes; verify certifications at the
issuer; and approve a representative sample, first article, or trial order.
Approval should be limited to named commodities, processes, sites, and
customer/regulatory scopes, with periodic scorecard review and requalification.

**Evidence:**
- [21 CFR 820.50, Purchasing controls](https://www.law.cornell.edu/cfr/text/21/820.50) — **Evidence class:** primary official evidence. The regulation requires documented supplier evaluation, selection based on ability to meet specified requirements, defined controls, and an approved-supplier record.
- [IAQG Supply Chain Management Handbook](https://iaqg.org/tools/scmh/) — **Evidence class:** standards-body practitioner guidance. Aerospace quality experts publish supplier-selection, capability, product-conformity, and on-time-delivery guidance, but it is guidance rather than a substitute for contract requirements.

**Conflicting evidence:** Certifications can reduce audit effort, but neither
source treats a certificate alone as proof of actual capability or performance.

**Confidence:** High on the qualification framework; the required depth is
commodity-, customer-, and risk-specific.

**Business-specific validation:** Define supplier risk tiers, required
certifications, trial/first-article criteria, audit checklist, scorecard
thresholds, and requalification cadence with quality and procurement leads.

**Second-opinion review:** GPT-5.4 — Agree. Risk-based supplier qualification with scope-limited approval is well supported by the cited controls.

### SUP-002 — Which work may be subcontracted without separate customer approval?

**State:** Exploring

**First-pass answer:** Only work that the customer contract, drawing,
specification, purchase order, and applicable regulation do not reserve for an
approved source or prior consent should be subcontracted without separate
approval. Never assume approval for special processes, design work,
customer-furnished property, controlled technical data, key characteristics, or
work whose source is named; flow all applicable requirements to every tier.

**Evidence:**
- [FAR 52.244-2, Subcontracts](https://www.acquisition.gov/far/52.244-2) — **Evidence class:** primary official evidence. Federal contracts can require written contracting-officer consent for specified subcontracts and require advance disclosure of source, scope, type, and price.
- [21 CFR 820.50, Purchasing controls](https://www.law.cornell.edu/cfr/text/21/820.50) — **Evidence class:** primary official evidence. A manufacturer remains responsible for controls over purchased products and services and for clearly stated purchasing requirements.

**Conflicting evidence:** Commercial contracts often permit ordinary
subcontracting, while regulated, defense, aerospace, and customer-source-
controlled work can require explicit approval; the governing contract controls.

**Confidence:** High that there is no universal permission rule.

**Business-specific validation:** Counsel and contract administration must
build a clause matrix showing customer-consent, approved-source, flowdown, and
notification requirements for each order before release.

**Second-opinion review:** GPT-5.4 — Agree. Subcontract permission is contract- and regulation-specific; there is no safe general assumption.

### SUP-003 — How are supplier capacity, quality, lead time, security, and financial risk evaluated?

**State:** Exploring

**First-pass answer:** Evaluate each dimension separately and combine them in a
risk register, not one opaque score. Use audited capacity and load data,
on-time-in-full and defect history, process-capability/escape evidence,
cybersecurity controls, concentration and geographic exposure, financial and
insurance checks, recovery plans, and trial performance; monitor leading
indicators and require mitigation for single-source critical items.

**Evidence:**
- [NIST SP 800-161 Rev. 1, Cybersecurity Supply Chain Risk Management](https://doi.org/10.6028/NIST.SP.800-161r1-upd1) — **Evidence class:** primary official guidance. NIST calls for multilevel supplier risk assessments, policies, plans, monitoring, and mitigation across the acquisition life cycle.
- [21 CFR 820.50, Purchasing controls](https://www.law.cornell.edu/cfr/text/21/820.50) — **Evidence class:** primary official evidence. Required supplier controls are based on documented evaluation results, supporting risk-tiered rather than uniform oversight.

**Conflicting evidence:** Supplier self-reports and certifications are cheaper
than audits and operating data but can overstate capacity or control maturity;
use them as screening evidence, not final proof.

**Confidence:** High on the dimensions and evidence types; low on any universal
weighting formula.

**Business-specific validation:** Collect actual quote-to-ship, rejection,
capacity, expedite, incident, and financial data from candidate suppliers and
set tier-specific thresholds.

**Second-opinion review:** GPT-5.4 — Agree. Separate risk dimensions and ongoing monitoring are better supported than one opaque supplier score.

### SUP-004 — How is customer intellectual property shared with the minimum necessary supplier scope?

**State:** Exploring

**First-pass answer:** Share a supplier-specific, revision-controlled work
package containing only the geometry, tolerances, process requirements, and
acceptance data needed for that scope. Enforce named-user access, least
privilege, encryption, download/export restrictions, subcontractor approval,
retention/destruction terms, access logs, and prompt revocation; do not send the
complete customer data set when a derived drawing or model will suffice.

**Evidence:**
- [NIST SP 800-171 Rev. 3](https://csrc.nist.gov/pubs/sp/800/171/r3/final) — **Evidence class:** primary official guidance. Its control families include access control, identification, audit, media protection, communications protection, physical protection, and supply-chain risk management for controlled information.
- [FAR 52.227-14, Rights in Data—General](https://www.acquisition.gov/far/52.227-14) — **Evidence class:** primary official evidence. The clause distinguishes technical data, limited-rights data, restricted software, and form-fit-function data, showing that rights and permitted disclosure must be classified contractually.

**Conflicting evidence:** Sending full native models may reduce supplier errors,
but it increases unnecessary disclosure; the minimum package must still be
sufficient to manufacture and inspect conforming work.

**Confidence:** High on least-privilege handling; export-control and ownership
rules are contract- and data-specific.

**Business-specific validation:** Have counsel/security classify customer data,
approve supplier-access templates and transfer tools, and test a revoke-and-
destroy workflow with one supplier.

**Second-opinion review:** GPT-5.4 — Agree. Minimum-necessary, revision-controlled data sharing fits both the security and contract-rights evidence.

### SUP-005 — How are supplier quotes normalized and compared?

**State:** Exploring

**First-pass answer:** Normalize to the same revision, quantity breaks,
incoterms/delivery point, currency/date, lead-time definition, material source,
process and inspection scope, tooling/NRE, minimums, payment terms, warranty,
freight/tax/duty, expedite assumptions, scrap/yield, and included documentation.
Compare risk-adjusted landed cost and capacity commitment, not unit price alone,
and record every exclusion and exception.

**Evidence:**
- [FAR 52.244-2, Subcontracts](https://www.acquisition.gov/far/52.244-2) — **Evidence class:** primary official evidence. Its consent package identifies source, subcontract type and price, negotiation rationale, pricing-data reliance, and significant differences between objectives and negotiated results.
- [Treasury Regulation 26 CFR 1.263(a)-2](https://www.law.cornell.edu/cfr/text/26/1.263%28a%29-2) — **Evidence class:** primary official evidence. Acquisition-related transaction costs may have to be capitalized, reinforcing that comparison must include costs beyond sticker price.

**Conflicting evidence:** The lowest normalized expected cost may not be the
best choice when delivery or quality tails threaten customer commitments.

**Confidence:** High on the normalization fields; risk premiums require
business judgment and actual performance data.

**Business-specific validation:** Re-price three recent purchases in a common
bid-comparison template and compare predicted landed cost with invoices,
receiving effort, defects, and expedites.

**Second-opinion review:** GPT-5.4 — Agree. Quotes should be normalized to landed, risk-adjusted scope rather than compared on unit price alone.

### SUP-006 — How are supplier substitutions and process changes approved?

**State:** Exploring

**First-pass answer:** Require advance written notification, affected part and
revision, reason, risk analysis, changed material/process/site/subtier, updated
certificates and validation, and a proposed effective lot/date. Quality,
engineering, procurement, and the customer when contractually required should
approve before shipment; segregate and identify the first changed lot and
repeat first-article or process qualification proportional to risk.

**Evidence:**
- [21 CFR 820.50, Purchasing controls](https://www.law.cornell.edu/cfr/text/21/820.50) — **Evidence class:** primary official evidence. Purchasing data should, where possible, require suppliers to notify the manufacturer of changes so the manufacturer can determine whether requirements are affected.
- [IAQG Supply Chain Management Handbook](https://iaqg.org/tools/scmh/) — **Evidence class:** standards-body practitioner guidance. The handbook addresses supply-chain product conformity and controlled supplier processes for aerospace organizations.

**Conflicting evidence:** Routine commodity substitutions may need only
procurement approval, while special-process, material, design, site, or subtier
changes can require complete customer reapproval.

**Confidence:** High on the controlled-change workflow.

**Business-specific validation:** Build a change-classification matrix from
customer clauses and run it against likely material, processor, and site-change
examples.

**Second-opinion review:** GPT-5.4 — Agree. Material, process, site, and subtier changes need advance notice, risk review, and sometimes customer approval.

### SUP-007 — Who owns scrap, overruns, fixtures, and special tooling held by suppliers?

**State:** Exploring

**First-pass answer:** Ownership must be explicit in the purchase order or
tooling/property agreement. Record title, identification, permitted use,
custody, maintenance, inventory, loss liability, scrap authorization,
overrun/disposition rights, return timing, and whether the supplier may assert a
lien. Customer- or company-owned tooling and scrap must remain segregated from
supplier-owned property.

**Evidence:**
- [FAR 52.245-1, Government Property](https://www.acquisition.gov/far/52.245-1) — **Evidence class:** primary official evidence. It defines material, equipment, production scrap, special tooling, records, custody, loss, subcontractor possession, and disposition across the property life cycle.
- [FAR 52.227-14, Rights in Data—General](https://www.acquisition.gov/far/52.227-14) — **Evidence class:** primary official evidence. It demonstrates that ownership/use rights for physical tooling and the data needed to reproduce it are distinct and both require contractual treatment.

**Conflicting evidence:** Trade custom may let a supplier retain ordinary
scrap or overrun, but it cannot safely override customer-property, export,
confidentiality, or written ownership terms.

**Confidence:** High that contract language and property records are required;
the commercial allocation is negotiable.

**Business-specific validation:** Counsel should approve standard property,
scrap, lien-waiver, audit, and return clauses and reconcile them with customer
flowdowns.

**Second-opinion review:** GPT-5.4 — Agree. Ownership, lien, use, and disposition rights must be explicit in contract records.

### SUP-008 — What incoming inspection is required?

**State:** Exploring

**First-pass answer:** Use a documented risk-based receiving plan: verify
identity, quantity, revision, damage, certificates and traceability on every
receipt; inspect or test critical characteristics at a statistically or
contractually justified frequency; quarantine pending acceptance; and tighten,
reduce, or use supplier-source inspection based on demonstrated performance.
Never rely on sampling where the contract requires 100% verification.

**Evidence:**
- [21 CFR 820.80, Receiving acceptance activities](https://www.law.cornell.edu/cfr/text/21/820.80) — **Evidence class:** primary official evidence. Incoming product must be inspected, tested, or otherwise verified, with acceptance/rejection documented and product controlled until release.
- [FAR 52.246-2, Inspection of Supplies—Fixed-Price](https://www.acquisition.gov/far/52.246-2) — **Evidence class:** primary official evidence. It requires an inspection system and records and preserves rights to inspect, test, reject, correct, and disclose prior rejection.

**Conflicting evidence:** Supplier certification and reduced inspection can
lower cost, but neither source supports eliminating verification without
evidence that controls remain effective.

**Confidence:** High on the framework; sampling levels and characteristics are
part-, process-, and customer-specific.

**Business-specific validation:** Quality engineering must classify purchased
items by failure severity and supplier performance, then validate plans with
receiving data and measurement-system capability.

**Second-opinion review:** GPT-5.4 — Agree. Incoming inspection should be risk-based but must still honor any mandatory verification requirement.

## Used Machinery and Capital Equipment

### SUP-009 — Where can the business find a good market for second-hand machinery?

**State:** Exploring

**Original wording:** "where do we find a good market for second-hand machinery"

**First-pass answer:** Use several channels because no one market covers the
required equipment or risk level: OEM/distributor certified-used programs and
reputable dealers for supportable production machines; Machinery Dealers
National Association or AMT networks and direct manufacturer sales for
inspected equipment; industrial auctions/liquidations for price discovery and
opportunistic buys; and GSA/educational surplus and broad marketplaces for
support equipment. Search strongest US inventories first in the Great Lakes and
Midwest, then the Northeast industrial corridor, Southeast automotive/aerospace
states, Texas, and Southern California, while pricing transport to the actual
facility.

**Evidence:**
- [Surplus Record marketplace](https://surplusrecord.com/) — **Evidence class:** transaction-market evidence. The long-running marketplace exposes current dealer listings across CNC, fabrication, metrology, forklifts, compressors, and support equipment, but asking prices are not verified sale prices.
- [GSA Auctions](https://gsaauctions.gov/) — **Evidence class:** primary official transaction evidence. The federal platform provides a distinct government-surplus channel with auction-specific inspection, payment, and removal conditions.
- [AMT member directory](https://www.amtonline.org/membership/directory) — **Evidence class:** industry-association evidence. It helps identify machine-tool builders, distributors, integrators, and service firms rather than relying on anonymous listings.
- [TRUMPF pre-owned machines](https://www.trumpf.com/en_INT/products/services/services-machines-systems-and-lasers/pre-owned-machines/) — **Evidence class:** OEM supplier evidence. TRUMPF states that its pre-owned machines receive quality checks, OEM parts, setup, and commissioning, illustrating the added scope available from an OEM channel.

**Conflicting evidence:** Auctions can produce lower hammer prices, while
dealer/OEM channels can reduce inspection, missing-component, and support risk;
published asking prices do not prove either channel has lower installed cost.

**Confidence:** Medium; the channels and regional pattern are credible, but
current inventory and completed-sale prices change continuously.

**Business-specific validation:** Issue a live search specification to at least
three dealers, two OEM/distributors, two auction platforms, and one surplus
channel for each near-term machine class and compare inspected, landed offers.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The channel mix is reasonable, but regional market strength and price advantage are dynamic and need live searches.

### SUP-010 — Which types of equipment are reasonable to buy used?

**State:** Exploring

**First-pass answer:** Good used candidates are inspectable, mechanically
understandable, supported, non-proprietary assets: conventional three-axis
mills/lathes, saws, welders, forklifts, cranes/hoists, compressors, packaging,
storage, and standard workholding. Five-axis, mill-turn, lasers, precision
press brakes, CMMs, high-speed spindles, and highly automated cells can still be
good buys only with powered demonstrations, geometry/process-capability tests,
software/license transfer, service history, current OEM support, and a priced
repair/upgrade plan.

**Evidence:**
- [Rockwell Product Compatibility and Download Center](https://www.rockwellautomation.com/en-us/support/product/product-compatibility-migration.html) — **Evidence class:** OEM lifecycle evidence. It exposes lifecycle status, firmware/software, compatibility, dependencies, and migration information, illustrating the obsolescence risk in control-dependent assets.
- [Renishaw, Ballbar testing explained](https://www.renishaw.com/en/ballbar-testing-explained--6818) — **Evidence class:** supplier technical evidence. Measured circular tests reveal positioning errors caused by geometry, control behavior, and wear rather than relying on appearance or hours alone.
- [TRUMPF pre-owned machines](https://www.trumpf.com/en_INT/products/services/services-machines-systems-and-lasers/pre-owned-machines/) — **Evidence class:** OEM supplier evidence. Its inspection, OEM-parts, setup, and commissioning claims show why complex equipment should be evaluated differently when OEM refurbishment is available.

**Conflicting evidence:** Age alone is a poor rejection rule: a simple older
machine with support and verified capability may be lower risk than a newer,
crashed, proprietary, or unsupported asset.

**Confidence:** Medium-high on the risk ranking; individual condition dominates
equipment category.

**Business-specific validation:** Define required tolerances, duty cycle,
automation, safety, energy, service response, and expected utilization for each
equipment class before screening actual serial numbers.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The category ranking is directionally right, but serial-number condition and supportability outweigh class averages.

### SUP-011 — What inspection is required before purchasing used machinery?

**State:** Exploring

**First-pass answer:** Before an irrevocable bid, verify seller/title/liens,
serial/year/options/licenses, hours and service/crash records; inspect structure,
ways, screws, spindle, rotary axes, tool changer, fluids, electrical cabinet,
safety and utilities; obtain parameter and program backups; run warm-up,
vibration/runout, laser/ballbar or equivalent geometry tests, and a
representative loaded cut; and price every exception. Use an independent OEM-
qualified technician and metrology provider for critical machines.

**Evidence:**
- [Renishaw, Ballbar testing explained](https://www.renishaw.com/en/ballbar-testing-explained--6818) — **Evidence class:** supplier technical evidence. Ballbar measurement converts machine geometry, servo/control behavior, and wear into diagnosable accuracy evidence.
- [OSHA 29 CFR 1910.212, General requirements for all machines](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.212) — **Evidence class:** primary official evidence. Used equipment still must protect operators from point-of-operation, rotating-part, chip, and spark hazards; a productive test does not establish safety compliance.
- [MachinesUsed auction terms](https://www.machinesused.com/terms-and-conditions) — **Evidence class:** commercial transaction evidence. Its lots are sold as-is, where-is, with bidder inspection responsibility and no warranty, demonstrating why pre-bid inspection must precede purchase.

**Conflicting evidence:** Seller videos and hour meters are useful screening
evidence but cannot replace independent, under-power inspection and measured
capability because records may be incomplete and idle machines may test
differently after warm-up/load.

**Confidence:** High on the inspection scope; exact tests depend on machine
type and required capability.

**Business-specific validation:** Have the intended OEM/service and metrology
providers create a machine-class checklist and quote an inspection for each
shortlisted serial number.

**Second-opinion review:** GPT-5.4 — Agree. Independent powered inspection plus safety, title, and service verification is necessary before buying used machinery.

### SUP-012 — What is the complete installed cost of a used machine?

**State:** Exploring

**First-pass answer:** Use the question's full bridge from purchase price to
production-ready cost: premium, tax/fees, inspection, decommissioning,
cleaning, rigging/loading, freight/insurance, unloading/placement, foundation
and facility work, utilities/networking, software, repairs, calibration and
acceptance, tooling/workholding/probes, training, early downtime, and
contingency, less included accessories and realistic recovery value. Compare
cash timing and expected cost under low/base/high repair cases, not one point
estimate.

**Evidence:**
- [Treasury Regulation 26 CFR 1.263(a)-2](https://www.law.cornell.edu/cfr/text/26/1.263%28a%29-2) — **Evidence class:** primary official evidence. It requires capitalization of acquisition and applicable transaction costs for tangible property, confirming that accounting basis extends beyond invoice price.
- [MachinesUsed auction terms](https://www.machinesused.com/terms-and-conditions) — **Evidence class:** commercial transaction evidence. The buyer bears removal, disconnection, liability, rigging insurance, taxes, and as-is condition risk—material installed-cost items often absent from the hammer price.

**Conflicting evidence:** Tax basis, management's total cost of ownership, and
cash paid to become production-ready overlap but are not identical; a CPA must
classify costs while operations estimates economic exposure.

**Confidence:** High on cost categories; dollar amounts cannot be estimated
reliably without a machine, origin, destination, and facility.

**Business-specific validation:** Obtain written machine, rigger, freight,
facility-trade, OEM repair, metrology, tooling, training, and insurance quotes,
plus CPA review, before approval.

**Second-opinion review:** GPT-5.4 — Agree. Installed cost must include transaction, removal, facility, repair, and startup costs beyond invoice price.

### SUP-013 — How should auction purchases differ from dealer purchases?

**State:** Exploring

**First-pass answer:** Treat an auction as a short-deadline, largely
nonrecourse acquisition: inspect before bidding, pre-approve a hard all-in bid
cap, verify title and included components, line up approved rigging/freight,
and reserve cash for immediate payment and repairs. A dealer/OEM purchase
should be compared on demonstrated operation, disclosure, refurbishment,
warranty, financing, installation, training, and post-sale support; negotiate
acceptance and holdback where possible.

**Evidence:**
- [MachinesUsed auction terms](https://www.machinesused.com/terms-and-conditions) — **Evidence class:** commercial transaction evidence. The terms require rapid payment/removal, sell as-is/where-is, place inspection and description verification on bidders, and require insured movers.
- [TRUMPF pre-owned machines](https://www.trumpf.com/en_INT/products/services/services-machines-systems-and-lasers/pre-owned-machines/) — **Evidence class:** OEM supplier evidence. TRUMPF describes quality checks, original parts, setup, and commissioning, concrete services that can distinguish an OEM/dealer purchase from an as-is auction.

**Conflicting evidence:** Dealers do not automatically warrant or accurately
represent every machine, and some auctions permit powered inspection; compare
the actual contract and access, not the channel label.

**Confidence:** High on the default risk distinction.

**Business-specific validation:** Counsel and operations should approve
separate auction and dealer checklists, bid authority, minimum inspection, and
contract exceptions.

**Second-opinion review:** GPT-5.4 — Agree. Auction purchases deserve a stricter bid-cap and inspection posture than dealer or OEM deals.

### SUP-014 — Which regions have the strongest used-equipment ecosystems?

**State:** Exploring

**First-pass answer:** For broad US machine-tool and fabrication searches,
prioritize the Great Lakes/Midwest manufacturing belt (especially Michigan,
Ohio, Illinois, Indiana, and Wisconsin), the Pennsylvania–New York–New England
corridor, the Carolinas–Georgia–Tennessee automotive/aerospace belt, Texas, and
Southern California. “Strongest” must be scored by relevant installed base,
live listings/auctions, OEM/distributor coverage, independent technicians,
riggers, rebuilders, controls/metrology support, parts access, and freight to
the candidate site—not listing count alone.

**Evidence:**
- [US Census County Business Patterns data](https://www.census.gov/programs-surveys/cbp/data.html) — **Evidence class:** primary official data. Annual establishment and employment data can map machine shops and relevant manufacturing/service NAICS by state and county.
- [AMT member directory](https://www.amtonline.org/membership/directory) — **Evidence class:** industry-association evidence. Builder, distributor, integrator, and service membership provides a second indicator of local support density.
- [Surplus Record marketplace](https://surplusrecord.com/) — **Evidence class:** transaction-market evidence. Current dealer and machine locations reveal available inventory, though listings are not completed-sale or service-quality data.

**Conflicting evidence:** High manufacturing density often improves selection
and service, but a distant region can lose its advantage after removal, freight,
permits, taxes, and downtime.

**Confidence:** Medium; the named regions are plausible first-pass priorities,
but a current machine-class map has not yet been run.

**Business-specific validation:** Query CBP and live listings by required NAICS
and machine class, then map qualified service/rigging firms and obtain landed
quotes to each candidate facility.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The named regions are plausible first-pass clusters, but they are heuristics until mapped to the target machine class and site.

### SUP-015 — When is a used machine better than a new, leased, or outsourced capability?

**State:** Exploring

**First-pass answer:** Used is better when measured capability and remaining
support life meet the process need, production can start sooner, and
risk-adjusted installed cost plus downtime is lower than alternatives over the
expected holding period. New/lease wins when warranty, uptime, financing,
automation, energy, accuracy, or service response is decisive; outsourcing wins
while demand, process maturity, staffing, or utilization is too uncertain to
cover ownership's fixed cost.

**Evidence:**
- [Treasury Regulation 26 CFR 1.263(a)-2](https://www.law.cornell.edu/cfr/text/26/1.263%28a%29-2) — **Evidence class:** primary official evidence. Acquisition and transaction costs belong in the capital comparison, not only purchase price.
- [Rockwell Product Compatibility and Download Center](https://www.rockwellautomation.com/en-us/support/product/product-compatibility-migration.html) — **Evidence class:** OEM lifecycle evidence. Control lifecycle, firmware, compatibility, and migration dependencies are measurable ownership risks for used automation.
- [MachinesUsed auction terms](https://www.machinesused.com/terms-and-conditions) — **Evidence class:** commercial transaction evidence. As-is condition and buyer-paid removal demonstrate the repair and execution risk that must be priced against new, leased, and outsourced options.

**Conflicting evidence:** A low-price used asset can have the best expected
return at high utilization but the worst downside if a rare control or spindle
failure causes prolonged downtime.

**Confidence:** High on the decision framework; no option can be chosen without
actual demand and quotes.

**Business-specific validation:** Build a five-year low/base/high cash-flow and
capacity model using inspected used offers, new/lease proposals, and
supplier quotes, with downtime and ramp assumptions signed off by operations.

**Second-opinion review:** GPT-5.4 — Agree. Used versus new, lease, or outsource should be decided on risk-adjusted time-to-capability and life-cycle cost.

### SUP-016 — What acceptance test must a used machine pass?

**State:** Exploring

**First-pass answer:** Define pass/fail limits before purchase for safety,
documentation, warm-up, spindle/runout/vibration, axis geometry and squareness,
positioning accuracy and repeatability, backlash, rotary performance, tool
change, probing, fluids/chip systems, alarms, and every purchased option. Then
run a representative material/toolpath test and demonstrate dimensional
capability and surface finish under stable production conditions; record
baseline results for later maintenance.

**Evidence:**
- [Renishaw, Ballbar testing explained](https://www.renishaw.com/en/ballbar-testing-explained--6818) — **Evidence class:** supplier technical evidence. Ballbar tests objectively identify geometry, servo, control, and wear-related deviations.
- [ISO 230-2, Test code for machine tools—positioning accuracy and repeatability](https://www.iso.org/standard/50919.html) — **Evidence class:** consensus standard. It provides the recognized framework for measuring numerically controlled axis positioning and repeatability, although the full standard is paid.
- [OSHA 29 CFR 1910.212](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.212) — **Evidence class:** primary official evidence. Guarding and operator protection are acceptance gates, not optional repairs after production starts.

**Conflicting evidence:** A standard geometry test can pass while the intended
process fails because of thermal behavior, fixturing, tooling, dynamics, or
software; both machine-level and representative-part tests are needed.

**Confidence:** High on the structure; numerical limits are process-specific.

**Business-specific validation:** A metrology specialist, OEM technician, and
manufacturing engineer must turn target parts/tolerances into a signed site-
acceptance protocol and gauge-capable test piece.

**Second-opinion review:** GPT-5.4 — Agree. Predefined safety, geometry, and representative-part acceptance tests are the right gate.

### SUP-017 — How should used-equipment opportunities be tracked?

**State:** Exploring

**First-pass answer:** Maintain one comparable record per serial number with
seller/source, location, machine identity/configuration/options, age/hours,
support status, inspection evidence, known defects, included/missing items,
asking/hammer price, comparable listings or sales, total installed-cost range,
removal deadline, target capability/phase, utilization and risk-adjusted return,
decision owner, and purchase/reject/defer reason. Snapshot listings and terms
because auction pages and availability disappear.

**Evidence:**
- [Surplus Record marketplace](https://surplusrecord.com/) — **Evidence class:** transaction-market evidence. Its changing multi-dealer listings provide configuration, location, and asking-price observations suitable for a comparable-opportunity log, not proof of final sale value.
- [MachinesUsed auction terms](https://www.machinesused.com/terms-and-conditions) — **Evidence class:** commercial transaction evidence. Payment, removal, condition, tax, export, mover-insurance, and description-risk terms show why opportunity records need deadlines and non-price obligations.

**Conflicting evidence:** Asking prices are readily available but can be stale
or aspirational; auction hammer prices are more transactional but may omit
premium, removal, condition, and included accessories.

**Confidence:** High on the record design.

**Business-specific validation:** Enter at least 20 live opportunities across
three target machine classes and test whether reviewers can reproduce the
shortlist and installed-cost comparison from stored evidence.

**Second-opinion review:** GPT-5.4 — Agree. One serial-number opportunity record with snapshots and an installed-cost range is the right control.

### SUP-018 — Which used machines are good candidates for retrofit automation?

**State:** Exploring

**First-pass answer:** Choose machines that already hold the required process
capability and have supported controls, complete electrical documentation,
spare or documented I/O, safe machine-ready/cycle/alarm/door interfaces,
repeatable workholding, probing/tool monitoring, reliable chip/coolant
management, accessible loading, predictable restart, and available parts.
Reject candidates where the retrofit must compensate for mechanical
instability, obsolete controls, undocumented safety circuits, or frequent
manual recovery; the machine should remain useful in manual mode.

**Evidence:**
- [OPC UA for Machine Tools](https://reference.opcfoundation.org/specs/OPC-40501-1) — **Evidence class:** consensus interoperability standard. It defines a common information model for machine-tool status and integration, supporting preference for documented, supported interfaces.
- [Rockwell Product Compatibility and Download Center](https://www.rockwellautomation.com/en-us/support/product/product-compatibility-migration.html) — **Evidence class:** OEM lifecycle evidence. Lifecycle, compatibility, firmware, and dependencies can be checked before committing to a control-dependent retrofit.
- [OSHA 29 CFR 1910.147, Lockout/tagout](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** primary official evidence. Automated modifications must preserve hazardous-energy control and cannot bypass safety for convenience.

**Conflicting evidence:** External I/O gateways can connect older controls, but
connectivity alone does not establish safe command authority, alarm recovery,
mechanical repeatability, or supportability.

**Confidence:** High on screening criteria; retrofit economics are serial-
number and application specific.

**Business-specific validation:** Have a qualified machine builder/integrator
perform an I/O, safety, cycle-recovery, process-capability, and parts-support
assessment and provide a fixed-scope retrofit quote.

**Second-opinion review:** GPT-5.4 — Agree. Retrofit candidates should already be mechanically capable and safely integrable before automation is added.

### SUP-019 — How does candidate-facility location affect freight, rigging, crane, permit, escort, insurance, unloading, and commissioning cost for key equipment?

**State:** Exploring

**First-pass answer:** Location can change installed cost materially through
distance and lane availability, state size/weight permits and escorts, bridge
and route surveys, border/port handling, local rigger/crane competition,
insurance requirements, dock/door/floor access, utility readiness, union or
site rules, and OEM technician travel. Model each origin–facility pair rather
than applying a national percentage to purchase price.

**Evidence:**
- [FHWA compilation of state truck size and weight laws](https://ops.fhwa.dot.gov/freight/policy/rpt_congress/truck_sw_laws/app_a.htm) — **Evidence class:** primary official evidence. State limits, exemptions, and permit regimes differ, so identical machines can face different legal routes and escort requirements.
- [Treasury Regulation 26 CFR 1.263(a)-2](https://www.law.cornell.edu/cfr/text/26/1.263%28a%29-2) — **Evidence class:** primary official evidence. Acquisition-related transaction costs can be capital costs, reinforcing the need to compare facilities on delivered/installed rather than invoice cost.

**Conflicting evidence:** Central industrial regions may have higher labor rates
but lower mobilization, travel, and service-delay costs because qualified firms
are nearby.

**Confidence:** High on cost drivers; no reliable amount exists without routes,
dimensions, weights, and building surveys.

**Business-specific validation:** Quote representative moves from actual
equipment origins to every finalist site with riggers, heavy-haul carriers,
crane firms, insurers, facility trades, and commissioning providers.

**Second-opinion review:** GPT-5.4 — Agree. Facility location can materially change installed cost, so route- and site-specific quotes are required.

### SUP-020 — Which machine sizes, weights, origins, routes, and building constraints can cause transport and installation to rival or exceed purchase price?

**State:** Exploring

**First-pass answer:** The highest-risk cases are low-priced but heavy or
oversize machines, tall gantries/lasers, long-bed lathes and brakes, large
rotaries, machines needing disassembly, imported or remote assets, and moves
requiring permits/escorts, route surveys, cranes, roof/wall removal, pits or
foundations, transformer/service upgrades, or environmental remediation.
Risk rises sharply when door, aisle, floor, ceiling, dock, crane, or utility
capacity is unknown before bidding.

**Evidence:**
- [FHWA compilation of state truck size and weight laws](https://ops.fhwa.dot.gov/freight/policy/rpt_congress/truck_sw_laws/app_a.htm) — **Evidence class:** primary official evidence. It documents differing state axle, gross-weight, and permit constraints that drive route and equipment complexity.
- [MachinesUsed auction terms](https://www.machinesused.com/terms-and-conditions) — **Evidence class:** commercial transaction evidence. Buyers bear disconnection, removal, mover insurance, deadlines, liability, and abandonment/storage risk, which can exceed a low hammer price.

**Conflicting evidence:** A heavy machine near a competent rigger with direct
grade-level access may be cheaper to install than a lighter machine in a
constrained urban or upper-floor site.

**Confidence:** High on risk factors; thresholds require route and building
engineering.

**Business-specific validation:** Record shipping dimensions/weight and center
of gravity, then complete route, structural, access, utility, and hazardous-
material surveys before setting a bid cap.

**Second-opinion review:** GPT-5.4 — Agree. Oversize, heavy, remote, or building-constrained moves can dominate purchase price, so engineering surveys are necessary.

### SUP-021 — Which regions provide enough machine dealers, auctions, OEM support, riggers, technicians, rebuilders, and spare parts to lower installed cost and downtime?

**State:** Exploring

**First-pass answer:** Start with the Great Lakes/Midwest, Northeast industrial
corridor, Southeast automotive/aerospace belt, Texas, and Southern California,
but qualify the exact metro for the selected OEMs and machine classes. A region
counts as sufficient only if it has competing local providers for sales,
rigging, controls, mechanical/spindle repair, metrology, automation, and parts,
with documented response times—not merely many factories.

**Evidence:**
- [US Census County Business Patterns data](https://www.census.gov/programs-surveys/cbp/data.html) — **Evidence class:** primary official data. Establishment and employment counts can identify concentrations of machine shops and relevant industrial services at county level.
- [AMT member directory](https://www.amtonline.org/membership/directory) — **Evidence class:** industry-association evidence. It provides a current cross-check for builders, distributors, integrators, and service organizations.

**Conflicting evidence:** Dense ecosystems can reduce response time and
mobilization cost, but high demand can also raise labor rates and extend service
queues.

**Confidence:** Medium until the target OEM/machine mix and facility shortlist
are known.

**Business-specific validation:** For each finalist metro, obtain named
providers, references, labor/travel rates, parts stocking, and emergency
response commitments for the intended fleet.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Dense industrial regions usually help, but actual service availability must be verified for the chosen OEMs and metro.

### SUP-022 — How many representative equipment moves should be quoted before comparing candidate regions?

**State:** Exploring

**First-pass answer:** Quote at least three independent providers for each of at
least three representative move archetypes—ordinary machine, heavy/oversize
machine, and complex crane/foundation/utility installation—per finalist region.
Add origins and quotes until the low–high spread stops changing the facility
ranking; for a near-term named acquisition, quote the actual serial-number move
rather than relying on archetypes.

**Evidence:**
- [FHWA compilation of state truck size and weight laws](https://ops.fhwa.dot.gov/freight/policy/rpt_congress/truck_sw_laws/app_a.htm) — **Evidence class:** primary official evidence. Route legality and permits vary by state and equipment geometry, so one generic freight quote cannot represent a region.
- [MachinesUsed auction terms](https://www.machinesused.com/terms-and-conditions) — **Evidence class:** commercial transaction evidence. Buyer-paid disconnection, removal deadlines, liability, and insured-mover requirements make rigger scope and availability a major quote variable.

**Conflicting evidence:** Three quotes is a procurement heuristic, not a
statistical guarantee; more samples are justified when routes, machine sizes,
or building conditions vary widely.

**Confidence:** Medium; no authoritative universal sample count was found.

**Business-specific validation:** Run the proposed quote matrix and perform a
sensitivity check showing whether another high/low observation could reverse
the region decision.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The proposed quote matrix is a prudent heuristic, not an authoritative minimum sample size.

### SUP-023 — When does a distant low-price machine remain economical after inspection, decommissioning, loading, freight, unloading, repairs, and acceptance?

**State:** Exploring

**First-pass answer:** It remains economical only when its conservative
production-ready cost—including travel/inspection, premium/tax, removal,
freight/insurance, facility work, repairs, acceptance, delay, and a condition-
appropriate contingency—is below the best local/new/lease/outsource alternative
by enough to compensate for weaker recourse and longer recovery. Require the
decision to survive a high-cost case and a delayed-start case.

**Evidence:**
- [Treasury Regulation 26 CFR 1.263(a)-2](https://www.law.cornell.edu/cfr/text/26/1.263%28a%29-2) — **Evidence class:** primary official evidence. Acquisition-related transaction costs belong in the economic and accounting analysis rather than being ignored after purchase.
- [MachinesUsed auction terms](https://www.machinesused.com/terms-and-conditions) — **Evidence class:** commercial transaction evidence. As-is condition, buyer inspection, rapid payment/removal, and buyer-paid rigging transfer substantial tail risk to the purchaser.

**Conflicting evidence:** Distant markets can offer rare configurations and
larger discounts, but a single major spindle/control/geometry failure can erase
the apparent saving.

**Confidence:** High on the decision rule; the contingency percentage cannot
be universal.

**Business-specific validation:** Obtain binding quotes and independent test
results for the exact machine, then compare low/base/high net present cost and
time-to-capability against local and non-ownership alternatives.

**Second-opinion review:** GPT-5.4 — Agree. A distant low-price machine only wins if it survives conservative installed-cost and delayed-start scenarios.

### SUP-024 — How should future relocation, resale, and equipment-removal cost influence the initial facility and machine choice?

**State:** Exploring

**First-pass answer:** Treat exit as part of the initial design. Favor
grade-level access, adequate doors/aisles/floors, removable utility drops,
documented lift points and shipping fixtures, modular foundations and
automation, transferable software, supported controls, common voltage, and
machines with liquid secondary markets. Include expected decommissioning,
restoration, freight, broker/auction fees, downtime, and residual value in the
site and machine life-cycle model.

**Evidence:**
- [Treasury Regulation 26 CFR 1.263(a)-2](https://www.law.cornell.edu/cfr/text/26/1.263%28a%29-2) — **Evidence class:** primary official evidence. Tangible-property acquisition and transaction costs demonstrate why physical configuration and future capital transactions must be evaluated beyond purchase price.
- [MachinesUsed auction terms](https://www.machinesused.com/terms-and-conditions) — **Evidence class:** commercial transaction evidence. Removal, disconnection, insured movers, deadlines, and abandonment/storage consequences show the real cost of an exit-constrained site.
- [Surplus Record marketplace](https://surplusrecord.com/) — **Evidence class:** transaction-market evidence. Current listing depth by make/model provides a practical, though imperfect, indicator of resale liquidity and parts/service familiarity.

**Conflicting evidence:** Permanent pits, foundations, or tightly integrated
automation may maximize present productivity even while increasing exit cost;
the trade is justified only by expected operating value.

**Confidence:** High on the life-cycle principle; residual values and move
probabilities are uncertain.

**Business-specific validation:** Have a rigger and facility engineer review
each finalist layout and machine for initial and reverse moves, and obtain
broker/dealer residual-value ranges under several holding periods.

**Second-opinion review:** GPT-5.4 — Agree. Exit, relocation, and resale economics belong in the initial facility and machine decision.
