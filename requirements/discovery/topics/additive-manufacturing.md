# Additive Manufacturing Questions

## Purpose

Questions that determine whether, where, and how polymer or metal additive
manufacturing belongs in the business. The topic separates additive economics,
design rules, feedstock, safety, process qualification, postprocessing, and
quality from the broader manufacturing-process comparison.

These are discovery questions, not approved requirements. Question IDs are
permanent and canonical in this file.

## Initial Scope and Process Fit

### ADD-001 — Which additive processes and materials are initially supported?

**State:** Exploring

**First-pass answer:** Start narrowly: use qualified external powder-bed and
vat-polymerization suppliers for prototypes, fixtures, and low-risk parts, and
consider an enclosed material-extrusion cell only after demand and ventilation
are verified. Keep metal powder-bed fusion outsourced until powder safety,
thermal processing, inspection, qualification, and repeat demand are proven.
“Supported” means a frozen machine/material/parameter/postprocess route, not an
OEM material-list claim.

**Evidence:**
- [ISO/ASTM 52900 additive-manufacturing terminology](https://www.iso.org/standard/74514.html) — **Evidence class:** consensus standard; defines process families but does not establish local capability.
- [NIST qualification of AM feedstocks, machines, and processes](https://www.nist.gov/programs-projects/qualification-am-feedstocks-machines-and-processes) — **Evidence class:** official research; shows qualification is a system problem, not printer ownership.
- [NIOSH additive-manufacturing safety](https://www.cdc.gov/niosh/manufacturing/additive/index.html) — **Evidence class:** official field and laboratory guidance; identifies emissions, powder, resin, and fire hazards.

**Conflicting evidence:** Certified aerospace and medical production proves
AM can make final parts, while desktop demonstrations prove only printability;
neither establishes dependable production on this business's route.

**Confidence:** Medium; the staged scope is defensible, but demand is unknown.

**Business-specific validation:** Compare outsourced results, hazards, total
cost, yield, and customer requirements for three to five candidate part
families before equipment purchase.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is directionally correct, but much of the evidence comes from standards, medical/aerospace guidance, and benchmark programs; do not generalize across AM process families or confuse demonstrations with dependable qualified production.

### ADD-002 — How are orientation, support, anisotropy, warpage, shrinkage, porosity, and surface finish incorporated?

**State:** Exploring

**First-pass answer:** Treat each item as a quoted configuration and acceptance
variable. Record orientation and build location, identify supported and
customer-visible surfaces, use measured orientation-specific properties,
include distortion and shrink compensation, and specify allowable porosity,
roughness, and machining. A single datasheet strength or tolerance is
insufficient.

**Evidence:**
- [NIST AM Bench](https://www.nist.gov/ambench) — **Evidence class:** measured benchmark data; quantifies process, distortion, and material behavior for defined builds.
- [NIST metal-AM test artifact](https://doi.org/10.6028/jres.119.019) — **Evidence class:** empirical metrology research; demonstrates feature- and orientation-dependent measurement.
- [FDA technical considerations for AM medical devices](https://www.fda.gov/regulatory-information/search-fda-guidance-documents/technical-considerations-additive-manufactured-medical-devices) — **Evidence class:** official regulatory guidance; requires orientation, anisotropy, and postprocessing to be considered.

**Conflicting evidence:** Simulation and slicer compensation can reduce error,
but predictive output without repeated physical correlation is not production
acceptance evidence.

**Confidence:** High on the controls; low on numerical allowances until a
machine/material route is selected.

**Business-specific validation:** Print artifacts at build extremes and
orientations; measure CMM/CT, roughness, density, tensile properties, and
postprocess movement over multiple builds.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is directionally correct, but much of the evidence comes from standards, medical/aerospace guidance, and benchmark programs; do not generalize across AM process families or confuse demonstrations with dependable qualified production.

### ADD-003 — When is additive appropriate for a functional final part versus a prototype, pattern, fixture, or visual model?

**State:** Exploring

**First-pass answer:** Treat a part as functional production only when loads,
environment, life, failure consequence, material pedigree, process route,
acceptance tests, and change control are defined and demonstrated. Otherwise
label it explicitly as a prototype, visual model, pattern, or non-safety-
critical fixture; “production-grade material” is not enough.

**Evidence:**
- [NASA-STD-6030, AM requirements for spaceflight systems](https://standards.nasa.gov/sites/default/files/standards/NASA/Baseline/0/2021-04-21_nasa-std-6030-approveddocx.pdf) — **Evidence class:** official technical standard; ties final-part use to qualification and controls.
- [FDA AM technical guidance](https://www.fda.gov/regulatory-information/search-fda-guidance-documents/technical-considerations-additive-manufactured-medical-devices) — **Evidence class:** official regulatory guidance; requires application-specific validation.
- [NIST qualification of AM materials, processes, and parts](https://www.nist.gov/programs-projects/qualification-additive-manufacturing-materials-processes-and-parts) — **Evidence class:** official research; identifies remaining qualification challenges.

**Conflicting evidence:** Qualified AM final parts exist in regulated service,
but that success is route- and application-specific and does not make AM
interchangeable with wrought, cast, or molded material.

**Confidence:** High.

**Business-specific validation:** Risk-classify each part family and obtain
customer engineering approval of the AM specification, validation plan, and
permitted use.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is directionally correct, but much of the evidence comes from standards, medical/aerospace guidance, and benchmark programs; do not generalize across AM process families or confuse demonstrations with dependable qualified production.

### ADD-004 — What postprocessing is required?

**State:** Exploring

**First-pass answer:** Define the complete route before quoting. Depending on
the process it may include depowdering or resin removal, support removal,
washing and curing, sintering, stress relief or heat treatment, HIP, machining,
blasting or polishing, coating, cleaning, and final inspection. “As built” is
acceptable only when validated against the actual requirement.

**Evidence:**
- [FDA AM technical guidance](https://www.fda.gov/regulatory-information/search-fda-guidance-documents/technical-considerations-additive-manufactured-medical-devices) — **Evidence class:** official regulatory guidance; treats postprocessing as part of the manufacturing process.
- [ASTM F3301 thermal postprocessing of metal AM parts](https://www.astm.org/f3301-18a.html) — **Evidence class:** consensus standard; establishes thermal-processing controls.
- [NASA-STD-6030](https://standards.nasa.gov/sites/default/files/standards/NASA/Baseline/0/2021-04-21_nasa-std-6030-approveddocx.pdf) — **Evidence class:** official technical standard; requires a qualified end-to-end route.

**Conflicting evidence:** Vendors may market ready-to-use or near-net output,
while regulated practice treats cleaning, heat treatment, machining, and
inspection as potentially essential production steps.

**Confidence:** Medium-high.

**Business-specific validation:** Create and qualify a route card for every
supported machine/material combination and every external postprocessor.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is directionally correct, but much of the evidence comes from standards, medical/aerospace guidance, and benchmark programs; do not generalize across AM process families or confuse demonstrations with dependable qualified production.

### ADD-005 — How are build failures and material-property uncertainty priced and communicated?

**State:** Exploring

**First-pass answer:** Price from observed route-specific yield rather than a
perfect-build assumption. Include expected rebuild burden, lost feedstock and
machine time, repeated downstream work, inspection, and schedule contingency.
State whether properties are datasheet values, coupon results, statistically
substantiated allowables, or unverified estimates.

**Evidence:**
- [NIST review of AM costs](https://doi.org/10.6028/NIST.SP.1176) — **Evidence class:** official literature review; identifies yield, qualification, and postprocessing as material economic variables.
- [NIST AM qualification research](https://www.nist.gov/programs-projects/qualification-am-feedstocks-machines-and-processes) — **Evidence class:** official research; supports explicit treatment of process and property uncertainty.
- [NIST AM Bench](https://www.nist.gov/ambench) — **Evidence class:** measured benchmark program; shows why properties and distortion must be route-specific.

**Conflicting evidence:** Supplier ROI calculators often assume high yield and
utilization; public production-yield datasets remain limited and may not
transfer to a different machine, geometry, or postprocess.

**Confidence:** Medium.

**Business-specific validation:** Record each failure mode, cost, delay,
disposition, and property result; update quote contingencies from actual builds.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is directionally correct, but much of the evidence comes from standards, medical/aerospace guidance, and benchmark programs; do not generalize across AM process families or confuse demonstrations with dependable qualified production.

### ADD-006 — What dimensional and mechanical validation is required?

**State:** Exploring

**First-pass answer:** Inspect drawing-defined characteristics with calibrated
methods suited to accessible and internal geometry. Mechanical evidence must
represent the same feedstock, machine, parameters, orientation, build location,
and postprocessing; risk may require tensile, fatigue, hardness, density,
chemistry, fracture, or environmental testing. Coupons do not replace part
inspection unless correlation is demonstrated.

**Evidence:**
- [ASTM F3122 mechanical properties of metal AM materials](https://store.astm.org/standards/f3122) — **Evidence class:** consensus guide; addresses testing and reporting limitations.
- [NASA-STD-6030](https://standards.nasa.gov/sites/default/files/standards/NASA/Baseline/0/2021-04-21_nasa-std-6030-approveddocx.pdf) — **Evidence class:** official technical standard; requires risk-based qualification and acceptance.
- [NIST AM Bench](https://www.nist.gov/ambench) — **Evidence class:** measured benchmark data; supports multi-build physical validation.

**Conflicting evidence:** One tensile coupon or first-article scan is useful
development evidence but does not establish an ongoing capable process.

**Confidence:** High.

**Business-specific validation:** Build a risk-based inspection/test matrix and
run multi-build measurement-system and capability studies.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is directionally correct, but much of the evidence comes from standards, medical/aerospace guidance, and benchmark programs; do not generalize across AM process families or confuse demonstrations with dependable qualified production.

### ADD-007 — Which additive use cases provide a defensible advantage over machining, molding, casting, fabrication, or an external print service?

**State:** Exploring

**First-pass answer:** Strong candidates are low-volume or legacy parts,
complex internal geometry, part consolidation, lightweighting, customization,
tooling and patterns, or cases where conventional tooling dominates lead time.
AM is weak for simple geometry, high volume, extensive finish machining,
qualification-dominated work, or where a qualified service has better
utilization.

**Evidence:**
- [NIST AM cost review](https://doi.org/10.6028/NIST.SP.1176) — **Evidence class:** official literature review; finds economics depend strongly on geometry, volume, utilization, and downstream work.
- [ORNL additively manufactured casting molds and patterns](https://impact.ornl.gov/en/publications/additively-manufactured-single-use-molds-and-reusable-patterns-fo/) — **Evidence class:** measured pilot-production case; documents a specific tooling use rather than universal direct-part savings.
- [FAA additive-manufactured parts program](https://www.faa.gov/about/plansreports/additive-manufactured-parts) — **Evidence class:** official oversight; shows production use requires application-specific substantiation.

**Conflicting evidence:** Compelling measured cases exist, but many vendor case
studies omit qualification, utilization, failure, and postprocessing costs.

**Confidence:** Medium.

**Business-specific validation:** Compare landed cost, lead time, quality,
inventory, tooling, and qualification for real parts against conventional and
external-AM quotes.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is directionally correct, but much of the evidence comes from standards, medical/aerospace guidance, and benchmark programs; do not generalize across AM process families or confuse demonstrations with dependable qualified production.

### ADD-008 — What build envelope, feature size, tolerance, surface, quantity, and lead-time limits define the initial additive capability?

**State:** Exploring

**First-pass answer:** Publish conservative limits for each qualified
machine/material/route: usable rather than nominal build volume, validated
walls, holes, gaps and unsupported features, tolerance by size and orientation,
roughness by surface orientation, qualified nest density, quantity band, and
lead time including downstream work.

**Evidence:**
- [NIST AM Bench](https://www.nist.gov/ambench) — **Evidence class:** measured benchmark program; supports empirical envelope definition.
- [ASTM F3122](https://store.astm.org/standards/f3122) — **Evidence class:** consensus guide; requires process-relevant mechanical evidence.
- [ISO/ASTM 52901 AM purchased-part requirements](https://www.iso.org/standard/67288.html) — **Evidence class:** consensus standard; supports explicit part and process requirements.

**Conflicting evidence:** OEM build volumes and demonstration minimum features
are equipment claims, not dependable production capability; secondary
machining may explain unusually tight reported tolerances.

**Confidence:** High on the method; low until internal data exist.

**Business-specific validation:** Repeat capability artifacts across the build
volume and full downstream route, then publish limits from measured results.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is directionally correct, but much of the evidence comes from standards, medical/aerospace guidance, and benchmark programs; do not generalize across AM process families or confuse demonstrations with dependable qualified production.

## Design, Build Preparation, and Postprocessing

### ADD-009 — Which additive design rules must be checked before quoting and customer approval?

**State:** Exploring

**First-pass answer:** Require a documented DfAM review covering minimum walls,
holes and gaps; unsupported geometry; trapped powder or resin and escape paths;
support access; orientation-sensitive loads; distortion; machining allowance
and datums; inspection and cleaning access; and visible surfaces. Numerical
rules must be route-specific; a universal “45-degree rule” is not dependable.

**Evidence:**
- [ISO/ASTM 52911-2 design for laser powder-bed fusion](https://www.iso.org/standard/67289.html) — **Evidence class:** consensus design standard; provides process-specific design guidance.
- [FDA AM technical guidance](https://www.fda.gov/regulatory-information/search-fda-guidance-documents/technical-considerations-additive-manufactured-medical-devices) — **Evidence class:** official regulatory guidance; addresses internal geometry, cleaning, and validation.
- [NIST AM Bench](https://www.nist.gov/ambench) — **Evidence class:** measured research; supports validating numerical rules physically.

**Conflicting evidence:** Slicer warnings and vendor design guides are useful
screening aids, but they are not acceptance evidence for the selected route.

**Confidence:** High.

**Business-specific validation:** Establish a signed review checklist and
validate each numerical design rule through representative builds.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is directionally correct, but much of the evidence comes from standards, medical/aerospace guidance, and benchmark programs; do not generalize across AM process families or confuse demonstrations with dependable qualified production.

### ADD-010 — How are build orientation, support strategy, nesting, lot segregation, witness coupons, and downstream machining approved and recorded?

**State:** Exploring

**First-pass answer:** Release an immutable build plan/traveler containing part
revision, build coordinates and orientation, support version, nesting and part
IDs, feedstock lots and segregation, coupon type/location, machine and
parameter IDs, software versions, machining datums/allowances, approvals,
deviations, and final disposition.

**Evidence:**
- [NASA-STD-6030](https://standards.nasa.gov/sites/default/files/standards/NASA/Baseline/0/2021-04-21_nasa-std-6030-approveddocx.pdf) — **Evidence class:** official technical standard; requires controlled production records.
- [ISO/ASTM 52901](https://www.iso.org/standard/67288.html) — **Evidence class:** consensus procurement standard; structures part and process information.
- [NIST AM qualification research](https://www.nist.gov/programs-projects/qualification-am-feedstocks-machines-and-processes) — **Evidence class:** official research; supports machine, material, and parameter traceability.

**Conflicting evidence:** Native printer logs are useful but often omit the
complete engineering pedigree and may be editable or vendor-specific.

**Confidence:** High.

**Business-specific validation:** Demonstrate reconstruction of a released
build and trace every delivered serial/lot through feedstock and postprocessing.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is directionally correct, but much of the evidence comes from standards, medical/aerospace guidance, and benchmark programs; do not generalize across AM process families or confuse demonstrations with dependable qualified production.

### ADD-011 — Which postprocessing steps must be available in-house or through qualified partners before an additive capability is marketable?

**State:** Exploring

**First-pass answer:** Before marketing a route, ensure qualified access to safe
unloading and cleaning, depowdering or resin removal, support removal,
mandatory cure/sinter/thermal treatment, machining of critical features,
surface treatment, final cleaning, and calibrated inspection. Metal routes
often also need stress relief and may need HIP or NDE. Outsourcing is acceptable
only with approved suppliers and preserved traceability.

**Evidence:**
- [ASTM F3301](https://www.astm.org/f3301-18a.html) — **Evidence class:** consensus standard; controls metal-AM thermal postprocessing.
- [NASA-STD-6030](https://standards.nasa.gov/sites/default/files/standards/NASA/Baseline/0/2021-04-21_nasa-std-6030-approveddocx.pdf) — **Evidence class:** official technical standard; treats downstream work as part of the qualified route.
- [NIOSH AM safety](https://www.cdc.gov/niosh/manufacturing/additive/index.html) — **Evidence class:** official guidance; addresses safe handling and cleaning.

**Conflicting evidence:** A successful printed blank is not a marketable
finished capability if downstream quality, capacity, or safety is unproven.

**Confidence:** Medium-high.

**Business-specific validation:** Audit partner recipes, certifications,
capacity, measurement capability, nonconformance control, and change notice.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is directionally correct, but much of the evidence comes from standards, medical/aerospace guidance, and benchmark programs; do not generalize across AM process families or confuse demonstrations with dependable qualified production.

## Feedstock, Safety, and Qualification

### ADD-012 — How are additive feedstock identity, lot traceability, storage, conditioning, shelf life, contamination, recovery, and reuse controlled?

**State:** Exploring

**First-pass answer:** Assign unique lots; retain CoA, SDS, receipt, and release
records; control packaging, environment, drying/conditioning, shelf life,
dispensing, returns, sieving, reuse count and blend ratio; prevent cross-
material contamination; and quarantine questionable material. Recovered
material must not silently become equivalent to virgin feedstock.

**Evidence:**
- [ISO/ASTM 52907 metal-powder characterization](https://www.iso.org/standard/73565.html) — **Evidence class:** consensus standard; defines relevant powder characterization.
- [ISO/ASTM 52928 polymer feedstock recycling](https://www.iso.org/standard/78527.html) — **Evidence class:** consensus standard; addresses polymer-AM feedstock life-cycle controls.
- [NIST powder reuse study](https://www.nist.gov/publications/oxidation-reused-powder-bed-fusion-additive-manufacturing-ti-6al-4v-feedstock-brief) — **Evidence class:** measured research; documents oxidation and reuse-reporting limitations.

**Conflicting evidence:** Controlled studies sometimes find stable reused-
powder properties, while other data show oxidation or changed flow; results are
material-, machine-, and handling-specific.

**Confidence:** High for the control principle.

**Business-specific validation:** Run age/reuse studies tied to chemistry,
moisture, flow, particle size, build quality, and mechanical properties.

**Second-opinion review:** GPT-5.4 — Agree. These sections correctly treat AM as a tightly controlled process system: feedstock, hazards, machine/parameter qualification, software versions, operators, and postprocessing all need explicit control rather than generic printer ownership assumptions.

### ADD-013 — What powder, resin, gas, fume, fire, explosion, static, toxicity, housekeeping, and waste controls are required for each candidate process?

**State:** Exploring

**First-pass answer:** Complete a material/process hazard assessment before
acquisition. Controls may include enclosure and local exhaust, exposure
verification, HazCom, compatible PPE, grounded/bonded transfers, ignition and
combustible-dust controls, oxygen monitoring for inert gas, approved storage,
fire response, and waste characterization. Do not dry-sweep or casually vacuum
reactive powder; respirators require a compliant program.

**Evidence:**
- [NIOSH safe 3D-printing practices](https://www.cdc.gov/niosh/docs/2024-103/pdfs/2024-103.pdf) — **Evidence class:** official field and laboratory guidance; identifies emission and handling controls.
- [OSHA combustible dust](https://www.osha.gov/combustible-dust) — **Evidence class:** official safety guidance; covers fire and explosion hazards.
- [NIOSH AM topic page](https://www.cdc.gov/niosh/manufacturing/additive/index.html) — **Evidence class:** official research summary; covers polymer, resin, and powder hazards.

**Conflicting evidence:** Small printers are marketed for offices, but measured
VOC/particle emissions and resin, powder, fire, and gas hazards vary materially
by feedstock and process.

**Confidence:** High.

**Business-specific validation:** Commission ventilation and industrial-
hygiene sampling, combustible-dust/fire review, oxygen-deficiency assessment,
waste determination, and emergency-response review for each route.

**Second-opinion review:** GPT-5.4 — Agree. These sections correctly treat AM as a tightly controlled process system: feedstock, hazards, machine/parameter qualification, software versions, operators, and postprocessing all need explicit control rather than generic printer ownership assumptions.

### ADD-014 — How are machines, parameter sets, software versions, materials, operators, and postprocessing routes qualified and change-controlled?

**State:** Exploring

**First-pass answer:** Qualify the complete system: facility, individual
machine, calibration and maintenance, feedstock specification, frozen parameter
set, build-preparation and software versions, operators, postprocessing,
inspection, and part family. Predetermine requalification triggers for
relocation, critical repair, software/parameter changes, feedstock changes, and
thermal-route changes.

**Evidence:**
- [NIST feedstock/machine/process qualification](https://www.nist.gov/programs-projects/qualification-am-feedstocks-machines-and-processes) — **Evidence class:** official research; identifies system-level qualification needs.
- [NASA-STD-6030](https://standards.nasa.gov/sites/default/files/standards/NASA/Baseline/0/2021-04-21_nasa-std-6030-approveddocx.pdf) — **Evidence class:** official technical standard; provides production and change-control requirements.
- [ISO/ASTM 52926 metal-AM qualification principles](https://www.iso.org/standard/76911.html) — **Evidence class:** consensus standard; addresses machine/operator qualification.

**Conflicting evidence:** Model-based equivalence may reduce testing, but “same
printer model” or a simulation does not by itself establish equivalence.

**Confidence:** High.

**Business-specific validation:** Develop installation, operational, and
performance qualification protocols, periodic artifacts, and approval roles.

**Second-opinion review:** GPT-5.4 — Agree. These sections correctly treat AM as a tightly controlled process system: feedstock, hazards, machine/parameter qualification, software versions, operators, and postprocessing all need explicit control rather than generic printer ownership assumptions.

### ADD-015 — Which in-process monitoring, coupon testing, dimensional inspection, nondestructive evaluation, and destructive testing are required by application risk?

**State:** Exploring

**First-pass answer:** Apply a defect-mode and consequence-based matrix. Every
build retains defined logs and receives visual/dimensional acceptance; higher-
risk applications add representative coupons, density/chemistry/mechanical
tests, validated monitoring, and NDE selected for expected flaws and geometry.
Destructive qualification remains necessary where correlation is unproven.

**Evidence:**
- [NIST review of metal-AM monitoring and NDE](https://doi.org/10.6028/NIST.IR.8538) — **Evidence class:** official technical review; documents capability and remaining gaps.
- [NASA-STD-6030](https://standards.nasa.gov/sites/default/files/standards/NASA/Baseline/0/2021-04-21_nasa-std-6030-approveddocx.pdf) — **Evidence class:** official technical standard; requires risk-based verification.
- [ORNL AI defect-detection report](https://www.ornl.gov/news/ai-technology-boosts-confidence-cuts-costs-metal-3d-printing) — **Evidence class:** prototype research; promising, but not general production acceptance proof.

**Conflicting evidence:** AI monitoring can detect selected defects in research
trials, but cannot replace final acceptance without validated detection
probability and property correlation.

**Confidence:** High on the risk-based approach.

**Business-specific validation:** Build designed defects and correlate sensor
signals with CT, metallography, coupons, and part tests, including false rates.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The risk-based inspection logic is sound, and the answer correctly refuses to treat monitoring or AI demonstrations as qualified acceptance proof; contrary literature still shows in-situ monitoring and AI models do not generalize reliably enough to replace separate validation for critical production.

## Economics and Capability Ownership

### ADD-016 — What complete cost model captures preparation, machine time, warmup, cooldown, inert gas, feedstock loss, labor, failed builds, postprocessing, inspection, maintenance, and working capital?

**State:** Exploring

**First-pass answer:** Use build-level activity-based costing allocated to
sellable parts. Include engineering, setup, warmup/cooldown, machine/facility
time, gas and utilities, virgin and lost feedstock, labor, supports, expected
failure burden, downstream work, testing, maintenance/calibration, software,
consumables, depreciation, qualification amortization, queues, inventory, and
working capital.

**Evidence:**
- [NIST AM cost review](https://doi.org/10.6028/NIST.SP.1176) — **Evidence class:** official literature review; identifies the major cost drivers and uncertainty.
- [NIST AM-part qualification research](https://www.nist.gov/programs-projects/qualification-additive-manufacturing-materials-processes-and-parts) — **Evidence class:** official research; supports including qualification and test burden.
- [ORNL AM casting-tooling case](https://impact.ornl.gov/en/publications/additively-manufactured-single-use-molds-and-reusable-patterns-fo/) — **Evidence class:** measured pilot case; demonstrates application-specific, not universal, economics.

**Conflicting evidence:** AM may remove tooling and reduce startup time, but
utilization, downstream work, qualification, and failure assumptions can
reverse the result; supplier calculators are scenarios, not audited economics.

**Confidence:** Medium.

**Business-specific validation:** Time-study representative builds and
reconcile predicted versus actual total cost and cash-cycle duration.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The economics and ownership logic is appropriately conservative, but public evidence does not support universal utilization or break-even thresholds; actual make-buy decisions remain route-, yield-, postprocessing-, and demand-specific.

### ADD-017 — Which utilization, yield, repeat demand, qualification, and postprocessing thresholds justify owning an additive process instead of outsourcing it?

**State:** Exploring

**First-pass answer:** No universal utilization threshold is defensible. Own a
route only when stable demand and contribution cover equipment, facility,
safety, labor, maintenance, qualification, and capacity risk, and when internal
yield, downstream capability, inspection, and delivery outperform qualified
suppliers. Volatile mix or expensive qualification favors outsourcing.

**Evidence:**
- [NIST AM cost review](https://doi.org/10.6028/NIST.SP.1176) — **Evidence class:** official literature review; shows sensitivity to utilization, yield, and postprocessing.
- [NIST AM qualification research](https://www.nist.gov/programs-projects/qualification-am-feedstocks-machines-and-processes) — **Evidence class:** official research; identifies ownership's qualification burden.
- [ISO/ASTM 52926](https://www.iso.org/standard/76911.html) — **Evidence class:** consensus standard; supports site-specific qualification rather than plug-and-play ownership.

**Conflicting evidence:** Vendor break-even claims commonly assume favorable
yield and utilization; outsourcing adds markup and queue risk but spreads
capital, safety, expertise, and qualification over more work.

**Confidence:** Medium-low until demand exists.

**Business-specific validation:** Build a 12–24-month demand model with supplier
quotes and sensitivities for yield, labor, financing, obsolescence, and delays.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The economics and ownership logic is appropriately conservative, but public evidence does not support universal utilization or break-even thresholds; actual make-buy decisions remain route-, yield-, postprocessing-, and demand-specific.

### ADD-018 — How will actual build success, property variation, lead time, total cost, and customer acceptance determine whether the additive offering expands, changes, or stops?

**State:** Exploring

**First-pass answer:** Use predeclared part-family gates for first-pass yield,
property and dimensional distributions, escapes, lead-time attainment, total
margin, repeat demand, returns, safety events, and change burden. Expand only
after repeatable multi-build/multi-lot evidence; change correctable routes; stop
when safety, acceptance, quality, or sustained economics fail.

**Evidence:**
- [NIST AM Bench higher-TRL initiative](https://www.nist.gov/ambench/new-am-bench-focus-higher-technology-readiness-level-applications) — **Evidence class:** official research program; explicitly distinguishes simple benchmarks from representative industrial applications.
- [NIST AM-part qualification research](https://www.nist.gov/programs-projects/qualification-additive-manufacturing-materials-processes-and-parts) — **Evidence class:** official research; supports evidence-based qualification gates.
- [FDA AM technical guidance](https://www.fda.gov/regulatory-information/search-fda-guidance-documents/technical-considerations-additive-manufactured-medical-devices) — **Evidence class:** official regulatory guidance; supports production monitoring and controlled change.

**Conflicting evidence:** Successful prototypes, simulations, and AI
demonstrations show technical potential, not stable production or market fit.

**Confidence:** Medium-high for governance; thresholds are business-specific.

**Business-specific validation:** Set thresholds before launch, review monthly
by part family, and require independent customer/quality approval before scope
expansion.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The economics and ownership logic is appropriately conservative, but public evidence does not support universal utilization or break-even thresholds; actual make-buy decisions remain route-, yield-, postprocessing-, and demand-specific.

## Related Topics

- [Manufacturing Processes and Quality](manufacturing-processes-and-quality.md)
- [Standards, Certifications, and Quality Systems](standards-certifications-and-quality-systems.md)
- [Facility, Site, Utilities, and Layout](facility-site-utilities-and-layout.md)
- [Lifecycle and Sustainability](lifecycle-and-sustainability.md)
- [Suppliers and Procurement](suppliers-and-procurement.md)
