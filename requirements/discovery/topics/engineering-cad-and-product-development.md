# Engineering, CAD, and Product Development Questions

## Purpose

Questions about engineering analysis, design approval, CAD intake, model authority, revisions, and technical deliverables.

These are discovery questions, not approved requirements. Question IDs are permanent and canonical in this file.

## Design and Engineering

### ENG-001 — What design deliverables does the customer receive: requirements, sketches, renders, CAD, drawings, calculations, BOMs, or manufacturing files?

**State:** Exploring

**First-pass answer:** Define and price deliverables per engagement. A practical tiering is concept artifacts marked nonproduction; controlled engineering requirements, released CAD/drawings, BOM, and named analyses; then a manufacturing-release package with authority, material/process, and inspection requirements. Native CAD, editable simulations, CAM, fixtures, and internal working files are included only when the SOW names them.

**Evidence:**
- [NASA technical data management](https://www.nasa.gov/reference/6-6-technical-data-management/) — **Evidence class:** official engineering guidance. Required data content, format, rights, storage, and delivery should be planned and contracted.
- [PPPL statement-of-work format](https://ncsx.pppl.gov/SystemsEngineering/SOW/sow_mt.html) — **Evidence class:** observed laboratory procurement practice. It ties tasks, reviews, baselines, tests, and data deliverables to the SOW; it is more formal than most small jobs.
- [Xometry MBD/PMI intake](https://community.xometry.com/kb/articles/851-can-xometry-accept-mbd-or-pmi-files) — **Evidence class:** delivered digital-manufacturing workflow. It requires a model, supporting human-readable artifact, and explicit configuration.

**Conflicting evidence:** Some manufacturers produce from one solid model, but that does not prove tolerances, finish, inspection, ownership, or all contractual requirements are present.

**Confidence:** High on enumerating and contracting deliverables; medium on the eventual packages.

**Business-specific validation:** Interview target buyers, test three priced packages, and obtain counsel review of native files, analysis models, CAM, and ownership terms.

**Second-opinion review:** GPT-5.4 — Agree. The answer properly makes deliverables an explicit contracted scope and does not overclaim that one artifact set fits concept, engineering, and manufacturing release equally well.

### ENG-002 — Who owns the design and intellectual property at each stage?

**State:** Exploring

**First-pass answer:** Allocate ownership in writing by IP category and stage. Each party should retain background IP; customer-supplied data remains customer-controlled; reusable provider tools, methods, and libraries normally remain provider IP; project-specific deliverables receive either assignment after payment or a precise license. Address copyright, patent, trade secret, portfolio use, subcontractors, and unpaid work separately.

**Evidence:**
- [17 U.S.C. §§201–204](https://www.copyright.gov/title17/92chap2.html) — **Evidence class:** primary law. Copyright initially vests in the author except qualifying work made for hire, and transfers generally require signed writing.
- [USPTO MPEP §301](https://www.uspto.gov/web/offices/pac/mpep/s301.html) — **Evidence class:** official patent guidance. Patent assignments and licenses transfer different rights and require deliberate documentation.
- [FAR Rights in Data—General](https://www.acquisition.gov/far/52.227-14) — **Evidence class:** official contract practice. It demonstrates separate allocation of technical-data delivery, licenses, and restrictions; federal terms are not a commercial default.

**Conflicting evidence:** Payment does not automatically transfer all IP, but provider retention of everything can leave the customer unable to use the deliverable.

**Confidence:** High on explicit written allocation; low on the allocation this business should choose.

**Business-specific validation:** Obtain IP counsel review of the MSA/SOW, employee and contractor assignments, subcontractor flow-downs, and target-customer expectations.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The legal principle is sound, but the sources show why ownership must be allocated in writing rather than proving any single commercial default is best.

### ENG-003 — What level of engineering analysis is included by default?

**State:** Exploring

**First-pass answer:** Include a documented requirements/assumptions review, manufacturability review, obvious interface and dimensional checks, and proportionate engineering sanity checks. Do not imply unstated FEA, CFD, fatigue, tolerance simulation, certification, or physical validation; each higher-order analysis should be a named deliverable with inputs, method, accuracy objective, acceptance criteria, exclusions, and reviewer.

**Evidence:**
- [NASA verification and validation plan outline](https://www.nasa.gov/reference/appendix-i-verification-and-validation-plan-outline/) — **Evidence class:** official engineering guidance. Verification methods and validation activities are planned against requirements rather than assumed.
- [NASA CFD verification and validation overview](https://www.grc.nasa.gov/WWW/winddocs/) — **Evidence class:** official technical guidance. Required accuracy depends on whether results are qualitative, incremental, or absolute.
- [Protolabs manufacturing analysis](https://www.protolabs.com/resources/blog/a-deep-dive-into-manufacturing-analysis-accessing-and-interpreting-feedback-to-optimize-your-cad-model/) — **Evidence class:** delivered production workflow. Automated DFM identifies process issues but is not complete engineering validation.

**Conflicting evidence:** Automated platforms include useful analysis by default, but those checks are process-focused and cannot establish application safety or fitness.

**Confidence:** High.

**Business-specific validation:** Pilot a default-review checklist on real jobs, measure effort and escaped issues, and test demand for separately priced analysis tiers.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly limits the default bundle to review and sanity-check work and rejects the unsupported inference that unstated simulation or validation is included.

### ENG-004 — When are hand calculations, FEA, CFD, tolerance analysis, fatigue analysis, or physical testing required?

**State:** Exploring

**First-pass answer:** Select methods through a requirement-verification matrix based on failure consequence, code, uncertainty, novelty, geometry, loading, variability, and required accuracy. Use transparent hand checks, domain-appropriate simulation, tolerance analysis where variation affects function, fatigue analysis for meaningful cyclic loads, and physical testing when required or when uncertain material, joint, or model behavior materially affects risk.

**Evidence:**
- [NASA verification and validation plan outline](https://www.nasa.gov/reference/appendix-i-verification-and-validation-plan-outline/) — **Evidence class:** official engineering guidance. Verification may use analysis, test, inspection, and demonstration tied to requirements.
- [NASA CFD verification and validation overview](https://www.grc.nasa.gov/WWW/winddocs/) — **Evidence class:** official technical guidance. Verification checks computation; validation compares the model with experiment.
- [NASA-STD-5001](https://standards.nasa.gov/standard/nasa/nasa-std-5001) — **Evidence class:** primary safety standard. It uses design, analysis, and testing for structural verification; its numeric factors are spaceflight-specific.

**Conflicting evidence:** Physical tests can be unrepresentative, while simulations can be accurate inside validated domains; neither method is inherently superior.

**Confidence:** High on the framework; medium on thresholds before product classes are selected.

**Business-specific validation:** Create a hazard/criticality matrix and have qualified discipline engineers define analysis and test triggers for initial products.

**Second-opinion review:** GPT-5.4 — Agree. The requirement-verification-matrix framing is robust and production-applicable, with the answer appropriately treating test and simulation as complementary rather than interchangeable.

### ENG-005 — Who is qualified to approve engineering calculations?

**State:** Exploring

**First-pass answer:** Approval belongs to a demonstrably competent engineer in the relevant discipline who accepts technical responsibility and reviews assumptions, inputs, method, results, and limitations. A licensed PE in responsible charge is required when law, permitting, governing code, public practice, or contract requires it; other industrial work may use documented internal technical authority and independent review.

**Evidence:**
- [NSPE responsible-charge guidance](https://www.nspe.org/career-growth/ethics/board-ethical-review-cases/responsible-charge-sealing-drawings) — **Evidence class:** professional ethics guidance. Responsible charge requires direction, control, competence, and detailed review.
- [NCEES Model Rules](https://ncees.org/wp-content/uploads/2024/01/Model_Rules_January-2024_web.pdf) — **Evidence class:** official model licensure rules. They provide a framework for responsible charge and sealing; states adopt their own rules.
- [NASA technical assessment](https://www.nasa.gov/reference/6-7-technical-assessment/) — **Evidence class:** official operating practice. It calls for defined review teams, success criteria, findings, and action resolution.

**Conflicting evidence:** Not every private industrial calculation requires a PE seal, and universal sealing can add cost without providing the right specialty.

**Confidence:** High.

**Business-specific validation:** Counsel must map jurisdictional practice laws, while the company defines discipline-specific authority, competence evidence, and independent-review levels.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Competence plus accountable review is the right rule, but PE-seal requirements remain jurisdiction- and product-specific and cannot be generalized from model rules alone.

### ENG-006 — How are safety factors selected and communicated?

**State:** Exploring

**First-pass answer:** Do not use one universal factor. Start with the governing code or standard, then consider failure mode, load and material uncertainty, manufacturing variability, environment, inspection, degradation, analysis credibility, test evidence, consequence, and service life. Communicate load basis, yield/ultimate/fatigue/buckling factors, allowables, margins, assumptions, exclusions, and approver.

**Evidence:**
- [NASA-STD-5001](https://standards.nasa.gov/standard/nasa/nasa-std-5001) — **Evidence class:** primary safety standard. Its factors are minimums unless a documented risk assessment authorizes otherwise; values must not be copied outside scope.
- [NASA technical risk management](https://www.nasa.gov/reference/6-4-technical-risk-management/) — **Evidence class:** official guidance. Risk includes scenario, likelihood, consequence, and uncertainty.
- [NASA CFD verification and validation overview](https://www.grc.nasa.gov/WWW/winddocs/) — **Evidence class:** official technical guidance. Credibility depends on intended use and quantified error and uncertainty.

**Conflicting evidence:** Some codes prescribe fixed minima, while reliability-based standards use probabilistic targets; each can be correct in scope.

**Confidence:** High that factors must be standard- and risk-based; medium until target products are known.

**Business-specific validation:** Identify governing standards by product class and have qualified engineers create a reviewed safety-factor and margin policy.

**Second-opinion review:** GPT-5.4 — Agree. The answer is appropriately cautious about fixed universal safety factors and correctly ties margins to governing standards, uncertainty, and failure consequence.

### ENG-007 — How are unknown loads or environments converted into bounded design cases?

**State:** Exploring

**First-pass answer:** Build a controlled load-and-environment matrix covering intended use, interfaces, transport, installation, duty cycle, foreseeable misuse, off-normal events, maintenance, and end of life. Record ranges, combinations, duration/cycles, spatial application, uncertainty, evidence, and consequence; analyze envelopes and sensitivities, then close critical assumptions by measurement, instrumentation, customer confirmation, or test.

**Evidence:**
- [NASA technical risk management](https://www.nasa.gov/reference/6-4-technical-risk-management/) — **Evidence class:** official guidance. It constructs credible scenarios and evaluates likelihood, consequence, and uncertainty.
- [NASA-STD-5001](https://standards.nasa.gov/standard/nasa/nasa-std-5001) — **Evidence class:** primary structural standard. It illustrates definition and verification of service cases; its scope is spaceflight.
- [NASA verification and validation plan outline](https://www.nasa.gov/reference/appendix-i-verification-and-validation-plan-outline/) — **Evidence class:** official guidance. Requirements and assumptions are tied to verification evidence.

**Conflicting evidence:** A simple worst case can be overconservative yet miss a critical combination; sparse probabilistic data can create false precision.

**Confidence:** High.

**Business-specific validation:** Run structured load interviews on representative work and instrument or physically test the highest-uncertainty, highest-consequence cases.

**Second-opinion review:** GPT-5.4 — Agree. Converting unknown environments into a controlled load matrix is sound engineering practice and better than either naive worst-case assumptions or false probabilistic precision.

### ENG-008 — How do we manage revisions, alternatives, and design branches?

**State:** Exploring

**First-pass answer:** Use PDM/configuration management with immutable revision IDs and explicit working, review, released, obsolete, and as-built states. Alternatives need separate identifiers or branches; every release records parent revision, change request, rationale, affected requirements/BOM/artifacts, approvers, effectivity, and commercial impact, and must not silently replace a released baseline.

**Evidence:**
- [NASA configuration management](https://www.nasa.gov/reference/6-5-configuration-management/) — **Evidence class:** official engineering practice. It defines baselines, configuration identification, change control, status accounting, and verification.
- [NASA technical data management](https://www.nasa.gov/reference/6-6-technical-data-management/) — **Evidence class:** official engineering practice. Controlled changes require defined authority, metadata, access, and storage.
- [Protolabs CAD/PDM guidance](https://www.protolabs.com/resources/design-tips/selecting-the-best-3d-cad-software-for-digital-manufacturing/) — **Evidence class:** practitioner production guidance. It notes that PDM revision control prevents overwrites and tracks dependencies.

**Conflicting evidence:** Git-style branching fits text better than binary, dependency-heavy CAD; generic source control alone may not preserve assemblies or release authority.

**Confidence:** High.

**Business-specific validation:** Trial the chosen PDM with a real assembly, concurrent edits, alternatives, an engineering change, and an as-built record.

**Second-opinion review:** GPT-5.4 — Agree. Immutable revision IDs, explicit lifecycle states, and branch handling are necessary once binary CAD, BOM, and release authority have to stay synchronized.

### ENG-009 — What constitutes customer design approval?

**State:** Exploring

**First-pass answer:** Require an affirmative, authenticated action by an authorized customer representative on a uniquely identified revision package. The record states the authority package, assumptions and exclusions, open conditions, acceptance criteria, and next activity authorized. Approval permits progression but does not waive provider professional, quality, or statutory responsibilities.

**Evidence:**
- [FAR acceptance definition](https://www.acquisition.gov/far/46.501) — **Evidence class:** official contract practice. Acceptance acknowledges conformity to identified requirements and is normally evidenced by an executed record.
- [NASA technical assessment](https://www.nasa.gov/reference/6-7-technical-assessment/) — **Evidence class:** official engineering practice. Reviews have entry/success criteria and capture decisions, findings, recommendations, and actions.
- [Protolabs manufacturing-analysis approval workflow](https://www.protolabs.com/resources/blog/a-deep-dive-into-manufacturing-analysis-accessing-and-interpreting-feedback-to-optimize-your-cad-model/) — **Evidence class:** delivered production workflow. Customers review, comment, receive revisions, and approve before ordering.

**Conflicting evidence:** Contracts can define silence as deemed acceptance, but that is risky for ambiguous or safety-significant designs.

**Confidence:** High.

**Business-specific validation:** Define approver roles, signature evidence, review period, deemed-approval policy, and the contractual meaning and limits of approval.

**Second-opinion review:** GPT-5.4 — Agree. The answer sets an appropriately strong approval record and correctly preserves provider quality and statutory duties after customer sign-off.

### ENG-010 — Can approval be conditional, and how are the conditions tracked?

**State:** Exploring

**First-pass answer:** Yes, when the contract and governing rules allow it. Each condition needs an ID, owner, due date, severity, affected configuration, allowed work boundary, closure evidence, and reapproval requirement. Unresolved safety, compliance, interface, or manufacturability conditions must block procurement or fabrication rather than remain as informal comments.

**Evidence:**
- [NASA technical assessment](https://www.nasa.gov/reference/6-7-technical-assessment/) — **Evidence class:** official engineering practice. Review outcomes include findings, action items, responsible resolution, and captured decisions.
- [NASA configuration management](https://www.nasa.gov/reference/6-5-configuration-management/) — **Evidence class:** official engineering practice. Baselines and changes require defined approvals and controlled status.
- [FAR acceptance definition](https://www.acquisition.gov/far/46.501) — **Evidence class:** official contract practice. Acceptance is subject to stated terms and conditions; commercial treatment must be drafted separately.

**Conflicting evidence:** Some code, permit, or safety gates require complete closure and cannot be conditionally released.

**Confidence:** Medium-high.

**Business-specific validation:** Test a condition register with engineering, production, quality, and customers and obtain legal review of partial-release risk.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Conditional approval is workable for some commercial flows, but the file correctly notes that safety, compliance, and manufacturability blockers cannot simply remain open comments.

### ENG-011 — What changes after approval require reapproval and a new price or schedule?

**State:** Exploring

**First-pass answer:** Reapprove any change affecting requirements, authority files, form/fit/function, interfaces, material, treatment, tolerance/GD&T, finish, quantity, inspection, regulatory or safety assumptions, manufacturing/test method, deliverables, or released revision. Reassess price and schedule when scope, effort, risk, procurement, scrap exposure, qualification, or lead time changes; use an expedited path only for verified zero-impact corrections.

**Evidence:**
- [NASA configuration management](https://www.nasa.gov/reference/6-5-configuration-management/) — **Evidence class:** official engineering practice. Baseline changes are proposed, assessed, authorized, recorded, and verified.
- [FAR change-order definitization](https://www.acquisition.gov/far/43.204) — **Evidence class:** official contract practice. Change orders require documentation and price or delivery adjustment; commercial contracts may differ.
- [Xometry model/drawing discrepancy practice](https://community.xometry.com/kb/articles/690-model-drawing-and-quote-discrepancies) — **Evidence class:** delivered production workflow. Cost-, lead-time-, or manufacturability-affecting conflicts trigger a hold and clarification.

**Conflicting evidence:** Spelling, metadata, and other zero-impact corrections should not reopen commercial terms.

**Confidence:** High.

**Business-specific validation:** Build and test a change-classification matrix with engineering, estimating, purchasing, quality, and production.

**Second-opinion review:** GPT-5.4 — Agree. The reapproval triggers are production-relevant and the zero-impact exception is narrowly enough framed to avoid accidental commercial or technical scope drift.

### ENG-012 — How do we prevent a visual rendering from being mistaken for a fully engineered design?

**State:** Exploring

**First-pass answer:** Make rendering a distinct artifact class, permanently marked `CONCEPT — NOT FOR MANUFACTURE / NOT ENGINEERED`, with maturity, revision, unresolved assumptions, and a link to the only releasable package. Suppress approval/release symbols and technically prevent renderings from entering procurement or manufacturing workflows; a disclaimer alone is insufficient.

**Evidence:**
- [NASA configuration management](https://www.nasa.gov/reference/6-5-configuration-management/) — **Evidence class:** official engineering practice. It distinguishes evolving design information from controlled product baselines.
- [NIST model-based enterprise TDP requirements](https://www.nist.gov/publications/model-based-enterprise-technical-data-package-requirements) — **Evidence class:** official government/industry research. It identifies the need for defined technical-data packages rather than ambiguous artifacts.
- [NSPE responsible-charge guidance](https://www.nspe.org/career-growth/ethics/board-ethical-review-cases/responsible-charge-sealing-drawings) — **Evidence class:** professional guidance. Engineering responsibility requires actual technical involvement and review.

**Conflicting evidence:** Watermarks can be cropped, and polished imagery still implies completion; access controls, status metadata, and release rules matter more than wording alone.

**Confidence:** High.

**Business-specific validation:** Usability-test concept and released artifacts with prospects and shop staff and verify they can identify the manufacturing authority.

**Second-opinion review:** GPT-5.4 — Agree. The review correctly rejects disclaimer-only handling and requires both status marking and workflow controls so polished concept art cannot leak into manufacturing authority.

## CAD Intake and Digital Models

### CAD-001 — Which native and neutral CAD formats will be accepted?

**State:** Exploring

**First-pass answer:** Begin with a versioned whitelist tied to demonstrated capability: prefer STEP AP242 for solid/assembly exchange; accept AP214/AP203 as fallbacks; accept native or precise formats only where licensed and tested; use DXF under process rules; consider 3MF over STL for additive only where the downstream slicer and archival toolchain preserve its richer metadata; and treat PDFs/drawings as supplemental unless contractually authoritative. “Accepted” must specify version, process, preserved semantics, and authority.

**Evidence:**
- [Xometry accepted file types](https://community.xometry.com/kb/articles/643-what-file-types-does-xometry-accept) — **Evidence class:** delivered production intake. It accepts multiple native, neutral, mesh, and 2D formats with process limitations.
- [NIST STEP Analyzer](https://www.nist.gov/services-resources/software/step-file-analyzer-and-viewer) — **Evidence class:** official implementation guidance. STEP supports parts, assemblies, and PMI across CAD/CAM/CAE/CMM.
- [Open CASCADE data exchange](https://dev.opencascade.org/doc/overview/html/occt_user_guides__de_wrapper.html) — **Evidence class:** official open-source implementation documentation. It identifies supported neutral CAD and mesh formats.

**Conflicting evidence:** A file can be uploadable or viewable yet not editable, semantically complete, or safe as manufacturing authority.

**Confidence:** Medium-high.

**Business-specific validation:** Run a golden-file corpus through the intended CAD/CAM/inspection stack and publish a matrix by format, version, assembly, PMI, process, and known losses.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. I narrowed the additive-format language because a blanket 3MF preference was stronger than the cited evidence; use 3MF only where the downstream toolchain preserves its richer metadata and keep compatibility fallbacks.

### CAD-002 — When must customers export STEP rather than submit a native CAD format?

**State:** Exploring

**First-pass answer:** Require STEP when no validated licensed native translator exists, vendor-neutral automation or supplier portability matters, or long-term access is needed and feature history is not. Require AP242 when semantic PMI must travel; request both native and STEP when editable design intent, configurations, equations, or feature history matter. Prefer customer export from the authoritative native system.

**Evidence:**
- [NIST STEP Analyzer](https://www.nist.gov/services-resources/software/step-file-analyzer-and-viewer) — **Evidence class:** official implementation guidance. AP242/AP214/AP203 can exchange geometry, assemblies, PMI, and validation properties.
- [Xometry MBD/PMI intake](https://community.xometry.com/kb/articles/851-can-xometry-accept-mbd-or-pmi-files) — **Evidence class:** delivered production workflow. It accepts STEP AP242 and selected native files with a supporting 3D PDF or drawing.
- [NIST PMI validation project](https://www.nist.gov/ctl/smart-connected-systems-division/smart-connected-manufacturing-systems-group/mbe-pmi-validation) — **Evidence class:** official conformance research. It demonstrates the need to verify cross-format PMI fidelity.

**Conflicting evidence:** Native files preserve constraints, configurations, and feature history that STEP can flatten or lose.

**Confidence:** High.

**Business-specific validation:** Compare native and AP242 round trips for representative parts and assemblies, including PMI, mass properties, configurations, and CAM recognition.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly prefers STEP/AP242 where portability and PMI fidelity matter while still recognizing that native files remain necessary for editable design intent and history.

### CAD-003 — How are units detected and confirmed?

**State:** Exploring

**First-pass answer:** Parse declared units where available, preserve the original declaration, normalize only in a derived copy, and run bounding-box and feature-size plausibility checks. Require explicit inch/mm selection for unit-poor mesh formats, then display interpreted units and key dimensions for customer confirmation before quoting or release.

**Evidence:**
- [Open CASCADE STEP translator](https://dev.opencascade.org/doc/overview/html/occt_user_guides__step.html) — **Evidence class:** official implementation documentation. STEP representations carry length and angle units used during translation.
- [Open CASCADE IGES translator](https://dev.opencascade.org/doc/overview/html/occt_user_guides__iges.html) — **Evidence class:** official implementation documentation. IGES header units are read and converted.
- [Library of Congress STL description](https://www.loc.gov/preservation/digital/formats/fdd/fdd000504.shtml) — **Evidence class:** official format assessment. STL has major metadata limitations, including no dependable physical-unit convention.

**Conflicting evidence:** A file may declare the wrong unit or have geometry modeled at the wrong scale; metadata alone is insufficient.

**Confidence:** High.

**Business-specific validation:** Test every accepted format with correct, missing, and deliberately wrong units and establish customer-confirmation and plausibility thresholds.

**Second-opinion review:** GPT-5.4 — Agree. The combination of parsed metadata, plausibility checks, and explicit customer confirmation is production-applicable and correctly addresses unit-poor mesh formats.

### CAD-004 — How are corrupt, open, non-manifold, self-intersecting, or otherwise invalid models handled?

**State:** Exploring

**First-pass answer:** Quarantine the original, validate syntax, bodies, open edges, manifoldness, self-intersections, overlaps, tiny features, topology, and process manufacturability, and return a defect report. Never silently alter authority data; repairs create a derived revision with tool/version/log and geometry comparisons, and receive approval when intent may change.

**Evidence:**
- [NIST STEP Analyzer](https://www.nist.gov/services-resources/software/step-file-analyzer-and-viewer) — **Evidence class:** official implementation. It checks STEP syntax, references, attributes, PMI, and validation properties.
- [Open CASCADE STEP translator](https://dev.opencascade.org/doc/overview/html/occt_user_guides__step.html) — **Evidence class:** official open-source documentation. It supports consistency checks and shape healing, whose results are tolerance-dependent.
- [Protolabs STL preparation](https://www.protolabs.com/resources/design-tips/how-to-design-stl-files-for-3d-printing-in-your-cad-program/) — **Evidence class:** delivered production guidance. Overlapping bodies can cause pricing, build, and software failures.

**Conflicting evidence:** Automated healing is useful for minor defects, but a successful repair can still change geometry or hide a design error.

**Confidence:** High.

**Business-specific validation:** Test intentionally broken models and define automatic-repair tolerances, manual-review triggers, customer-RFI rules, and prohibited repairs.

**Second-opinion review:** GPT-5.4 — Agree. Quarantining originals and treating repairs as derived revisions is the right control because successful healing does not guarantee preserved design intent.

### CAD-005 — How do we compare a drawing against the supplied 3D model when they disagree?

**State:** Exploring

**First-pass answer:** Apply the contractual authority and precedence rule. If it does not unambiguously resolve the feature, place the job on hold and issue an RFI naming the conflicting values; do not blend files or choose the easier requirement. Record the resolution in a new approved revision package.

**Evidence:**
- [Xometry model/drawing discrepancy practice](https://community.xometry.com/kb/articles/690-model-drawing-and-quote-discrepancies) — **Evidence class:** delivered production workflow. It uses a source-of-truth matrix and seeks clarification when discrepancies affect cost, lead time, or manufacturability.
- [Xometry MBD/PMI intake](https://community.xometry.com/kb/articles/851-can-xometry-accept-mbd-or-pmi-files) — **Evidence class:** delivered production workflow. MBD discrepancies place an order on hold.
- [NIST model-based enterprise TDP requirements](https://www.nist.gov/publications/model-based-enterprise-technical-data-package-requirements) — **Evidence class:** official research. It identifies authority and completeness as key model-based TDP issues.

**Conflicting evidence:** High-volume platforms may use a default hierarchy for efficiency; that works only when customers knowingly accept it.

**Confidence:** High.

**Business-specific validation:** Draft and exercise a feature-level precedence matrix for geometry, dimensions, GD&T, threads, material, finish, notes, quote configuration, and PO terms.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly centers contractual precedence and RFI discipline instead of allowing silent file blending when drawing and model disagree.

### CAD-006 — Which file is the contractual manufacturing authority: drawing, model, model-based definition, or approved revision package?

**State:** Exploring

**First-pass answer:** Make the approved revision package, with explicit precedence by requirement category, the authority—not a presumed file type. Drawing-based work may use drawings for dimensions/notes and a model for nominal geometry; MBD may use native or AP242 geometry plus semantic PMI and linked specifications. Name exact filenames, revisions, and conflict handling in the contract.

**Evidence:**
- [MIL-STD-31000 official record](https://quicksearch.dla.mil/qsDocDetails.aspx?ident_number=276980) — **Evidence class:** official DoD standard record. It governs tailored drawing- and model-based technical-data packages.
- [NIST model-based enterprise TDP requirements](https://www.nist.gov/publications/model-based-enterprise-technical-data-package-requirements) — **Evidence class:** official government/industry research. It documents acquisition, production, sustainment, and authority issues.
- [Xometry model/drawing discrepancy practice](https://community.xometry.com/kb/articles/690-model-drawing-and-quote-discrepancies) — **Evidence class:** delivered production workflow. Authority can vary by requirement category and conflicts require clarification.

**Conflicting evidence:** “Drawing always wins” or “model always wins” is simpler but fails when critical information exists only in the other artifact.

**Confidence:** High.

**Business-specific validation:** Have engineering, quality, production, sales, and counsel approve and test a contractual data-authority matrix.

**Second-opinion review:** GPT-5.4 — Agree. Making the approved revision package authoritative by requirement category is better supported than any blanket “drawing wins” or “model wins” rule.

### CAD-007 — How are assemblies, configurations, suppressed features, reference geometry, and linked files packaged?

**State:** Exploring

**First-pass answer:** Require the authoring system’s pack-and-go/dependency collector for native work. Include components, subassemblies, drawings, references, tables, permitted library items, active configuration, and a manifest with hashes, CAD/version, authoritative configuration, suppressed/excluded items, units, origin, BOM, and unresolved links; add a frozen STEP AP242 assembly when practical.

**Evidence:**
- [Xometry accepted file types](https://community.xometry.com/kb/articles/643-what-file-types-does-xometry-accept) — **Evidence class:** delivered production intake. It warns that native assembly containers may lack embedded solid data and supports STEP assemblies.
- [Open CASCADE STEP translator](https://dev.opencascade.org/doc/overview/html/occt_user_guides__step.html) — **Evidence class:** official implementation documentation. STEP can preserve nested assemblies, shared components, names, colors, and validation properties to varying degrees.
- [NASA technical data management](https://www.nasa.gov/reference/6-6-technical-data-management/) — **Evidence class:** official engineering practice. Data packages need defined content, formats, metadata, and exchange rules.

**Conflicting evidence:** STEP reduces missing-link risk but usually loses configurations, suppression logic, constraints, equations, and feature history.

**Confidence:** High.

**Business-specific validation:** Run pack/unpack tests outside the author’s environment for each supported CAD system, including broken links, configurations, suppression, and renamed files.

**Second-opinion review:** GPT-5.4 — Agree. Pack-and-go plus manifesting and neutral-reference exports is a realistic way to control missing links and configuration loss in native assembly exchanges.

### CAD-008 — How do we preserve model provenance and revision history?

**State:** Exploring

**First-pass answer:** Preserve the original upload immutably and never overwrite it with conversions. Record hash, original name, customer/job, uploader/time, declared authority/revision/units, authoring application/version, export settings, parent-child derivation, translator/version/log, validation results, approvals, change reason, effectivity, and produced orders/parts; audit access and transformations.

**Evidence:**
- [NIST Digital Thread for Manufacturing](https://www.nist.gov/programs-projects/digital-thread-manufacturing) — **Evidence class:** official manufacturing research. It addresses authorization, authentication, stable identifiers, tampering, and links to originating CAD characteristics.
- [NIST Digital Manufacturing Certificate Toolkit](https://www.nist.gov/publications/digital-manufacturing-certificate-toolkit-adding-trust-and-traceability-product-data) — **Evidence class:** official technical research. It addresses trust and traceability for product data.
- [NASA configuration management](https://www.nasa.gov/reference/6-5-configuration-management/) — **Evidence class:** official engineering practice. It requires identifiers, baselines, change control, status accounting, and verification.

**Conflicting evidence:** Hashes prove byte continuity, not that the original geometry, metadata, or approval was correct.

**Confidence:** High.

**Business-specific validation:** Demonstrate reconstruction from customer upload through conversion, CAM/inspection use, approval, manufacture, and as-built record.

**Second-opinion review:** GPT-5.4 — Agree. Immutable originals plus detailed derivation metadata is the right traceability pattern for customer CAD, conversions, approvals, and downstream manufacturing use.

### CAD-009 — Which conversions can be performed reliably with open source software, and which require licensed vendor translators?

**State:** Exploring

**First-pass answer:** Open-source tooling is credible for validated exchange among STEP, IGES, BREP, STL, OBJ, glTF, and related open/neutral formats, but fidelity for assemblies, semantic PMI, exact geometry, and metadata must be measured. Current proprietary native formats such as CATIA, NX, Creo, SOLIDWORKS, and Inventor generally need licensed vendor or commercial SDKs for broad production support.

**Evidence:**
- [Open CASCADE data exchange](https://dev.opencascade.org/doc/overview/html/occt_user_guides__de_wrapper.html) — **Evidence class:** official open-source implementation documentation. It lists read/write support for major neutral and mesh formats.
- [HOOPS Exchange format support](https://www.techsoft3d.com/developers/products/hoops-exchange/) — **Evidence class:** commercial translator documentation. It lists native support for major proprietary CAD systems; claims require acceptance testing.
- [NIST PMI validation project](https://www.nist.gov/ctl/smart-connected-systems-division/smart-connected-manufacturing-systems-group/mbe-pmi-validation) — **Evidence class:** official conformance research. It validates derivatives against native CAD and documents translator issues.

**Conflicting evidence:** Some open tools can partially read proprietary variants and free viewers can open files, but opening is not production-fidelity conversion.

**Confidence:** High.

**Business-specific validation:** Build a benchmark corpus, score geometry/assembly/PMI fidelity by translator/version, and classify outputs as authority-capable or reference-only.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The open-source-versus-licensed boundary is directionally right, but production fidelity has to be proven on a benchmark corpus rather than inferred from claimed format support.

### CAD-010 — How is proprietary customer CAD isolated and protected?

**State:** Exploring

**First-pass answer:** Classify at intake and enforce tenant/project isolation, least privilege, role separation, strong authentication, encryption, malware sandboxing, controlled downloads, access/transformation logs, supplier disclosure controls, geographic/export restrictions, backup, deletion, and incident response. Prohibit training, demonstrations, benchmarking, or cross-customer use unless expressly authorized; use a separately validated path for CUI or export-controlled work.

**Evidence:**
- [NIST SP 800-171 Rev. 3](https://csrc.nist.gov/pubs/sp/800/171/r3/final) — **Evidence class:** official cybersecurity requirements. It covers access, audit, configuration, identity, media, communications, integrity, and supplier controls for applicable CUI.
- [NIST SP 800-53 Rev. 5](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) — **Evidence class:** official control catalog. It provides risk-tailorable security and privacy controls.
- [Xometry ITAR/EAR/CUI discrepancy workflow](https://community.xometry.com/kb/articles/853-drawing-discrepancies-in-itar-ear-and-cui) — **Evidence class:** delivered production practice. Ambiguous restrictions cause holds and ITAR work is restricted to eligible suppliers.

**Conflicting evidence:** Properly assessed cloud environments can protect CAD without an air gap, while encryption alone does not stop authorized-user misuse or improper supplier disclosure.

**Confidence:** High on the framework; medium on the required compliance boundary.

**Business-specific validation:** Conduct a data-flow threat model, review cloud/AI/supplier terms with cybersecurity and export counsel, and test access, deletion, incident, and supplier-release controls.

**Second-opinion review:** GPT-5.4 — Agree. The control set is strong and production-relevant, and the answer appropriately distinguishes ordinary proprietary CAD from more restrictive CUI/export-controlled paths.
