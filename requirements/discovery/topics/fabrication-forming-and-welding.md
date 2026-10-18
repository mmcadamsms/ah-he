# Fabrication, Forming, and Welding Questions

## Purpose

Questions that determine whether and how sheet, plate, tube, structural,
welding, and formed-product capabilities belong in the business. This topic
separates fabrication economics and process control from support-equipment
questions.

These are discovery questions, not approved requirements. Question IDs are
permanent and canonical in this file.

## Capability and Product Envelope

### FAB-001 — Which sheet, plate, tube, sawing, cutting, forming, welding, and fabrication processes are initially supported?

**State:** Exploring

**First-pass answer:** Begin as a high-mix, low-volume integrator: partner-
sourced 2D laser, plasma, or waterjet cutting and press-brake forming; in-house
sawing, drilling/tapping, deburring, fit-up, fixturing, and manual GMAW/GTAW
for non-code, non-safety-critical work. Defer owned laser/tube-laser, heavy
plate, robotic, structural/code, and unattended processes until recurring
demand and process reliability are measured.

**Evidence:**
- [OSHA 29 CFR 1910.212 machine guarding](https://www.ecfr.gov/current/title-29/subtitle-B/chapter-XVII/part-1910/subpart-O/section-1910.212) — **Evidence class:** primary regulation; guarding affects every owned saw, brake, and cutter.
- [OSHA 29 CFR 1910.252 welding and cutting](https://www.ecfr.gov/current/title-29/subtitle-B/chapter-XVII/part-1910/subpart-Q/section-1910.252) — **Evidence class:** primary regulation; establishes fire, cylinder, ventilation, and operating controls.
- [NIST MEP Supplier Scouting](https://www.nist.gov/mep/supply-chain/supplier-scouting) — **Evidence class:** official delivered program; supports qualified domestic partner discovery, not local economics.

**Conflicting evidence:** Owning a laser may improve speed and learning, but
without recurring downstream bending, deburring, inspection, and handling
demand it can become an expensive underused island.

**Confidence:** Medium-low.

**Business-specific validation:** Route 30–50 representative RFQs through
partners and record process mix, lead time, quality, contribution, and load for
each candidate machine.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The staged scope is prudent, but public sources do not prove that this exact product mix or process envelope will win locally; repeat demand, code requirements, alloy mix, and partner performance still need direct validation.

### FAB-002 — What material, thickness, tube profile, part size, bend, weld, tolerance, quantity, and cosmetic limits define the initial fabrication envelope?

**State:** Exploring

**First-pass answer:** Start with simple industrial brackets, guards, trays,
panels, frames, fixtures, and short-run weldments in common mild steel and 304
stainless; use partners for 5052 aluminum cut/bend work. Treat aluminum
welding, 6061-T6 bending, exposed architectural cosmetics, thick plate,
pressure boundaries, structural members, and safety-critical work as exceptions
until qualified. Publish limits only from demonstrated material/tool/machine
combinations, not generic maxima.

**Evidence:**
- [AWS D9.1 sheet-metal welding scope](https://store.accuristech.com/standards/aws-d9-1-d9-1m?product_id=3114316) — **Evidence class:** consensus-standard description; distinguishes procedure, qualification, workmanship, and exclusions.
- [Hypertherm technical document library](https://www.hypertherm.com/resources/documents-library/) — **Evidence class:** OEM manuals; model-specific cut charts are stronger than generic process claims.
- [Autodesk sheet-metal rule reference](https://help.autodesk.com/cloudhelp/ENU/Fusion-Sheet-Metal/files/SM-RULES-REF.htm) — **Evidence class:** OEM software documentation; shows thickness, radius, K-factor, gap, and relief must be explicit.

**Conflicting evidence:** A broad catalog can win more quotes, but uncommon
alloys, cosmetic surfaces, and near-limit geometry create disproportionate
setup, scrap, and supplier risk.

**Confidence:** Medium on the method; low on final numerical limits.

**Business-specific validation:** Build a 20–30-part benchmark library and
require quotes, coupons, first articles, and inspection before publishing each
material/process envelope.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The staged scope is prudent, but public sources do not prove that this exact product mix or process envelope will win locally; repeat demand, code requirements, alloy mix, and partner performance still need direct validation.

### FAB-003 — Which fabricated products and end markets offer enough repeat demand, value density, and differentiation to justify the capability?

**State:** Exploring

**First-pass answer:** Test recurring industrial work first: machine guards and
panels, brackets, trays, fixtures, carts, racks, repair frames, automation-
integrator weldments, and local job-shop overflow. These reward rapid
clarification, mixed processes, and repeat revisions. Decorative consumer,
railing, structural, defense, food, and transportation work may be attractive
but often adds field, code, documentation, or liability burdens.

**Evidence:**
- [BEA Input-Output Accounts](https://www.bea.gov/data/industries/input-output-accounts-data) — **Evidence class:** primary government data; identifies industries consuming fabricated-metal output.
- [Census Annual Integrated Economic Survey](https://www.census.gov/programs-surveys/aies.html) — **Evidence class:** primary government survey; provides sector revenue, expense, payroll, and employment context.
- [Federal Reserve industrial production, fabricated metals](https://www.federalreserve.gov/releases/g17/SandDesc/table1.06.htm) — **Evidence class:** primary government production data; shows subsector scale, not local demand.

**Conflicting evidence:** Larger regulated markets can offer bigger orders, but
qualifications, insurance, installation, and documentation may outweigh volume
for an entry-stage shop.

**Confidence:** Medium-low; macrodata cannot prove a local niche.

**Business-specific validation:** Collect at least 50 qualified opportunities
and rank repeat rate, quote effort, value per shop-hour, liability, and paid
repeat conversion.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The staged scope is prudent, but public sources do not prove that this exact product mix or process envelope will win locally; repeat demand, code requirements, alloy mix, and partner performance still need direct validation.

## Planning and Process Control

### FAB-004 — How are sheet and tube nesting, grain direction, common-line cutting, kerf, lead-ins, tabs, small features, tip-up risk, remnants, and mixed-customer material segregation controlled?

**State:** Exploring

**First-pass answer:** Release every nest as a controlled record linked to
drawing revision, customer, material/heat, thickness, finish side, grain,
machine, cut condition, and remnant ID. Lock grain-sensitive rotation; use
machine/material-specific kerf and lead-ins; permit common-line cutting only
after edge and thermal trials; use validated sequencing and tabs for tip-up
risk; and segregate customer-owned and traceability-controlled material.

**Evidence:**
- [Autodesk CAMduct common-line procedure](https://help.autodesk.com/cloudhelp/ENU/Fabrication-UsersGuide/files/GUID-2C2968CC-36EA-4A3B-A883-2E15AC00E101.htm) — **Evidence class:** OEM software manual; links common-line setup to kerf and generated-NC review.
- [Hypertherm common-line cutting guidance](https://www.hypertherm.com/resources/more-resources/blogs/common-line-cutting/) — **Evidence class:** OEM technical guidance; covers shared paths, feed, direction, and lead-in/out.
- [Hypertherm document library](https://www.hypertherm.com/resources/documents-library/) — **Evidence class:** OEM manuals; provides machine- and version-specific controls.

**Conflicting evidence:** Tight/common-line nesting can improve yield but can
increase thermal interaction, shared-edge defects, sorting difficulty, and
correlated scrap. One-click nesting is not unattended-production proof.

**Confidence:** Medium.

**Business-specific validation:** Compare controlled nests by yield, cut time,
edge quality, dimensions, tip-ups, sorting labor, and remnant recovery.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The process-control approach is sound, but nesting, bend behavior, and weld quality remain machine-, tooling-, joint-, and material-specific; OEM and standards sources justify the controls, not any universal numeric envelope.

### FAB-005 — How are bend allowance, bend deduction, K-factor, tooling, springback, reliefs, sequencing, and collision risk validated before release?

**State:** Exploring

**First-pass answer:** Maintain shop-specific bend tables indexed by material
grade/temper and lot behavior, thickness, grain, punch, V-die, bend method,
radius, and machine. Calculate from a controlled rule, then calibrate K-factor,
springback, and angle offsets with same-material coupons. Validate tool load,
flange support, reliefs, sequence, handling, and collisions offline, followed
by slow prove-out and first-article inspection.

**Evidence:**
- [Autodesk sheet-metal rule reference](https://help.autodesk.com/cloudhelp/ENU/Fusion-Sheet-Metal/files/SM-RULES-REF.htm) — **Evidence class:** OEM software manual; defines thickness, radius, K-factor, and relief inputs.
- [TRUMPF bending-tools catalog](https://www.trumpf.com/filestorage/TRUMPF_Master/Products/Services/01_brochures/TRUMPF-bending-tools-catalog-EN.pdf) — **Evidence class:** OEM tooling documentation; supports geometry- and load-based setup.
- [DECETEC K-factor guide](https://www.decetec.com/technical-guide/k-factor-guide-k-factor-table/) — **Evidence class:** supplier practice; explicitly states K-factor is not universal and recommends trial bends.

**Conflicting evidence:** Offline simulation can eliminate impossible
sequences, but material variation, tool wear, thickness, and handling
deflection remain physical-production variables.

**Confidence:** Medium-high on the method; low on values before trials.

**Business-specific validation:** Produce a coupon matrix across proposed
materials, lots, and operators; lock rules only after repeated measurements.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The process-control approach is sound, but nesting, bend behavior, and weld quality remain machine-, tooling-, joint-, and material-specific; OEM and standards sources justify the controls, not any universal numeric envelope.

### FAB-006 — How are fit-up, tack sequence, heat input, distortion, weld procedure, welder qualification, inspection, and post-weld correction controlled?

**State:** Exploring

**First-pass answer:** Use a controlled WPS or shop instruction for each
material/process/joint family, including preparation, fit-up and root gap, tack
size/spacing/sequence, consumables, gas, parameter range, passes, interpass
controls, and acceptance. Qualify welders when code, contract, or risk requires
it; otherwise document internal competency without claiming code qualification.
Repairs, grinding, straightening, or thermal correction require approved
disposition and reinspection.

**Evidence:**
- [TWI distortion control by fabrication technique](https://www.twi-global.com/technical-knowledge/job-knowledge/distortion-control-prevention-by-fabrication-techniques-036) — **Evidence class:** empirical/practitioner guidance; addresses fit-up, balanced welding, sequence, restraint, and distortion.
- [ASME Section IX sample WPS form QW-482](https://www.asme.org/wwwasmeorg/media/resourcefiles/aboutasme/who%20we%20are/bpvc%20resources/section_ix/bpvc_ix_qw-482.pdf) — **Evidence class:** official standards-body form; illustrates controlled procedure variables.
- [ASME welder-performance form QW-484B](https://www.asme.org/wwwasmeorg/media/resourcefiles/aboutasme/who%20we%20are/bpvc%20resources/section_ix/bpvc_ix_qw-484b.pdf) — **Evidence class:** official standards-body form; records qualification conditions.

**Conflicting evidence:** Mechanized welding may improve consistency, but
fixtures, sensing, programming, stable fit-up, and fault recovery must be
proven; a demonstration weld is not dependable automated production.

**Confidence:** High on control structure; medium-low on process limits.

**Business-specific validation:** Weld representative coupons and assemblies,
measure distortion and rework, destructively test selected samples, and obtain
qualified welding-professional approval.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The process-control approach is sound, but nesting, bend behavior, and weld quality remain machine-, tooling-, joint-, and material-specific; OEM and standards sources justify the controls, not any universal numeric envelope.

## Economics and Ownership

### FAB-007 — What complete ecosystem cost includes material handling, gas, extraction, deburring, forming, welding, inspection, finishing, consumables, scrap, maintenance, and floor space?

**State:** Exploring

**First-pass answer:** Cost accepted parts, not cutting or arc-on minutes.
Include equipment/automation, installation, utilities, foundations, permits,
storage/handling, CAD/CAM, programming, setup, loading, gas, air, extraction,
PPE, consumables, deburring, forming, welding, inspection, finishing,
packaging, scrap/rework, maintenance/spares, downtime, floor space, inventory,
training, insurance, financing, waste, and decommissioning. Use demonstrated,
not brochure, utilization and yield.

**Evidence:**
- [NIST Manufacturing Cost Guide 2.0 primer](https://www.nist.gov/publications/manufacturing-cost-guide-version-20-primer) — **Evidence class:** primary government methodology; supports auditable cost and supply-chain analysis.
- [DOE compressed-air sourcebook](https://www.energy.gov/sites/default/files/2016/03/f30/Improving%20Compressed%20Air%20Sourcebook%20version%203.pdf) — **Evidence class:** official technical guidance; shows compressed air is an energy and maintenance system.
- [GAO Cost Estimating and Assessment Guide](https://www.gao.gov/products/gao-20-195g) — **Evidence class:** primary government methodology; supports assumptions, risk, sensitivity, and independent checks.

**Conflicting evidence:** Vendor ROI cases may accurately report high process
speed while omitting programming, handling, secondary operations, downtime,
financing, and unsold capacity.

**Confidence:** High on categories; low on totals until quoted and measured.

**Business-specific validation:** Obtain installed quotes and shadow-price at
least 30 outsourced jobs using actual touch time, yield, rework, and financing.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The cost and make-buy framework is sensible, but ownership thresholds need local quote history, utilization, partner quality evidence, and real floor-space/consumable burdens before they become dependable.

### FAB-008 — Which demand, utilization, yield, changeover, outside-process, and quality thresholds justify owning each fabrication process rather than outsourcing it?

**State:** Exploring

**First-pass answer:** Classify each process as must-own, must-outsource, or
make/buy. Own only when conservative recurring demand exceeds break-even with
margin; accepted-part cost beats qualified outsourcing; yield and capability
are demonstrated; lead-time/control benefits matter; staffing and maintenance
exist; and concentration and cash risk are acceptable. Revisit quarterly; no
universal utilization percentage fits lasers, brakes, welding, and finishing.

**Evidence:**
- [FAR 15.407-2 make-or-buy programs](https://www.acquisition.gov/far/15.407-2) — **Evidence class:** primary government framework; requires rationale, capacity, schedule, technical risk, facilities, and supplier data.
- [DFARS PGI 215.407-2](https://www.acquisition.gov/dfarspgi/pgi-215.407-2-make-or-buy-programs.) — **Evidence class:** primary government guidance; adds capacity, quality, resources, overhead, and schedule factors.
- [GAO Cost Estimating Guide](https://www.gao.gov/products/gao-20-195g) — **Evidence class:** primary methodology; supports uncertainty and sensitivity analysis.

**Conflicting evidence:** Ownership can improve responsiveness and learning
before accounting break-even; outsourcing preserves cash and flexibility but
can hide queues, freight, quality, and supplier-management costs.

**Confidence:** High on the framework; low on thresholds pending demand.

**Business-specific validation:** Compare twelve months of RFQs/orders under
make and buy scenarios using accepted-part cost, practical capacity, changeover,
yield, supplier defects, cash, and downside demand.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The cost and make-buy framework is sensible, but ownership thresholds need local quote history, utilization, partner quality evidence, and real floor-space/consumable burdens before they become dependable.

## Related Topics

- [Manufacturing Processes and Quality](manufacturing-processes-and-quality.md)
- [Minimum Shop Support Capabilities](minimum-shop-support-capabilities.md)
- [Standards, Certifications, and Quality Systems](standards-certifications-and-quality-systems.md)
- [Facility, Site, Utilities, and Layout](facility-site-utilities-and-layout.md)
- [Suppliers and Procurement](suppliers-and-procurement.md)
