# Data, AI, and Platform Questions

## Purpose

Questions about customer data, intellectual property, platform architecture, integrations, auditability, security, and automation limits.

These are discovery questions, not approved requirements. Question IDs are permanent and canonical in this file.

## Data, Privacy, and Intellectual Property

### DAT-001 — What customer and project data is collected?

**State:** Exploring

**First-pass answer:** Maintain an explicit inventory and collect only data needed for intake, engineering, quoting, approvals, manufacturing, quality, delivery, support, legal obligations, and separately authorized learning. Classify identity, requirements, contracts, CAD/drawings, communications, pricing, plans/programs, telemetry, cost actuals, inspection, media, shipment, support, and audit records by owner, purpose, sensitivity, retention, permitted users, export status, and whether raw, derived, or aggregated.

**Evidence:**
- [NIST Privacy Framework](https://www.nist.gov/privacy-framework) — **Evidence class:** official guidance. It supports identifying purposes, data flows, roles, and minimization; industrial IP extends beyond privacy.
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It links product definition, manufacturing, quality, authorization, and traceability data.
- [NIST CSF 2.0](https://doi.org/10.6028/NIST.CSWP.29) — **Evidence class:** official consensus framework. It supports identifying assets and information flows but does not decide which fields are commercially necessary.

**Conflicting evidence:** More telemetry can improve diagnosis and estimation, but “collect everything” increases security, privacy, storage, review, and discovery costs.

**Confidence:** High on inventory and minimization; medium on the final field list.

**Business-specific validation:** Map one representative job end to end, interview customers on sensitive fields, and measure which telemetry materially improves quality, cost, or repeatability.

**Second-opinion review:** GPT-5.4 — Agree. The inventory-and-minimization approach is well supported and avoids the unsupported “collect everything now, decide later” posture.

### DAT-002 — What is the retention and deletion policy?

**State:** Exploring

**First-pass answer:** Use record-class schedules, not one period. Retain contractual, tax, quality, safety, approval, shipment, security, and reproducibility records for applicable obligations; keep routine raw video and high-rate telemetry much less time unless tied to an incident or approved need. On expiry or valid request, delete or render inaccessible primary data and derived indexes, embeddings, caches, and training copies, while expiring backups through a disclosed cycle.

**Evidence:**
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** official control catalog. SI-12 and related controls address lifecycle management, retention, audit, and disposal.
- [NIST SP 800-88 Rev. 1](https://csrc.nist.gov/pubs/sp/800/88/r1/final) — **Evidence class:** official technical guidance. It defines media sanitization and verification practices, though cloud replicas require additional controls.
- [NIST Privacy Framework](https://www.nist.gov/privacy-framework) — **Evidence class:** official guidance. It supports purpose limitation and controlled retention/deletion.

**Conflicting evidence:** Long retention supports repeatability, warranty defense, and estimating; short retention reduces exposure and cost, and immutable records can conflict with valid deletion duties.

**Confidence:** High on differentiated schedules; low on durations before markets and contracts are known.

**Business-specific validation:** Build a legal/contract retention matrix, test deletion across object/search/vector/backup systems, and price any long-retention service tier.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The record-class approach is correct, but actual durations and deletion feasibility across backups, embeddings, caches, and provider copies remain business- and architecture-specific.

### DAT-003 — May customer data be used to improve models, planners, or benchmarks, and what consent is required?

**State:** Exploring

**First-pass answer:** Not by default beyond serving that customer. Require separate, explicit contractual permission for cross-customer training, planner improvement, or benchmarks, specifying data categories, purpose, recipients, providers, identifiability, retention, withdrawal/deletion behavior, and derivative models. Offer no reuse, customer-specific learning, approved de-identified aggregates, and specifically authorized broader training as distinct choices.

**Evidence:**
- [NIST Privacy Framework](https://www.nist.gov/privacy-framework) — **Evidence class:** official guidance. It supports documented purposes, roles, minimization, and control of secondary use.
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** official AI governance guidance. It calls for data provenance, stakeholder, risk, and lifecycle controls.

**Conflicting evidence:** Pooled data can improve models, but distinctive geometry may remain identifiable and consent cannot cure export-control or confidentiality restrictions.

**Confidence:** High on opt-in separation; medium on exact contract language.

**Business-specific validation:** Obtain IP/privacy/export counsel review, test customer willingness, enforce consent as machine-readable policy, and verify provider/model deletion capabilities.

**Second-opinion review:** GPT-5.4 — Agree. The answer is appropriately restrictive, and the contrary evidence is correctly handled: consent improves legitimacy but does not waive confidentiality, export-control, or identifiability risk.

### DAT-004 — How are confidential designs segregated?

**State:** Exploring

**First-pass answer:** Segregate by tenant, customer, project, and export-control compartment using deny-by-default authorization, object labels, least privilege, scoped service identities, encrypted storage, isolated search/vector indexes, audited emergency access, and optional dedicated keys or environments. Do not rely only on URL paths, application filters, or network location.

**Evidence:**
- [NIST SP 800-171 Rev. 3](https://csrc.nist.gov/pubs/sp/800/171/r3/final) — **Evidence class:** official sensitive-data guidance. It covers access, audit, identity, media, communications, and supplier risks for applicable CUI.
- [NIST SP 800-162](https://csrc.nist.gov/pubs/sp/800/162/upd2/final) — **Evidence class:** official authorization guidance. Attribute-based access control can use user, resource, action, and environment attributes.
- [NIST SP 800-207](https://doi.org/10.6028/NIST.SP.800-207) — **Evidence class:** official architecture guidance. Zero trust rejects implicit trust based on network location or ownership.

**Conflicting evidence:** Dedicated physical isolation can reduce shared-platform risk but increases cost and operational complexity; logical isolation can be strong only if continuously tested.

**Confidence:** High on the control pattern; medium on which customers require dedicated infrastructure.

**Business-specific validation:** Threat-model CAD, cache, search, and support flows; run cross-tenant and backup-restore tests; and obtain independent penetration testing.

**Second-opinion review:** GPT-5.4 — Agree. The segregation controls are aligned with modern access-control and zero-trust guidance and correctly reject URL- or network-only isolation as sufficient.

### DAT-005 — What encryption, access control, audit, backup, and recovery protections are required?

**State:** Exploring

**First-pass answer:** Baseline controls should include authenticated encryption in transit and at rest, managed key rotation and separation, MFA, least privilege, short-lived service credentials, protected audit logs, encrypted and isolated recovery copies, documented RPO/RTO, restore exercises, and incident response. Apply stricter controls to CUI, export-controlled, or high-consequence projects.

**Evidence:**
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** official control catalog. It covers access, cryptography, audit, contingency, integrity, and supply chain.
- [NIST SP 800-171 Rev. 3](https://csrc.nist.gov/pubs/sp/800/171/r3/final) — **Evidence class:** official CUI guidance. It is relevant when federal or contractual requirements apply.
- [NIST SP 800-34 Rev. 1](https://csrc.nist.gov/pubs/sp/800/34/r1/upd1/final) — **Evidence class:** official contingency guidance. It supports recovery planning, alternate processing, and testing.

**Conflicting evidence:** Encryption and backup existence do not prove recoverability, and poorly tested IT controls can impair OT availability.

**Confidence:** High.

**Business-specific validation:** Classify impact tiers, choose RPO/RTO per workflow, run restore and ransomware exercises, and document shop operations that continue without central systems.

**Second-opinion review:** GPT-5.4 — Agree. This is a sound baseline security posture, and the answer correctly notes that encryption and backups do not prove recoverability without restore testing.

### DAT-006 — Can customers require regional storage, isolated processing, or offline handling?

**State:** Exploring

**First-pass answer:** Yes, as priced service tiers subject to law and feasibility: approved-region storage and processing, dedicated tenancy/keys, private connectivity, on-premises edge processing, or fully offline transfer. Document the location of primary data, backups, logs, support access, and subprocessors—not only the headline database region.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** official OT guidance. It recognizes isolation, availability, connectivity constraints, and local operational needs.
- [22 CFR §120.54](https://www.ecfr.gov/current/title-22/chapter-I/subchapter-M/part-120/subpart-C/section-120.54) — **Evidence class:** primary regulation. It addresses controlled technical data sent, stored, or accessed through qualifying cryptographic means; legal analysis is fact-specific.
- [CISA Secure Connectivity Principles for OT](https://www.cisa.gov/resources-tools/resources/secure-connectivity-principles-operational-technology-ot) — **Evidence class:** official guidance. It supports minimizing and governing external connectivity.

**Conflicting evidence:** Offline handling improves confidentiality but complicates collaboration, patching, support, backup, and security monitoring.

**Confidence:** High that options should exist; medium on eligibility and cost.

**Business-specific validation:** Interview target defense/research customers, obtain export counsel advice, prototype an offline project package, and price deployment/support overhead.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Regional, isolated, and offline options are plausible and often necessary, but the final offer set depends on export law, support model, and priceable operational overhead.

### DAT-007 — How are AI providers, CAD services, suppliers, and other subprocessors governed?

**State:** Exploring

**First-pass answer:** Maintain an approved-subprocessor register and risk tier. Contracts should limit purpose, data, location, access, retention, model training, downstream providers, incident notice, security evidence, IP, export handling, availability, return/export, deletion, and exit; technical controls should minimize and log every transfer and preserve manual or replacement fallbacks for critical providers.

**Evidence:**
- [NIST SP 800-161 Rev. 1](https://csrc.nist.gov/pubs/sp/800/161/r1/upd1/final) — **Evidence class:** official supply-chain guidance. It covers acquisition, supplier assessment, visibility, resilience, and lifecycle risk.
- [NIST Privacy Framework](https://www.nist.gov/privacy-framework) — **Evidence class:** official guidance. It supports mapping roles and risks across the processing ecosystem.
- [NIST AI 600-1](https://doi.org/10.6028/NIST.AI.600-1) — **Evidence class:** official AI guidance. It addresses third-party model, privacy, provenance, and supply-chain risks.

**Conflicting evidence:** Certifications and contracts reduce but do not eliminate opaque downstream processing or concentration risk, and a small buyer may lack audit leverage.

**Confidence:** High on governance structure; medium on attainable terms.

**Business-specific validation:** Issue vendor questionnaires, inspect actual terms and settings, test egress enforcement and deletion, and identify a fallback for each critical provider.

**Second-opinion review:** GPT-5.4 — Agree. The governance structure is strong and appropriately treats subprocessors as both contractual and technical control points, not just vendor paperwork.

### DAT-008 — How can a customer export the complete project record?

**State:** Exploring

**First-pass answer:** Provide a versioned, signed bundle with manifest, stable IDs, timestamps, provenance links, requirements, communications/approvals, licensed native and neutral CAD, drawings, quotes, plans, inspection/quality, shipment, and customer-visible production history. Prefer JSON/CSV, STEP AP242, QIF, PDF, and original media, with hashes and schema/version documentation.

**Evidence:**
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It supports lifecycle linkage and trustworthy product data.
- [STEP AP242](https://www.iso.org/standard/66654.html) — **Evidence class:** primary international standard. It supports managed model-based engineering exchange; implementation fidelity varies.
- [QIF](https://qifstandards.org/) — **Evidence class:** official industry standard. It covers geometry/PMI-related quality planning and results; adoption is not universal.
- [W3C PROV-O](https://www.w3.org/TR/prov-o/) — **Evidence class:** official web standard. It models entities, activities, agents, and derivations.

**Conflicting evidence:** A complete internal record may contain margin, employee, supplier, security, licensed-software, or other-customer data that cannot be disclosed wholesale.

**Confidence:** High on bundle structure; medium on the customer-visible boundary.

**Business-specific validation:** Define the disclosure matrix, test independent import, review CAD licenses, and have pilot customers verify repeat-order and supplier-transfer utility.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. A signed, versioned export bundle is the right direction, but the “complete record” still needs a disclosure boundary for licensed, security-sensitive, supplier, and internal-only content.

### DAT-009 — Which historical customer-part data may be reused for internal estimating and learning?

**State:** Exploring

**First-pass answer:** Reuse only records with documented contractual and policy authority. Treat raw geometry, drawings, identity, pricing, margin, and restricted technical data as highest sensitivity; prefer customer-specific reuse, then approved derived process features and actuals, and use aggregated benchmarks only after re-identification and competitive-sensitivity review. Removing a name does not make a distinctive part anonymous.

**Evidence:**
- [NIST Privacy Framework](https://www.nist.gov/privacy-framework) — **Evidence class:** official guidance. It supports purpose limitation and controlled secondary use, though manufacturing IP is broader than personal data.
- [NISTIR 8053](https://doi.org/10.6028/NIST.IR.8053) — **Evidence class:** official research guidance. It explains why apparently de-identified data can sometimes be re-identified.
- [Reference-based manufacturing cost estimation study](https://doi.org/10.1017/pds.2023.263) — **Evidence class:** empirical prototype study. Experienced cost engineers saw potential, but weak data availability and linkage limited automation; it is one-company evidence.

**Conflicting evidence:** Strict customer isolation reduces legal risk but also reduces cross-job learning; aggregation can remove the distinctions that make analogs useful.

**Confidence:** High on category separation; medium on permitted derived-data use.

**Business-specific validation:** Review actual NDAs and terms, create a rights matrix per field, red-team geometry re-identification, and measure estimation value from progressively less-sensitive data.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly treats distinctive geometry and commercial actuals as re-identification risks and does not overstate what de-identification can achieve.

### DAT-010 — How are customer permissions and confidentiality enforced in the historical-part inventory?

**State:** Exploring

**First-pass answer:** Encode permissions as data: every source and derivative carries tenant, project, owner, purpose, sensitivity, export, consent, retention, and reuse attributes. Enforce authorization during ingestion, search, analog ranking, training, explanation, export, support access, and deletion; a no-reuse decision must exclude the record from cross-customer features, indexes, caches, and training, not just hide its name.

**Evidence:**
- [NIST SP 800-162](https://csrc.nist.gov/pubs/sp/800/162/upd2/final) — **Evidence class:** official access-control guidance. ABAC supports policy decisions using subject, resource, action, and environmental attributes.
- [NIST SP 800-171 Rev. 3](https://csrc.nist.gov/pubs/sp/800/171/r3/final) — **Evidence class:** official sensitive-data guidance. It covers access, audit, media, communications, and suppliers.
- [NISTIR 8053](https://doi.org/10.6028/NIST.IR.8053) — **Evidence class:** official research guidance. It shows the limits of simplistic anonymization.

**Conflicting evidence:** Fine-grained authorization can make search slower and policy harder to reason about, while broad “derived data” exceptions can defeat the original restriction.

**Confidence:** High.

**Business-specific validation:** Build negative tests for forbidden analogs, simulate deletion and policy changes, audit every estimate’s source records, and obtain export-control review.

**Second-opinion review:** GPT-5.4 — Agree. Encoding permissions as data and enforcing them through ingestion, search, ranking, training, and deletion is the right production-grade interpretation of confidentiality controls.

## Platform and Architecture

### PLT-001 — What is the authoritative digital thread for a project?

**State:** Exploring

**First-pass answer:** Use a versioned project graph or ledger linking immutable revisions and events, not a single CAD file or mutable row. Every requirement, artifact, approval, plan, execution record, inspection, exception, and shipment needs a stable ID, revision, status, provenance, and relationship to the exact inputs that produced it; individual ERP, PLM, or quality systems may remain authoritative for their own objects.

**Evidence:**
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It describes bidirectional exchange among product definition, manufacturing, and quality.
- [W3C PROV-O](https://www.w3.org/TR/prov-o/) — **Evidence class:** official standard. It models entities, activities, agents, use, generation, and derivation.
- [QIF](https://qifstandards.org/) — **Evidence class:** official quality standard. It links quality plans and results to geometry and PMI.

**Conflicting evidence:** A graph improves traceability but adds governance and synchronization complexity, and no one database should unnecessarily duplicate every authoritative system.

**Confidence:** High on a federated linked-record model.

**Business-specific validation:** Map system-of-record ownership for a pilot job and prove every delivered item traces to approved requirements and revisions.

**Second-opinion review:** GPT-5.4 — Agree. A linked revision-and-event graph is the right digital-thread model here, and the answer sensibly avoids pretending one mutable table or CAD file can be authoritative for everything.

### PLT-002 — Which objects require immutable revision history?

**State:** Exploring

**First-pass answer:** Preserve revisions for customer inputs, requirements, contracts, CAD/drawings/PMI, assumptions, approvals, quotes, plans, setups, tooling, NC programs, inspection plans/results, deviations, nonconformances, shipments, algorithms/models/rules, access-policy changes, and human overrides. Correct by append or supersession, not silent overwrite; retain transient raw data only as long as justified.

**Evidence:**
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It emphasizes trustworthy lifecycle data and physical risk from tampering.
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** official control catalog. Audit, configuration management, integrity, and change controls support attributable history.
- [W3C PROV-O](https://www.w3.org/TR/prov-o/) — **Evidence class:** official standard. It represents derivation and responsibility, though provenance alone is not tamper-proof.

**Conflicting evidence:** Making every sensor sample immutable indefinitely is costly and may conflict with deletion duties; preserve evidentiary events and bounded raw windows.

**Confidence:** High on record classes; medium on retention granularity.

**Business-specific validation:** Run failure, dispute, recall, and repeat-build tabletop exercises to determine which records are needed.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The immutable-history list is strong, but the answer correctly stops short of making every raw stream immutable forever; evidentiary value should drive granularity.

### PLT-003 — How are requirements, CAD revisions, approvals, quotes, manufacturing plans, inspection records, and shipments linked?

**State:** Exploring

**First-pass answer:** Assign stable IDs and typed relationships such as `satisfies`, `derived-from`, `approved`, `priced-by`, `planned-from`, `executed-as`, `inspected-against`, `deviates-from`, and `shipped-as`. Every approval and downstream artifact must identify its exact revision; upstream changes should automatically mark affected approvals, plans, programs, and inspections stale until revalidated.

**Evidence:**
- [W3C PROV-O](https://www.w3.org/TR/prov-o/) — **Evidence class:** official standard. It supports generation, derivation, use, responsibility, and time.
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It supports lifecycle interoperability and traceability.
- [QIF](https://qifstandards.org/) — **Evidence class:** official standard. It associates geometry, PMI, measurement plans, resources, and results.

**Conflicting evidence:** Document folders are cheaper and familiar but cannot reliably answer which exact revision an approval, program, or result covered.

**Confidence:** High.

**Business-specific validation:** Reconstruct a changed-order scenario and verify automated stale-artifact detection from requirement change through shipment.

**Second-opinion review:** GPT-5.4 — Agree. Stable IDs plus typed relationships and stale-artifact detection are exactly what turns traceability from documentation into operational control.

### PLT-004 — Which workflow states are visible to customers versus internal users?

**State:** Exploring

**First-pass answer:** Customers should see a small accurate set—submitted, clarification needed, quoting, awaiting approval/payment, planning, material/supplier work, production, inspection, shipping, complete, and blocked with an appropriate explanation. Internal users need finer setup, queue, maintenance, scrap/rework, supplier, commercial, security, and root-cause states; do not expose margin, employee, other-customer, security, or unverified fault detail.

**Evidence:**
- [ISA-95](https://www.isa.org/standards-and-publications/isa-standards/isa-95-standard) — **Evidence class:** primary manufacturing standard. It separates enterprise, operations, and control activities and exchanges.
- [NIST Privacy Framework](https://www.nist.gov/privacy-framework) — **Evidence class:** official guidance. It supports controlled disclosure and minimization.
- [Fictiv workflow](https://www.fictiv.com/why-choose-fictiv/how-it-works) — **Evidence class:** delivered digital-manufacturing workflow. It demonstrates customer-facing order, configuration, DFM, and inspection records without exposing every internal operation.

**Conflicting evidence:** Greater transparency can build trust, but raw machine and supplier states can confuse customers and reveal sensitive operations.

**Confidence:** Medium.

**Business-specific validation:** Prototype state labels with inventors, engineers, and overflow-shop buyers and measure support questions and misunderstandings.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The customer/internal split is sensible, but the exact visible state set still needs usability testing so it informs rather than confuses or over-discloses.

### PLT-005 — Which events must be auditable?

**State:** Exploring

**First-pass answer:** Audit authentication, authorization, sensitive reads/exports, uploads and revisions, approvals/rejections, quote changes, model/rule execution, overrides, NC release, supervisory commands, inspection acceptance, nonconformance disposition, supplier handoffs, shipment, policy changes, privileged support, deletion, and incident actions. Record actor, time, object/revision, action, reason, result, and correlation ID without logging secrets.

**Evidence:**
- [NIST SP 800-92](https://csrc.nist.gov/pubs/sp/800/92/final) — **Evidence class:** official log-management guidance. It covers collection, protection, analysis, and retention.
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** official control catalog. Audit, access, configuration, and integrity controls support accountability.
- [W3C PROV-O](https://www.w3.org/TR/prov-o/) — **Evidence class:** official standard. It supports attributable activity and derivation chains.

**Conflicting evidence:** Logging everything can expose IP, credentials, personal data, and excessive cost; payloads should be minimized.

**Confidence:** High.

**Business-specific validation:** Run incident, dispute, and recall exercises and verify the logs answer who did what, to which revision, why, and with what outcome.

**Second-opinion review:** GPT-5.4 — Agree. The audited-event set is appropriately consequence-focused and already incorporates the main contrary evidence that overlogging can create its own security and privacy problems.

### PLT-006 — What must continue working when an external AI, CAD, supplier, payment, or notification service is unavailable?

**State:** Exploring

**First-pass answer:** Safety, machine protection, current-job instructions, approved programs, local inspection, evidence capture, data integrity, and emergency/manual procedures must not depend on an internet service. Existing-record viewing should degrade gracefully and outbound calls should queue idempotently; AI recommendations, payments, conversion, supplier, and notification work may pause without corrupting state or substituting unapproved output.

**Evidence:**
- [NIST SP 800-34 Rev. 1](https://csrc.nist.gov/pubs/sp/800/34/r1/upd1/final) — **Evidence class:** official contingency guidance. It supports business impact analysis, alternate processing, recovery, and exercises.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** official OT guidance. It prioritizes safety, availability, resilience, and operational constraints.
- [CISA Secure Connectivity Principles for OT](https://www.cisa.gov/resources-tools/resources/secure-connectivity-principles-operational-technology-ot) — **Evidence class:** official guidance. It supports controlled dependencies and minimized connectivity risk.

**Conflicting evidence:** Full offline equivalence is costly and unnecessary for many convenience features; active production and safety deserve stronger independence than quoting.

**Confidence:** High.

**Business-specific validation:** Drill provider failures during quoting and simulated production and establish RTO/RPO and manual work instructions per dependency.

**Second-opinion review:** GPT-5.4 — Agree. The answer draws the right resilience boundary: safety, current operations, and evidence integrity must survive provider loss, while convenience features may degrade gracefully.

### PLT-007 — Which recommendations must be deterministic and reproducible?

**State:** Exploring

**First-pass answer:** Geometry validation, units, tolerance checks, machine-envelope and collision/safety limits, cost and tax arithmetic, access decisions, release gates, and machine commands should be deterministic or independently verifiable. AI may propose interpretations, plans, analogs, and explanations, but final engineering and commercial checks must be rerunnable from versioned inputs.

**Evidence:**
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** official AI guidance. It emphasizes validity, reliability, transparency, accountability, and risk-proportionate controls.
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It highlights trustworthy product information and physical consequences of tampering.
- [NISTIR 8312](https://doi.org/10.6028/NIST.IR.8312) — **Evidence class:** official explainability research. It requires accurate explanations and awareness of knowledge limits.

**Conflicting evidence:** Useful optimization can be stochastic; reproducibility may mean preserving seed, version, environment, and constraint verification rather than banning randomness.

**Confidence:** High on safety and calculation boundaries.

**Business-specific validation:** Classify every recommendation by consequence and require independent validators for quote, manufacturing, and control-affecting releases.

**Second-opinion review:** GPT-5.4 — Agree. The deterministic-versus-proposed split is well drawn and is especially important for calculations, release gates, and any path that could affect machine behavior or customer commitments.

### PLT-008 — How are model and algorithm versions recorded?

**State:** Exploring

**First-pass answer:** For every consequential invocation record provider/model, immutable version or artifact hash, deployment/configuration, prompt template, tool/rule versions, input artifact IDs, retrieval snapshot, parameters/seed where available, output, validator results, policy decision, human corrections, and release status. Mutable provider aliases are insufficient.

**Evidence:**
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** official guidance. It calls for lifecycle documentation, measurement, and accountability.
- [NIST AI 600-1](https://doi.org/10.6028/NIST.AI.600-1) — **Evidence class:** official GenAI guidance. It addresses provenance, third-party models, evaluations, and change risk.
- [W3C PROV-O](https://www.w3.org/TR/prov-o/) — **Evidence class:** official provenance standard. It links inputs, activities, outputs, and responsible agents.

**Conflicting evidence:** Full prompts and inputs may contain confidential data, so trace records need the same access and retention controls as source projects.

**Confidence:** High.

**Business-specific validation:** Replay sampled decisions after model upgrades, verify provider version guarantees, and define secure redaction and retention for invocation logs.

**Second-opinion review:** GPT-5.4 — Agree. Recording immutable versions, prompts, tool/rule context, and human corrections is necessary for replay, audit, and post-incident analysis of consequential automation.

### PLT-009 — What evidence should accompany every automated decision?

**State:** Exploring

**First-pass answer:** Include decision type, exact input revisions, output, rule/model/version, source records, calculations or similarity factors, uncertainty, missing data, assumptions, applicable limits, validator results, alternatives when material, and any human approval or override. Evidence depth should increase with consequence, and generated rationale must not substitute for independent checks.

**Evidence:**
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** official guidance. It supports documented context, measurement, accountability, and monitoring.
- [NISTIR 8312](https://doi.org/10.6028/NIST.IR.8312) — **Evidence class:** official research. It defines explanation, meaningfulness, accuracy, and knowledge limits.
- [W3C PROV-O](https://www.w3.org/TR/prov-o/) — **Evidence class:** official standard. It supports derivation and responsibility chains but does not prove correctness.

**Conflicting evidence:** More explanation can create false confidence if it is plausible but unfaithful; evidence must be reconstructable and checked.

**Confidence:** High.

**Business-specific validation:** Have machinists, estimators, customers, auditors, and engineers review sample evidence packets for faithful reconstruction and usefulness.

**Second-opinion review:** GPT-5.4 — Agree. The evidence packet is appropriately reconstruction-oriented and does not confuse generated narrative with independent technical justification.

### PLT-010 — What interfaces are needed for CAD tools, ERP/MRP, accounting, inventory, machines, metrology, suppliers, and shipping?

**State:** Exploring

**First-pass answer:** Use a canonical internal model with versioned adapters: STEP/native CAD and PMI; QIF for quality; ISA-95-style ERP/MES boundaries; MTConnect for normalized monitoring; OPC UA for secure richer integration where justified; and APIs, webhooks, or controlled files for accounting, inventory, suppliers, and shipping. Preserve source IDs and raw payloads for reversible audit.

**Evidence:**
- [ISA-95](https://www.isa.org/standards-and-publications/isa-standards/isa-95-standard) — **Evidence class:** primary manufacturing standard. It defines enterprise/control integration objects and exchanges.
- [OPC UA](https://opcfoundation.org/about/opc-technologies/opc-ua/) — **Evidence class:** official industry architecture. It supports secure information models, reads/writes, events, methods, and auditing.
- [MTConnect documentation](https://docs.mtconnect.org/) — **Evidence class:** official open manufacturing standard. It provides normalized equipment observations, usually focused on monitoring.
- [QIF](https://qifstandards.org/) — **Evidence class:** official quality standard. It supports interoperable metrology and inspection data.

**Conflicting evidence:** Standards reduce but do not eliminate semantic mismatches; legacy machines and business systems often require proprietary adapters.

**Confidence:** High on interface families; medium on implementation priority.

**Business-specific validation:** Inventory actual target machines and partner systems and build one end-to-end CAD-to-inspection-to-accounting pilot.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The interface families are well chosen, but real deployments will still need proprietary adapters and careful semantic mapping beyond what the standards alone guarantee.

### PLT-011 — What progressive levels of production history should customers and internal users be able to inspect?

**State:** Exploring

**First-pass answer:** Provide four controlled levels: milestone/status history; material/setup/machine/supplier/inspection summary; revisioned operations, parameters, measurements, approvals, custody, and exceptions; and restricted raw programs, telemetry, images/video, environment, and complete evidence. Customers receive contract-appropriate views; qualified internal users receive deeper need-to-know access.

**Evidence:**
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It supports lifecycle traceability across engineering, manufacturing, and quality.
- [QIF](https://qifstandards.org/) — **Evidence class:** official quality standard. It supports structured inspection plans and results.
- [NIST Privacy Framework](https://www.nist.gov/privacy-framework) — **Evidence class:** official guidance. It supports minimization and controlled disclosure.

**Conflicting evidence:** Raw history can overwhelm customers and expose trade secrets, while summaries may be insufficient for repeatability or disputes.

**Confidence:** Medium-high.

**Business-specific validation:** Test the four levels with target customer roles and determine storage cost, confidentiality exclusions, and willingness to pay.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The four-level history model is sensible, but contract scope, confidentiality, and storage cost will determine how much raw evidence is customer-visible by default.

### PLT-012 — Which actual machine, process, image, video, and inspection records should be retained for part reproducibility?

**State:** Exploring

**First-pass answer:** Always retain approved revision, machine/fixture/setup identity, program hash, offsets and critical parameters, material/lot, tooling, process sequence, deviations, inspection plan/results, nonconformance disposition, and actual cycle/setup/labor summaries. Retain high-rate telemetry or video selectively around first articles, faults, anomalies, learning jobs, and regulated evidence, and summarize routine stable production.

**Evidence:**
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It supports trustworthy product/manufacturing/quality linkage.
- [MTConnect documentation](https://docs.mtconnect.org/) — **Evidence class:** official standard. It provides normalized observations, events, conditions, and equipment-state records.
- [QIF](https://qifstandards.org/) — **Evidence class:** official quality standard. It covers structured measurement planning and results.
- [NIST Privacy Framework](https://www.nist.gov/privacy-framework) — **Evidence class:** official guidance. It is relevant when cameras capture workers or unrelated activity.

**Conflicting evidence:** More raw data does not guarantee reproducibility; unsynchronized, uncalibrated, context-free signals can mislead.

**Confidence:** Medium-high.

**Business-specific validation:** Conduct a repeat-build exercise using retained records only and identify both missing data and streams that are never used.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The selective-retention approach is practical, but reproducibility depends as much on synchronization, calibration, and context quality as on data volume.

### PLT-013 — How should the platform integrate heterogeneous legacy machine controls and add-on automation?

**State:** Exploring

**First-pass answer:** Use staged trust: begin with segmented read-only adapters and edge gateways, normalize observations, then add authenticated allow-listed supervisory commands only where justified; reserve direct control for vendor-supported and independently safeguarded designs. Gateways need protocol conversion, clock synchronization, local buffering, configuration/version records, fail-safe behavior, and manual operation during platform loss.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** official OT security guidance. It covers segmentation, legacy constraints, safety, availability, and architecture.
- [CISA Secure Connectivity Principles for OT](https://www.cisa.gov/resources-tools/resources/secure-connectivity-principles-operational-technology-ot) — **Evidence class:** official guidance. It supports controlled, minimized, resilient connectivity.
- [MTConnect documentation](https://docs.mtconnect.org/) — **Evidence class:** official manufacturing standard. It provides practical read-oriented normalization but is not a safety-control protocol.
- [OPC UA](https://opcfoundation.org/about/opc-technologies/opc-ua/) — **Evidence class:** official architecture. It supports secure models and authorized methods; write capability increases risk.

**Conflicting evidence:** Retrofit connectivity can extend useful machine life, but insecure undocumented legacy controls may be unsuitable for networked command.

**Confidence:** High on staged integration; medium per machine.

**Business-specific validation:** Survey each control and firmware, obtain OEM/integrator review, test gateway and malformed-command failures, and perform machinery risk assessment before writes.

**Second-opinion review:** GPT-5.4 — Agree. Staged read-first integration with tightly governed writes is the safest credible pattern for mixed legacy controls and retrofit automation.

### PLT-014 — What common machine-state and job interface should every retrofit expose?

**State:** Exploring

**First-pass answer:** Expose identity, availability, controller mode, execution, condition/faults, safety state, active job/operation, setup/material/operator waits, inspection/maintenance, quantities, timestamps, source quality, and command capability. Map raw evidence to unavailable, idle, setup, ready, running, waiting, inspection, blocked, faulted, maintenance, complete, and safely stopped, while preserving an unknown/ambiguous state.

**Evidence:**
- [MTConnect documentation](https://docs.mtconnect.org/) — **Evidence class:** official manufacturing standard. It defines availability, execution, controller mode, conditions, observations, and sequence/time mechanisms.
- [ISA-95](https://www.isa.org/standards-and-publications/isa-standards/isa-95-standard) — **Evidence class:** primary standard. It provides equipment hierarchy and manufacturing-operations activity models.
- [OPC UA](https://opcfoundation.org/about/opc-technologies/opc-ua/) — **Evidence class:** official architecture. It supports extensible information models, events, history, and methods.

**Conflicting evidence:** A universal state model can hide machine-specific nuance; raw events and ambiguity must remain accessible.

**Confidence:** High on the core interface; medium on derived-state definitions.

**Business-specific validation:** Label real shifts across several machine families and measure state ambiguity against operator judgments before using states for costing or scheduling.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The common state model is strong, provided unknown and machine-specific nuance remain available so the abstraction does not hide important operational context.

### PLT-015 — What low-cost observability architecture supports bottleneck analysis?

**State:** Exploring

**First-pass answer:** Use edge-first collection: existing controller data through MTConnect/OPC UA; inexpensive current, vibration, or digital sensors only for defined questions; fixed cameras for bounded observations; synchronized clocks; local store-and-forward; health/calibration monitoring; and a common event schema linking machine, job, operation, source, confidence, and raw evidence. Retain derived events longer than routine raw video.

**Evidence:**
- [MTConnect documentation](https://docs.mtconnect.org/) — **Evidence class:** official standard. It lowers integration cost for normalized machine observations, though adapter quality varies.
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** official guidance. It supports segmented edge architectures and operational resilience.
- [NIST MEP TPM case](https://www.nist.gov/mep/successstories/2022/total-productive-maintenance-reduces-equipment-downtime-and-lost-capacity) — **Evidence class:** observed operating result. One CNC-focused improvement reported about 22% productivity improvement and OEE rising from 39% to 45%; it was not an AI or controlled sensor study.

**Conflicting evidence:** Manual observation and visual management can outperform an expensive data platform when process definitions and maintenance discipline are weak.

**Confidence:** Medium-high.

**Business-specific validation:** Instrument one bottleneck cheaply, compare automated events with operator logs, quantify misses and false events, and require measured savings before scaling.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Edge-first observability is production-applicable, but the strongest cited benefit evidence is operational case study rather than controlled ROI proof, so scale should follow measured savings.

### PLT-016 — How are bottleneck evidence, recommendations, approvals, and outcomes traced?

**State:** Exploring

**First-pass answer:** Create an intervention record with baseline period and product mix, raw/derived evidence, bottleneck definition, rule/model version, candidate actions, cost/ROI assumptions, risks, approver and decision, implementation, confounders, post-change window, result, and keep/rollback conclusion. Avoid attributing unmatched before/after trends to the intervention.

**Evidence:**
- [W3C PROV-O](https://www.w3.org/TR/prov-o/) — **Evidence class:** official standard. It supports linked evidence, activities, derivations, and responsibility.
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** official guidance. It calls for outcome measurement, documentation, and continuing risk management.
- [NIST MEP TPM case](https://www.nist.gov/mep/successstories/2022/total-productive-maintenance-reduces-equipment-downtime-and-lost-capacity) — **Evidence class:** observed operation. It demonstrates baseline and OEE measurement around improvement, but not controlled causal attribution.

**Conflicting evidence:** Demand, product mix, staffing, maintenance, and learning can cause apparent gains unrelated to the recommendation.

**Confidence:** High on trace structure; medium on causal inference.

**Business-specific validation:** Pre-register metrics and attribution rules and use controlled or staggered trials where practical.

**Second-opinion review:** GPT-5.4 — Agree. The intervention record structure is the right way to separate evidence, recommendation, approval, and outcome without overclaiming causality from simple before/after trends.

### PLT-017 — What is the canonical digital record for a previously manufactured part?

**State:** Exploring

**First-pass answer:** Use a revisioned part/job dossier or manifest containing identity and access scope, requirements and geometry, material/stock, quantity, setups, workholding, tools and programs, planned and actual machine/labor events, inspection and deviations, outside processing, costs, price and margin, delivery/quality outcomes, approved media/logs, lessons, and links to source evidence. Keep physical cost distinct from commercial price.

**Evidence:**
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It supports product-definition, manufacturing, quality, trust, and traceability links.
- [QIF](https://qifstandards.org/) — **Evidence class:** official quality standard. It structures geometry/PMI-related inspection information.
- [ISA-95](https://www.isa.org/standards-and-publications/isa-standards/isa-95-standard) — **Evidence class:** primary manufacturing standard. It supplies equipment, material, personnel, production, and operations concepts.

**Conflicting evidence:** A physically duplicated master record creates synchronization risk; the canonical record should usually be a manifest over authoritative systems.

**Confidence:** High.

**Business-specific validation:** Conduct a repeat estimate and repeat build using the dossier and compare needs across estimating, machining, quality, finance, and customers.

**Second-opinion review:** GPT-5.4 — Agree. A dossier or manifest over authoritative systems is the right canonical record shape and correctly avoids duplicating every source system into one brittle “master” row.

### PLT-018 — How are historical actuals normalized for new estimates?

**State:** Exploring

**First-pass answer:** Preserve the original actual and calculate a separate auditable normalized value using current material/stock quotes, labor and burden, energy, tooling, suppliers, freight, machine capability, quantity/setup amortization, utilization, geography, inspection scope, process improvement, and abnormal-event adjustments. Use broad economic indexes only when direct current quotes or rates are unavailable.

**Evidence:**
- [Reference-based manufacturing cost estimation study](https://doi.org/10.1017/pds.2023.263) — **Evidence class:** empirical prototype study. It found potential effort reduction but weak product/manufacturing-data linkage as a major limit.
- [BLS Producer Price Indexes](https://www.bls.gov/ppi/) — **Evidence class:** official economic data. It supports broad material/service price changes but may poorly match a specific alloy or supplier.
- [BLS Employment Cost Index](https://www.bls.gov/eci/) — **Evidence class:** official economic data. It supports broad labor-cost changes but not actual local wage and burden.
- [EIA Electricity Data Browser](https://www.eia.gov/electricity/data/browser/) — **Evidence class:** official economic data. It provides regional electricity prices, while tariffs and machine loads remain shop-specific.

**Conflicting evidence:** Inflation indexing alone creates false precision when geometry, yield, machine choice, quantity, or supplier changes dominate.

**Confidence:** High on the method; medium on formulas.

**Business-specific validation:** Back-test normalized estimates against recent actuals, capture current supplier quotes and local rates, and report prediction intervals.

**Second-opinion review:** GPT-5.4 — Agree. Preserving raw actuals and computing auditable normalized views is the right estimating pattern, and the answer properly warns against inflation-only false precision.

## AI and Automation Boundaries

For AI claims below, **AI potential** means plausible guidance or capability without operating proof; **benchmark demonstration** means measured performance in a bounded test; and **dependable operating evidence** means sustained real-work use with measured outcomes. Vendor/customer operating reports are useful but weaker than independently audited results.

### AIA-001 — Which tasks are suitable for generative AI versus deterministic engineering software?

**State:** Exploring

**First-pass answer:** Use generative AI for language-heavy, ambiguity-tolerant work such as intake summarization, requirement extraction for review, document search, draft communications, supplier comparison, explanation, and candidate plans. Use deterministic or constrained software for geometry, units, tolerance stacks, physics, collision checks, cost arithmetic, access control, quality acceptance, release gates, and machine control; AI proposals must pass independent validators and qualified review when consequences are material.

**Evidence:**
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** AI potential / official guidance. It supports context- and consequence-based control allocation but does not provide a manufacturing task list.
- [NIST AI 600-1](https://doi.org/10.6028/NIST.AI.600-1) — **Evidence class:** AI potential / official guidance. It identifies confabulation, privacy, over-reliance, and third-party GenAI risks.
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It demonstrates the physical importance of conformant product information, not LLM reliability.

**Conflicting evidence:** Deterministic software can encode wrong assumptions, while AI can improve discovery and exception handling; consequence and independent verifiability matter more than the label.

**Confidence:** High.

**Business-specific validation:** Classify planned features by harm and verifiability and run shadow-mode comparisons against qualified staff before granting workflow authority.

**Second-opinion review:** GPT-5.4 — Agree. The split between language-heavy assistance and deterministic engineering or control logic is well supported, especially given the physical consequence of manufacturing errors.

### AIA-002 — How are hallucinations or unsupported claims detected?

**State:** Exploring

**First-pass answer:** Do not rely on an AI hallucination detector. Ground factual outputs in approved sources, require claim-level citations, verify quotations and calculations programmatically, constrain schemas, compare critical fields with authoritative records, test adversarial and out-of-scope inputs, and route unsupported, conflicting, or high-consequence claims to humans. “Unsupported” must be a valid output.

**Evidence:**
- [NIST AI 600-1](https://doi.org/10.6028/NIST.AI.600-1) — **Evidence class:** AI potential / official guidance. It treats confabulation as a key GenAI risk and recommends evaluation and controls; no control eliminates it.
- [NIST TEVV-Athlon](https://www.nist.gov/artificial-intelligence/ai-research/tevv-athlon-framework-evaluating-ai-systems) — **Evidence class:** AI potential / draft official evaluation framework. It emphasizes customized real-world testing; it is not evidence of this system’s performance.
- [NISTIR 8312](https://doi.org/10.6028/NIST.IR.8312) — **Evidence class:** official research. It requires explanation accuracy and knowledge-limit awareness, while models may still fail to recognize their limits.

**Conflicting evidence:** Retrieval and citations reduce unsupported answers but can attach irrelevant or incorrect sources; fluent self-critique is not independent verification.

**Confidence:** High on the controls; low that all hallucinations can be detected.

**Business-specific validation:** Build a gold set from actual drawings, requirements, quotes, and adversarial cases and measure unsupported-claim rate and severity per workflow.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly rejects the idea of a reliable hallucination detector and instead uses grounding, validation, citations, schemas, and human escalation.

### AIA-003 — When must the system say it does not know?

**State:** Exploring

**First-pass answer:** Abstain when required inputs or authoritative sources are missing, revisions conflict, the request is outside validated scope, data is out-of-distribution, confidence is below a task threshold, validators disagree, tools fail, permissions block evidence, or consequences exceed authorized autonomy. High-risk thresholds should favor deferral over unsafe certainty.

**Evidence:**
- [NISTIR 8312](https://doi.org/10.6028/NIST.IR.8312) — **Evidence class:** AI potential / official research. Its knowledge-limits principle says systems should recognize conditions outside competence.
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** AI potential / official guidance. It calls for mapped context, validity, reliability, and escalation.
- [NIST TEVV-Athlon](https://www.nist.gov/artificial-intelligence/ai-research/tevv-athlon-framework-evaluating-ai-systems) — **Evidence class:** AI potential / draft official framework. It supports task-specific real-world evaluation but does not supply universal abstention thresholds.

**Conflicting evidence:** Excessive abstention destroys usefulness and shifts hidden work to humans; thresholds need measured risk/coverage trade-offs.

**Confidence:** High on abstention conditions; medium on thresholds.

**Business-specific validation:** Define harm-weighted false-answer versus deferral costs and measure accepted-answer coverage and error on representative jobs.

**Second-opinion review:** GPT-5.4 — Agree. The abstention conditions are appropriate and the review rightly favors deferral over unsupported certainty in high-consequence workflows.

### AIA-004 — Which outputs require source citations or calculations?

**State:** Exploring

**First-pass answer:** Require citations for factual claims affecting requirements, material/process selection, machine capability, standards, law, safety, quality, supplier performance, delivery, and commitments. Require visible inputs, units, formulas, rates, assumptions, ranges, and revision dates for quotes, cycle time, capacity, tolerance, ROI, and schedules. Clearly labeled style-only drafting may not require citations.

**Evidence:**
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** AI potential / official guidance. It supports transparency, accountability, and documented measurement.
- [NISTIR 8312](https://doi.org/10.6028/NIST.IR.8312) — **Evidence class:** official explainability research. It requires meaningful and accurate evidence or reasons.
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It emphasizes trustworthy product data and the physical effects of corruption.

**Conflicting evidence:** Citations can overwhelm routine interfaces and still be misapplied; show concise evidence by default with drill-down.

**Confidence:** High.

**Business-specific validation:** Define a consequence matrix and test whether evidence and calculation drill-downs support real customer and engineering approvals.

**Second-opinion review:** GPT-5.4 — Agree. The proposed citation and calculation boundary is practical and consequence-based, with enough nuance to avoid overwhelming low-risk style-only interactions.

### AIA-005 — How are prompts, model versions, inputs, outputs, and human corrections traced?

**State:** Exploring

**First-pass answer:** Treat each consequential invocation as a provenance event: actor, project, purpose, prompt-template version, authorized input references, retrieval snapshot, model/provider/version, parameters, tools, output, validators, confidence, policy result, human edit/override and reason, approval, and observed outcome. Protect these logs as customer data and reference immutable source IDs instead of duplicating secrets unnecessarily.

**Evidence:**
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** AI potential / official guidance. It supports lifecycle documentation and accountability.
- [NIST AI 600-1](https://doi.org/10.6028/NIST.AI.600-1) — **Evidence class:** AI potential / official guidance. It addresses provenance, third-party model risk, and monitoring.
- [W3C PROV-O](https://www.w3.org/TR/prov-o/) — **Evidence class:** official provenance standard. It provides a general representation of entities, activities, and agents.
- [NIST SP 800-92](https://csrc.nist.gov/pubs/sp/800/92/final) — **Evidence class:** official logging guidance. It supports protected and reviewable logs.

**Conflicting evidence:** Full prompt and input logging can duplicate confidential designs and enlarge breach exposure.

**Confidence:** High.

**Business-specific validation:** Replay audited samples, verify correction and deletion propagation, and test access policy for AI logs.

**Second-opinion review:** GPT-5.4 — Agree. Treating consequential AI use as a provenance event is the right control, and the answer correctly flags the resulting logs as sensitive customer data.

### AIA-006 — What customer data may be sent to an external model?

**State:** Exploring

**First-pass answer:** Send only minimum authorized fields after policy checks, preferring redacted text and derived features over raw CAD, drawings, identity, price, export-controlled data, credentials, or full project records. External use requires an approved provider, no-training/retention terms, allowed region, encryption, audit, and project/customer permission; restricted projects use an approved isolated/local model or no model.

**Evidence:**
- [NIST AI 600-1](https://doi.org/10.6028/NIST.AI.600-1) — **Evidence class:** AI potential / official guidance. It covers privacy, information-security, third-party, and data risks.
- [NIST SP 800-171 Rev. 3](https://csrc.nist.gov/pubs/sp/800/171/r3/final) — **Evidence class:** official sensitive-data guidance. It is relevant to CUI access, transmission, acquisition, and suppliers when applicable.
- [NIST SP 800-161 Rev. 1](https://csrc.nist.gov/pubs/sp/800/161/r1/upd1/final) — **Evidence class:** official supply-chain guidance. It supports provider assessment and contractual risk treatment.

**Conflicting evidence:** Enterprise no-training terms reduce risk but do not resolve support access, abuse monitoring, breach, jurisdiction, or downstream-subprocessor concerns.

**Confidence:** High on a restrictive default; medium on provider eligibility.

**Business-specific validation:** Create field-level egress policy, inspect provider contracts and architecture, test DLP/redaction, and obtain export/privacy/IP counsel review.

**Second-opinion review:** GPT-5.4 — Agree. A minimum-necessary egress policy with provider, region, retention, and no-training checks is the right default for external-model use.

### AIA-007 — How is automation confidence calibrated against actual manufacturing outcomes?

**State:** Exploring

**First-pass answer:** Calibrate separately by task and operating domain. Link predictions to actual cycle time, cost, scrap, inspection, downtime, delivery, and human correction; report error distributions, prediction intervals, and coverage rather than a generic AI score. Use holdout jobs, risk-coverage curves, drift monitoring, cost-weighted errors, and automatic deferral when calibration degrades.

**Evidence:**
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** AI potential / official guidance. It requires context-specific measurement and monitoring.
- [NIST TEVV-Athlon](https://www.nist.gov/artificial-intelligence/ai-research/tevv-athlon-framework-evaluating-ai-systems) — **Evidence class:** AI potential / draft official framework. It centers evaluation on real-world objectives and outcomes.
- [Siemens/P&G AI inspection deployment](https://press.siemens.com/global/en/pressrelease/siemens-and-procter-gamble-scale-ai-based-quality-inspection-across-global-production) — **Evidence class:** dependable operating evidence, vendor/customer reported. It reports production rollout and 10–20% scrap reduction depending on product, but is not independently audited and is task-specific.

**Conflicting evidence:** Benchmark accuracy can remain high while factory calibration fails under new materials, machines, lighting, quantities, or maintenance states.

**Confidence:** High on method; low until sufficient shop actuals exist.

**Business-specific validation:** Begin in shadow mode and require statistically meaningful outcomes across intended materials, machines, and product mix before operational reliance.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The calibration method is strong, but the cited operating evidence is still task-specific and partly vendor/customer reported, so real trust must come from local shadow-mode outcomes.

### AIA-008 — What mechanisms prevent AI-generated content from directly controlling hazardous equipment?

**State:** Exploring

**First-pass answer:** Give generative AI no direct write path to PLCs, CNC controls, robots, or safety systems. It may produce only a candidate artifact that passes deterministic schema and constraint checks, simulation, revision control, qualified-human release, signed job authorization, allow-listed commands, and independent safety PLCs/interlocks, guarding, emergency stops, and local fail-safe control.

**Evidence:**
- [NIST SP 800-82 Rev. 3](https://csrc.nist.gov/pubs/sp/800/82/r3/final) — **Evidence class:** official OT guidance. It prioritizes safety, segmentation, access control, tested change, and availability.
- [OSHA 29 CFR 1910.212](https://www.osha.gov/laws-regs/regulations/standardnumber/1910/1910.212) — **Evidence class:** primary regulatory guidance. It requires guarding against machine hazards, though additional machinery standards may apply.
- [CISA Secure Connectivity Principles for OT](https://www.cisa.gov/resources-tools/resources/secure-connectivity-principles-operational-technology-ot) — **Evidence class:** official guidance. It supports minimizing and governing control-network connectivity.

**Conflicting evidence:** A human click is not meaningful protection when review is rushed or the generated artifact is unreadable; independent technical validation is essential.

**Confidence:** High on prohibition and separation; implementation requires qualified safety engineering.

**Business-specific validation:** Obtain machine-safety/integrator review, perform hazard analysis and validation, penetration-test command boundaries, and simulate AI, network, and validator failures.

**Second-opinion review:** GPT-5.4 — Agree. The no-direct-write boundary for generative AI is the correct safety posture; meaningful protection comes from deterministic validation and independent safeguards, not a nominal human click.

### AIA-009 — How are customer-facing explanations kept accurate but understandable?

**State:** Exploring

**First-pass answer:** Use layers: one-sentence conclusion, key drivers and assumptions, confidence/range and missing information, source/calculation links, then an expert evidence view. Generate the explanation from the structured decision record rather than asking a model to invent a post-hoc rationale, and visibly distinguish customer facts, measured actuals, estimates, and alternatives.

**Evidence:**
- [NISTIR 8312](https://doi.org/10.6028/NIST.IR.8312) — **Evidence class:** AI potential / official research. It requires explanations meaningful to the audience and accurate to the process.
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** AI potential / official guidance. It supports stakeholder-appropriate transparency and accountability.
- [W3C PROV-O](https://www.w3.org/TR/prov-o/) — **Evidence class:** official provenance standard. It can anchor explanations in actual evidence chains, though raw provenance is too technical for most customers.

**Conflicting evidence:** Simplification can omit decisive caveats, while technical completeness can obscure the decision.

**Confidence:** High on layered design; medium on presentation.

**Business-specific validation:** Test explanations with machinists, engineers, purchasing staff, and nontechnical inventors and measure comprehension and mistaken inference.

**Second-opinion review:** GPT-5.4 — Agree. Layered customer explanations grounded in structured decision records are much more reliable than post-hoc freeform rationales and better match explainability guidance.

### AIA-010 — Where can AI allow a small company to perform at the level of a much larger organization?

**State:** Exploring

**First-pass answer:** Near-term potential is amplifying scarce expertise in structured intake, retrieval, customer communication, quote preparation, schedule/supplier exception triage, coding/data work, inspection assistance, and knowledge capture. AI does not replace machines, metrology, maintenance, certifications, cash, qualified people, or customer trust.

**Evidence:**
- [NIST AI in manufacturing overview](https://www.nist.gov/mep/manufacturing-infographics/rise-artificial-intelligence-us-manufacturing) — **Evidence class:** AI potential / official practitioner guidance. It identifies opportunities but does not measure this business’s results.
- [Generative AI at Work](https://www.nber.org/papers/w31161) — **Evidence class:** dependable workplace evidence outside manufacturing. A study of 5,179 support agents found about 14% average productivity improvement, concentrated among less-experienced workers; transfer to engineering is unproven.
- [Siemens/P&G AI inspection deployment](https://press.siemens.com/global/en/pressrelease/siemens-and-procter-gamble-scale-ai-based-quality-inspection-across-global-production) — **Evidence class:** dependable operating evidence, vendor/customer reported. It reports production use and 10–20% scrap reduction, but at large-enterprise scale.
- [NIST MEP TPM case](https://www.nist.gov/mep/successstories/2022/total-productive-maintenance-reduces-equipment-downtime-and-lost-capacity) — **Evidence class:** dependable non-AI operating evidence. Reported productivity and OEE gains show that operating discipline can outperform novelty.

**Conflicting evidence:** AI can initially increase review, integration, training, and error-management work; large-company performance depends on systems and capital, not model access alone.

**Confidence:** Medium.

**Business-specific validation:** Baseline workflow time and quality, run paid pilots including review/integration cost, and scale only capabilities with measured net contribution.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The near-term opportunities are credible, but the strongest productivity evidence is cross-domain and the inspection example is enterprise-specific, so claims of “large-company level” performance should stay bounded.

### AIA-011 — Which AI-assisted capabilities create durable advantage rather than temporary novelty?

**State:** Exploring

**First-pass answer:** Durable advantage comes from permissioned operating actuals, unique process knowledge, tight workflow integration, reliable evaluation, customer trust, fast correction loops, and proprietary links among requirements, geometry, operations, cost, quality, and delivery. Generic chat, summarization, and foundation-model access will commoditize; the moat is the operating system and evidence corpus.

**Evidence:**
- [Reference-based manufacturing cost estimation study](https://doi.org/10.1017/pds.2023.263) — **Evidence class:** benchmark/prototype demonstration. It found potential but identified data availability and linkage as the automation bottleneck; evidence came from one company.
- [Generative AI at Work](https://www.nber.org/papers/w31161) — **Evidence class:** dependable workplace evidence outside manufacturing. It suggests AI can diffuse expert practices but does not establish lasting competitive advantage.
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It emphasizes interoperable, trustworthy lifecycle data.
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** AI potential / official guidance. It supports repeatable evaluation, governance, and monitoring.

**Conflicting evidence:** Historical data can encode obsolete practices and errors; accumulated scale becomes a liability without rights, curation, and feedback.

**Confidence:** Medium-high.

**Business-specific validation:** Identify capabilities that improve per completed job and measure recreation difficulty and customer value in accuracy, speed, transparency, or repeatability.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The durable-advantage claim is partly strategic inference, but the cited evidence does support workflow integration, permissioned actuals, and evaluation discipline as the least commoditized components.

### AIA-012 — When can camera and AI observations be trusted for operational decisions?

**State:** Exploring

**First-pass answer:** Trust them only for a defined camera, lens, lighting, layout, part/process envelope, threshold, and error cost after representative field validation. Monitor focus, motion, occlusion, illumination, clock alignment, drift, rare events, and unknown states; begin in shadow/advisory mode and require independent confirmation for safety, final quality acceptance, or costly stops until sustained field performance is proven.

**Evidence:**
- [EMVA 1288](https://www.emva.org/standards-technology/emva-1288/) — **Evidence class:** official machine-vision standard. It supports reproducible camera/sensor characterization but does not validate an AI task.
- [MVTec AD](https://doi.org/10.1007/s11263-020-01400-4) — **Evidence class:** benchmark demonstration. It provides controlled industrial anomaly-detection imagery, not dependable factory operation.
- [MVTec AD 2](https://arxiv.org/abs/2503.21622) — **Evidence class:** benchmark demonstration. It adds lighting shifts and harder scenes but remains a preprint benchmark.
- [Siemens/P&G AI inspection deployment](https://press.siemens.com/global/en/pressrelease/siemens-and-procter-gamble-scale-ai-based-quality-inspection-across-global-production) — **Evidence class:** dependable operating evidence, vendor/customer reported. It reports in-line production use and scrap reduction, but remains product-specific and unaudited.

**Conflicting evidence:** High benchmark scores can collapse under lighting, camera, material, layout, or rare-defect changes; humans also err, so comparison must use measured task outcomes.

**Confidence:** High on validation conditions; low before site trials.

**Business-specific validation:** Collect representative shifts, changeovers, occlusions, faults, and lighting conditions; blind-label ground truth; measure false-positive/negative cost and fallback behavior.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The trust conditions are right, but most evidence is benchmark or vendor/customer report rather than independently audited cross-site production proof, so field validation remains decisive.

### AIA-013 — How should the system find and rank comparable historical parts?

**State:** Exploring

**First-pass answer:** Use staged hybrid retrieval: exact part/revision, parameterized family, then broader analogs ranked by geometry/features, material, process, size/removal, tolerance/finish, setup/workholding, tooling, NC complexity, quantity, and actual outcomes. Apply hard incompatibility filters first and show matched, differing, and missing attributes plus why each analog ranked.

**Evidence:**
- [Reference-based manufacturing cost estimation study](https://doi.org/10.1017/pds.2023.263) — **Evidence class:** benchmark/prototype demonstration. Experienced cost engineers evaluated reference identification; weak data linkage was the primary constraint.
- [Feature- and process-based manufacturing cost estimation](https://www.mdpi.com/2075-1702/10/5/319) — **Evidence class:** empirical research. It supports combining manufacturing features and process information rather than relying on visual envelope alone.
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It supports standardized product/manufacturing information and stable identifiers.

**Conflicting evidence:** Visually similar parts can have very different cost because of tolerance, fixturing, inspection, material, quantity, or access; opaque embeddings can hide why.

**Confidence:** Medium-high on architecture; low on weights until actual jobs exist.

**Business-specific validation:** Have estimators independently rank known jobs, compare retrieval with actual cost/time, and learn weights only from permissioned records while preserving override reasons.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The staged retrieval architecture is plausible, but ranking weights and incompatibility filters need to be learned and audited on real permissioned jobs.

### AIA-014 — How should similarity confidence and missing historical data affect a quote?

**State:** Exploring

**First-pass answer:** Reduce quote confidence as similarity, sample count, completeness, calibration, or outcome consistency decreases. Widen cost and lead-time ranges, add explicit contingencies or paid discovery, qualify terms, and require human review; block instant firm pricing for unusual geometry, missing tolerances, unsupported processes, weak actual-cost capture, or out-of-range quantities. Prefer a deterministic bottom-up estimate when historical analogs are weak.

**Evidence:**
- [Reference-based manufacturing cost estimation study](https://doi.org/10.1017/pds.2023.263) — **Evidence class:** benchmark/prototype demonstration. It shows automation value constrained by missing product and manufacturing information.
- [NIST AI RMF](https://doi.org/10.6028/NIST.AI.100-1) — **Evidence class:** AI potential / official guidance. It supports validity, uncertainty communication, monitoring, and consequence-based review.
- [NIST TEVV-Athlon](https://www.nist.gov/artificial-intelligence/ai-research/tevv-athlon-framework-evaluating-ai-systems) — **Evidence class:** AI potential / draft official framework. It supports application-specific outcome evaluation.
- [BLS Producer Price Indexes](https://www.bls.gov/ppi/) — **Evidence class:** official economic data. It can support fallback price normalization but cannot compensate for sparse technical analogs.

**Conflicting evidence:** Very wide ranges can be commercially unusable; bottom-up process estimates can be stronger than weak similarity.

**Confidence:** High on direction; medium on thresholds and range construction.

**Business-specific validation:** Back-test range coverage, margin loss, and win rate by confidence band and set escalation thresholds from the company’s cash and risk tolerance.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly ties quote confidence to similarity, coverage, and calibration and appropriately prefers deterministic bottom-up estimating when analog evidence is weak.
