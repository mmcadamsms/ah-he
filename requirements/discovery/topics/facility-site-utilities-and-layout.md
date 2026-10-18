# Facility, Site, Utilities, and Layout Questions

## Purpose

This topic tracks the physical space, land, building, utilities, access,
employee facilities, environmental controls, safety systems, and expansion
capacity required to operate the planned design, machining, fabrication,
additive, assembly, inspection, packaging, and shipping business.

The requirements must be derived from realistic equipment and process
scenarios. A low lease rate does not make a building suitable if power,
loading, slab, ventilation, fire protection, utilities, access, or permitting
cannot support the work.

## Question Supplied in Conversation

### FAC-001 — What does the physical space need in order to run this business?

**State:** Exploring

**Original wording:** "need to gather list of needs for the phyisical space to run this biz ... things like 3-phase power, square footage, delivery access, physical layout, water, compressed air?, bottled gas?, break room / kitchen, bathrooms, etc."

The eventual answer should create a facility requirements matrix for the
initial operation and the year-two/year-three target state.

**First-pass answer:** The physical space needs legal industrial use; verified
truck and rigging access; a sound slab and structure; model-specific electrical,
air, gas, water, drainage, exhaust, thermal, and network services; code-compliant
fire protection and egress; separated receiving, production, inspection,
quarantine, waste, office, and employee areas; and reserved expansion capacity.
No building is suitable until the planned equipment/process schedule has been
checked against the property, utilities, permits, and insurer conditions.

**Evidence:**
- [Haas TM-1 pre-installation guide](https://www.haascnc.com/owners/pre-install-guide/mills-pre-install/TM-1.html) — **Evidence class:** OEM installation manual. It demonstrates that even one machine has specific power, air, foundation, clearance, and environmental prerequisites.
- [OSHA general-industry standards](https://www.osha.gov/laws-regs/regulations/standardnumber/1910) — **Evidence class:** binding U.S. regulation. The facility must accommodate electrical, egress, sanitation, material-handling, ventilation, and other workplace duties.
- [EPA industrial wastewater](https://www.epa.gov/npdes/industrial-wastewater) — **Evidence class:** primary official guidance. Process discharge is a permitting question, not an ordinary-building assumption.

**Conflicting evidence:** Generic broker checklists often treat square feet and
rent as decisive; OEM, code, utility, and discharge constraints can make a
larger or cheaper building unusable.

**Confidence:** High on the categories; low on quantities before the product,
equipment, staffing, and candidate jurisdiction are fixed.

**Business-specific validation:** Create phased equipment, material, staffing,
and process inventories; obtain written zoning/AHJ, utility, sewer, landlord,
structural-engineer, environmental-professional, and insurer findings for each
candidate property.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The category list is directionally correct and the cited OEM, OSHA, and EPA sources support the need to treat utilities, safety, and discharge as real constraints, but this is still a framework answer rather than building-specific proof; keep final requirements tied to exact OEM manuals, local AHJ determinations, sewer acceptance, and insurer conditions.

## Facility Size and Growth

### FAC-002 — How much total square footage is required at each business phase?

**State:** Exploring

Separate:

- receiving;
- raw-material storage;
- saw and stock preparation;
- CNC machining;
- laser and tube cutting;
- welding and fabrication;
- additive manufacturing;
- finishing and cleaning;
- inspection and metrology;
- assembly;
- WIP and quarantine;
- tooling and maintenance;
- finished-goods storage;
- packaging and shipping;
- offices and customer areas;
- IT and electrical rooms;
- employee areas;
- waste and scrap;
- aisles and egress; and
- future expansion.

**First-pass answer:** Public evidence cannot produce a credible universal
square-foot number. Build block layouts for startup, year two, and year three
from actual equipment envelopes plus OEM service clearances, inventory/WIP
positions, safe aisles and exits, employee/office space, and a separately
identified expansion reserve; compare usable production area, not advertised
gross area.

**Evidence:**
- [Haas TM-1 pre-installation guide](https://www.haascnc.com/owners/pre-install-guide/mills-pre-install/TM-1.html) — **Evidence class:** OEM technical data. Machine footprint alone is insufficient because access, foundation, utilities, and service space also consume area.
- [OSHA 29 CFR 1910.36, exit routes](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.36) — **Evidence class:** binding U.S. regulation. Required exit routes constrain usable layout and cannot be treated as storage.

**Conflicting evidence:** Rules of thumb expressed as square feet per employee
or machine ignore long stock, sheet handling, queue policy, fire area, and
future equipment; they are useful only as a reasonableness check.

**Confidence:** High that bottom-up block planning is required; low on a number
until the phased operating plan exists.

**Business-specific validation:** Produce scaled layouts with an industrial
engineer and architect, then test them with full-size handling envelopes and
peak pallet/WIP counts.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly rejects generic square-foot rules and keeps sizing anchored to actual machine envelopes, clearances, exits, WIP, and expansion reserve; no better public shortcut appears more reliable than phased block layouts on usable production area.

### FAC-003 — What utilization and storage assumptions determine square footage?

**State:** Exploring

Consider machine count, stock length and sheet size, batch size, queue policy,
WIP, pallet count, packaging, customer-owned property, inspection quarantine,
maintenance access, and seasonality.

**First-pass answer:** Size from peak credible occupancy of each location, not
annual-average throughput: machine count and service envelope, raw-stock and
packaging units, maximum queue/WIP and quarantine, customer property, scrap,
maintenance staging, and seasonal inbound/outbound peaks. Require each storage
assumption to state unit dimensions, stackability, dwell time, replenishment
frequency, and aisle/handling method.

**Evidence:**
- [OSHA 29 CFR 1910.22, walking-working surfaces](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.22) — **Evidence class:** binding U.S. regulation. Storage and housekeeping cannot create unsafe or obstructed walking-working surfaces.
- [NIST MEP, Lean and process improvement](https://www.nist.gov/mep/lean-and-process-improvement) — **Evidence class:** primary official practitioner guidance. Flow and inventory policy affect space, travel, and delay rather than square footage being an isolated real-estate input.

**Conflicting evidence:** Lean targets can reduce WIP, but designing to an
unproven ideal queue creates overflow; designing to every imaginable peak
wastes capital.

**Confidence:** High on the drivers; medium on peak percentiles until measured
orders and receipts exist.

**Business-specific validation:** Simulate at least a representative busy month,
count pallet/rack/floor positions by state, and validate dwell-time assumptions
through a pilot or comparable-shop observations.

**Second-opinion review:** GPT-5.4 — Agree. Peak-occupancy sizing and explicit assumptions for unit size, dwell, stackability, and aisle method are the right controls; the cited OSHA and NIST material supports treating inventory policy as a layout driver rather than a separate real-estate guess.

### FAC-004 — How much expansion should be possible without relocating?

**State:** Exploring

Evaluate adjacent space, yard, electrical and utility capacity, additional
shifts, mezzanine, machine pads, cranes, docks, parking, and future process
permits.

**First-pass answer:** Preserve enough contiguous, serviceable area and utility
capacity to install the credible year-three constraint equipment without
relocating; a practical target is the approved phased block layout plus one
defined alternate cell, not an arbitrary percentage. Expansion must include
electrical demand, air/exhaust/fire capacity, truck/parking impacts, and
permitting—not merely empty floor.

**Evidence:**
- [DOE compressed-air sourcebook](https://www.energy.gov/sites/default/files/2016/03/f30/Improving%20Compressed%20Air%20Sourcebook%20version%203.pdf) — **Evidence class:** primary official engineering guidance. Distribution, storage, controls, pressure drop, and future demand must be assessed as a system.
- [Dominion Energy electric-service requirements](https://www.dominionenergy.com/-/media/content/start-stop-service/new-construction/pdfs/global/information-and-requirements-for-electric-service-blue-book.pdf) — **Evidence class:** actual utility service rules. Added load can require utility engineering, customer work, clearances, and inspection rather than a simple panel addition.

**Conflicting evidence:** Extra leased area buys flexibility but raises carrying
cost; adding shifts may defer floor expansion but increases parking, HVAC,
supervision, noise, and maintenance requirements.

**Confidence:** Medium; the correct reserve is strategy- and property-specific.

**Business-specific validation:** Approve a year-three equipment/shift scenario,
obtain utility and AHJ capacity letters, price expansion options, and place
lease rights for adjacent space, penetrations, roof equipment, and alterations
in writing.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Using a defined year-three scenario is better than a generic expansion percentage, but the conclusion remains strategy- and property-specific because utility lead times, adjacent-space rights, and permitting can become the real constraint even when open floor area exists.

## Site, Zoning, and Property

### FAC-005 — Which zoning and permitted uses are required?

**State:** Exploring

Include machining, welding, laser cutting, additive manufacturing, compressed
gas, material storage, truck traffic, noise, fumes, dust, painting or coatings,
heat treatment if contemplated, assembly, offices, and customer pickup.

**First-pass answer:** The site must expressly allow the actual use mix and
hazard profile, not merely “industrial”: machining/fabrication, hot work,
laser/additive equipment, gases and chemicals, outdoor storage, truck hours,
noise/exhaust, office/customer access, and any finishing. Obtain a written
zoning determination and identify conditional-use, building, fire,
environmental, and occupancy approvals before commitment.

**Evidence:**
- [EPA Smart Location Database](https://www.epa.gov/smartgrowth/smart-location-mapping) — **Evidence class:** official planning data. National datasets can screen access and surrounding development but do not establish parcel permission.
- [SBA licenses and permits](https://www.sba.gov/business-guide/launch-your-business/apply-licenses-permits) — **Evidence class:** primary official guidance. Required approvals vary by activity and location and must be confirmed with issuing authorities.

**Conflicting evidence:** A broker or landlord may describe a prior shop use as
proof, but nonconforming rights, expired permits, occupancy classification, or
a changed process can defeat that inference.

**Confidence:** High on the due-diligence method; low on any parcel until the
local authority answers.

**Business-specific validation:** Submit a process narrative, equipment list,
hours, quantities, truck plan, emissions/noise sources, and floor plan to local
planning, building, fire, sewer, and environmental authorities and retain their
written responses; use land-use counsel where material.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The conclusion that the actual process mix must be approved in writing is sound, but the cited national planning/licensing sources are only screening aids; parcel permission is ultimately a local zoning, building, fire, environmental, and occupancy question.

### FAC-006 — What property boundaries and neighbors are compatible?

**State:** Exploring

Consider homes, schools, healthcare, food production, sensitive electronics,
noise, night shifts, truck routes, emissions, outdoor storage, lighting, and
future neighborhood development.

**First-pass answer:** Prefer established industrial surroundings with legal
truck routes and buffers from residences, schools, healthcare, food production,
and other sensitive receptors. Review current neighbors and adopted future-land-
use plans because night work, lights, exhaust, odors, outdoor storage, and
deliveries can remain lawful yet produce complaints or permit constraints.

**Evidence:**
- [EPA EJScreen technical documentation](https://www.epa.gov/system/files/documents/2024-07/ejscreen-tech-doc-version-2-3.pdf) — **Evidence class:** primary official screening data. It supports review of nearby populations and environmental burdens but is not a site-specific risk determination.
- [FHWA Freight Analysis Framework](https://ops.fhwa.dot.gov/freight/freight_analysis/faf/) — **Evidence class:** official transport data. Freight-network context can screen access, while local routes and restrictions still require field confirmation.

**Conflicting evidence:** Remote industrial sites reduce neighbor conflict but
may worsen labor access, emergency response, utilities, and freight time.

**Confidence:** Medium because compatibility depends on local plans, operations,
and measured impacts.

**Business-specific validation:** Walk the boundary by shift, inspect zoning and
future-land-use maps, measure background noise/traffic, drive every truck route,
and have environmental and land-use professionals assess receptors and
setbacks.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Preferring industrial neighbors and legal truck access is reasonable, but compatibility still depends on local receptor sensitivity, future-land-use plans, hours, noise, and route restrictions, so this should stay as a due-diligence method rather than a universal siting rule.

### FAC-007 — Is leasing, buying, or building the facility preferable?

**State:** Exploring

Compare capital, flexibility, tax, maintenance, customization, expansion,
landlord approval, restoration obligations, financing, and equipment-removal
risk.

**First-pass answer:** There is no universal preference. Lease when demand,
process mix, and geography are still uncertain and a compliant building needs
only modest reversible work; buy when the site is strategically durable and
substantial long-lived power, structure, crane, ventilation, or environmental
improvements would otherwise be stranded. Build only when existing supply
cannot satisfy mandatory gates and schedule/capital can absorb design,
permitting, and construction risk.

**Evidence:**
- [SBA guidance on buying business assets](https://www.sba.gov/business-guide/manage-your-business/buy-assets-equipment) — **Evidence class:** primary official business guidance. Ownership decisions require life-cycle financing and cash-flow analysis rather than price alone.
- [IRS Publication 946](https://www.irs.gov/publications/p946) — **Evidence class:** primary official tax guidance. Owned improvements and equipment have tax lives and depreciation treatment that differ from current rent, subject to professional application.

**Conflicting evidence:** Leasing preserves cash but may restrict alterations
and expose the tenant to renewal/restoration risk; ownership provides control
but concentrates capital and property/environmental liabilities.

**Confidence:** Medium; no universal tenure choice exists.

**Business-specific validation:** Compare after-tax discounted cash flows for
lease, buy, and build using lender/landlord term sheets, improvement ownership,
restoration clauses, downtime, residual value, and CPA, counsel, and lender
review.

**Second-opinion review:** GPT-5.4 — Disagree. The original wording leaned too hard toward leasing as a default; the evidence supports a conditional lease-versus-buy-versus-build framework, not a presumptive first choice, so the answer was revised to make the decision explicitly NPV-, control-, and upgrade-dependent.

### FAC-008 — What due diligence is required before committing to a site?

**State:** Exploring

Include zoning confirmation, title and survey, environmental history, building
condition, roof, slab, structure, utilities, fire system, code status, permits,
ADA, flood, weather, insurance, taxes, easements, truck access, and expansion.

**First-pass answer:** Make commitment conditional on written land-use and
utility findings; title/survey and lease-right review; Phase I environmental
assessment; architect/engineer inspections of structure, slab, roof, fire,
plumbing, electrical and accessibility; flood/weather review; truck/rigging
test; permit/code-history search; insurer review; and a costed remedial scope
with exit rights.

**Evidence:**
- [EPA All Appropriate Inquiries](https://www.epa.gov/brownfields/brownfields-all-appropriate-inquiries) — **Evidence class:** binding/official environmental due-diligence framework. Environmental liability defenses require a qualifying inquiry, not a visual walk-through.
- [FEMA Flood Map Service Center](https://msc.fema.gov/portal/home) — **Evidence class:** primary official hazard data. It is the official public source for NFIP flood-hazard information, while maps can change and do not cover every water risk.
- [ADA 2010 Standards](https://www.ada.gov/law-and-regs/design-standards/2010-stds/) — **Evidence class:** binding U.S. accessibility standard where applicable. Existing condition and planned alterations require professional review.

**Conflicting evidence:** Seller property-condition reports are useful but may
exclude destructive testing, code compliance, production loads, or buyer
reliance.

**Confidence:** High on the checklist; property conclusions require licensed
professionals and authorities.

**Business-specific validation:** Use environmental counsel/professional,
surveyor, architect, structural/electrical/mechanical engineers, roof/fire
specialists, insurer, and local AHJs; require documents and intrusive tests
where risk warrants.

**Second-opinion review:** GPT-5.4 — Agree. The checklist is appropriately conservative and matches the cited environmental, flood, and accessibility sources; seller narratives and visual walk-throughs are not enough for a manufacturing property with process, insurer, and utility constraints.

## Electrical Power

### FAC-009 — What three-phase electrical service is required?

**State:** Exploring

Determine:

- available voltage;
- phase;
- amperage and kVA;
- transformer capacity;
- utility service size;
- main switchgear;
- fault current;
- grounding;
- demand;
- future reserve;
- meter and tariff;
- service-upgrade lead time; and
- responsibility for utility and customer-side work.

**First-pass answer:** Do not select service amperage by machine-nameplate
addition alone. A licensed electrical engineer should create startup and
year-three connected-load, demand, duty-cycle, starting/inrush, harmonic, and
diversity schedules; then the serving utility must confirm in writing available
voltage, phase, fault current, transformer/feeder capacity, tariff, metering,
upgrade scope, allocation of cost, and energization lead time. Reserve capacity
for the approved expansion plan.

**Evidence:**
- [Haas TM-1 pre-installation guide](https://www.haascnc.com/owners/pre-install-guide/mills-pre-install/TM-1.html) — **Evidence class:** OEM technical data. It states model-specific voltage, breaker, conductor, grounding, and air requirements.
- [SWEPCO 2024 Electric Service Handbook](https://www.swepco.com/lib/docs/builders/meter/2024SWEPCOElectricServiceHandbook.pdf) — **Evidence class:** actual utility service rules. Utility approval, service arrangements, inspections, customer work, access, and equipment location govern connection.
- [Dominion Energy filed tariff](https://www.dominionenergy.com/-/media/content/rates-and-tariffs/pdfs/virginia/shared/entire-filed-tariff.pdf) — **Evidence class:** actual tariff. Rate eligibility and demand/service charges are service-territory-specific.

**Conflicting evidence:** Nameplate totals overstate coincident demand, while
historical building bills can understate future CNC, laser, compressor, HVAC,
or charger peaks.

**Confidence:** High on method; low on size, cost, and schedule before utility
engineering.

**Business-specific validation:** Obtain stamped one-line/load calculations,
12–24 months of interval/billing data if occupied, short-circuit and protective-
device studies, and a utility capacity/cost/lead-time letter tied to the exact
parcel.

**Second-opinion review:** GPT-5.4 — Agree. The method is correct: engineer the load profile first, then obtain written utility confirmation of voltage, capacity, tariff, cost allocation, and lead time. The cited OEM and utility materials support rejecting both naive nameplate addition and reliance on old building bills.

### FAC-010 — Which machines require which voltage, phase, frequency, and connection?

**State:** Exploring

Create an equipment schedule covering CNC machines, lasers, welders,
compressors, HVAC, dust or mist collection, cranes, saws, chargers, inspection,
IT, packaging, and future automation.

**First-pass answer:** Create a controlled equipment utility schedule from each
exact model and option: nominal/allowable voltage and frequency, phase, full-
load and maximum demand, breaker/conductor, connection, ground, inrush,
harmonic/power-quality limits, compressed air, cooling/exhaust, and disconnect.
Never infer requirements from equipment type or a similar model.

**Evidence:**
- [Haas TM-1 pre-installation guide](https://www.haascnc.com/owners/pre-install-guide/mills-pre-install/TM-1.html) — **Evidence class:** OEM installation manual. It requires machine-specific electrical, air, foundation, and placement preparation before installation.
- [OSHA 29 CFR 1910.303](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.303) — **Evidence class:** binding U.S. regulation. Listed/labeled electrical equipment must be installed and used according to included instructions, with required working space and guarding.

**Conflicting evidence:** Resellers may quote “standard 480 V three-phase,” but
options, export configurations, heaters, chillers, transformers, and ancillary
equipment can change the requirement.

**Confidence:** High on the schedule requirement; low on entries before model-
specific submittals are obtained.

**Business-specific validation:** Require current OEM installation drawings and
serial/configuration confirmation in every purchase order; have the engineer
and OEM sign off the coordinated schedule before releasing construction.

**Second-opinion review:** GPT-5.4 — Agree. Exact model-and-option schedules are the only reliable basis for voltage, phase, frequency, inrush, and disconnect requirements; the first-pass answer properly avoids generalizing from machine type or reseller shorthand.

### FAC-011 — Are transformers or power conditioning required?

**State:** Exploring

Consider machine voltage, control sensitivity, harmonics, power factor,
surges, brownouts, phase imbalance, grounding, isolation, and warranty
requirements.

**First-pass answer:** Transformers are required where utility/building voltage
does not match an approved machine input; conditioning is justified only by OEM
limits and measured/studied power quality. Specify taps, impedance, grounding,
heat/clearance, efficiency, overcurrent protection, fault duty, harmonics, power
factor, phase loss/imbalance, surge protection, and warranty responsibility as
a coordinated system.

**Evidence:**
- [Xcel Energy Standard for Electric Installation and Use](https://www.xcelenergy.com/staticfiles/xe-responsive/Admin/Managed%20Documents%20%26%20PDFs/Xcel-Energy-Standard-For-Electric-Installation-and-Use.pdf) — **Evidence class:** actual utility service rules. Customer equipment must be compatible with the utility supply and utility requirements.
- [IEEE 519 overview](https://standards.ieee.org/ieee/519/10677/) — **Evidence class:** official standards-body summary. Harmonic control is assessed at the system point of common coupling, not solved by indiscriminate equipment purchases.

**Conflicting evidence:** Vendors may recommend isolation transformers or
conditioners defensively; unnecessary units add losses, heat, fault-current and
maintenance issues, while omitting required protection risks controls and
warranty.

**Confidence:** Medium until OEM data and site measurements/studies exist.

**Business-specific validation:** Have a power-quality engineer compare OEM
limits with utility data and a week of voltage/harmonic/event monitoring;
obtain written OEM acceptance of the proposed transformer and grounding design.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer correctly makes transformers and conditioning a response to actual OEM limits and measured power quality, not a default purchase, but the decisive evidence will be site monitoring plus written OEM acceptance of the grounding and transformer scheme.

### FAC-012 — How are electrical distribution and machine drops laid out?

**State:** Exploring

Address busway versus conduit, overhead versus floor, disconnects, lockout,
clearance, relocation, expansion, cable protection, and machine-service access.

**First-pass answer:** Use a coordinated overhead distribution spine or busway
where frequent relocation is credible, with local code-compliant disconnects
and protected drops; use fixed conduit where loads and positions are stable or
the environment demands it. Keep electrical working space, egress, rigging,
sprinkler, crane, duct, and service envelopes clear, and avoid floor trenches
unless drainage and contamination risks are engineered.

**Evidence:**
- [OSHA 29 CFR 1910.303](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.303) — **Evidence class:** binding U.S. regulation. Electrical equipment requires safe installation, guarding, access, and working clearances.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Layout must support identifiable energy-isolation devices and hazardous-energy control.

**Conflicting evidence:** Busway improves flexibility but can cost more and
conflict with cranes/ducts; floor drops shorten runs but are vulnerable to
coolant, traffic, and relocation.

**Confidence:** High on coordination principles; medium on distribution type.

**Business-specific validation:** Produce a stamped coordinated reflected-
ceiling/utility plan and one-line; conduct OEM, electrician, fire-protection,
rigging, and maintenance-access reviews before installation.

**Second-opinion review:** GPT-5.4 — Agree. The coordination logic is strong: distribution choice is a layout decision that must preserve lockout access, working clearances, egress, rigging, and future change. No contrary evidence suggests a universal preference for busway, conduit, floor, or overhead drops.

### FAC-013 — What backup power or controlled shutdown is required?

**State:** Exploring

Evaluate UPS, generator, control backup, safe machine stop, data systems,
network, metrology, lighting, fire and security, coolant, and restart.

**First-pass answer:** Provide UPS-backed orderly shutdown and tested recovery
for network, servers, controls needing ride-through, access/security, and
critical monitoring; provide code-required emergency systems independently.
Do not size a generator to run production unless lost-margin and safe-restart
analysis justifies the fuel, transfer, emissions, maintenance, and load-bank
burden. Each machine needs a documented response to voltage loss and restart.

**Evidence:**
- [NIST SP 800-34 Rev. 1](https://csrc.nist.gov/pubs/sp/800/34/r1/upd1/final) — **Evidence class:** primary official continuity guidance. Recovery priorities should follow business-impact and dependency analysis.
- [Haas power-failure recovery procedure](https://www.haascnc.com/service/troubleshooting-and-how-to/how-to/power-fault-recovery.html) — **Evidence class:** OEM operating guidance. Machine recovery after interruption is model/control-state-specific and may require controlled actions.

**Conflicting evidence:** A whole-shop generator appears resilient but may not
support motor starting or process quality and may cost more than alternate
capacity; UPS units themselves introduce battery and maintenance failure modes.

**Confidence:** Medium; required autonomy follows process and outage evidence.

**Business-specific validation:** Conduct a business-impact and safe-state
workshop, obtain outage-history data, test power-loss/restart on each equipment
class, and have electrical/fire professionals and the insurer review generator,
fuel, and emergency-system design.

**Second-opinion review:** GPT-5.4 — Agree. The answer properly limits backup power to justified shutdown, continuity, and code-required systems unless a business-impact analysis supports production generation; the OEM recovery source is especially important because restart behavior is machine-state-specific.

## Compressed Air

### FAC-014 — Which equipment requires compressed air?

**State:** Exploring

Include machine tools, tool changers, air blast, automation, robots, fixtures,
lasers, packaging, cleaning, and maintenance.

**First-pass answer:** Assume air only for equipment whose current manual
requires it: common loads include CNC actuation/tool change and spindle air,
fixtures, robots, laser pneumatics, blow-off, packaging, and maintenance.
Cleaning uses must be separately controlled; compressed air must not substitute
for ventilation and OSHA limits cleaning pressure when dead-ending hazards are
present.

**Evidence:**
- [Haas TM-1 pre-installation guide](https://www.haascnc.com/owners/pre-install-guide/mills-pre-install/TM-1.html) — **Evidence class:** OEM technical data. It supplies a concrete machine-specific pressure and flow requirement.
- [OSHA 29 CFR 1910.242](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.242) — **Evidence class:** binding U.S. regulation. Compressed-air cleaning is restricted and requires effective chip guarding and PPE.

**Conflicting evidence:** Pneumatics are convenient, but electric actuation,
vacuum, or dedicated blowers may use less energy and avoid contaminating
precision processes.

**Confidence:** High on inventorying exact consumers; low on total demand.

**Business-specific validation:** List every connection and operating mode from
OEM manuals, measure/pilot simultaneous demand, and eliminate open blowing
where a safer lower-energy method works.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Exact OEM manuals should drive the air-consumer list, and the warning against using compressed air as a casual cleaning substitute is well supported; total demand and simultaneity remain unproven until the real equipment roster exists.

### FAC-015 — What pressure, flow, quality, and duty cycle are required?

**State:** Exploring

Determine peak and average SCFM, pressure, dew point, oil content, filtration,
dryer, receiver, redundancy, leaks, and future demand.

**First-pass answer:** Size for measured or manufacturer-stated average and
credible coincident peak flow at the minimum pressure at each point of use,
including distribution/dryer/filter losses—not compressor discharge pressure.
Set dew point, particulate and oil classes from the strictest equipment/process
manual; provide receiver/control capacity and, where downtime matters, N+1 or a
rentable bypass connection.

**Evidence:**
- [DOE compressed-air sourcebook](https://www.energy.gov/sites/default/files/2016/03/f30/Improving%20Compressed%20Air%20Sourcebook%20version%203.pdf) — **Evidence class:** primary official engineering guidance. It addresses demand profiles, storage, controls, pressure drop, air treatment, leaks, and life-cycle cost.
- [Haas TM-1 pre-installation guide](https://www.haascnc.com/owners/pre-install-guide/mills-pre-install/TM-1.html) — **Evidence class:** OEM installation manual. It illustrates model-specific clean, dry air pressure and flow prerequisites.

**Conflicting evidence:** Oversizing appears safe but causes inefficient cycling
and poor control; raising system pressure masks restrictions while increasing
energy and leakage.

**Confidence:** High on method; medium on capacity before measurements.

**Business-specific validation:** Create a point-of-use quality/pressure/SCFM
schedule, log pressure and flow through representative cycles, run a leak test,
and have the compressor supplier guarantee performance at site altitude and
ambient conditions.

**Second-opinion review:** GPT-5.4 — Agree. Sizing to point-of-use minimum pressure, credible coincident peak flow, and the strictest quality class is the right method; the DOE sourcebook strongly supports avoiding both blind oversizing and pressure creep as a substitute for system design.

### FAC-016 — How should the compressed-air system be distributed?

**State:** Exploring

Compare loop versus branches, pipe material, slope, drains, drops, isolation,
monitoring, outdoor versus indoor compressor, noise, heat recovery, and
maintenance.

**First-pass answer:** Use a generously sized loop or other low-pressure-drop
header with short isolated drops, point-of-use regulation/filtration, planned
drains, monitoring, and a bypass around maintainable components. Select pipe
material and joining for pressure, cleanliness, corrosion, code, and expansion;
place compressors where noise, intake quality, service access, rejected heat,
freeze protection, and heat recovery are deliberately managed.

**Evidence:**
- [DOE compressed-air sourcebook](https://www.energy.gov/sites/default/files/2016/03/f30/Improving%20Compressed%20Air%20Sourcebook%20version%203.pdf) — **Evidence class:** primary official engineering guidance. It recommends whole-system design, low pressure drop, storage, leak management, controls, and heat recovery.
- [OSHA 29 CFR 1910.169](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.169) — **Evidence class:** binding U.S. regulation. Air receivers require safe installation, drainage, gauges, relief devices, and inspection access.

**Conflicting evidence:** A loop is not automatically superior in a tiny linear
shop, and outdoor compressor placement reduces indoor noise/heat but increases
weather and freeze exposure.

**Confidence:** High on design criteria; medium on topology.

**Business-specific validation:** Have a qualified designer calculate pressure
drop at startup and future peak, map drains/condensate disposal, conduct a noise
study, and commission flow, pressure, dew point, alarms, and isolation.

**Second-opinion review:** GPT-5.4 — Agree. The first-pass answer captures the real tradeoffs among looped headers, short drops, drainage, monitoring, compressor siting, and maintenance bypass. Distribution topology should remain a calculated outcome, but the design criteria are well stated.

## Water, Sewer, and Drainage

### FAC-017 — Which operations require water?

**State:** Exploring

Consider domestic use, coolant makeup, parts washing, cleaning, process water,
fire protection, HVAC, additive systems, and future finishing.

**First-pass answer:** Domestic fixtures and fire protection are baseline
building uses; process water must be scheduled separately for coolant makeup,
parts washing, cleaning, HVAC humidification/cooling, and any additive or
finishing equipment. Prefer closed-loop or containerized processes where
practical, and pair every inlet with its required quality, backflow protection,
maximum demand, and lawful discharge route.

**Evidence:**
- [EPA WaterSense best-management practices](https://www.epa.gov/watersense/best-management-practices) — **Evidence class:** primary official conservation guidance. Commercial/industrial water use should be inventoried by end use and managed rather than inferred from building size.
- [EPA National Pretreatment Program](https://www.epa.gov/npdes/national-pretreatment-program) — **Evidence class:** primary official regulatory guidance. Introducing nondomestic wastewater to a sewer can trigger local limits and pretreatment duties.

**Conflicting evidence:** Municipal supply may be ample for domestic use yet
unsuitable in quality or permitted discharge for a process; “dry” machining
still creates cleaning and coolant-management needs.

**Confidence:** Medium until process selection and local utility information are
known.

**Business-specific validation:** Build an inlet/outlet water balance by process,
obtain water-quality and pressure data, fire-flow results, sewer authority
approval, and equipment/chemical supplier requirements.

**Second-opinion review:** GPT-5.4 — Agree. Separating domestic/fire uses from process-water scheduling is the right frame, and the cited EPA pretreatment guidance supports treating every process inlet and discharge route as an engineered decision rather than a normal-building assumption.

### FAC-018 — What water quality and treatment are required?

**State:** Exploring

Evaluate hardness, minerals, conductivity, corrosion, filtration, reverse
osmosis, deionization, bacterial control, and machine or chemical supplier
requirements.

**First-pass answer:** No single shop-water specification is defensible.
Domestic water must be potable; each process must be checked against OEM and
chemical-supplier limits for hardness, chloride, conductivity, microbes, pH,
and dissolved solids. Treat only the streams that need it, then monitor the
parameter that protects coolant life, corrosion control, cleaning, or additive
quality.

**Evidence:**
- [EPA drinking-water regulations](https://www.epa.gov/dwreginfo/drinking-water-regulations) — **Evidence class:** primary official regulatory guidance. Potability standards address human consumption, not necessarily machine/process suitability.
- [Haas coolant-maintenance guidance](https://www.haascnc.com/service/troubleshooting-and-how-to/how-to/coolant-maintenance.html) — **Evidence class:** OEM practitioner guidance. Coolant concentration, water condition, contamination, and maintenance affect machine and process performance.

**Conflicting evidence:** RO/DI water can reduce minerals but may be corrosive or
incompatible when used without the chemical supplier's formulation; municipal
water that meets drinking standards may still harm a process.

**Confidence:** High that requirements are process-specific; low on treatment
selection.

**Business-specific validation:** Lab-test source water across seasons and have
each coolant, washer, additive, humidification, and equipment supplier approve
the proposed treatment and control limits.

**Second-opinion review:** GPT-5.4 — Agree. Potable water standards do not define process suitability, so stream-by-stream treatment and supplier approval are the right controls. The answer appropriately avoids claiming one universal hardness, chloride, or conductivity target.

### FAC-019 — Which discharges may enter sanitary sewer?

**State:** Exploring

Determine limits and pretreatment for coolant, oils, cleaners, metals, wash
water, chemicals, floor cleaning, and process wastewater. Do not assume floor
drains may receive industrial waste.

**First-pass answer:** Only domestic sewage and process streams expressly
accepted by the local publicly owned treatment works may enter sanitary sewer.
Coolant, oils, solvents, metals, concentrated cleaners, and unknown floor-wash
water require characterization; they may need segregation, pretreatment,
permitting, hauled disposal, or prohibition. Nothing industrial belongs in a
storm drain.

**Evidence:**
- [EPA National Pretreatment Program](https://www.epa.gov/npdes/national-pretreatment-program) — **Evidence class:** primary official regulatory guidance. Industrial users are subject to national prohibitions, categorical requirements where applicable, and local limits.
- [EPA industrial wastewater](https://www.epa.gov/npdes/industrial-wastewater) — **Evidence class:** primary official regulatory guidance. Process-water discharge is regulated by discharge route and pollutant source.

**Conflicting evidence:** A sewer connection or prior tenant practice is not
approval for a new waste stream; some authorities accept treated metalworking
wastewater while others impose stricter local limits or prohibit it.

**Confidence:** High on the prohibition against assumption; local acceptance is
unknown.

**Business-specific validation:** Inventory and sample every discharge, map all
drains, submit SDSs and expected flow/concentrations to the sewer authority,
and obtain a permit or written determination with environmental-professional
review.

**Second-opinion review:** GPT-5.4 — Agree. The conclusion is correctly conservative: only domestic sewage and explicitly accepted industrial streams belong in sanitary sewer, and prior tenant practice or existing drains are not proof of present permission.

### FAC-020 — Are floor drains desirable, permitted, or prohibited?

**State:** Exploring

Consider spills, washdown, containment, sewer connection, oil-water
separation, plugging, environmental liability, and insurer preference.

**First-pass answer:** Floor drains are desirable only where a defined
washdown/emergency function has a permitted destination and engineered
containment. In machining, chemical, and waste areas, default to curbed or
sealed floors and closed collection unless the sewer authority, environmental
professional, fire engineer, and insurer approve a trapped, labeled,
monitorable connection.

**Evidence:**
- [EPA Industrial Floor Drains and Ground Water Quality](https://archive.epa.gov/region9/water/archive/web/pdf/industrialdrainagewells.pdf) — **Evidence class:** primary official enforcement guidance. Improper industrial drains can become prohibited disposal/injection pathways.
- [FM DS 7-32, Ignitable Liquid Operations](https://www.fm.com/FMAApi/data/ApprovalStandardsDownload?itemId=%7B0E226C82-CA57-47C4-8ECB-E384AA589D34%7D) — **Evidence class:** insurer loss-control guidance. Drainage, containment, and fire protection must be coordinated for ignitable-liquid operations.

**Conflicting evidence:** Drains speed cleanup and firefighting-water removal,
but they also create an easy route for unpermitted discharge and can spread
fire or contamination.

**Confidence:** High on conditional use; property-specific on permission and
design.

**Business-specific validation:** Smoke/dye-test and camera-map every drain,
verify records and outfalls, obtain written discharge approval, and have the
design reviewed by environmental, plumbing/fire, and insurance professionals.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Defaulting machining, chemical, and waste areas toward sealed or curbed collection is prudent because unverified floor drains create real environmental and fire-routing risk, but engineered drains can still be justified where washdown or firefighting-water control has an approved destination.

### FAC-021 — How are stormwater and outdoor material areas controlled?

**State:** Exploring

Include loading, scrap, waste, pallets, oil, coolant, dumpsters, spill
containment, snow, and outdoor equipment.

**First-pass answer:** Keep oils, coolant, chips, powders, scrap, waste,
dumpsters, and loading residues indoors or under cover and away from storm
inlets; use secondary containment, protected transfer points, spill kits, drain
maps, snow/yard procedures, and inspection records. Determine whether the site
needs industrial-stormwater permit coverage or qualifies for a documented
no-exposure exclusion.

**Evidence:**
- [EPA stormwater discharges from industrial activities](https://www.epa.gov/npdes/stormwater-discharges-industrial-activities) — **Evidence class:** primary official regulatory guidance. Covered industrial stormwater generally requires permit coverage and pollution-prevention controls.
- [EPA industrial stormwater fact sheets](https://www.epa.gov/npdes/stormwater-discharges-industrial-activities-fact-sheets-and-guidance) — **Evidence class:** primary official sector guidance. Good housekeeping, exposure control, inspections, and sector-specific practices address pollutants at industrial sites.

**Conflicting evidence:** Outdoor storage saves indoor area, but coverings and
containment can fail in wind, snow, traffic, or firefighting; local permits may
be more stringent than federal examples.

**Confidence:** High on controls; medium on permit applicability.

**Business-specific validation:** Delineate drainage areas/outfalls, inspect in
rain, inventory exposed materials, and have the state/local permitting
authority or environmental professional document permit or no-exposure status.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly centers exposure control, containment, inspections, and permit/no-exposure determination; outdoor convenience is not contrary evidence because it usually shifts cost into weather, stormwater, and spill-control risk.

## Gases, Fuel, and Chemicals

### FAC-022 — Which bottled or bulk gases are required?

**State:** Exploring

Potential gases include argon, carbon dioxide, oxygen, nitrogen, shielding-gas
mixtures, laser gases, propane, natural gas, and compressed breathing or
instrument air where applicable.

**First-pass answer:** The likely startup gases are shielding gas for the
selected welding processes and possibly oxygen/fuel gas for cutting; nitrogen
or other laser/additive gases apply only to the chosen machines. Select
cylinders, microbulk, bulk, pipeline natural gas, or generated nitrogen from
measured consumption, purity, peak flow, delivery reliability, code quantity
limits, and total installed cost.

**Evidence:**
- [OSHA 29 CFR 1910.101](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.101) — **Evidence class:** binding U.S. regulation. Compressed-gas cylinders and systems must meet recognized safe-design, handling, and inspection requirements.
- [Lincoln Electric shielding-gas guide](https://www.lincolnelectric.com/en/welding-and-cutting-resource-center/process-and-theory/shielding-gas-guide) — **Evidence class:** supplier technical guidance. Gas selection depends on welding process, material, transfer mode, and desired result rather than a universal shop list.

**Conflicting evidence:** Bulk delivery can reduce unit cost and changeovers at
stable volume but creates siting, permitting, evaporation/minimum-use, and
supplier-lock risks for a small shop.

**Confidence:** Medium; process/equipment choices are not final.

**Business-specific validation:** Create an annual/peak gas mass balance from
weld procedures and machine manuals, obtain cylinder and bulk quotes, and have
the gas supplier, fire AHJ, insurer, and environmental/building authorities
approve the installation.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The likely startup gases are plausible for welding and cutting, but the list should stay explicitly tied to chosen processes and exact machines; bulk versus cylinders is a measured-consumption and siting decision, not a generic small-shop norm.

### FAC-023 — How are cylinders stored and distributed?

**State:** Exploring

Address indoor/outdoor storage, separation, securing, caps, ventilation,
manifolds, piping, flashback protection, leak detection, signage, quantities,
fire code, delivery, and empty/full segregation.

**First-pass answer:** Store upright, secured, capped when not connected,
identified, protected from damage/heat, and segregated by hazard as required;
provide ventilated approved locations, safe delivery paths, compatible
manifolds/piping and regulators, flashback/check devices where applicable, and
quantity control. Indoor versus outdoor storage and separation distances are
local fire-code/AHJ decisions based on actual gases and quantities.

**Evidence:**
- [OSHA 29 CFR 1910.253](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.253) — **Evidence class:** binding U.S. regulation. Oxygen-fuel gas systems have specific cylinder storage, separation, handling, and equipment rules.
- [NFPA free access to codes and standards](https://www.nfpa.org/customer-support/how-can-i-access-nfpa-codes-and-standards-for-free) — **Evidence class:** official standards-body access. NFPA 55 and adopted fire codes provide gas-specific quantity, location, ventilation, piping, and protection requirements.

**Conflicting evidence:** Generic “20-foot separation” advice does not cover all
gases, cabinets, barriers, sprinkler conditions, or adopted-code editions.

**Confidence:** High on handling principles; medium on layout until gas/quantity
and jurisdiction are known.

**Business-specific validation:** Submit a gas inventory and plan to the fire
marshal; obtain supplier-engineered manifold/piping drawings, leak/pressure
tests, permits, signage, training, and insurer approval.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The storage and distribution principles are correct, but local fire-code edition, actual gas chemistry, aggregate quantity, cabinet/barrier details, and supplier manifold design are the decisive specifics; generic separation-distance advice is not enough.

### FAC-024 — Which flammable, combustible, corrosive, or hazardous materials are stored?

**State:** Exploring

Include coolant, oils, solvents, cleaners, adhesives, paint, coatings, welding
supplies, batteries, powders, and waste.

**First-pass answer:** The answer must come from a live chemical/material
inventory and SDS review, including production materials, maintenance products,
batteries, gases, powders, and wastes. Record maximum container and aggregate
quantity, physical hazard, incompatibilities, use/storage location, waste
status, and emergency reporting threshold; prohibit unapproved purchases.

**Evidence:**
- [OSHA 29 CFR 1910.1200, Hazard Communication](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.1200) — **Evidence class:** binding U.S. regulation. Employers must maintain hazard information, labels, SDS access, and employee training.
- [EPA hazardous-waste generators](https://www.epa.gov/hwgenerators) — **Evidence class:** primary official regulatory guidance. Generator category and duties follow the waste actually generated and accumulated.
- [EPA EPCRA](https://www.epa.gov/epcra) — **Evidence class:** primary official regulatory guidance. Certain hazardous chemicals and releases trigger state/local emergency-planning and reporting duties.

**Conflicting evidence:** An SDS “nonhazardous” classification does not alone
settle fire-code, sewer, waste, environmental, or customer restrictions.

**Confidence:** High on the inventory method; low on actual materials before
process purchasing is frozen.

**Business-specific validation:** Have EHS counsel/professional review every SDS,
waste determination, maximum quantity, local fire-code threshold, and EPCRA/
state reporting obligation before occupancy.

**Second-opinion review:** GPT-5.4 — Agree. A live inventory plus SDS and waste review is the only defensible way to answer this question. The first-pass answer properly resists turning vague startup assumptions into a chemical-storage conclusion.

### FAC-025 — What cabinets, rooms, containment, ventilation, and reporting are required?

**State:** Exploring

Derive requirements from actual materials, quantities, safety data, fire code,
environmental rules, and insurer conditions.

**First-pass answer:** Provide listed cabinets, dedicated rooms, containment,
ventilation, alarms, and reporting only as derived from each material's hazard,
maximum quantity, transfer/use method, adopted building/fire code, environmental
rules, and insurer conditions. Separate incompatible materials and design spill
control and exhaust as part of the fire strategy, not as after-market storage.

**Evidence:**
- [OSHA 29 CFR 1910.106](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.106) — **Evidence class:** binding U.S. regulation. Flammable-liquid storage, cabinets, rooms, transfer, and handling have quantity- and use-specific requirements.
- [FM DS 7-32, Ignitable Liquid Operations](https://www.fm.com/FMAApi/data/ApprovalStandardsDownload?itemId=%7B0E226C82-CA57-47C4-8ECB-E384AA589D34%7D) — **Evidence class:** insurer loss-control guidance. It coordinates containment, drainage, ventilation, ignition control, and fire protection.
- [EPA EPCRA](https://www.epa.gov/epcra) — **Evidence class:** primary official regulatory guidance. Reporting depends on chemical and quantity thresholds.

**Conflicting evidence:** A rated cabinet can increase allowable safe storage but
does not legalize excessive quantities, incompatible storage, or uncontrolled
dispensing.

**Confidence:** High on derivation; local and material-specific on details.

**Business-specific validation:** Have the fire marshal, EHS professional,
mechanical/fire-protection engineer, and insurer review the inventory, control
areas, maximum allowable quantities, ventilation, drainage, and emergency plan.

**Second-opinion review:** GPT-5.4 — Agree. Cabinets, rooms, containment, ventilation, and reporting should indeed be derived from actual hazards and quantities, and the cited OSHA, insurer, and EPCRA sources support treating storage and use controls as part of the fire and spill strategy rather than afterthoughts.

## Ventilation, Air Quality, and Thermal Control

### FAC-026 — Which processes require source capture or dedicated exhaust?

**State:** Exploring

Include welding fumes, laser smoke, metal and polymer additive emissions,
coolant mist, grinding dust, cutting fumes, solvent vapor, cleaning, heat, and
combustion products.

**First-pass answer:** Provide capture at the source for welding/cutting fumes,
coolant mist, grinding and combustible dust, laser/additive emissions, solvent
vapors, and combustion products whenever exposure/hazard assessment or the OEM
requires it. Do not rely on general room dilution for concentrated, toxic,
flammable, or combustible emissions; keep incompatible dust streams separate.

**Evidence:**
- [OSHA 29 CFR 1910.94](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.94) — **Evidence class:** binding U.S. regulation. Certain abrasive, grinding, spray, and ventilation applications require engineered controls.
- [OSHA metalworking-fluid controls](https://www.osha.gov/metalworking-fluids/solutions) — **Evidence class:** primary official workplace guidance. Enclosures and local exhaust are preferred controls for mist exposure.
- [NIOSH Additive Manufacturing Program](https://www.cdc.gov/niosh/research-programs/portfolio/additive-manufacturing.html) — **Evidence class:** primary official research/guidance. Additive processes can present process- and feedstock-specific particle, chemical, and physical hazards.

**Conflicting evidence:** Enclosed modern machines may need less room exhaust,
but opening, cleaning, filter change, powder handling, and maintenance can be
the highest-exposure tasks.

**Confidence:** High on source-control hierarchy; medium on required rates and
systems.

**Business-specific validation:** Conduct task-based industrial-hygiene and
combustible-dust assessments; obtain OEM emission/exhaust data and stamped
mechanical/fire design; commission capture velocity, balance, alarms, and
exposure.

**Second-opinion review:** GPT-5.4 — Agree. Source capture at the emission point is the right default for fumes, mist, dust, vapor, and additive emissions, and the first-pass answer correctly rejects room dilution as a substitute for concentrated or combustible hazards.

### FAC-027 — How are makeup air, heating, and cooling sized?

**State:** Exploring

Account for exhaust, machine heat, compressors, lasers, people, doors, climate,
humidity, process stability, and energy cost.

**First-pass answer:** Size makeup air and HVAC from a room-by-room heat/moisture
and pressure balance that includes code/OEM exhaust, machine and compressor
rejection, lights/people, door infiltration, solar/weather design conditions,
desired pressure relationships, and future loads. Temper and interlock makeup
air with exhaust so capture works without drafts, freezing, or uncontrolled
negative pressure.

**Evidence:**
- [OSHA Technical Manual, ventilation investigation](https://www.osha.gov/otm/section-3-health-hazards/chapter-3) — **Evidence class:** primary official technical guidance. Effective industrial ventilation depends on capture, duct, air cleaner, fan, makeup air, and commissioning as a system.
- [DOE Better Buildings, industrial](https://betterbuildingssolutioncenter.energy.gov/industrial) — **Evidence class:** primary official operating guidance. Industrial energy and building/process systems should be evaluated together rather than by nameplate alone.

**Conflicting evidence:** Air changes per hour are convenient but can miss
source capture and process heat; oversized HVAC can short-cycle and control
humidity poorly.

**Confidence:** High on load-based design; low on tonnage/airflow before
equipment and climate are fixed.

**Business-specific validation:** Require a licensed mechanical engineer's load
and air-balance model, utility-rate analysis, OEM heat-rejection values, TAB and
commissioning reports, and seasonal trend logging.

**Second-opinion review:** GPT-5.4 — Agree. Room-by-room heat, moisture, exhaust, infiltration, and pressure modeling is the right sizing method. The cited OSHA and DOE sources support system-level design rather than simplistic air-change or tonnage rules of thumb.

### FAC-028 — Which areas require tighter temperature or humidity control?

**State:** Exploring

Potential areas include metrology, material conditioning, additive
manufacturing, electronics, customer parts, coatings, adhesives, and offices.

**First-pass answer:** The metrology room and any precision cell normally need
the tightest stable temperature and lowest gradients; additive feedstock,
electronics, coatings/adhesives, and customer materials need the limits in
their OEM/SDS/specifications. General machining/fabrication can use a broader
comfort range only where thermal error, condensation, material conditioning,
and worker exposure remain controlled.

**Evidence:**
- [NIST, ISO 1 reference temperature revision](https://www.nist.gov/publications/2016-revision-iso-1-150-standard-reference-temperature-specification-geometrical-and) — **Evidence class:** primary official metrology research. Dimensional properties are referenced to 20 °C, and nonstandard temperatures require attention to thermal effects.
- [Haas TM-1 pre-installation guide](https://www.haascnc.com/owners/pre-install-guide/mills-pre-install/TM-1.html) — **Evidence class:** OEM technical data. The machine has an allowable operating environment, but that broad survival range is not a precision guarantee.

**Conflicting evidence:** Holding an entire plant at laboratory conditions may
be uneconomic; a perfect inspection room cannot correct thermally unstable
parts, tools, fixtures, coolant, or machines.

**Confidence:** High on zoning by process; medium on setpoints.

**Business-specific validation:** Convert drawing tolerances and material CTEs
into a thermal error budget; obtain OEM/material limits and perform temperature/
humidity mapping during representative production.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The conclusion that metrology and precision work usually need tighter stability is sound, but some precision operations can remain in broader ambient conditions if capability studies, compensation, and acclimation practices prove the thermal budget is still met.

### FAC-029 — How are noise and vibration controlled?

**State:** Exploring

Consider compressors, saws, lasers, machining, grinding, forklifts, cranes,
neighbors, offices, metrology, hearing conservation, foundations, and
isolation.

**First-pass answer:** Control noise first by equipment selection, enclosure,
isolation, maintenance, and source placement; then separate compressors,
grinding, saws, docks, and vibration-sensitive metrology/office areas. Perform
personal exposure and property-boundary measurements; establish hearing
conservation where OSHA action levels are reached and address tonal/impact
noise and structure-borne vibration separately.

**Evidence:**
- [OSHA 29 CFR 1910.95](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.95) — **Evidence class:** binding U.S. regulation. Occupational exposure, monitoring, engineering/administrative controls, and hearing conservation are regulated.
- [NIOSH Buy Quiet](https://www.cdc.gov/niosh/noise/prevent/buy-quiet.html) — **Evidence class:** primary official prevention guidance. Purchasing quieter equipment is generally more effective than relying on PPE after installation.

**Conflicting evidence:** Area dBA averages can hide worker dose, impulse noise,
low-frequency vibration, or boundary nuisance; isolation pads can also impair
machine stability if not engineered.

**Confidence:** High on hierarchy; site-specific on controls.

**Business-specific validation:** Obtain vendor sound/vibration data, conduct
baseline and operating dosimetry/boundary surveys, and have an acoustical/
structural engineer design isolation for sensitive equipment and neighbors.

**Second-opinion review:** GPT-5.4 — Agree. The hierarchy of quieter equipment, enclosure, isolation, maintenance, and measured exposure is well supported. The answer also correctly distinguishes worker dose and boundary nuisance from simple area dBA averages.

### FAC-062 — What ambient temperature range is acceptable for each manufacturing process?

**State:** Exploring

Derive the range from part size, material thermal expansion, tolerance,
machine thermal behavior, fixture, process heat, coolant, cycle duration, and
inspection method rather than selecting one universal shop temperature.

**First-pass answer:** Use each OEM's permissible range only as an equipment-
operation boundary. Set narrower process ranges from a thermal error budget
covering part/fixture/tool/coolant/machine temperatures, material expansion,
tolerance, cycle time, and inspection compensation. Precision work may require
stable near-reference conditions; welding and rough fabrication usually do not.

**Evidence:**
- [NIST, 20 °C reference-temperature history](https://www.nist.gov/publications/20-degrees-celsius-short-history-standard-reference-temperature-industrial-dimensional) — **Evidence class:** primary official metrology evidence. Industrial length is referenced to 20 °C because thermal expansion changes measured size.
- [Haas TM-1 pre-installation guide](https://www.haascnc.com/owners/pre-install-guide/mills-pre-install/TM-1.html) — **Evidence class:** OEM technical data. Its allowable ambient range does not establish part accuracy across that range.

**Conflicting evidence:** A shop can machine accurately away from 20 °C using
equilibrium and compensation, but unknown gradients and mismatched CTEs can
make numerical compensation unreliable.

**Confidence:** High on method; low on ranges before parts and machines are
defined.

**Business-specific validation:** Calculate worst-case thermal error for
representative parts, run capability studies across seasons/shifts, and approve
ranges with manufacturing and quality/metrology professionals.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer correctly separates OEM operating range from process-capability range; acceptable ambient temperature must come from thermal-error analysis, and compensation claims should be treated as provisional until validated on representative parts.

### FAC-063 — Which areas require continuous temperature monitoring and logging?

**State:** Exploring

Potential areas include machine zones, coolant systems, raw stock, WIP,
metrology, gauge storage, additive processes, heat-producing equipment, and
shipping or receiving acclimation.

**First-pass answer:** Continuously log locations where temperature materially
affects acceptance, traceability, safety, or failure response: metrology,
precision cells, critical coolant, conditioned raw/WIP, additive material/
build areas, server/electrical rooms, freezable systems, and receiving
acclimation where applicable. General-shop logging is useful for correlation
but should not create meaningless alarms.

**Evidence:**
- [NIST dimensional-metrology publications](https://www.nist.gov/pml/sensor-science/dimensional-metrology/selected-publications-dimensional-metrology-20-degree) — **Evidence class:** primary official metrology evidence. Temperature and thermal-expansion uncertainty directly affect dimensional results.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT guidance. Monitoring systems that affect operations need defined availability, integrity, segmentation, and response.

**Conflicting evidence:** More sensors do not improve control if placement,
calibration, time synchronization, retention, and response limits are poor.

**Confidence:** Medium until critical processes and error budgets are known.

**Business-specific validation:** Perform a seasonal temperature survey, select
calibrated sensors and locations from the error/risk analysis, and challenge
alarms, data retention, backup, and corrective-action workflows.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Continuous logging belongs where temperature affects acceptance, safety, traceability, or response, not everywhere by default; the caution against sensor sprawl without calibration, alarm logic, and corrective action is well placed.

### FAC-064 — Should precision machining and general fabrication use different thermal zones?

**State:** Exploring

Compare whole-building control with a controlled metrology room, precision
cell, machine enclosure, coolant chiller, localized conditioning, and process-
specific scheduling.

**First-pass answer:** Yes, plan separate thermal zones unless production trials
show the broad shop environment can hold required capability. A stable
metrology room is the minimum; add a precision machining cell, machine/coolant
thermal control, or scheduled equilibration where tolerance and part size
justify it, while leaving welding and general fabrication in a wider band.

**Evidence:**
- [NIST uncertainties at nonstandard temperatures](https://www.nist.gov/publications/uncertainties-dimensional-measurements-made-nonstandard-temperatures) — **Evidence class:** primary official metrology research. Measurement uncertainty depends on object and instrument temperature, CTE knowledge, and gradients.
- [OSHA welding ventilation hazards and solutions](https://www.osha.gov/welding-cutting-brazing/hazards-solutions) — **Evidence class:** primary official workplace guidance. Welding requires emission controls that can conflict with quiet, clean, stable metrology conditions.

**Conflicting evidence:** Multiple zones add walls, HVAC, material acclimation,
and workflow cost; one well-controlled small shop may be simpler.

**Confidence:** Medium; likely appropriate but must be justified by the product
mix.

**Business-specific validation:** Compare capability and HVAC life-cycle cost
for whole-building, room, and cell strategies; map gradients and perform gauge/
process studies with representative parts.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Separate thermal zones are usually the safer design for mixed precision and fabrication work, but they are not automatically mandatory if one zone can demonstrate stable capability, acceptable gradients, and workable contamination control.

### FAC-065 — How quickly may shop temperature change?

**State:** Exploring

Daily rate and spatial gradients may matter as much as the average because
machine castings, fixtures, stock, coolant, and air do not change temperature
at the same rate.

**First-pass answer:** There is no universal allowable rate. Set hourly and
shift limits so differential temperatures among part, fixture, tool, coolant,
machine structure, and measurement system remain within the thermal error
budget; spatial gradients and door/HVAC events often matter more than the
daily mean.

**Evidence:**
- [NIST thermal-expansion uncertainty paper](https://emtoolbox.nist.gov/Publications/NCSLIMeasure011-2006.pdf) — **Evidence class:** primary official metrology research. Temperature measurement, gradients, CTE, and nonequilibrium contribute to dimensional uncertainty.
- [NIST ISO 1 revision](https://doi.org/10.6028/jres.121.026) — **Evidence class:** primary official standards research. Reference temperature standardizes dimensions but does not eliminate real thermal behavior.

**Conflicting evidence:** A slowly changing room can still contain large local
machine/coolant gradients, while validated compensation can tolerate faster
ambient changes for some work.

**Confidence:** Medium; limits need measured process response.

**Business-specific validation:** Log air, machine, coolant, fixture, and part
temperatures with dimensional results through door and HVAC cycles; set control
limits from capability evidence and metrology review.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer correctly says there is no universal rate-of-change limit and that gradients and thermal lag matter more than average room temperature, but the final limits must be process-derived rather than policy-derived.

### FAC-066 — How should exterior doors, receiving, HVAC cycling, sunlight, and machine heat be isolated from precision work?

**State:** Exploring

Evaluate vestibules, air curtains, zoning, insulation, equipment placement,
thermal mass, operating schedule, and acclimation areas.

**First-pass answer:** Put docks, exterior doors, compressors, weld/grind areas,
and major heat sources away from precision work; use enclosed receiving/
vestibules where justified, separate HVAC zones, insulated/low-solar-load
envelopes, shaded equipment, controlled door schedules, and marked acclimation
areas. Air curtains help infiltration but do not replace separation or a
validated thermal plan.

**Evidence:**
- [DOE Building Envelope Campaign](https://betterbuildingssolutioncenter.energy.gov/alliance/technology-solution/building-envelope) — **Evidence class:** primary official building-performance guidance. Envelope and infiltration measures affect thermal loads and stability.
- [NIST thermal-expansion uncertainty paper](https://emtoolbox.nist.gov/Publications/NCSLIMeasure011-2006.pdf) — **Evidence class:** primary official metrology research. Local gradients and uncertain object temperature drive dimensional uncertainty.

**Conflicting evidence:** Physical separation adds travel and floor area; very
low-volume shops may achieve capability through scheduling and equilibration
instead of construction.

**Confidence:** High on isolating disturbances; medium on the chosen measures.

**Business-specific validation:** Map temperatures during worst weather and
door cycles, model receiving/HVAC loads, and validate final zoning with
representative precision parts before acceptance.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Isolating doors, receiving, sunlight, HVAC cycling, and machine heat from precision work is directionally right, yet very low-volume operations may sometimes reach capability by scheduling, acclimation, and local shielding instead of dedicated construction.

## Floors, Structure, Height, and Lifting

### FAC-030 — What floor loading and slab conditions are required?

**State:** Exploring

Record machine weight, footprint, point load, anchoring, thickness,
reinforcement, cracks, flatness, isolation, pits, and engineered foundations.

**First-pass answer:** Accept a slab only after a structural engineer compares
as-built thickness, reinforcement, subgrade, and condition with each machine's
total, point, anchor, dynamic, leveling, and pit loads. Scan and core where
records are unreliable; address joints, cracks, buried utilities, flatness,
vibration sources, and removal/rigging paths before commitment.

**Evidence:**
- [Haas TM-1 pre-installation guide](https://www.haascnc.com/owners/pre-install-guide/mills-pre-install/TM-1.html) — **Evidence class:** OEM installation manual. Foundation thickness, level condition, anchoring, and location are model-dependent prerequisites.
- [OSHA 29 CFR 1910.22](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.22) — **Evidence class:** binding U.S. regulation. Floors and walking-working surfaces must support intended loads and remain safe.

**Conflicting evidence:** A slab that carried prior machinery may still be
unsuitable for a different footprint, anchor pattern, tolerance, or dynamic
load; an isolated foundation may be unnecessary for a lighter tool.

**Confidence:** High on engineering verification; property-specific on adequacy.

**Business-specific validation:** Obtain sealed OEM foundation drawings, GPR/
rebar scans, cores/geotechnical information where needed, level/crack survey,
and a stamped structural acceptance or remediation design.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Requiring structural verification before accepting the slab is correct, and that is the part that should carry the most weight; OSHA walking-surface rules are supporting context, not proof that a slab can carry specific machine loads.

### FAC-031 — What clear height and structural capacity are required?

**State:** Exploring

Include cranes, rigging, machine height, ductwork, utilities, fire protection,
lights, material storage, laser loading, automation, and future equipment.

**First-pass answer:** Clear height must accommodate the tallest machine in all
service/loading positions plus crane/rigging, duct, cable tray, lighting, and
sprinkler clearances. Roof framing, columns, joists, mezzanines, and attachment
points must be checked for existing and future suspended loads; a high roof
does not imply crane or collector capacity.

**Evidence:**
- [OSHA 29 CFR 1910.179](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.179) — **Evidence class:** binding U.S. regulation. Overhead crane rated load, clearances, inspection, and operation require known capacity.
- [FM DS 2-0, automatic sprinklers](https://www.fm.com/FMAApi/data/ApprovalStandardsDownload?itemId=%7b88A80534-F010-4A3A-93D8-DDA8AA724B53%7d) — **Evidence class:** insurer loss-control guidance. Obstructions, storage height, and geometry affect sprinkler performance.

**Conflicting evidence:** High bays add flexibility but increase HVAC volume
and may still have low joists, sprinklers, doors, or weak structure at the
required path.

**Confidence:** High on criteria; property-specific on capacity.

**Business-specific validation:** Laser-survey clearances, obtain structural
drawings and field verification, and have structural, fire-protection, rigging,
and OEM reviewers approve every load and envelope.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly separates nominal roof height from usable clear height and from actual structural capacity for suspended loads and sprinkler/duct conflicts; no contrary evidence defeats the need for survey and structural review.

### FAC-032 — Is an overhead crane required?

**State:** Exploring

Compare bridge crane, jib cranes, gantry, forklift, pallet stacker, lift table,
and outside rigging based on loads, frequency, reach, building structure,
inspection, training, and cost.

**First-pass answer:** Install an overhead crane only when recurring load,
frequency, reach, precision, and safe path cannot be handled economically by a
forklift, lift table, jib, gantry, or contracted rigger. The device must cover
machine loading and maintenance without passing suspended loads over people or
blocked egress, and the building must support it.

**Evidence:**
- [OSHA 29 CFR 1910.179](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.179) — **Evidence class:** binding U.S. regulation. Rated loads, inspections, testing, maintenance, and safe operation apply to overhead cranes.
- [OSHA 29 CFR 1910.178](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.178) — **Evidence class:** binding U.S. regulation. Forklift selection, modification, operation, charging/fueling, and training impose different facility constraints.

**Conflicting evidence:** A crane frees aisles and improves reach but locks in
structural cost and coverage; mobile devices are flexible but consume floor
space and may offer less load control.

**Confidence:** Medium until the load matrix and frequency are measured.

**Business-specific validation:** Create a lift register with weight, center of
gravity, frequency, origin/destination, height, and precision; conduct qualified
rigging/ergonomic and structural reviews with insurer input.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The decision framework is sound: an overhead crane is a handling option, not a default amenity. The lift matrix, safe path, structural capacity, and frequency of recurring moves should decide between crane, jib, forklift, gantry, or outsourced rigging.

### FAC-033 — What material-handling paths and capacities are required?

**State:** Exploring

Include forklift capacity, turning radius, aisle, door, dock, ramp, floor,
pedestrian separation, long stock, sheets, pallets, machine loading, scrap,
and battery charging or fuel.

**First-pass answer:** Design routes from the largest credible load and vehicle:
capacity at load center, turning/swing envelope, overhead/door/dock clearance,
grade and floor rating, rack interface, long-stock tail swing, and emergency
stopping. Separate pedestrians with barriers or controlled crossings where
feasible and provide a compliant charging/fueling area.

**Evidence:**
- [OSHA 29 CFR 1910.178](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.178) — **Evidence class:** binding U.S. regulation. Truck rating, visibility, ramps, loading, charging, and training govern the system.
- [OSHA 29 CFR 1910.176](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.176) — **Evidence class:** binding U.S. regulation. Material handling must provide safe clearances and stable storage.

**Conflicting evidence:** Painted lines are inexpensive but provide little
protection; barriers improve separation but can reduce maneuvering and emergency
access if poorly placed.

**Confidence:** High on design method; medium on dimensions.

**Business-specific validation:** Select vehicles/attachments, mock up routes at
full scale, test the worst load and sight lines, and have safety, structural,
fire, and operations reviewers approve traffic and charging plans.

**Second-opinion review:** GPT-5.4 — Agree. Designing routes from the largest credible load and vehicle is the right basis for aisle width, turning, clearance, stopping, grade, and pedestrian protection; cheap visual separation alone is not strong contrary evidence when impact risk is credible.

## Receiving, Delivery, and Vehicle Access

### FAC-034 — What truck and delivery access is required?

**State:** Exploring

Evaluate semitrailers, box trucks, parcel vans, flatbeds, riggers, cranes,
turning, road limits, gates, queues, backing, appointments, neighbors, snow,
and operating hours.

**First-pass answer:** The site should accept the longest/heaviest routine
vehicle and occasional machine rigger without unsafe public-road backing:
verify legal routes, bridge/weight/height limits, turning and staging, gate and
dock geometry, crane setup, appointments, snow storage, lighting, and neighbor/
hour restrictions. Satellite imagery is not proof of access.

**Evidence:**
- [FHWA National Network overview](https://ops.fhwa.dot.gov/freight/sw/overview/) — **Evidence class:** primary official transport guidance. Federal truck-network rules are only part of route legality; state/local restrictions remain.
- [FHWA Freight Analysis Framework](https://ops.fhwa.dot.gov/freight/freight_analysis/faf/) — **Evidence class:** official freight data. It supports regional screening, not parcel-level turning or permit confirmation.

**Conflicting evidence:** A site convenient for parcel/LTL may be poor for
flatbeds and riggers; oversize permits can enable occasional moves but add cost
and schedule risk.

**Confidence:** Medium until vehicle and parcel tests are complete.

**Business-specific validation:** Drive routes with carrier/rigger
representatives, perform swept-path and grade analysis, confirm local
restrictions in writing, and stage a representative delivery.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer correctly rejects parcel photos and generic freight data as proof of access; route legality, turning, rigging setup, and neighbor/hour constraints need field testing and written local confirmation.

### FAC-035 — How many docks and drive-in doors are needed?

**State:** Exploring

Consider initial and future volume, parcel, LTL, machinery, long stock, sheet,
customer pickup, simultaneous receiving and shipping, and secure after-hours
delivery.

**First-pass answer:** No universal dock or drive-in count is defensible. A
starter shop may operate with one dock-height position and one grade-level
drive-in door only if actual freight, machine moves, and scheduling keep queues
and unsafe crossings acceptably low. Add separate positions when measured
concurrent shipping/receiving, long stock, sheet, customer pickup, machinery,
after-hours deliveries, or resilience needs justify them.

**Evidence:**
- [OSHA 29 CFR 1910.26](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.26) — **Evidence class:** binding U.S. regulation. Dockboards must support loads, be secured, and provide safe handling.
- [OSHA 29 CFR 1910.178](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.178) — **Evidence class:** binding U.S. regulation. Loading/unloading requires secured vehicles, adequate capacity, lighting, and safe truck operation.

**Conflicting evidence:** Extra docks reduce queueing but consume wall, yard,
and conditioned space; one door lowers cost but creates a common failure point.

**Confidence:** Medium; transaction data is missing.

**Business-specific validation:** Model peak appointments and unload times,
include machinery/long-stock exceptions, observe comparable practice, and test
year-three volume in the block layout.

**Second-opinion review:** GPT-5.4 — Disagree. The original answer stated a baseline door mix too confidently for evidence this local and volume-dependent, so it was revised to remove any implied standard count and to make concurrency, machine delivery, and resilience the deciding factors.

### FAC-036 — What dock, ramp, door, and yard dimensions are required?

**State:** Exploring

Record dock height, leveler, door width/height, trailer clearance, grade,
canopy, lighting, security, and weather protection.

**First-pass answer:** Dimensions must follow the actual trailer, forklift,
flatbed, crane, long-stock, and machine envelopes. Survey dock height and
leveler range, door clear opening, apron depth/grade, drainage, trailer
approach, canopy/sprinkler conflicts, lighting, fall protection, restraint/
security, and winter operation; prove one path for the largest planned machine.

**Evidence:**
- [OSHA 29 CFR 1910.26](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.26) — **Evidence class:** binding U.S. regulation. Dockboards require adequate capacity, anchoring, run-off protection, and safe handling.
- [OSHA 29 CFR 1910.178](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.178) — **Evidence class:** binding U.S. regulation. Trailer loading, ramps, grades, lighting, floor condition, and truck operation affect safe dimensions.

**Conflicting evidence:** Nominal “standard dock” dimensions can fail with low
trailers, liftgates, snow, steep aprons, swing doors, or unusual machine moves.

**Confidence:** High on vehicle-specific verification; low on dimensions until
the fleet is known.

**Business-specific validation:** Obtain a survey and swept-path/grade study,
collect carrier/rigger vehicle data, and conduct witnessed trials with
representative trailers and handling equipment.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly insists that door, dock, apron, ramp, and yard dimensions follow the actual vehicle and machine envelopes rather than nominal “standard dock” assumptions; witnessed trials are the strongest available contrary-evidence check.

### FAC-037 — How are receiving, quarantine, inspection, storage, and production connected?

**State:** Exploring

Prevent unidentified or uninspected material and customer returns from entering
available inventory or production.

**First-pass answer:** Use one-way status-controlled flow: unload into a bounded
receiving area; identify and record ownership/condition; hold in pending
inspection/quarantine; release only through an authorized transaction into
location-controlled stock; route returns and nonconforming material to distinct
locked or marked areas. Physical and digital status must agree.

**Evidence:**
- [NIST MEP, Lean and process improvement](https://www.nist.gov/mep/lean-and-process-improvement) — **Evidence class:** primary official practitioner guidance. Flow and visual control reduce travel, waiting, and mix-ups.
- [FDA 21 CFR 117.80](https://www.ecfr.gov/current/title-21/chapter-I/subchapter-B/part-117/subpart-B/section-117.80) — **Evidence class:** binding sector regulation used as a control example. It separates receiving, inspection, storage, and contamination/status control; applicability depends on product sector.

**Conflicting evidence:** Small shops may use marked floor zones rather than
rooms, but shared unbounded areas depend on discipline and fail during peaks.

**Confidence:** High on status separation; medium on physical form.

**Business-specific validation:** Walk sample receipts/returns through a scaled
layout, run peak-volume and wrong-status challenge tests, and align ERP/barcode
permissions with physical release and quarantine controls.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. One-way status-controlled flow is the right operating principle, but the FDA citation is only an analogy for segregation discipline; the binding proof for this business will be its own quality system, customer requirements, and inventory controls.

## Physical Layout and Flow

### FAC-038 — What is the preferred high-level process flow?

**State:** Exploring

Potential flow:

```text
receiving
-> identification and inspection
-> raw storage
-> stock preparation
-> machining/fabrication/additive
-> deburr and clean
-> inspection
-> outside-process staging
-> assembly
-> finished-goods control
-> packaging
-> shipping
```

**First-pass answer:** Use this sequence as the default, arranged to minimize
backtracking and crossings while preserving branches for outside processing,
rework, and distinct fabrication/additive flows. Put quality gates and status
locations at transitions; locate shared deburr/clean, inspection, tooling,
maintenance, and shipping by measured travel and contamination needs.

**Evidence:**
- [NIST MEP, Lean and process improvement](https://www.nist.gov/mep/lean-and-process-improvement) — **Evidence class:** primary official practitioner guidance. Value-stream and flow analysis target excess transport, waiting, inventory, and motion.
- [OSHA 29 CFR 1910.176](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.176) — **Evidence class:** binding U.S. regulation. Handling and storage clearances must remain safe through the flow.

**Conflicting evidence:** A strict linear layout can be poor for high-mix
routing and shared resources; cells shorten travel but may duplicate equipment.

**Confidence:** Medium until routing and volume data exist.

**Business-specific validation:** Build from-to charts and spaghetti diagrams
from representative jobs, simulate peak WIP, and pilot taped block layouts with
operators, handlers, quality, maintenance, and safety.

**Second-opinion review:** GPT-5.4 — Agree. The proposed default flow is a good starting point because it minimizes transport and preserves explicit quality gates, while still admitting branches for outside processing, rework, and non-linear high-mix routing.

### FAC-039 — Which flows must be separated?

**State:** Exploring

Consider people and forklifts, raw and finished goods, clean and dirty work,
customer returns, nonconforming material, welding and metrology, flammable
storage, additive powder, food and shop chemicals, visitors, and confidential
projects.

**First-pass answer:** Physically separate people from vehicle travel where
credible impact exists; released from quarantined/nonconforming/returned
material; clean metrology/electronics/food from dust, mist, fumes, chemicals,
and hot work; incompatible hazardous materials; powder handling; and visitors
from confidential or hazardous production. Use access and pressure/exhaust
zoning where walls alone are insufficient.

**Evidence:**
- [OSHA 29 CFR 1910.176](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.176) — **Evidence class:** binding U.S. regulation. Safe material-handling clearances and storage are mandatory.
- [OSHA 29 CFR 1910.141](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.141) — **Evidence class:** binding U.S. regulation. Food and sanitary facilities must be protected from toxic materials and contamination.
- [NIOSH Additive Manufacturing Program](https://www.cdc.gov/niosh/research-programs/portfolio/additive-manufacturing.html) — **Evidence class:** primary official research/guidance. Powder and emission hazards require process-specific controls.

**Conflicting evidence:** Dedicated rooms improve control but reduce flexibility
and area; marked zones may suffice for low-hazard, low-volume status separation.

**Confidence:** High on categories; medium on barrier type.

**Business-specific validation:** Perform flow, industrial-hygiene, fire,
security, and customer-confidentiality assessments; challenge separations at
peak operation and during tours.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The separation categories are right, but barrier type should remain risk-based: some status and visitor separations need hard barriers or pressure control, while lower-hazard cases may be handled with robust marked zones and access discipline.

### FAC-040 — How much service and maintenance clearance does every machine require?

**State:** Exploring

Include electrical cabinets, chip conveyors, coolant tanks, doors, panels,
spindle service, rigging, tool changers, robots, guarding, and removal path.

**First-pass answer:** Reserve the maximum OEM envelope for operation, guarding,
all doors/panels/cabinets fully open, chip/coolant removal, filter and major-
component replacement, safe isolation, ladders/lifts, rigging, and eventual
removal. Clearance is model/configuration-specific and must remain protected
from WIP creep.

**Evidence:**
- [Haas TM-1 pre-installation guide](https://www.haascnc.com/owners/pre-install-guide/mills-pre-install/TM-1.html) — **Evidence class:** OEM installation manual. Delivery, placement, service access, utilities, and foundation preparation are explicit inputs.
- [OSHA 29 CFR 1910.303](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.303) — **Evidence class:** binding U.S. regulation. Electrical working space and access cannot be used for storage.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Maintenance layout must support effective energy isolation.

**Conflicting evidence:** OEM minimum clearance may permit service but not local
rigging or safe concurrent work; excessive clearance wastes floor.

**Confidence:** High on model-specific envelopes.

**Business-specific validation:** Obtain OEM general-arrangement/service
drawings, conduct maintenance and rigging reviews, tape the envelope, and make
clearance a facility acceptance item.

**Second-opinion review:** GPT-5.4 — Agree. Service and maintenance clearance must remain model-specific and protected from storage creep; the cited OEM and OSHA sources support treating service access as mandatory floor demand rather than optional convenience.

### FAC-041 — How should modular automation cells influence layout?

**State:** Exploring

Allow safe tending, material presentation, fencing, scanners, sensors, cameras,
maintenance, manual fallback, and later reconfiguration.

**First-pass answer:** Reserve cells as replaceable modules with defined utility
boundaries, safeguarded perimeter and access, robot/load motion envelopes,
operator presentation zones, scanner/camera sight lines, maintenance withdrawal
space, and a safe manual mode. Keep main aisles, egress, fire protection, and
common utilities independent of later cell changes.

**Evidence:**
- [OSHA robot systems technical manual](https://www.osha.gov/otm/section-4-safety-hazards/chapter-4) — **Evidence class:** primary official technical guidance. Hazards change across programming, setup, operation, maintenance, and safeguarding modes.
- [NIOSH safe maintenance guidelines for robotic workstations](https://stacks.cdc.gov/view/cdc/158177) — **Evidence class:** primary official technical guidance. Maintenance and troubleshooting require designed access, isolation, procedures, and trained personnel.

**Conflicting evidence:** Large generic fenced boxes ease later changes but
consume area and worsen reach; close-fit cells improve density but can strand
infrastructure.

**Confidence:** Medium until automation concepts and payloads exist.

**Business-specific validation:** Require integrator risk assessments and
validated safeguarding, simulate material and human access including faults,
and price one documented reconfiguration and manual-continuity mode.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Modular automation boundaries, safeguarding, and withdrawal space are correctly emphasized, but exact cell footprint depends on the chosen safety concept, presentation method, and manual-fallback expectations, so this should stay at principle level for now.

### FAC-042 — How should customer-visible flow differ from actual secure production flow?

**State:** Exploring

Support transparency without exposing confidential customer work, unsafe
areas, security controls, or misleading simplified status.

**First-pass answer:** Create a controlled visitor route to a meeting, viewing,
and acceptance area with safe demonstrations and selected digital status. It
should not traverse normal forklift/hot-work zones or reveal other customers'
parts, screens, drawings, labels, or networks. Externally shown production
records must be accurate, permissioned, and intentionally redacted.

**Evidence:**
- [CISA physical security guidance](https://www.cisa.gov/topics/physical-security) — **Evidence class:** primary official risk guidance. Layered access, visitor control, monitoring, and response protect facilities and assets.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT-security guidance. OT access and network pathways require segmentation and controls appropriate to safety and availability.

**Conflicting evidence:** Open tours build trust, but permanent visual openness
creates safety, confidentiality, export-control, and customer-consent problems.

**Confidence:** High on controlled separation; medium on dedicated-space value.

**Business-specific validation:** Obtain customer confidentiality/export
requirements, perform sight-line and wireless/security tests, and run a mock
tour with safety, legal/security, operations, and quality observers.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. A controlled visitor path and curated status view are sound for safety and confidentiality, but dedicated customer-facing space should still be justified by actual tour, acceptance, and export-control needs rather than assumed as permanent overhead.

## Inspection, Office, Customer, and IT Space

### FAC-043 — What metrology and inspection space is required?

**State:** Exploring

Consider temperature, humidity, vibration, cleanliness, lighting, granite,
CMM, gauges, calibration storage, quarantine, documentation, and customer
source inspection.

**First-pass answer:** Provide a clean, low-vibration, access-controlled area
sized for receiving/in-process/final queues, granite/CMM and gauge service
envelopes, calibration storage, documentation, and source inspection. Control
temperature to the needed uncertainty; provide acclimation and visible
quarantine/status boundaries.

**Evidence:**
- [NIST dimensional-metrology publications](https://www.nist.gov/pml/sensor-science/dimensional-metrology/selected-publications-dimensional-metrology-20-degree) — **Evidence class:** primary official metrology evidence. Temperature, CTE, gradients, and equilibrium affect results.
- [ISO/IEC 17025 overview](https://www.iso.org/isoiec-17025-testing-and-calibration-laboratories.html) — **Evidence class:** official standards-body summary. Competent measurement requires controlled methods, equipment, environment, records, and validity.

**Conflicting evidence:** A dedicated laboratory improves control but can delay
feedback and create acclimation errors; in-process gauging may be better for
some characteristics.

**Confidence:** High on functions; low on area and control band.

**Business-specific validation:** Build an uncertainty budget and peak queue/
part-size model, map vibration/temperature, obtain CMM foundation and service
data, and validate measurement capability after installation.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer correctly calls for controlled metrology space and acclimation, but not all inspection work must sit in a separate laboratory; fast in-process gauging may outperform lab isolation for some features if uncertainty is still controlled.

### FAC-044 — What office and collaboration space is required?

**State:** Exploring

Include engineering, programming, quoting, purchasing, scheduling, quality,
customer meetings, secure calls, document review, and future staffing.

**First-pass answer:** Provide quiet secure workpoints for startup staffing plus
rooms for confidential calls/design reviews, production coordination, document
review, and customer meetings. Keep programmers/engineers close enough for shop
feedback but acoustically and environmentally separated; use flexible shared
space until sustained headcount justifies dedicated offices.

**Evidence:**
- [OSHA computer workstation eTool](https://www.osha.gov/etools/computer-workstations) — **Evidence class:** primary official workplace guidance. Workstation layout, lighting, seating, and equipment arrangement affect ergonomic risk.
- [ADA 2010 Standards](https://www.ada.gov/law-and-regs/design-standards/2010-stds/) — **Evidence class:** binding U.S. accessibility standard where applicable. Routes, doors, rooms, and amenities must be accessible.

**Conflicting evidence:** Open plans improve quick coordination but increase
noise and confidentiality problems; private offices consume area and separate
engineering from production.

**Confidence:** Medium; staffing and work pattern remain unresolved.

**Business-specific validation:** Create startup/year-three headcount and
adjacency schedules, observe work modes, test acoustic privacy, and have the
architect review occupancy, accessibility, ventilation, and egress.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The adjacency and acoustic-separation logic is sound, but the cited evidence supports ergonomics and accessibility more than office quantity, so confidence should remain tied to actual headcount, collaboration pattern, and privacy needs.

### FAC-045 — Is a customer viewing or acceptance area needed?

**State:** Exploring

Balance tours, design reviews, first articles, inspection, safety, access,
confidentiality, and disruption.

**First-pass answer:** Include a modest controlled room with safe viewing and
access to an inspection/acceptance setup if source inspection, first-article
review, or tours are part of the sales model. Do not build a large showroom
before customer evidence; a flexible conference room and scheduled acceptance
zone can serve startup needs.

**Evidence:**
- [CISA physical security guidance](https://www.cisa.gov/topics/physical-security) — **Evidence class:** primary official risk guidance. Visitor access should be layered, authorized, monitored, and separated from protected operations.
- [ADA 2010 Standards](https://www.ada.gov/law-and-regs/design-standards/2010-stds/) — **Evidence class:** binding U.S. accessibility standard where applicable. Customer routes and facilities require accessible design.

**Conflicting evidence:** A viewing area can shorten acceptance and build trust,
but it consumes scarce space and can expose confidential work.

**Confidence:** Medium.

**Business-specific validation:** Interview target customers about source-
inspection/tour frequency, conduct confidentiality and safety walkthroughs,
and pilot acceptance in a flexible room before permanent construction.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. A modest controlled acceptance/viewing area is reasonable only if source inspection, tours, or first-article reviews are genuinely part of the sales model; the answer is strongest where it keeps flexible space as the startup default.

### FAC-046 — What IT, server, network, and security space is required?

**State:** Exploring

Consider internet diversity, firewall, switches, edge computers, cameras,
servers, backups, UPS, cooling, physical access, machine-network isolation,
wireless coverage, and future cabling.

**First-pass answer:** Provide locked, cooled, clean, dry, UPS-backed network/
server space with rated power and grounding, fire detection, cable pathways,
diverse internet where justified, controlled access, backups, and expansion.
Segment business IT, machine/OT, cameras/access control, guests, and vendor
remote access; do not place core equipment on the open shop floor.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT-security guidance. OT requires segmentation, controlled remote access, physical protection, backups, monitoring, and availability-aware controls.
- [CISA Cross-Sector Cybersecurity Performance Goals](https://www.cisa.gov/cross-sector-cybersecurity-performance-goals) — **Evidence class:** primary official baseline guidance. Inventory, backups, segmentation, access control, logging, and incident response reduce common risks.

**Conflicting evidence:** Cloud services reduce onsite servers but do not remove
switches, edge controls, cameras, internet dependency, UPS, or recovery needs;
two carriers may share one physical route.

**Confidence:** High on architecture; medium on space and redundancy.

**Business-specific validation:** Produce logical/physical diagrams, trace
carrier routes, calculate heat/load, perform wireless and recovery tests, and
assess customer security obligations with IT/OT owners.

**Second-opinion review:** GPT-5.4 — Agree. Locked, cooled, UPS-backed, segmented IT/OT space is the right baseline, and the cited NIST and CISA guidance supports the separation of business IT, OT, guest, and remote-vendor access as a facility requirement rather than a later patch.

## Employee Facilities

### FAC-047 — How many restrooms are required?

**State:** Exploring

Determine fixture count, accessibility, occupancy, plumbing, showers where
needed, visitors, cleaning, and future headcount.

**First-pass answer:** Fixture count is determined by the locally adopted
building/plumbing code and design occupant load, with accessible fixtures and
routes; OSHA also sets minimum workplace toilet availability and prompt access.
Size for year-three headcount and visitors, and verify locally permitted
single-user or sex-designated arrangements.

**Evidence:**
- [OSHA 29 CFR 1910.141](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.141) — **Evidence class:** binding U.S. regulation. It sets sanitation, potable-water, washing, and toilet-access requirements.
- [ADA 2010 Standards](https://www.ada.gov/law-and-regs/design-standards/2010-stds/) — **Evidence class:** binding U.S. accessibility standard where applicable. Toilet rooms, fixtures, doors, clearances, and routes have accessibility requirements.

**Conflicting evidence:** OSHA employee-count minima do not replace local
plumbing-code calculations and may not cover visitors or future occupancy.

**Confidence:** High on method; local on count.

**Business-specific validation:** Have the architect/code official calculate
occupant load and fixtures for startup/year three, confirm accessibility and
shift assumptions, and document occupancy limits.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly separates OSHA sanitation minima from the locally adopted plumbing/building code count, and it appropriately sizes to future occupancy and accessibility rather than current convenience alone.

### FAC-048 — What break room or kitchen facilities are appropriate?

**State:** Exploring

Include seating, refrigeration, microwave, sink, potable water, food storage,
cleaning, vending, shift overlap, separation from chemicals and production,
and applicable health or building rules.

**First-pass answer:** Provide a clean area physically separated from chemicals
and production contamination, with washable surfaces, potable water/hand-
washing, sink where utensils are cleaned, refrigerator, microwave, waste, and
seats for the largest overlapping break. A commercial kitchen is unnecessary
unless food preparation/service triggers health requirements.

**Evidence:**
- [OSHA 29 CFR 1910.141](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.141) — **Evidence class:** binding U.S. regulation. Food, potable water, waste, vermin control, and sanitary facilities must be protected from contamination.
- [ADA 2010 Standards](https://www.ada.gov/law-and-regs/design-standards/2010-stds/) — **Evidence class:** binding U.S. accessibility standard where applicable. Employee common-use spaces and routes require accessible design.

**Conflicting evidence:** A larger kitchen may aid retention and shifts but
increases cleaning, plumbing, pest, and health-code burden.

**Confidence:** High on baseline; medium on size/equipment.

**Business-specific validation:** Model shift overlap and meal practices, ask
the local building/health authority whether appliances constitute food service,
and have employees review the layout.

**Second-opinion review:** GPT-5.4 — Agree. A clean, separated break area with potable water, basic appliances, and seating sized to the largest overlapping break is a sound baseline; the answer also correctly avoids overbuilding into commercial food-service obligations without evidence.

### FAC-049 — Are lockers, changing rooms, showers, or laundry needed?

**State:** Exploring

Consider PPE, welding, coolant, dust, heat, contamination, uniforms, personal
belongings, and separation of clean and dirty clothing.

**First-pass answer:** Provide lockers and clean/dirty changing arrangements
when PPE, fluids, dust, welding residue, powders, heat, or uniforms should not
travel home or enter food/office areas. Showers/laundry are required only where
exposure standards, SDSs, customer controls, or industrial-hygiene findings
justify them; use controlled laundering for contaminated garments.

**Evidence:**
- [OSHA 29 CFR 1910.141](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.141) — **Evidence class:** binding U.S. regulation. Change rooms, washing, and sanitation are required in specified circumstances.
- [OSHA 29 CFR 1910.1200](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.1200) — **Evidence class:** binding U.S. regulation. SDSs and hazard assessment inform contamination and hygiene controls.

**Conflicting evidence:** Universal showers/laundry may be unnecessary for
enclosed low-exposure machining, while street clothes can transfer hazards.

**Confidence:** Medium until exposure and work practices are known.

**Business-specific validation:** Have an industrial hygienist review tasks,
materials, and wipe/air data; define PPE/uniform policy and have plumbing/code
professionals size any facilities.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Lockers and clean/dirty separation are often justified earlier than showers or laundry, and the first-pass answer is right to require exposure, SDS, or customer evidence before adding the more burdensome facilities.

### FAC-050 — What employee parking and transportation access is required?

**State:** Exploring

Include shifts, visitors, accessible parking, bicycles, public transit,
carpooling, snow, lighting, security, and future headcount.

**First-pass answer:** Size onsite parking from peak overlapping startup and
year-three shifts plus visitors, service vehicles, accessible spaces, and snow
loss—not average headcount. Provide safe lit walking routes and evaluate
bicycle/transit access, charging demand, drainage, security, and local zoning
minimums or maximums.

**Evidence:**
- [ADA parking requirements](https://www.ada.gov/topics/parking/) — **Evidence class:** primary official accessibility guidance. Accessible-space counts, dimensions, signage, and routes depend on total parking and use.
- [FHWA pedestrian and bicycle safety](https://highways.dot.gov/safety/pedestrian-bicyclist) — **Evidence class:** primary official transport-safety guidance. Site access should protect walking and cycling movements.

**Conflicting evidence:** Zoning ratios can oversupply land; transit assumptions
can fail in industrial areas or off-hours.

**Confidence:** Medium; local rules and staffing are unresolved.

**Business-specific validation:** Count peak arrivals by shift/visitor/vendor,
map accessible and winter routes, verify transit schedules, and obtain written
zoning/site-plan approval.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Peak-overlap parking is the right metric, but local zoning/site-plan requirements, snow storage loss, transit reality, and shift mix can dominate the final answer, so the conclusion should remain site-specific.

### FAC-051 — What first-aid, wellness, and emergency facilities are required?

**State:** Exploring

Consider first-aid supplies, eyewash, safety shower, AED, heat stress, potable
water, emergency communication, muster, and access for responders.

**First-pass answer:** Provide accessible first-aid supplies and trained
responders based on injury risk and EMS response; compliant eyewash/body
flushing wherever corrosives can injure; potable water, heat/cold controls,
emergency communication, marked muster, and responder access. Add an AED where
the medical/response assessment supports it.

**Evidence:**
- [OSHA 29 CFR 1910.151](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.151) — **Evidence class:** binding U.S. regulation. First-aid readiness and immediate flushing/drenching are required based on medical access and corrosive exposure.
- [OSHA heat exposure](https://www.osha.gov/heat-exposure) — **Evidence class:** primary official workplace guidance. Water, rest, acclimatization, planning, and response are core heat controls.

**Conflicting evidence:** Purchased eyewash bottles or AEDs create false
assurance without correct placement, flow, inspection, training, and response.

**Confidence:** High on risk-based provision; medium on exact equipment.

**Business-specific validation:** Conduct first-aid/corrosive/heat and EMS
response assessments with occupational health/EHS professionals; have AHJ/EMS
walk the site and drill emergencies.

**Second-opinion review:** GPT-5.4 — Agree. The risk-based framing is correct: first-aid, eyewash/drenching, potable water, heat controls, responder access, and emergency communication should follow actual hazards and medical-response conditions, not a generic small-shop checklist.

## Fire, Life Safety, and Security

### FAC-052 — What fire detection and suppression are required?

**State:** Exploring

Derive from building construction, occupancy, processes, storage, gases,
flammables, combustible dust, batteries, unattended operation, and insurer
requirements.

**First-pass answer:** Design from occupancy, construction, storage arrangement/
height, liquids/gases, hot work, dust/powders, batteries, collectors, concealed
spaces, and unattended operation. Retain or upgrade automatic sprinklers where
feasible, provide monitored alarms, use special systems only where analysis
requires them, and manage impairment/shutdown states.

**Evidence:**
- [FM DS 2-0, automatic sprinklers](https://www.fm.com/FMAApi/data/ApprovalStandardsDownload?itemId=%7b88A80534-F010-4A3A-93D8-DDA8AA724B53%7d) — **Evidence class:** insurer loss-control guidance. Design depends on hazard, storage, obstruction, water supply, and details.
- [OSHA 29 CFR 1910 Subpart L](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910SubpartL) — **Evidence class:** binding U.S. regulation. Fire detection, suppression, alarms, and employee response have operational requirements.
- [NFPA free access](https://www.nfpa.org/customer-support/how-can-i-access-nfpa-codes-and-standards-for-free) — **Evidence class:** official standards-body access. Adopted NFPA provisions and local code determine design.

**Conflicting evidence:** Existing sprinklers may be inadequate for new storage,
plastics, liquids, powders, obstructions, or water supply.

**Confidence:** High on hazard-based design; property-specific on systems.

**Business-specific validation:** Obtain fire-flow tests and design/impairment
records, then require licensed fire-protection engineer, AHJ, and insurer review
of exact processes and storage.

**Second-opinion review:** GPT-5.4 — Agree. Hazard-based fire-protection design is the right conclusion, and the answer correctly warns that existing sprinklers or alarms may not remain adequate once storage, liquids, powders, batteries, or unattended operation change the exposure.

### FAC-053 — What egress and emergency-access requirements shape layout?

**State:** Exploring

Include exits, travel distance, aisle width, door swing, emergency lighting,
signage, fire-department access, and obstruction by WIP or equipment.

**First-pass answer:** Freeze compliant exit routes and responder access before
placing machines or WIP. Determine occupant load, exit number/remoteness and
capacity, travel/common-path/dead-end limits, door swing/hardware, illumination/
signage, accessible egress, and fire-lane/hydrant/FDC access for the maximum
storage and fenced-cell layout—not an empty floor.

**Evidence:**
- [OSHA 29 CFR 1910.36](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.36) — **Evidence class:** binding U.S. regulation. Exit routes require adequate number, capacity, height/width, separation, protection, and discharge.
- [OSHA 29 CFR 1910.37](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.37) — **Evidence class:** binding U.S. regulation. Routes must remain unobstructed, illuminated, marked, and maintained.

**Conflicting evidence:** Code-minimum compliance may still be poor for forklift
interactions, mobility limitations, smoke, or rapid metal/chemical fire.

**Confidence:** High on requirements; local on dimensions and alternatives.

**Business-specific validation:** Have the architect/fire engineer and fire
marshal review maximum occupancy/storage, then conduct evacuation and responder-
access drills before acceptance.

**Second-opinion review:** GPT-5.4 — Agree. Freezing egress and responder access before machine placement is the right design order; the answer appropriately treats maximum storage and fenced-cell conditions, not an empty floor, as the real compliance case.

### FAC-054 — What physical security is required?

**State:** Exploring

Consider perimeter, doors, alarms, cameras, access control, visitors, shipping,
customer property, tools, metals, controlled data, after-hours automation, and
law-enforcement response.

**First-pass answer:** Use layered security: defined perimeter and lighting;
reinforced controlled doors; monitored intrusion/fire alarms and cameras;
visitor/vendor management; separated shipping, customer-property, tool, and
metal storage; role-based access to production, IT/OT, drawings, and keys; and
tested after-hours response. Controls should follow asset/threat risk.

**Evidence:**
- [CISA physical security](https://www.cisa.gov/topics/physical-security) — **Evidence class:** primary official risk guidance. Layered deterrence, detection, delay, access control, and response protect facilities.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT-security guidance. Physical and logical OT access and remote connectivity require coordinated controls.

**Conflicting evidence:** Restrictive controls can slow flow and encourage
bypass; cameras investigate but do not physically delay theft.

**Confidence:** High on layered approach; medium on control level.

**Business-specific validation:** Inventory assets/customer obligations, obtain
crime/response data, conduct a professional threat assessment, test alarm
response/blind spots, and secure insurer/customer approval where required.

**Second-opinion review:** GPT-5.4 — Agree. Layered physical security tied to asset and threat value is the correct approach, and the cited CISA/NIST sources support integrating perimeter, visitor control, monitored detection, and protected OT/IT access.

### FAC-055 — How does reduced-supervision or unattended operation change facility requirements?

**State:** Exploring

Evaluate fire detection, thermal monitoring, remote alarms, automatic shutdown,
sprinklers, utility isolation, intrusion, emergency response, insurer approval,
and prohibited processes.

**First-pass answer:** Reduced supervision raises protection requirements:
approved automatic detection/suppression, thermal/utility/process monitoring,
fail-safe shutdown and isolation, remote alarms with named responders,
intrusion protection, power/network continuity, and explicit prohibition of
hot work, powder transfer, hazardous maintenance, or other unvalidated tasks.
Remote video is not an engineered safe state.

**Evidence:**
- [FM property loss-prevention data sheets](https://www.fm.com/resources/fm-data-sheets) — **Evidence class:** insurer loss-control guidance. Fire protection, equipment safeguards, human factors, and impairment management control unattended property loss.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** primary official OT-security guidance. Remote access, monitoring, safety, availability, and response require deliberate architecture.
- [OSHA 29 CFR 1910.147](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.147) — **Evidence class:** binding U.S. regulation. Automation does not remove hazardous-energy duties during intervention.

**Conflicting evidence:** OEM cycle capability is not evidence of insurer
acceptance, fire safety, cyber resilience, or safe jam/utility-fault recovery.

**Confidence:** High that safeguards are required; low on allowed processes.

**Business-specific validation:** Perform process risk and failure-injection
tests with OEM/integrator, fire engineer, EHS, cybersecurity, AHJ, and insurer;
obtain written approval of unattended modes and response time.

**Second-opinion review:** GPT-5.4 — Agree. The answer properly rejects remote video as a substitute for engineered safe state and insurer acceptance; reduced-supervision operation does increase facility, monitoring, shutdown, and response requirements.

## Waste, Scrap, and Housekeeping

### FAC-056 — How are metal chips, scrap, coolant, oil, filters, chemicals, packaging, and ordinary waste handled?

**State:** Exploring

Define containers, segregation, drainage, fire, indoor/outdoor storage,
pickup, recycling, records, spill control, and value recovery.

**First-pass answer:** Characterize each stream, then use labeled compatible
closed containers; segregate valuable alloys, oily chips, liquids, filters,
hazardous waste, batteries, universal waste, cardboard, and ordinary trash;
protect drains and weather exposure; schedule pickups before capacity limits;
and retain weights, profiles/manifests, recycling, spill, and vendor records.

**Evidence:**
- [EPA hazardous-waste generators](https://www.epa.gov/hwgenerators) — **Evidence class:** primary official regulatory guidance. Waste determination, accumulation, labeling, inspections, time/quantity limits, and shipment depend on generator status.
- [EPA Sustainable Materials Management](https://www.epa.gov/smm) — **Evidence class:** primary official guidance. Source reduction, reuse, recycling, and material tracking generally outrank disposal.
- [EPA industrial stormwater](https://www.epa.gov/npdes/stormwater-discharges-industrial-activities) — **Evidence class:** primary official regulatory guidance. Outdoor waste and scrap exposure can require stormwater controls and permitting.

**Conflicting evidence:** Recycling vendors may call a stream nonhazardous or
valuable, but the generator remains responsible for correct characterization
and lawful management; oily fines can create fire and leakage hazards.

**Confidence:** High on the system; low on classifications before testing.

**Business-specific validation:** Sample/profile every waste with an
environmental professional, verify recycler/hauler permits and contracts,
confirm fire-code quantities/locations, and audit the first three shipments.

**Second-opinion review:** GPT-5.4 — Agree. Stream-by-stream characterization, compatible containers, segregation, drain/weather protection, and documented pickups are the right controls; recycler claims are not credible contrary evidence against generator responsibility.

### FAC-057 — What housekeeping and cleaning infrastructure is required?

**State:** Exploring

Include chip and dust removal, floor cleaning, spill kits, vacuums, laundry,
parts cleaning, waste stations, tools, storage, schedules, and responsibilities.

**First-pass answer:** Provide point-of-use chip/scrap containers, compatible
industrial vacuums, spill kits, absorbent and drain covers, floor-cleaning
equipment, parts-cleaning stations, PPE/laundry controls, waste points, and
closed storage. Ban unsafe compressed-air dispersal and dry sweeping of
combustible/toxic dust; assign frequencies and ownership by zone and shift.

**Evidence:**
- [OSHA 29 CFR 1910.22](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.22) — **Evidence class:** binding U.S. regulation. Workplaces and walking surfaces must be clean, orderly, sanitary, and free of hazards.
- [OSHA combustible dust](https://www.osha.gov/combustible-dust) — **Evidence class:** primary official workplace guidance. Dust accumulation and dispersal can create flash-fire/explosion hazards requiring appropriate collection and housekeeping.
- [OSHA 29 CFR 1910.242](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.242) — **Evidence class:** binding U.S. regulation. Compressed-air cleaning is restricted and requires guarding/PPE.

**Conflicting evidence:** Aggressive cleaning can aerosolize hazards or wash
pollutants to drains; insufficient cleaning raises slip, fire, quality, and
equipment risks.

**Confidence:** High on infrastructure categories; material-specific on methods.

**Business-specific validation:** Conduct dust/exposure and waste-stream reviews,
approve vacuum classifications and cleaning chemicals, time trial tasks by
zone, and audit housekeeping under peak production.

**Second-opinion review:** GPT-5.4 — Agree. The infrastructure list is sound, and the answer correctly bans unsafe compressed-air dispersal and indiscriminate sweeping of hazardous dust because cleaning method can create the exposure or fire risk it is supposed to remove.

## Facility Selection and Acceptance

### FAC-058 — What mandatory criteria disqualify a building?

**State:** Exploring

Potential examples include incompatible zoning, inadequate power with no
feasible upgrade, insufficient slab or access, prohibited processes, poor fire
protection, unsafe egress, unresolved contamination, or no practical expansion.

**First-pass answer:** Disqualify a property for any unwaivable gate: no written
legal use/occupancy path; no feasible utility, sewer, fire-water, slab/
structure, height, truck/rigging, ventilation, egress, accessibility, or hazard-
quantity solution; unacceptable contamination/flood or neighbor constraint; no
landlord right to required work; or upgrade cost/time that misses the business
case. Do not let weighted scoring override a failed gate.

**Evidence:**
- [EPA All Appropriate Inquiries](https://www.epa.gov/brownfields/brownfields-all-appropriate-inquiries) — **Evidence class:** official environmental due-diligence framework. Environmental history and recognized conditions require qualified review before acquisition.
- [FEMA Flood Map Service Center](https://msc.fema.gov/portal/home) — **Evidence class:** primary official hazard data. Official flood information is a mandatory screen, though site drainage and future risk need additional review.
- [SWEPCO Electric Service Handbook](https://www.swepco.com/lib/docs/builders/meter/2024SWEPCOElectricServiceHandbook.pdf) — **Evidence class:** actual utility service rules. Service feasibility depends on utility facilities, customer work, inspection, access, and lead time.

**Conflicting evidence:** Many deficiencies are technically repairable, but a
feasible engineering fix may still be uneconomic or too slow; a pristine
building can still fail zoning.

**Confidence:** High on gate logic; thresholds are business-specific.

**Business-specific validation:** Approve numeric mandatory gates and maximum
cost/time before tours; require written authority, utility, engineer,
environmental, insurer, landlord, and lender findings for every finalist.

**Second-opinion review:** GPT-5.4 — Agree. Mandatory disqualification gates are the right protection against false economy, and the answer correctly requires written authority, utility, engineering, environmental, insurer, landlord, and lender evidence rather than optimistic assumptions.

### FAC-059 — What improvements and costs are required before occupancy?

**State:** Exploring

Estimate design, permitting, electrical, utilities, ventilation, fire,
plumbing, structure, floors, docks, security, offices, employee areas,
networking, rigging, and commissioning.

**First-pass answer:** Produce a permit-ready scope and bottom-up budget covering
professional surveys/design, approvals, demolition/remediation, utility and
electrical work, foundations, exhaust/HVAC/air/gas/water/drainage, fire/life
safety, docks/doors/cranes, offices/employee areas, security/network, equipment
delivery/rigging, testing, escalation, contingency, taxes, and startup downtime.
Separate landlord, utility, and tenant obligations.

**Evidence:**
- [BLS Producer Price Index](https://www.bls.gov/ppi/) — **Evidence class:** primary official economic data. Construction and equipment input prices change over time, so dated estimates need escalation and quote refresh.
- [SBA licenses and permits](https://www.sba.gov/business-guide/launch-your-business/apply-licenses-permits) — **Evidence class:** primary official guidance. Permit scope and fees vary by activity and jurisdiction.
- [Dominion Energy electric-service requirements](https://www.dominionenergy.com/-/media/content/start-stop-service/new-construction/pdfs/global/information-and-requirements-for-electric-service-blue-book.pdf) — **Evidence class:** actual utility service rules. Customer and utility work, access, inspection, and sequencing affect cost and schedule.

**Conflicting evidence:** Broker or contractor allowances are fast but often
exclude design, utility-side work, hazardous materials, shutdowns,
commissioning, and escalation.

**Confidence:** High on cost categories; low on amount before site design/bids.

**Business-specific validation:** Obtain coordinated drawings, permit/AHJ
comments, utility work order, at least budgetary trade quotes, landlord scope,
and independent estimator/contingency review before commitment.

**Second-opinion review:** GPT-5.4 — Agree. The bottom-up pre-occupancy cost model is well framed and appropriately separates landlord, tenant, and utility obligations; no credible contrary evidence suggests broker allowances or rough square-foot allowances are sufficient for this scope.

### FAC-060 — How is a candidate facility scored?

**State:** Exploring

Use mandatory gates plus weighted cost, customer, labor, supplier, logistics,
tax, utility, building, safety, community, expansion, and resilience criteria.

**First-pass answer:** Use pass/fail mandatory gates first; then score verified
total occupancy cost, startup time, customer/labor/supplier/logistics access,
utility/building/process fit, safety/environmental/community risk, resilience,
and expansion. Define weights before seeing finalist economics, record evidence
and uncertainty, and run sensitivity analysis so a subjective score does not
hide a fatal assumption.

**Evidence:**
- [FEMA National Risk Index](https://www.fema.gov/flood-maps/products-tools/national-risk-index) — **Evidence class:** primary official screening data. It supports consistent natural-hazard comparison but is not parcel engineering.
- [EPA Smart Location Database](https://www.epa.gov/smartgrowth/smart-location-mapping) — **Evidence class:** official planning data. Accessibility and location measures can support comparative screening.
- [FHWA Freight Analysis Framework](https://ops.fhwa.dot.gov/freight/freight_analysis/faf/) — **Evidence class:** official freight data. Regional freight context can be compared consistently but needs local route validation.

**Conflicting evidence:** Weighted matrices appear objective but outcomes can be
manipulated by weights and correlated criteria; simple lowest-cost rankings
ignore delay and failure risk.

**Confidence:** High on method; medium on weights.

**Business-specific validation:** Leadership pre-approves gates/weights and
tests scenarios; professionals verify high-impact inputs; retain a decision
record showing scores, source date, owner, and uncertainty.

**Second-opinion review:** GPT-5.4 — Agree. Pass/fail gates before weighted scoring is the right structure, and the answer correctly warns that sensitivity analysis is needed so a polished matrix does not hide a fatal assumption or overweight correlated criteria.

### FAC-061 — What acceptance testing is required before installing or operating equipment?

**State:** Exploring

Verify utilities, grounding, ventilation, fire systems, structural capacity,
egress, environmental controls, communications, safety systems, and permits.

**First-pass answer:** Before rigging, verify permits/occupancy, slab/anchors and
route, service voltage/phase/fault current/grounding, air pressure/flow/dew
point, water/gas/exhaust/HVAC, fire/egress and emergency systems, drainage/
discharge, network/security, and OEM environmental limits. Before production,
commission under load, test alarms/interlocks/shutdown/restart, baseline
environment and quality, close deficiencies, and obtain authority/OEM signoff.

**Evidence:**
- [Haas TM-1 pre-installation guide](https://www.haascnc.com/owners/pre-install-guide/mills-pre-install/TM-1.html) — **Evidence class:** OEM installation manual. It supplies concrete pre-delivery requirements for utilities, foundation, location, and access.
- [OSHA 29 CFR 1910.303](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.303) — **Evidence class:** binding U.S. regulation. Electrical equipment must be suitable, installed per instructions, guarded, grounded, and safely accessible.
- [FM property loss-prevention data sheets](https://www.fm.com/resources/fm-data-sheets) — **Evidence class:** insurer loss-control guidance. Fire protection and safeguards require inspection, testing, and impairment control.

**Conflicting evidence:** Utility presence does not prove capacity or quality
under simultaneous load; contractor completion does not prove process
capability.

**Confidence:** High on staged acceptance; model/site-specific on test limits.

**Business-specific validation:** Create signed FAT/SAT and facility checklists
with calibrated instruments and responsible parties; witness full-load and
fault tests with OEMs, engineers, AHJs, insurer, EHS, IT, and quality.

**Second-opinion review:** GPT-5.4 — Agree. Staged acceptance testing before rigging and again before production is the right conclusion; the answer correctly distinguishes utility presence from verified capacity, quality, protection, commissioning, and OEM signoff.

### FAC-067 — What do suitable starter machine-shop and fabrication buildings cost to lease or buy in each candidate region?

**State:** Exploring

**First-pass answer:** No answer is possible until candidate regions, size,
tenure, and mandatory gates are defined. For each region, report asking and
verified transaction ranges separately for compliant or feasibly convertible
industrial properties, normalized to usable production square feet, building
quality, clear height, power, docks, yard, and date; never use metro-wide
warehouse averages as machine-shop cost.

**Evidence:**
- [LoopNet commercial property listings](https://www.loopnet.com/) — **Evidence class:** current supplier/market evidence. Listings expose asking rent/price and property features but not final consideration, concessions, or hidden condition.
- [County Business Patterns](https://www.census.gov/programs-surveys/cbp.html) — **Evidence class:** primary official economic data. Local establishment counts can help define industrial-market context but do not report property prices.
- [BLS Producer Price Index](https://www.bls.gov/ppi/) — **Evidence class:** primary official economic data. Comparisons must be date-adjusted where market and construction inputs move.

**Conflicting evidence:** Broker market reports provide fast averages but mix
submarkets and building types; public sale records omit lease concessions and
condition.

**Confidence:** Low until regions and live properties are researched.

**Business-specific validation:** Engage tenant/buyer representation in each
region, collect a dated comparable set plus executed-deal evidence where
available, tour finalists, and obtain assessor/title, utility, engineering, and
fit-out findings.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer is right that no regional cost claim is credible before the regions and gate criteria are defined, but listing data is only discovery evidence; executed deals, concessions, utility adequacy, and conversion scope will determine the real result.

### FAC-068 — How should advertised rent or purchase price be converted into total usable and production-ready occupancy cost?

**State:** Exploring

Include usable square footage, common-area charges, property tax, insurance,
maintenance, utilities, required improvements, permitting, professional
design, financing, downtime, and restoration obligations.

**First-pass answer:** Model annual and present-value cash flows from possession
through exit: base price/rent on measured usable area; escalations, common-area
charges, taxes, insurance, maintenance and utilities; design/permit/fit-out and
utility contributions; financing/deposits; moving, rigging, commissioning,
downtime and contingency; incentives; and end-of-term removal/restoration and
residual value. Report both cost per usable production square foot and cost per
productive year after startup delay.

**Evidence:**
- [IRS Publication 946](https://www.irs.gov/publications/p946) — **Evidence class:** primary official tax guidance. Building improvements and equipment have differing recovery treatment requiring tax-aware cash flow.
- [SBA guidance on buying assets](https://www.sba.gov/business-guide/manage-your-business/buy-assets-equipment) — **Evidence class:** primary official business guidance. Acquisition decisions require financing, ownership, and cash-flow analysis beyond sticker price.
- [BLS Producer Price Index](https://www.bls.gov/ppi/) — **Evidence class:** primary official economic data. Future construction/maintenance estimates need dated escalation assumptions.

**Conflicting evidence:** Straight-line rent per advertised square foot is easy
to compare but can reverse rankings when unusable area, concessions, upgrades,
delay, and restoration are included.

**Confidence:** High on the model; low on inputs before documents/quotes.

**Business-specific validation:** Measure usable areas, abstract lease/purchase
documents with counsel, obtain utility/contractor quotes, and have CPA/lender
review tax, financing, discount-rate, and residual assumptions.

**Second-opinion review:** GPT-5.4 — Agree. Converting advertised price into productive occupancy cost through possession-to-exit cash flows is the correct economic model, and the answer properly includes usable area, delay, improvements, financing, and restoration instead of comparing sticker rates alone.

### FAC-069 — How do regional construction and fit-out costs differ for power, slab, doors, docks, cranes, ventilation, fire protection, compressed air, drainage, HVAC, security, and offices?

**State:** Exploring

**First-pass answer:** Regional multipliers alone are insufficient. Price the
same concept design and bill of quantities in each candidate region using local
labor/material quotes, code and permit scope, utility contribution/lead time,
climate loads, seismic/wind/frost conditions, contractor availability, taxes,
and escalation. Utility, fire, slab, and environmental scope can dominate the
apparent regional construction differential.

**Evidence:**
- [BLS Occupational Employment and Wage Statistics](https://www.bls.gov/oes/) — **Evidence class:** primary official labor data. Trade labor costs differ materially by metro/state.
- [BLS Producer Price Index](https://www.bls.gov/ppi/) — **Evidence class:** primary official input-price data. National material/equipment cost trends require current pricing.
- [FEMA National Risk Index](https://www.fema.gov/flood-maps/products-tools/national-risk-index) — **Evidence class:** primary official screening data. Regional hazards inform, but do not replace, site-specific design loads and code analysis.

**Conflicting evidence:** Published cost guides normalize early estimates but
can miss small-project premiums, live-building work, scarce trades, utility
extensions, and unusual process hazards.

**Confidence:** Medium on drivers; low on regional ranking without bids.

**Business-specific validation:** Issue one 30%-design pricing package to local
GCs and specialty trades in each region, obtain utility/AHJ feedback, and have
an independent estimator reconcile exclusions and contingency.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer correctly rejects regional multipliers as sufficient, because local quotes, utility contributions, permits, climate loads, and live-building constraints can outweigh published regional averages; confidence should stay low until pricing packages are bid.

### FAC-070 — Which regions have an adequate supply of small industrial buildings that already meet the minimum mandatory gates?

**State:** Exploring

**First-pass answer:** This cannot be answered nationally from vacancy rates.
For each region, query a live property set and count buildings that pass zoning,
usable area/height, three-phase capacity, slab/structure, fire protection,
truck/rigging access, discharge/exhaust feasibility, neighbors, and expansion;
also count near-matches with a priced, permitted conversion path. Report months
of qualifying supply at the planned decision date.

**Evidence:**
- [LoopNet commercial property listings](https://www.loopnet.com/) — **Evidence class:** current supplier/market evidence. It helps discover advertised inventory but is incomplete and self-reported.
- [County Business Patterns](https://www.census.gov/programs-surveys/cbp.html) — **Evidence class:** primary official economic data. Existing manufacturing/industrial establishments help screen industrial ecosystem depth, not building compliance.
- [EPA Smart Location Database](https://www.epa.gov/smartgrowth/smart-location-mapping) — **Evidence class:** official planning data. Regional access variables supplement, but do not replace, parcel due diligence.

**Conflicting evidence:** High industrial vacancy may consist of bulk warehouses
without power/slab/process permission; low-vacancy markets may have off-market
small-shop inventory.

**Confidence:** Low until candidate regions and broker/authority data exist.

**Business-specific validation:** Define gates first; engage local industrial
brokers and economic-development/utility contacts; screen every live and off-
market candidate with written zoning, utility, and engineering evidence.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Vacancy and industrial-establishment counts do not answer this question by themselves; the only reliable test is a live screened inventory plus a priced conversion path for near-matches, so the low confidence is appropriate.

### FAC-071 — How do vacancy, lease terms, landlord cooperation, expansion space, and time-to-occupancy change the practical building cost?

**State:** Exploring

**First-pass answer:** Treat practical cost as risk-adjusted productive
occupancy, not face rent. Vacancy affects negotiating leverage; free rent,
allowances, escalation, options, exclusivity/adjacent-space rights, alteration
and restoration clauses, security, approval response times, and utility/permit
schedule can outweigh nominal rent. Price expected contribution margin lost
until validated production starts and the probability/cost of forced relocation.

**Evidence:**
- [SBA guidance on choosing a business location](https://www.sba.gov/business-guide/launch-your-business/pick-your-business-location) — **Evidence class:** primary official business guidance. Location affects zoning, costs, taxes, regulations, and practical operating fit.
- [BLS Producer Price Index](https://www.bls.gov/ppi/) — **Evidence class:** primary official economic data. Delayed work and deferred fit-out can face changing input prices.
- [FEMA National Risk Index](https://www.fema.gov/flood-maps/products-tools/national-risk-index) — **Evidence class:** primary official screening data. Resilience and interruption exposure can alter practical cost beyond rent.

**Conflicting evidence:** High vacancy can improve terms but may signal weak
location or obsolete stock; a cooperative landlord's promises are valueless
unless written with deadlines/remedies.

**Confidence:** High on cost logic; low on market-specific effects.

**Business-specific validation:** Obtain competing term sheets and redlines,
landlord financial/approval evidence, permit/utility critical paths, option/
expansion rights, and a finance-reviewed delay/relocation scenario model.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Treating practical building cost as risk-adjusted productive occupancy is the right frame, but landlord cooperation, approval speed, and expansion rights only count when written with deadlines and remedies, not when promised informally.

### FAC-072 — When is a cheaper building uneconomic because of utility upgrades, process restrictions, poor flow, insufficient expansion, or delayed startup?

**State:** Exploring

**First-pass answer:** It is uneconomic when its risk-adjusted present value of
fit-out, operating inefficiency, lost contribution during delay, constrained
revenue/capacity, future relocation, and residual/restoration cost exceeds the
next-best compliant property's premium—or when it fails a mandatory safety,
legal, customer, or schedule gate at any price. Set a walk-away threshold before
negotiation and update it with verified quotes and schedules.

**Evidence:**
- [SBA guidance on choosing a business location](https://www.sba.gov/business-guide/launch-your-business/pick-your-business-location) — **Evidence class:** primary official business guidance. Zoning, regional costs, taxes, regulation, and operating fit matter beyond rent.
- [DOE compressed-air sourcebook](https://www.energy.gov/sites/default/files/2016/03/f30/Improving%20Compressed%20Air%20Sourcebook%20version%203.pdf) — **Evidence class:** primary official engineering guidance. Poor utility design creates persistent energy, pressure, reliability, and production penalties.
- [SWEPCO Electric Service Handbook](https://www.swepco.com/lib/docs/builders/meter/2024SWEPCOElectricServiceHandbook.pdf) — **Evidence class:** actual utility service rules. Upgrades depend on utility engineering, customer scope, inspections, facilities, and schedule—not rent.

**Conflicting evidence:** A low-cost building can be rational for a short,
low-capital pilot, but only if restrictions are known, reversible, and do not
block safety, quality, or contracted delivery.

**Confidence:** High on decision rule; low on threshold until economics exist.

**Business-specific validation:** Compare at least three properties in a
finance-reviewed NPV/scenario model using written utility/permit/landlord/
contractor evidence, measured flow penalties, startup critical path, and an
approved walk-away rule.

**Second-opinion review:** GPT-5.4 — Agree. The walk-away rule is correctly based on risk-adjusted present value and mandatory gates, not nominal cheapness. Utility upgrades, process limits, delayed startup, and forced relocation are all credible ways a cheaper building becomes uneconomic.

## Cross-Topic References

- [Business Climate and Community Acceptance](business-climate-and-community-acceptance.md)
- [Raw Materials, Energy, and Input Costs](raw-materials-energy-and-input-costs.md)
- [Freight, Shipping, and Customer Delivery](freight-shipping-and-customer-delivery.md)
- [Tax, Finance, and Capital](tax-finance-and-capital.md)
- [Workforce Cost and Labor Markets](workforce-cost-and-labor-markets.md)
- [Insurance and Risk Financing](insurance-and-risk-financing.md)
- [Manufacturing Processes and Quality](manufacturing-processes-and-quality.md)

## Expected Outputs

- phased facility requirements matrix;
- equipment utility schedule;
- square-footage and block-layout scenarios;
- site and building due-diligence checklist;
- candidate property scorecard;
- utility and infrastructure cost estimate;
- employee-facility plan;
- fire, safety, environmental, and security requirements;
- receiving and shipping flow;
- expansion plan; and
- formal facility design and acceptance requirements.
