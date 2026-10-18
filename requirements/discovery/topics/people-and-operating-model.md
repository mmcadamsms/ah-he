# People and Organization Questions

## Purpose

Questions about roles, employment, qualifications, training, working
conditions, organization, support, and human resilience. Production planning
and shop-control questions are maintained in
[Production Planning and Shop Operations](production-planning-and-shop-operations.md).

These are discovery questions, not approved requirements. Question IDs are permanent and canonical in this file.

## People, Employees, and Organization

### HUM-001 — Which people and roles are required for the first commercially viable version of the business?

**State:** Exploring

**First-pass answer:** The first viable team needs accountable coverage, not necessarily one person per title: business/customer ownership; quoting and order acceptance; manufacturing/process leadership; qualified machine and fabrication execution; independent quality authority; safety and environmental responsibility; maintenance; and finance/administration. Founders may combine roles, but quality release, safety decisions, and financial controls need explicit owners and backups.

**Evidence:**
- [NIST MEP, America's Manufacturing Workforce: Make or Buy?](https://www.nist.gov/blogs/manufacturing-innovation-blog/americas-manufacturing-workforce-make-or-buy) — **Evidence class:** official practitioner guidance. It frames staffing as a mix of short-term purchased capacity and long-term internal capability rather than a fixed organization chart.
- [BLS Occupational Outlook Handbook: Machinists and Tool and Die Makers](https://www.bls.gov/ooh/production/machinists-and-tool-and-die-makers.htm) — **Evidence class:** official occupational data. It identifies the setup, operation, inspection, and technical skills involved in core production work.
- [NIST Baldrige Excellence Framework](https://www.nist.gov/baldrige/publications/baldrige-excellence-framework) — **Evidence class:** official management framework. It treats leadership, customers, operations, workforce, measurement, and results as an integrated operating system.

**Conflicting evidence:** Startup advice often favors extreme generalists, while regulated or high-consequence customers require specialized independence and credentials. The latter should govern wherever law, safety, or contract requires it.

**Confidence:** Medium. The coverage areas are well supported, but the product, processes, customer requirements, and founders' capabilities are not yet known.

**Business-specific validation:** Map the first product and order lifecycle to a RACI, identify legally or contractually independent roles, and have a qualified manufacturing engineer, quality professional, safety professional, CPA, and counsel review the resulting gaps.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The role-coverage conclusion is sound, but the NIST/BLS evidence is general manufacturing guidance and does not prove this business's exact staffing depth or independence needs. Keep the RACI and qualified engineering, quality, safety, tax, and legal review before fixing headcount.

### HUM-002 — Which work should be performed by employees, contractors, specialist firms, suppliers, or software automation?

**State:** Exploring

**First-pass answer:** Keep recurring work that creates product knowledge, customer trust, process control, or safety/quality accountability under direct employee control. Use contractors for intermittent expert work, qualified suppliers for validated special processes or scalable commodity capacity, and software for repeatable calculations, routing, monitoring, and drafting with human approval. Classification must follow the actual control relationship, not the label in a contract.

**Evidence:**
- [IRS: Independent Contractor Defined](https://www.irs.gov/businesses/small-businesses-self-employed/independent-contractor-defined) — **Evidence class:** official guidance. Worker status depends on the facts and the right to control how work is performed.
- [NIST MEP, America's Manufacturing Workforce: Make or Buy?](https://www.nist.gov/blogs/manufacturing-innovation-blog/americas-manufacturing-workforce-make-or-buy) — **Evidence class:** official practitioner guidance. It recommends matching temporary or external capacity and internal development to short- and long-term needs.
- [NIST AI Risk Management Framework](https://www.nist.gov/itl/ai-risk-management-framework) — **Evidence class:** official risk framework. It supports governing automated systems according to risk, accountability, validity, and human oversight rather than treating automation as an accountable actor.

**Conflicting evidence:** Outsourcing can lower fixed cost and add scarce expertise, but it can also weaken learning, responsiveness, and process control. Automation can reduce routine effort but does not remove the employer's, engineer's, or quality authority's responsibility.

**Confidence:** Medium. The allocation principles are strong; the economical boundary depends on volume, utilization, IP sensitivity, supplier capability, and applicable law.

**Business-specific validation:** Build a task-level make/buy/automate matrix using frequency, criticality, response time, knowledge retention, data/IP exposure, and total cost; obtain employment counsel on classification and qualified quality/engineering approval for outsourced special processes.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The make/buy/automate boundary is framed correctly, but the right split depends on classification law, response-time needs, IP/process-control risk, and supplier quality. Do not infer that outsourcing or software transfer legal accountability.

### HUM-003 — Which roles require licensed engineers, experienced machinists, welders, quality professionals, designers, salespeople, or other qualifications?

**State:** Exploring

**First-pass answer:** There is no universal credential list. A state-licensed professional engineer is required only when the jurisdiction and scope of engineering practice require one; welding qualifications depend on the governing code and customer contract; machine setup and prove-out require demonstrated process-specific competence; and quality, inspection, design, and sales qualifications should match product risk and customer requirements. Voluntary credentials can show knowledge but do not replace demonstrated competence.

**Evidence:**
- [NCEES: Licensure](https://ncees.org/licensure/) — **Evidence class:** official professional-body guidance. Engineering licensure is state based, so the applicable board and statutory exemptions control.
- [AWS Professional Certification](https://www.aws.org/certification-and-education/professional-certification) — **Evidence class:** standards-body credential guidance. Welding qualifications are specific to responsibilities, processes, materials, and positions rather than a single universal certificate.
- [ASQ Certification](https://www.asq.org/cert) — **Evidence class:** accredited certification practice. Several quality credentials are ISO/IEC 17024 accredited, but certification remains different from legal authorization or customer approval.
- [BLS Occupational Outlook Handbook: Machinists](https://www.bls.gov/ooh/production/machinists-and-tool-and-die-makers.htm) — **Evidence class:** official occupational data. Machinists commonly develop competence through technical education, apprenticeship, and substantial on-the-job training.

**Conflicting evidence:** Some ordinary private manufacturing work falls within licensure exemptions and many capable tradespeople lack voluntary credentials; conversely, customer codes can demand qualifications beyond general law. The controlling jurisdiction, drawing, code, and contract must prevail.

**Confidence:** High on the conditional rule; low on which credentials this business needs before its products and jurisdictions are defined.

**Business-specific validation:** Have qualified engineering counsel or the relevant state board determine PE requirements, and have the responsible welding engineer, customer quality representative, and qualified quality professional create a product-by-product qualification matrix.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. It is correct to reject a universal credential list and to keep licensure, code, and customer flow-downs primary. State practice acts, weld codes, and contract requirements—not voluntary credentials alone—must control.

### HUM-004 — Which decisions require named human responsibility and cannot be delegated entirely to software?

**State:** Exploring

**First-pass answer:** Named humans should own customer commitments, design and process release, safety controls and stop-work resolution, material or product acceptance, nonconformance disposition, regulated submissions, access and cybersecurity exceptions, hiring/discipline, and financial authorization. Software may recommend or execute within approved limits, but a competent person must remain accountable and able to intervene.

**Evidence:**
- [NIST AI Risk Management Framework](https://www.nist.gov/itl/ai-risk-management-framework) — **Evidence class:** official risk framework. It calls for defined roles, responsibilities, oversight, validity, transparency, and management of harmful outcomes.
- [OSHA Recommended Practices for Safety and Health Programs](https://www.osha.gov/safety-management) — **Evidence class:** official operating guidance. Management leadership and worker participation are assigned to people; software cannot assume an employer's legal safety duties.
- [eCFR 29 CFR 1910.147: Control of Hazardous Energy](https://www.ecfr.gov/current/title-29/subtitle-B/chapter-XVII/part-1910/subpart-J/section-1910.147) — **Evidence class:** binding regulation. Lockout/tagout assigns training and control duties to authorized and affected employees.

**Conflicting evidence:** Highly automated systems can make reliable routine decisions faster than people, but evidence of technical capability does not transfer legal or organizational accountability to the system.

**Confidence:** High for the listed high-consequence decisions; medium for the precise approval thresholds.

**Business-specific validation:** Define an approval and escalation matrix with automation limits, named primary and backup owners, audit logs, and fail-safe behavior; obtain qualified legal, safety, engineering, quality, and cybersecurity review where applicable.

**Second-opinion review:** GPT-5.4 — Agree. The named-accountability categories are well supported and the answer properly separates software capability from legal and organizational responsibility. Approval thresholds still need a written delegation and escalation matrix.

### HUM-005 — What skills can realistically be trained internally, and which must be hired or contracted?

**State:** Exploring

**First-pass answer:** Train internally when work is repeatable, observable, safely practiced, and supported by a competent trainer—for example standard work, inspection routines, material handling, basic maintenance, and progressively bounded machine tasks. Hire or contract scarce judgment-heavy or legally controlled capability such as initial process development, complex prove-out, licensed engineering, industrial hygiene, code welding oversight, advanced metrology, and specialized tax or legal work until internal competence is demonstrable.

**Evidence:**
- [Apprenticeship.gov: Registered Apprenticeship](https://www.apprenticeship.gov/) — **Evidence class:** official operating program. Structured paid work, related instruction, mentorship, and progressive credentials demonstrate that many skilled trades can be developed internally over time.
- [BLS Occupational Outlook Handbook: Machinists](https://www.bls.gov/ooh/production/machinists-and-tool-and-die-makers.htm) — **Evidence class:** official occupational data. It describes technical schooling, apprenticeship, and long-term on-the-job learning as common routes.
- [OSHA Training Requirements in OSHA Standards](https://www.osha.gov/sites/default/files/publications/osha2254.pdf) — **Evidence class:** official compliance guidance. Many hazardous tasks require role-specific training and, in some cases, evaluation or authorization.

**Conflicting evidence:** Internal development improves retention and business-specific knowledge but can be unsafe or uneconomic without a competent trainer, enough repetition, and protected learning time. External credentials alone also do not prove local process competence.

**Confidence:** Medium. The boundary is determined by task risk, learning frequency, trainer quality, and time to needed proficiency.

**Business-specific validation:** Create a skills matrix and supervised progression for each process; have qualified trade, engineering, quality, and safety professionals identify tasks that novices may practice and the evidence required for independent authorization.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The train-versus-buy distinction is reasonable, but safe internal training depends on repetition, competent supervision, and task hazard rather than optimism about upskilling. Keep licensed and safety-critical work behind qualified oversight until competence is demonstrated.

### HUM-006 — Where are the relevant workers and skills geographically concentrated?

**State:** Exploring

**First-pass answer:** Public evidence cannot identify the right labor market without a proposed site and occupation list. Use current occupation-level employment and wages by metro/nonmetro area, manufacturing employment by county, and local hiring/separation data; then test the actual commuting radius and nearby apprenticeship, technical-school, supplier, and veteran pipelines.

**Evidence:**
- [BLS Occupational Employment and Wage Statistics Tables](https://www.bls.gov/oes/tables.htm) — **Evidence class:** official labor-market data. OEWS provides occupation employment and wage estimates at national, state, metropolitan, and nonmetropolitan levels.
- [BLS Quarterly Census of Employment and Wages](https://www.bls.gov/cew/) — **Evidence class:** official administrative data. QCEW measures industry employment and wages by county and other geographies and covers more than 95 percent of U.S. jobs.
- [Census Quarterly Workforce Indicators](https://www.census.gov/data/developers/data-sets/qwi.html) — **Evidence class:** official longitudinal data. QWI adds hires, separations, earnings, and workforce characteristics by industry and geography.
- [Apprenticeship.gov Partner Finder](https://www.apprenticeship.gov/partner-finder) — **Evidence class:** official program directory. It can identify registered apprenticeship infrastructure near candidate sites.

**Conflicting evidence:** Large manufacturing clusters offer deeper labor pools but also stronger wage competition and turnover; smaller markets may retain workers better but have thinner specialist coverage. National rankings cannot resolve a specific commuting market.

**Confidence:** High that the question requires site-specific analysis; low on any location conclusion before candidate sites are named.

**Business-specific validation:** For each candidate site, analyze OEWS/QCEW/QWI data, map a realistic shift-time commute shed, interview employers and schools, and run a small recruiting test for the hardest three roles.

**Second-opinion review:** GPT-5.4 — Agree. The conclusion that this cannot be answered credibly without named sites and occupations is correct, and BLS/Census/apprenticeship data are the right public starting point. Final conclusions still require commute-shed testing and live recruiting evidence.

### HUM-007 — What compensation, benefits, schedules, flexibility, and career paths are needed to attract and retain the workforce?

**State:** Exploring

**First-pass answer:** Offer a locally competitive total package, predictable schedules, paid overtime or lawful exempt treatment, shift differentials where needed, health and retirement benefits appropriate to the market, paid leave, paid training, and visible skill-based progression. Flexibility should include reliable advance notice, practical shift swaps, and limited administrative flexibility; production flexibility cannot be promised where safe staffing would fail.

**Evidence:**
- [BLS Occupational Employment and Wage Statistics](https://www.bls.gov/oes/) — **Evidence class:** official wage data. It provides occupation- and geography-specific wage distributions rather than a single national target.
- [BLS Employer Costs for Employee Compensation](https://www.bls.gov/ecec/) — **Evidence class:** official compensation data. It measures employer spending on wages and benefit categories by worker and industry groups.
- [Census Quarterly Workforce Indicators](https://qwiexplorer.ces.census.gov/) — **Evidence class:** official workforce-flow data. Earnings, hires, and separations can reveal local retention and competition patterns.
- [Apprenticeship.gov](https://www.apprenticeship.gov/) — **Evidence class:** official operating program. Registered apprenticeship formalizes paid progression, mentorship, instruction, and portable credentials.

**Conflicting evidence:** Higher pay can improve recruiting, but schedule instability, unsafe work, poor supervision, and weak advancement can still drive exits. Broad national shortage claims are less reliable than occupation-by-commute-market evidence.

**Confidence:** Medium. The package elements are well supported, but competitive amounts and preferred schedules are local and role specific.

**Business-specific validation:** Benchmark the relevant labor market, interview at least several target workers, price benefits with a broker, test shift designs against safe coverage, and obtain qualified employment counsel review for wage-hour and leave compliance.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The package elements are credible, but national labor evidence is only directional and cannot prove what this local market will accept. Schedule stability, supervision quality, and advancement can matter as much as cash.

### HUM-008 — How do we create safe working conditions around machines, welding, heat treatment, chemicals, lifting, noise, dust, and repetitive work?

**State:** Exploring

**First-pass answer:** Establish a formal safety system before production: inventory hazards; use the hierarchy of controls; engineer guarding, ventilation, isolation, lifting aids, and ergonomic workstations; implement lockout/tagout, hot-work, chemical, PPE, respiratory, hearing, fire, and emergency programs; train and verify competence; inspect and maintain controls; and investigate exposures and incidents. PPE is a last line, not the primary design.

**Evidence:**
- [eCFR 29 CFR Part 1910](https://www.ecfr.gov/current/title-29/subtitle-B/chapter-XVII/part-1910) — **Evidence class:** binding regulation. General-industry rules address machine guarding, hazardous energy, welding, electrical safety, chemicals, noise, respiratory protection, and PPE.
- [NIOSH: Hierarchy of Controls](https://www.cdc.gov/niosh/hierarchy-of-controls/about/index.html) — **Evidence class:** official technical guidance. It prioritizes elimination, substitution, and engineering controls above administrative controls and PPE.
- [OSHA Recommended Practices for Safety and Health Programs](https://www.osha.gov/safety-management) — **Evidence class:** official operating guidance. It provides a continuous system for leadership, worker participation, hazard identification, prevention, training, and program evaluation.
- [NIOSH Applications Manual for the Revised NIOSH Lifting Equation](https://www.cdc.gov/niosh/docs/94-110/default.html) — **Evidence class:** official ergonomic method. It provides a task-specific method for evaluating two-handed manual lifting.

**Conflicting evidence:** OSHA rules are legal minimums and may not fully control a specific exposure; vendor manuals and PPE-heavy programs can be easier to implement but may leave higher residual risk. A task-specific professional assessment is more credible.

**Confidence:** High on the required system; low on control adequacy until machines, materials, layouts, and exposures are measured.

**Business-specific validation:** Before operation, retain a qualified safety professional and industrial hygienist, involve the machine builder and fire authority, complete documented risk and exposure assessments, and obtain qualified engineering review for guarding, ventilation, lifting systems, and heat-treatment equipment.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The hierarchy-of-controls framing is correct, but OSHA/NIOSH minima and generic guidance do not prove adequacy for actual machines, fumes, heat, or dust loads. Industrial-hygiene measurements and machine/facility-specific engineering review remain mandatory.

### HUM-009 — How are training, demonstrated competency, authorization, and recertification recorded?

**State:** Exploring

**First-pass answer:** Use one controlled skills-and-authorization register linked to each person and task. Record the requirement and revision, trainer qualifications, instruction date, written or practical evidence, observed performance, restrictions, authorizer, effective and expiry/review dates, refresher triggers, and suspension history. Training completion alone must not equal authorization for hazardous or quality-critical work.

**Evidence:**
- [OSHA Training Requirements in OSHA Standards](https://www.osha.gov/sites/default/files/publications/osha2254.pdf) — **Evidence class:** official compliance guidance. It compiles standards that require training, records, evaluation, or certification for specific hazards and equipment.
- [eCFR 29 CFR 1910.178(l): Powered Industrial Truck Training](https://www.ecfr.gov/current/title-29/subtitle-B/chapter-XVII/part-1910/subpart-N/section-1910.178) — **Evidence class:** binding regulation and operating model. It requires formal instruction, practical training, workplace evaluation, certification, and periodic reevaluation.
- [AWS Professional Certification](https://www.aws.org/certification-and-education/professional-certification) — **Evidence class:** standards-body practice. Welding qualifications are bounded by process, material, position, and responsibility.

**Conflicting evidence:** Some standards mandate specific records or renewal intervals while many ordinary tasks do not. A universal annual recertification rule would be easy to administer but unsupported and potentially too weak or too burdensome.

**Confidence:** High on the record structure; medium on task-specific intervals.

**Business-specific validation:** Build the register from the legal, customer, machine-manual, and internal risk requirements for each task, and have qualified safety, quality, and trade professionals approve practical tests and refresher triggers.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Recording competency, authorization, and recertification is the right control, but record content, intervals, and legal sufficiency depend on process risk, code/customer rules, and OSHA-specific duties. Qualified quality, safety, and trade oversight should set the evidence standard.

### HUM-010 — How do we make it easy for employees to stop unsafe or questionable work without commercial pressure overriding them?

**State:** Exploring

**First-pass answer:** Give every worker explicit stop-work authority and a simple method to place work in a safe state, identify and quarantine affected product, and summon a named responder. Prohibit retaliation, preserve pay while good-faith concerns are assessed, require documented restart approval, track response time and recurrence, and evaluate leaders on safe resolution rather than uninterrupted output.

**Evidence:**
- [OSHA Recommended Practices: Worker Participation](https://www.osha.gov/safety-management/worker-participation) — **Evidence class:** official operating guidance. It calls for reporting without fear of retaliation, access to information, participation in solutions, and authority to act on imminent hazards.
- [eCFR 29 CFR 1904.35](https://www.ecfr.gov/current/title-29/subtitle-B/chapter-XVII/part-1904/subpart-D/section-1904.35) — **Evidence class:** binding regulation. Employers must provide a reasonable injury-reporting procedure and may not retaliate for reporting work-related injury or illness.
- [OSHA: Workers' Right to Refuse Dangerous Work](https://www.osha.gov/workers/right-to-refuse) — **Evidence class:** official legal guidance. The statutory right is real but narrower and more conditional than a broad internal stop-work policy should be.

**Conflicting evidence:** Production incentives can reward silence even when policy says otherwise. Legal refusal rights alone are too narrow; a broader, paid, good-faith internal policy is needed while retaining rules against knowingly reckless conduct.

**Confidence:** High. The main uncertainty is whether leaders will follow the policy under delivery pressure.

**Business-specific validation:** Test the process in drills and anonymous interviews, audit every stop for retaliation or lost pay, and have qualified employment counsel and a safety professional review the policy and restart authority.

**Second-opinion review:** GPT-5.4 — Agree. Good-faith stop-work with paid protection and documented restart is a sound correction to policy-only approaches. The remaining risk is management override, so drills and anti-retaliation auditing are essential.

### HUM-011 — How are mistakes, near misses, process improvements, and employee concerns reported without creating a blame culture?

**State:** Exploring

**First-pass answer:** Provide fast named and anonymous channels, acknowledge reports promptly, separate immediate containment from investigation, examine system causes before individual fault, and publish actions and closure. Use a written just-culture boundary: human error is consoled and systems improved, risky choices are coached, and intentional or reckless violations receive proportionate accountability.

**Evidence:**
- [OSHA Recommended Practices: Worker Participation](https://www.osha.gov/safety-management/worker-participation) — **Evidence class:** official operating guidance. It emphasizes reporting hazards, near misses, injuries, and concerns without retaliation and involving workers in solutions.
- [NIOSH Mining Program: Learning from Workers' Near-Miss Reports](https://stacks.cdc.gov/view/cdc/215299) — **Evidence class:** empirical government research. Worker reports can expose organizational contributors and direct preventive changes; mining evidence is relevant but not identical to a machine shop.
- [OSHA Safety Management Case Studies](https://www.osha.gov/safety-management/case-studies) — **Evidence class:** documented operating practice. The cases illustrate worker participation and management systems in actual organizations, though they are selected examples rather than controlled comparisons.

**Conflicting evidence:** Fully anonymous systems can improve candor but make follow-up harder; purely nonpunitive systems can obscure deliberate misconduct. A just-culture decision rule is more credible than either automatic punishment or no accountability.

**Confidence:** Medium-high. Reporting-system principles are strong, but culture must be verified through employee behavior and closure data.

**Business-specific validation:** Pilot the channels, measure reporting and closure latency without setting volume quotas, survey psychological safety, and have a qualified safety professional and employment counsel review investigation and discipline boundaries.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The learning-over-blame approach is sound, but reporting systems work only if concerns are investigated, closed, and protected from retaliation in practice. Culture claims are causal only when leadership behavior and metrics reinforce them.

### HUM-012 — What staffing model supports vacations, illness, demand spikes, after-hours incidents, and critical single-person expertise?

**State:** Exploring

**First-pass answer:** Maintain at least two authorized people for each time-critical safety, quality, customer, and production capability, with documented handoffs and cross-training; supplement with prequalified contractors and suppliers for infrequent specialties and demand spikes. Use an on-call roster only for defined incidents, cap hours and consecutive shifts, and set order-acceptance triggers that prevent overload.

**Evidence:**
- [NIST MEP, America's Manufacturing Workforce: Make or Buy?](https://www.nist.gov/blogs/manufacturing-innovation-blog/americas-manufacturing-workforce-make-or-buy) — **Evidence class:** official practitioner guidance. It supports combining internal pipelines with flexible external capacity according to short- and long-term need.
- [NIOSH: Training for Nurses on Shift Work and Long Work Hours](https://www.cdc.gov/niosh/work-hour-training-for-nurses/about/index.html) — **Evidence class:** official fatigue guidance. Although developed for health care, the physiology and risk-management principles for night work and long hours are cross-industry.
- [Ready.gov: Business Continuity Plan](https://www.ready.gov/business-continuity-plan) — **Evidence class:** official continuity guidance. It recommends identifying critical operations, dependencies, recovery priorities, and alternate arrangements.

**Conflicting evidence:** Lean staffing reduces fixed cost, but overtime and single-person dependencies increase fatigue and interruption risk; excess redundancy is unaffordable at small scale. Prequalified external coverage is a practical bridge, not a substitute for internal ownership.

**Confidence:** Medium. The resilience principles are sound; headcount and coverage windows depend on demand, process hazards, service promises, and response times.

**Business-specific validation:** Run absence and surge scenarios against the skills register, measure safe capacity by shift, price backup agreements, and have a qualified safety professional review fatigue and lone-work controls.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The resilience principle is correct, but minimum redundancy depends on hazards, customer response times, shift coverage, and single-point expertise. Failure scenarios should be modeled, not just average staffing.

### HUM-013 — How are invention ownership, confidentiality, customer IP, noncompetition concerns, and employee-created process improvements handled?

**State:** Exploring

**First-pass answer:** Use counsel-drafted agreements that define pre-existing IP, work product and invention assignment, customer flow-down duties, confidential information, permitted disclosures, records, return/deletion, and rewards or attribution for improvements. Protect trade secrets through actual access and handling controls. Do not rely on broad noncompetes; their enforceability and notice rules vary materially by state and federal policy remains contested.

**Evidence:**
- [35 U.S.C. § 261](https://www.law.cornell.edu/uscode/text/35/261) — **Evidence class:** federal statute. Patent interests are assignable in writing, making clear drafting and execution important.
- [18 U.S.C. § 1836](https://www.law.cornell.edu/uscode/text/18/1836) — **Evidence class:** federal statute. The Defend Trade Secrets Act provides civil remedies for trade-secret misappropriation.
- [18 U.S.C. § 1833](https://www.law.cornell.edu/uscode/text/18/1833) — **Evidence class:** federal statute. It preserves whistleblower immunity and requires notice of that immunity in relevant agreements.
- [Federal Register: removal of the Non-Compete Rule](https://www.federalregister.gov/documents/2026/02/12/2026-02866/revision-of-the-negative-option-rule-withdrawal-of-the-cars-rule-removal-of-the-non-compete-rule-to) — **Evidence class:** official current-status source. The 2026 notice records removal of the vacated federal rule; it does not replace state-law analysis.
- [USPTO: Recordation of Assignments](https://www.uspto.gov/patents/maintain/patents-assignments-change-search-ownership) — **Evidence class:** official administrative guidance. It explains recording changes in patent ownership.

**Conflicting evidence:** Employers need to protect genuine customer and process secrets, while overbroad restraints can be unlawful, inhibit mobility, and fail to protect information that was not actually kept secret. State invention-assignment and restrictive-covenant rules differ.

**Confidence:** High that written, narrow agreements and operational controls are necessary; low on enforceability until governing states, worker locations, and customer contracts are known.

**Business-specific validation:** Engage qualified employment and IP counsel in every governing state to draft agreements and notices, review customer terms, inventory pre-existing IP, and design trade-secret controls; do not implement noncompetes without that review.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer correctly favors narrow agreements and real handling controls over sweeping noncompetes, but enforceability varies sharply by state and worker location. Trade-secret protection fails without actual access discipline and counsel-drafted assignments.

### HUM-014 — Which performance measures encourage quality, safety, and learning rather than only speed or utilization?

**State:** Exploring

**First-pass answer:** Use a balanced set: leading safety actions and exposure-control closure; injuries and near misses interpreted together; first-pass yield, escapes, rework, and calibration status; on-time delivery and lead time; margin and cash; preventive-maintenance completion; cross-training and authorization depth; improvement closure; and employee/customer retention. Never pay on utilization or units alone, and apply explicit quality and safety gates.

**Evidence:**
- [NIST: Operations-Driven Performance Measurement for Smart Manufacturing Systems](https://www.nist.gov/programs-projects/operations-driven-performance-measurement-smart-manufacturing-systems) — **Evidence class:** official technical research. It emphasizes standardized, operationally derived measures and trade-offs among multiple objectives.
- [NIST Baldrige Excellence Framework](https://www.nist.gov/baldrige/publications/baldrige-excellence-framework) — **Evidence class:** official management framework. It integrates workforce, customer, operational, leadership, and results measures rather than optimizing one output.
- [BLS Injuries, Illnesses, and Fatalities](https://www.bls.gov/iif/) — **Evidence class:** official empirical data. It provides standard lagging safety measures and industry benchmarks, which should be paired with leading indicators rather than used alone.
- [OSHA Recommended Practices for Safety and Health Programs](https://www.osha.gov/safety-management) — **Evidence class:** official operating guidance. It recommends evaluating both program implementation and outcomes.

**Conflicting evidence:** Overall equipment effectiveness and utilization can expose constraints, but when tied directly to rewards they can encourage overproduction, deferred maintenance, or hidden defects. More measures also create noise; a small balanced set with clear definitions is preferable.

**Confidence:** High on balanced measurement; medium on the final metric set and targets.

**Business-specific validation:** Baseline the value stream, define each metric and anti-gaming rule, review incentives with workers, and have qualified safety, quality, and finance professionals test whether any target can reward unsafe or nonconforming output.

**Second-opinion review:** GPT-5.4 — Agree. Balanced measures that reward quality, safety, and learning are more defensible than pure speed or utilization metrics. The main qualification is to calibrate them by role and audit for gaming or conflicting incentives.

### HUM-015 — What parts of the customer relationship should always feel human even when much of the process is automated?

**State:** Exploring

**First-pass answer:** Keep a named person accountable for discovery, ambiguous requirements, manufacturability trade-offs, quotations with material assumptions, commitment changes, first-article or critical approvals, nonconformance decisions, service recovery, sensitive IP, and escalation. Automate routine status, document transfer, scheduling, and reminders, but always expose a prompt path to a competent human.

**Evidence:**
- [NIST Baldrige: Customer-Focused Excellence](https://www.nist.gov/baldrige/self-assessing/baldrige-key-terms) — **Evidence class:** official management framework. Baldrige treats customer engagement, voice, relationships, satisfaction, and complaint recovery as managed organizational processes.
- [ISO Quality Management Principles](https://www.iso.org/quality-management-principles.html) — **Evidence class:** standards-body guidance. Customer focus and relationship management require understanding needs and sustaining trust, not merely processing transactions.
- [NIST AI Risk Management Framework](https://www.nist.gov/itl/ai-risk-management-framework) — **Evidence class:** official risk framework. It supports transparency, accountability, and human oversight when automated systems affect consequential decisions.

**Conflicting evidence:** Some customers prefer self-service and fast automated updates, while others interpret automation during ambiguity or failure as avoidance. Human contact should therefore be triggered by consequence and customer preference, not applied to every routine event.

**Confidence:** Medium. The high-stakes moments are credible, but customer segments may value different channels.

**Business-specific validation:** Interview target customers, map trust-critical moments, test response and escalation service levels, and measure whether customers can reach someone empowered to decide rather than only a scripted support layer.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. It is sensible that trust, escalation, exceptions, and harm-relevant communications stay human, but the exact boundary is customer- and product-risk-specific. Automation can assist, not replace, accountability in quoting, change resolution, or incident response.

### HUM-016 — How should employing family members be governed, compensated, evaluated, and separated from family relationships?

**State:** Exploring

**First-pass answer:** Employ relatives only in documented, necessary roles with published qualifications, market-benchmarked pay, normal payroll and time records, a nonrelative supervisor or independent reviewer, written goals, the same conduct and safety rules, conflict disclosures and recusals, and a predefined discipline and exit process. Ownership distributions, gifts, and family support should remain separate from wages.

**Evidence:**
- [IRS: Family Employees](https://www.irs.gov/businesses/small-businesses-self-employed/family-employees) — **Evidence class:** official tax guidance. Tax treatment varies by relationship and entity form, while corporate family employees generally remain subject to ordinary payroll taxes.
- [EEOC: Small Business Requirements](https://www.eeoc.gov/employers/small-business) — **Evidence class:** official compliance guidance. Covered employers must apply nondiscrimination rules; family status does not excuse discriminatory treatment of other workers.
- [U.S. Department of Labor: Child Labor Requirements in Nonagricultural Occupations](https://www.dol.gov/agencies/whd/fact-sheets/43-child-labor-non-agriculture) — **Evidence class:** official legal guidance. Manufacturing includes hazardous occupations and restrictions that can apply even in family businesses.
- [NIST Baldrige Excellence Framework](https://www.nist.gov/baldrige/publications/baldrige-excellence-framework) — **Evidence class:** official management framework. Governance, ethics, workforce systems, and objective performance review support consistent treatment.

**Conflicting evidence:** Family employees can bring trust and continuity, but informal exceptions create tax, fairness, governance, and safety risk. Equal rules do not require identical roles or pay; documented market value and responsibility should control.

**Confidence:** Medium-high on governance principles; legal and tax treatment is entity-, age-, relationship-, and state-specific.

**Business-specific validation:** Adopt a board- or adviser-approved family-employment policy and have a qualified employment attorney and CPA review classification, payroll, youth-work restrictions, conflicts, compensation, and separation terms.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. These governance safeguards are sound, but tax, wage-hour, youth-work, and conflict rules depend on entity form, age, relationship, and state law. Independent supervision and market-based pay are the key protections.

### HUM-017 — Which engineering, manufacturing, sales, finance, quality, safety, maintenance, and leadership competencies do the founders already possess and which are missing?

**State:** Exploring

**First-pass answer:** Public evidence cannot answer this about unnamed founders. Assess each domain against observable work products and live demonstrations, not résumé labels: released designs and calculations; process plans and prove-outs; accepted quotes and customer discovery; cash forecasts and controls; inspection plans and dispositions; hazard assessments; preventive maintenance and troubleshooting; and leadership under delivery or safety conflict.

**Evidence:**
- [NIST Baldrige Excellence Builder](https://www.nist.gov/baldrige/products-services/baldrige-excellence-builder) — **Evidence class:** official assessment framework. It provides a structured self-assessment across leadership, strategy, customers, measurement, workforce, operations, and results.
- [O*NET OnLine](https://www.onetonline.org/) — **Evidence class:** official occupational database. It supplies occupation-specific tasks, knowledge, skills, abilities, work activities, and technology requirements for constructing a competency matrix.
- [BLS Occupational Outlook Handbook](https://www.bls.gov/ooh/) — **Evidence class:** official occupational data. It provides independent descriptions of typical preparation and duties for engineering, production, sales, finance, quality, and maintenance roles.

**Conflicting evidence:** Founders often perform effectively outside formal credentials, while confidence and prior titles can overstate readiness for a new process or regulated market. Demonstrated artifacts and external review are more credible than self-rating alone.

**Confidence:** High that direct assessment is required; no confidence is possible yet about any founder's actual competency.

**Business-specific validation:** Have each founder complete an evidence-linked matrix and practical scenarios, then obtain independent review from qualified engineering, manufacturing, sales, finance, quality, safety, maintenance, and leadership practitioners.

**Second-opinion review:** GPT-5.4 — Agree. This is correctly framed as a structured capability inventory rather than a self-impression exercise. Use observable evidence and outside challenge rather than titles or founder confidence.

### HUM-018 — Which missing competency creates the first mandatory employee, contractor, adviser, or partner role?

**State:** Exploring

**First-pass answer:** The first mandatory role is the earliest uncovered competency that either law requires or that gates safe, conforming delivery to a paying customer. Rank gaps in this order: legal or safety authority, product/process feasibility, independent quality acceptance, customer acquisition/order acceptance, and cash control. Use a qualified contractor or adviser for intermittent work; hire when response time, workload, knowledge retention, or independence requires continuous presence.

**Evidence:**
- [OSHA Recommended Practices for Safety and Health Programs](https://www.osha.gov/safety-management) — **Evidence class:** official operating guidance. Hazard prevention and management responsibility must exist before exposing workers, regardless of company size.
- [NIST MEP, America's Manufacturing Workforce: Make or Buy?](https://www.nist.gov/blogs/manufacturing-innovation-blog/americas-manufacturing-workforce-make-or-buy) — **Evidence class:** official practitioner guidance. It distinguishes urgent external capacity from capabilities that should be developed for long-term need.
- [NIST Baldrige Excellence Builder](https://www.nist.gov/baldrige/products-services/baldrige-excellence-builder) — **Evidence class:** official assessment framework. It supports identifying the operational, customer, workforce, measurement, and leadership gap that most constrains results.

**Conflicting evidence:** Conventional startup advice often says sales is always first, while manufacturing practice may make safety, technical feasibility, or quality approval the true prerequisite. The first binding constraint, not a generic title, should decide.

**Confidence:** High on the decision rule; low on the resulting role until HUM-017 and the first product/customer scope are completed.

**Business-specific validation:** Sequence the first order from lead through cash and safe production, identify every unsupported approval or task, and have qualified legal, safety, engineering, quality, finance, and market advisers challenge the highest-ranked gap.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The first mandatory hire or partner should be anchored in the earliest legally or operationally blocking gap, but which gap comes first depends on product risk and founder strengths. Safety, quality-release, and controlled-activity gaps should outrank convenience hires.

### HUM-019 — How many machines, cells, jobs, customers, or shifts can one qualified person safely and reliably support at each business stage?

**State:** Exploring

**First-pass answer:** No defensible universal ratio exists. Capacity per person depends on automatic run time, intervention frequency, setup and inspection load, process capability, guarding and interlocks, material handling, travel distance, alarm response, maintenance, customer complexity, and whether concurrent tasks can be abandoned safely. Set ratios from risk assessment and measured standard work, then reduce them during launch, training, abnormal conditions, or high mix.

**Evidence:**
- [eCFR 29 CFR 1910 Subpart O: Machinery and Machine Guarding](https://www.ecfr.gov/current/title-29/subtitle-B/chapter-XVII/part-1910/subpart-O) — **Evidence class:** binding regulation. Required protection depends on machine hazards, not a prescribed machine-to-operator ratio.
- [OSHA Technical Manual: Industrial Robot Systems and Industrial Robot System Safety](https://www.osha.gov/otm/section-4-safety-hazards/chapter-4) — **Evidence class:** official technical guidance. Automated cells require application-specific safeguarding, modes, controls, and risk analysis.
- [NIOSH: Elements of Ergonomics Programs](https://www.cdc.gov/niosh/docs/97-117/default.html) — **Evidence class:** official technical guidance. Work design must account for task demands, repetition, force, posture, recovery, and worker feedback.
- [NIST: Operations-Driven Performance Measurement for Smart Manufacturing Systems](https://www.nist.gov/programs-projects/operations-driven-performance-measurement-smart-manufacturing-systems) — **Evidence class:** official technical research. It supports deriving performance measures from actual operations and multiple competing objectives.

**Conflicting evidence:** Vendor demonstrations and steady-state utilization models may support many unattended machines, while high-mix setup, inspection, chip handling, faults, and emergencies can require one-to-one attention. Demonstrated safe operation under representative variability is more credible than nameplate automation.

**Confidence:** High that no fixed ratio is supportable; low on actual ratios before time, reliability, quality, and hazard data exist.

**Business-specific validation:** Have a qualified manufacturing engineer and safety professional conduct task and risk analyses, run representative timed trials including faults and breaks, verify machine-builder limits and emergency response, and approve stage-specific staffing with explicit stop conditions.

**Second-opinion review:** GPT-5.4 — Agree. No universal ratio is defensible, and the answer properly treats vendor demos as weaker evidence than measured standard work under fault conditions. Ratios must be stage-specific and signed off by manufacturing and safety leadership.
