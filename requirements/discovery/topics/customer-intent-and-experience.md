# Customer Intent and Experience Questions

## Purpose

Questions about understanding customer needs, resolving uncertainty, communicating progress, and maintaining customer trust.

These are discovery questions, not approved requirements. Question IDs are permanent and canonical in this file.

## Customer Intent and Requirements Discovery

### CUS-001 — How does a customer initially explain the idea: text, voice, sketches, photographs, existing parts, CAD, video, measurements, or some combination?

**State:** Exploring

**First-pass answer:** Accept any combination, preserve each original artifact with provenance, and normalize it into structured requirements, assumptions, and open questions. Photographs, sketches, voice, video, or physical samples without controlled dimensions should enter human-assisted discovery or reverse engineering; automated quoting or manufacturing should wait for adequate CAD and drawing authority.

**Evidence:**
- [NASA stakeholder expectations definition](https://www.nasa.gov/reference/4-1-stakeholder-expectations-definition/) — **Evidence class:** official guidance. NASA recognizes interviews, email, graphics, video, models, simulations, and scenarios as valid inputs; its aerospace process is more formal than a small commercial workflow.
- [Protolabs manufacturing analysis](https://www.protolabs.com/resources/design-tips/navigating-manufacturing-analysis/) — **Evidence class:** delivered digital-manufacturing workflow. A 3D CAD upload drives DFM, material, finish, quantity, price, and engineer-assisted clarification; it does not show that informal artifacts are design-ready.
- [Fictiv workflow](https://www.fictiv.com/why-choose-fictiv/how-it-works) — **Evidence class:** delivered digital-manufacturing workflow. The service retains design, DFM, configuration, inspection, and order records; public error and completion rates are not disclosed.

**Conflicting evidence:** CAD-first intake is faster and less ambiguous for mature buyers; accepting every medium increases interpretation cost and can create false expectations.

**Confidence:** Medium; multimodal discovery is well supported, but the viable mix depends on customer sophistication and reverse-engineering capability.

**Business-specific validation:** Observe 15–30 representative prospects explaining real jobs and measure artifact mix, missing information, clarification time, and human-assistance rate.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The answer fits sound requirements-intake practice, but the sources are mostly aerospace and platform analogues rather than direct proof for every intake medium; keep automated quoting gated on authority-grade geometry.

### CUS-002 — What questions must always be asked before design starts?

**State:** Exploring

**First-pass answer:** Always establish intended function, users and decision authority, normal and foreseeable abnormal use, loads and environment, interfaces, life and duty cycle, failure consequences, applicable rules, quantity and process constraints, units/tolerances/finish, cost and schedule priorities, acceptance method, unresolved assumptions, and change authority. Present these progressively and scale depth by risk.

**Evidence:**
- [NASA stakeholder expectations definition](https://www.nasa.gov/reference/4-1-stakeholder-expectations-definition/) — **Evidence class:** official guidance. It covers needs, goals, users, constraints, interfaces, lifecycle support, off-nominal scenarios, and stakeholder authority.
- [NASA technical requirements definition](https://www.nasa.gov/reference/4-2-technical-requirements-definition/) — **Evidence class:** official guidance. It requires functional, performance, interface, environmental, safety, human-factor, reliability, and verification requirements.
- [NASA good-requirement checklist](https://www.nasa.gov/reference/appendix-c-how-to-write-a-good-requirement/) — **Evidence class:** official checklist. It tests completeness, assumptions, tolerances, interfaces, feasibility, consistency, and verifiability.

**Conflicting evidence:** One exhaustive form can cause abandonment and ask irrelevant questions; only the safety and feasibility screen should be universal at first contact.

**Confidence:** High on the categories; medium on sequencing and wording.

**Business-specific validation:** Compare the checklist with prior quoting/design defects, then usability-test progressive versions by customer type.

**Second-opinion review:** GPT-5.4 — Agree. The question set matches mainstream systems-engineering discovery, and the file already notes the key contrary evidence that exhaustive forms can hurt conversion if not risk-tiered.

### CUS-003 — How do we distinguish what the customer explicitly said from assumptions inferred by the system or an engineer?

**State:** Exploring

**First-pass answer:** Store separate typed records for the verbatim customer statement, normalized requirement, derived requirement, assumption, constraint, and approved decision. Preserve source, author, time, rationale, confidence, affected items, and approval state; never overwrite the original statement or present an unapproved inference as customer fact.

**Evidence:**
- [NASA technical requirements definition](https://www.nasa.gov/reference/4-2-technical-requirements-definition/) — **Evidence class:** official guidance. It calls for requirement metadata, rationale, assumptions, constraints, relationships, and stakeholder validation.
- [NASA requirements management](https://www.nasa.gov/reference/6-2-requirements-management/) — **Evidence class:** official guidance. It requires source ownership, bidirectional traceability, controlled baselines, and concurrence for derived requirements.

**Conflicting evidence:** Engineers must infer routine low-impact details to progress; requiring customer approval for every interpretation would stall work.

**Confidence:** High.

**Business-specific validation:** Give identical briefs to multiple engineers and the system, then test whether reviewers can distinguish source statements, assumptions, and derived requirements.

**Second-opinion review:** GPT-5.4 — Agree. Separating verbatim customer input from derived assumptions is well supported by requirements-management practice and is essential for traceability, approvals, and dispute handling.

### CUS-004 — How do we capture function, loads, temperatures, pressures, chemicals, vibration, cycles, wear, environment, expected life, misuse, and failure consequences?

**State:** Exploring

**First-pass answer:** Use a structured operating-envelope and hazard record rather than one free-text field. For each relevant factor capture units, minimum/nominal/maximum and transient values, duration and frequency, combinations, uncertainty and source, maintenance assumptions, foreseeable misuse, failure mode, consequence, and required validation; require an explicit unknown or not-applicable disposition.

**Evidence:**
- [NASA technical requirements definition](https://www.nasa.gov/reference/4-2-technical-requirements-definition/) — **Evidence class:** official guidance. Environmental, safety, interface, performance, lifecycle, and reliability requirements form part of a complete set.
- [NASA technical risk management](https://www.nasa.gov/reference/6-4-technical-risk-management/) — **Evidence class:** official guidance. It characterizes risk through scenario, likelihood, consequence, and uncertainty.
- [NASA-STD-5017B](https://standards.nasa.gov/sites/default/files/standards/NASA/B//2022-12-06-NASA-STD-5017B-Approved.pdf) — **Evidence class:** primary engineering standard. It illustrates rigorous capture of service life and environmental inputs, but space mechanisms exceed most commercial needs.

**Conflicting evidence:** Requiring every field for a cosmetic prototype adds friction and false precision; mandatory depth should follow consequence and product class.

**Confidence:** High on the model; medium on field-level mandatory rules.

**Business-specific validation:** Build risk-based templates for the first product families and have qualified engineers challenge them with representative jobs and failure scenarios.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. A structured operating-envelope record is the right default, but field depth should scale with product risk so low-consequence prototypes are not forced into false precision.

### CUS-005 — What happens when a customer does not know the operating conditions?

**State:** Exploring

**First-pass answer:** Record the unknown with an owner, due date, consequence, and resolution method. Resolve it through observation, measurement, logging, supplier information, testing, or a bounded engineering assumption; allow reversible low-consequence concept work under conspicuous assumptions, but block production release when the unknown can materially affect safety, warranty, compliance, or irreversible cost.

**Evidence:**
- [NASA good-requirement checklist](https://www.nasa.gov/reference/appendix-c-how-to-write-a-good-requirement/) — **Evidence class:** official guidance. It recommends tracked unresolved items and confirmation of assumptions before baselining.
- [NASA decision analysis](https://www.nasa.gov/reference/6-8-decision-analysis/) — **Evidence class:** official guidance. It recommends reducing uncertainty when additional information could change the preferred alternative.
- [NASA technical risk management](https://www.nasa.gov/reference/6-4-technical-risk-management/) — **Evidence class:** official guidance. It treats uncertainty as part of likelihood and consequence and supports mitigation triggers.

**Conflicting evidence:** Stopping until every condition is measured is uneconomic; prototypes and reversible studies are often the best way to learn.

**Confidence:** High on the conditional policy.

**Business-specific validation:** Define consequence tiers and explicit may-proceed/must-stop rules, then test them against historical and supervised pilot jobs.

**Second-opinion review:** GPT-5.4 — Agree. The answer correctly treats unknown operating conditions as managed risk, allowing bounded concept work while blocking production commitments when safety, compliance, or irreversible cost are exposed.

### CUS-006 — Which default design-life options can be offered, and what warnings or approvals accompany them?

**State:** Exploring

**First-pass answer:** Offer labeled tiers rather than an unlabeled lifespan: concept/proof only; limited-duty pilot with stated hours or cycles and inspection; production service based on an approved duty cycle, environment, reliability, and maintenance assumptions; and no default for safety-critical or regulated use. State exclusions, uncertainty, inspection/replacement assumptions, failure consequences, and required approval authority.

**Evidence:**
- [NASA program/project lifecycle](https://www.nasa.gov/reference/3-0-nasa-program-project-life-cycle/) — **Evidence class:** official guidance. It separates concept, design, fabrication, test, operation, and sustainment through progressively stronger gates.
- [NASA good-requirement checklist](https://www.nasa.gov/reference/appendix-c-how-to-write-a-good-requirement/) — **Evidence class:** official guidance. It requires measurable life, loads, tolerances, fault response, and verification.
- [NASA technical risk management](https://www.nasa.gov/reference/6-4-technical-risk-management/) — **Evidence class:** official guidance. It supports consequence-based rigor and explicit residual-risk acceptance.

**Conflicting evidence:** Standard catalog products often use default ratings successfully, and excessive warnings can obscure ordinary risks.

**Confidence:** Medium; the no-silent-default principle is strong, but tier boundaries are product-specific.

**Business-specific validation:** A qualified engineer should define tiers by product family using duty-cycle tests, field/warranty evidence, applicable codes, and insurer or counsel review.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Labeled life tiers are safer than an implied default lifespan, but any production-service claim still needs product-class, maintenance, and code-specific engineering evidence.

### CUS-007 — How are units, tolerances, finish expectations, appearance, interfaces, and fit clarified?

**State:** Exploring

**First-pass answer:** Display units beside every value and prohibit silent inference or conversion. Require explicit critical dimensions, datums/GD&T, fits, surface texture, coatings, color/cosmetic zones, mating interfaces, and inspection method; any shop default tolerance or finish must be visible, versioned, priced, and affirmatively accepted.

**Evidence:**
- [ASME Y14.5](https://www.asme.org/codes-standards/find-codes-standards/y14-5-dimensioning-tolerancing) — **Evidence class:** primary standards-body guidance. It establishes a language for form, fit, function, and interchangeability; the full standard is paid.
- [NASA good-requirement checklist](https://www.nasa.gov/reference/appendix-c-how-to-write-a-good-requirement/) — **Evidence class:** official guidance. It requires clear units, tolerances, measurable performance, and interfaces.
- [Protolabs manufacturing analysis](https://www.protolabs.com/resources/design-tips/navigating-manufacturing-analysis/) — **Evidence class:** delivered digital-manufacturing workflow. Customers can see DFM and price effects while changing material, finish, quantity, and delivery.

**Conflicting evidence:** Full GD&T is unnecessary for many prototypes; disclosed general tolerances can be sufficient for noncritical features.

**Confidence:** High.

**Business-specific validation:** Compare target machine and inspection capability with supplier defaults and run sample quotes focused on unit, cosmetic, and fit misunderstandings.

**Second-opinion review:** GPT-5.4 — Agree. The answer is appropriately strict on explicit units and authority for critical tolerances while leaving room for disclosed general tolerances on noncritical features.

### CUS-008 — How do we identify requirements that conflict with one another?

**State:** Exploring

**First-pass answer:** Combine automated consistency checks with an explicit trade review. Flag incompatible units or ranges, impossible geometry, mutually exclusive materials/processes, tolerance-stack failures, and cost/schedule conflicts; then show the affected requirements, sources, mandatory status, and alternatives to the authorized decision-maker without silently relaxing anything.

**Evidence:**
- [NASA good-requirement checklist](https://www.nasa.gov/reference/appendix-c-how-to-write-a-good-requirement/) — **Evidence class:** official guidance. It checks consistency, contradiction, feasibility, interfaces, and defensible tolerances.
- [NASA decision analysis](https://www.nasa.gov/reference/6-8-decision-analysis/) — **Evidence class:** official guidance. It separates mandatory and enhancing criteria and compares alternatives across technical, cost, schedule, risk, and safety factors.
- [NASA requirements management](https://www.nasa.gov/reference/6-2-requirements-management/) — **Evidence class:** official guidance. It requires baseline consistency and cross-domain impact assessment.

**Conflicting evidence:** Apparent conflicts can disappear through creative design or process changes; a rigid rules engine may reject feasible innovation.

**Confidence:** High on the hybrid approach.

**Business-specific validation:** Seed representative briefs with known conflicts and measure automated detection, false positives, engineer detection, resolution time, and customer comprehension.

**Second-opinion review:** GPT-5.4 — Agree. Automated checks plus explicit human trade review is the right combination; the answer also handles the contrary case that some apparent conflicts are design-solvable rather than true blockers.

### CUS-009 — What evidence is sufficient to say the requirements are complete enough to begin design?

**State:** Exploring

**First-pass answer:** Concept design can begin when the need, principal use cases, stakeholders, hard constraints, interfaces, acceptance intent, major risks, assumptions, and decision authority are sufficient to compare feasible concepts. Detailed or released design needs traceable, feasible, consistent, verifiable requirements; bounded critical unknowns; off-nominal consideration; and change control. Completeness is gate-specific, not omniscience.

**Evidence:**
- [NASA technical requirements definition](https://www.nasa.gov/reference/4-2-technical-requirements-definition/) — **Evidence class:** official guidance. Its validation questions determine readiness for decomposition and design.
- [NASA program/project lifecycle](https://www.nasa.gov/reference/3-0-nasa-program-project-life-cycle/) — **Evidence class:** observed governance practice. It uses incremental go/no-go gates and permits qualified progression with tracked open items.
- [NASA good-requirement checklist](https://www.nasa.gov/reference/appendix-c-how-to-write-a-good-requirement/) — **Evidence class:** official checklist. It covers completeness, assumptions, consistency, feasibility, traceability, and testability.

**Conflicting evidence:** Waiting for perfect requirements causes analysis paralysis; iterative prototypes often reveal needs that interviews cannot.

**Confidence:** High.

**Business-specific validation:** Define separate entrance criteria for concept, detailed design, and production release, then retrospectively test them on prior projects.

**Second-opinion review:** GPT-5.4 — Agree. Gate-specific completeness is the correct frame, and the answer avoids the unsupported claim that requirements must be perfect before any concept work can start.

### CUS-010 — Which questions require a qualified engineer rather than an automated recommendation?

**State:** Exploring

**First-pass answer:** Require qualified human review for life safety, public welfare, code compliance, pressure containment, lifting/structural integrity, fire/electrical hazards, guarding, fatigue/service life, hazardous chemicals, medical use, novel materials/processes, high-consequence failure, unresolved conflicts, and approvals requiring licensure. Automation may screen and calculate but should not be the accountable authority outside a validated low-risk envelope.

**Evidence:**
- [NCEES licensure](https://ncees.org/licensure/) — **Evidence class:** professional licensing authority. It explains state-based licensure and its public-protection purpose; protected practice varies by jurisdiction.
- [OSHA machine guarding](https://www.osha.gov/machine-guarding) — **Evidence class:** regulatory guidance. Machine hazards invoke enforceable duties rather than only customer preferences.
- [NASA technical risk management](https://www.nasa.gov/reference/6-4-technical-risk-management/) — **Evidence class:** official guidance. It scales review rigor to safety, technical, cost, and schedule consequences.

**Conflicting evidence:** Many industrial designs do not legally require a PE, and universal PE review would add cost without ensuring the right specialty.

**Confidence:** High on consequence-based escalation; medium on legal boundaries.

**Business-specific validation:** Obtain jurisdiction-specific legal/licensing guidance and define a product-risk escalation matrix with the responsible engineer, insurer, and safety lead.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The safety and licensure boundaries are directionally right, but the exact human-review threshold varies by jurisdiction, product class, and contract.

### CUS-011 — How does the customer revise an idea without losing the history of earlier decisions?

**State:** Exploring

**First-pass answer:** Use append-only history and named immutable baselines. Let the customer revise a current draft or branch, compare prior versions, state the change reason, see cost/schedule/safety impact, obtain required approvals, and create a new baseline without overwriting comments, rejected alternatives, assumptions, quote configuration, or the manufactured revision.

**Evidence:**
- [Onshape versions and history](https://cad.onshape.com/help/Content/Document/versions_and_history.htm) — **Evidence class:** delivered CAD workflow. It provides immutable named versions, workspaces, branches, and merge history.
- [NASA requirements management](https://www.nasa.gov/reference/6-2-requirements-management/) — **Evidence class:** official guidance. It requires baselines, change requests, impact assessment, approval, and bidirectional traceability.
- [NASA configuration management](https://www.nasa.gov/reference/6-5-configuration-management/) — **Evidence class:** official guidance. It requires unique identifiers, historical status, version comparison, approved changes, and a released source of truth.

**Conflicting evidence:** Full configuration control is burdensome during disposable brainstorming; lightweight autosave may suffice before a quote or baseline exists.

**Confidence:** High.

**Business-specific validation:** Prototype branching and revision flows and test whether customers and staff can identify the released manufacturing revision and reconstruct why decisions changed.

**Second-opinion review:** GPT-5.4 — Agree. Append-only history plus immutable baselines is consistent with configuration-control practice and is necessary once quotes, approvals, or manufactured revisions exist.

### CUS-012 — How are customer priorities ranked when cost, strength, weight, finish, lead time, repairability, and lifespan conflict?

**State:** Exploring

**First-pass answer:** Separate hard constraints from preferences, then have the authorized customer rank or weight measurable criteria. Compare multiple alternatives and show uncertainty and sensitivity, especially when small weight changes alter the recommendation. The customer owns value priorities; engineers own feasibility and safety constraints.

**Evidence:**
- [NASA decision analysis](https://www.nasa.gov/reference/6-8-decision-analysis/) — **Evidence class:** official guidance. It covers mandatory versus enhancing criteria, normalization, uncertainty, sensitivity, and documented alternatives.
- [Protolabs manufacturing analysis](https://www.protolabs.com/resources/design-tips/navigating-manufacturing-analysis/) — **Evidence class:** delivered digital-manufacturing workflow. It exposes price effects as material, finish, quantity, and delivery selections change.
- [NASA stakeholder expectations definition](https://www.nasa.gov/reference/4-1-stakeholder-expectations-definition/) — **Evidence class:** official guidance. It calls for decision authority, constraints, stakeholder agreement, and measurable objectives.

**Conflicting evidence:** Weighted matrices can hide politics, thresholds, and nonlinear risks; Pareto alternatives and direct deliberation may be more honest than one score.

**Confidence:** High on the process; medium on the best elicitation method.

**Business-specific validation:** Run trade exercises with target buyers, engineering, and purchasing and compare ranking, point allocation, and pairwise methods for stability and comprehension.

**Second-opinion review:** GPT-5.4 — Agree. The answer properly separates customer value judgments from engineering feasibility and keeps weighted scoring subordinate to sensitivity and alternative review.

## Customer Identity, Trust, and Communication

### TRU-001 — When does the customer need an account, and which early prototype flows can remain unauthenticated?

**State:** Exploring

**First-pass answer:** Keep education, examples, capability checks, rough estimators, and nonsensitive exploratory intake unauthenticated. Require or strongly offer an account when proprietary files are saved, a durable quote is requested, collaboration or approval begins, an order or payment is placed, production/quality records are exposed, or regulated data is handled; use risk-based step-up authentication.

**Evidence:**
- [NIST SP 800-63B-4](https://pages.nist.gov/800-63-4/sp800-63b.html) — **Evidence class:** official identity guidance. It notes that one-time services do not always require persistent authentication and defines risk-based assurance levels.
- [Baymard cart-abandonment research](https://baymard.com/lists/cart-abandonment-rate) — **Evidence class:** empirical UX research. It reports account requirements as a checkout-abandonment reason; retail users differ from high-value B2B manufacturing customers.
- [Fictiv workflow](https://www.fictiv.com/why-choose-fictiv/how-it-works) — **Evidence class:** delivered digital-manufacturing workflow. Persistent team libraries, configurations, and documents demonstrate the value of authenticated continuity.

**Conflicting evidence:** Early accounts improve continuity, ownership, security, and collaboration and may be expected for high-value work.

**Confidence:** Medium-high.

**Business-specific validation:** A/B test guest exploration against early registration and measure qualified completion, later conversion, file-security incidents, and customer expectations.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The low-friction-intake logic is sound, but the Baymard evidence is e-commerce rather than industrial quoting, so unauthenticated flows should stay limited to nonsensitive exploration.

### TRU-002 — What information should the customer see at every stage?

**State:** Exploring

**First-pass answer:** Always show project/order ID, current stage in plain language, approved revision, last material update, owner, next action and responsible party, blockers and requested decisions, forecast price/completion range, active assumptions/risks, milestone history, and escalation contact. Add technical evidence progressively rather than exposing every internal event.

**Evidence:**
- [Fictiv workflow](https://www.fictiv.com/why-choose-fictiv/how-it-works) — **Evidence class:** delivered digital-manufacturing workflow. It centralizes order records, validated configurations, DFM, invoices, and inspection records.
- [NASA configuration management](https://www.nasa.gov/reference/6-5-configuration-management/) — **Evidence class:** official guidance. It emphasizes current and historical configuration, change status, workflow, notifications, and a released source of truth.
- [GOV.UK Notify status](https://status.notifications.service.gov.uk/) — **Evidence class:** delivered public-service workflow. It demonstrates current condition, incident history, and update subscriptions, though manufacturing projects are more complex.

**Conflicting evidence:** Exposing every risk, supplier detail, or engineering event can overwhelm customers, reveal IP, and amplify normal variability.

**Confidence:** Medium-high.

**Business-specific validation:** Card-sort and comprehension-test dashboard fields with engineers, purchasing users, and first-time inventors.

**Second-opinion review:** GPT-5.4 — Agree. The visible-state set is appropriately customer-centered and avoids the unsupported assumption that exposing every internal event improves trust.

### TRU-003 — What does "always in the know" mean in measurable terms?

**State:** Exploring

**First-pass answer:** Define it as service measures: material-event posting rate, dashboard freshness, notification delivery, exception acknowledgement time, maximum interval without a substantive update, changed commitments communicated before their former due date, customer comprehension, and milestone surprise rate. Set separate targets for routine work, customer blockers, and production-stopping incidents.

**Evidence:**
- [Atlassian incident-communication guidance](https://support.atlassian.com/statuspage/docs/incident-communication-tips/) — **Evidence class:** delivered operational guidance. It recommends early acknowledgement, explicit cadence, precise impact, and consistent channels.
- [GOV.UK Notify API status model](https://docs.notifications.service.gov.uk/rest-api.html#message-status-descriptions) — **Evidence class:** delivered government service. It uses durable message IDs, delivery states, and failure records.
- [NASA configuration management](https://www.nasa.gov/reference/6-5-configuration-management/) — **Evidence class:** official guidance. It calls for timely, accurate status, metrics, timestamps, and notifications.

**Conflicting evidence:** Real-time or excessive reporting can publish unstable estimates and create alert fatigue; freshness should match decision significance.

**Confidence:** Medium; dimensions are clear, thresholds are not.

**Business-specific validation:** Establish pilot baselines for support contacts and surprises, then test candidate freshness and acknowledgement targets against staffing cost and satisfaction.

**Second-opinion review:** GPT-5.4 — Agree. Turning "always in the know" into measurable freshness, acknowledgement, and surprise-rate metrics is stronger than vague transparency language and matches service-operations practice.

### TRU-004 — Which events should trigger notifications, and through which channels?

**State:** Exploring

**First-pass answer:** Notify on customer action required; quote or approval readiness; revision, price, or committed-date changes; major milestones; elevated risk; failed analysis or rejected design; production hold, scrap, or rework; supplier disruption; shipment; security/access events; and closure. Keep the portal canonical, use email for durable alerts, opt-in SMS/push for urgent events, and human outreach for high-consequence matters.

**Evidence:**
- [GOV.UK Notify API](https://docs.notifications.service.gov.uk/rest-api.html) — **Evidence class:** delivered government workflow. It supports templated email and SMS with references and delivery tracking.
- [Atlassian Statuspage notifications](https://www.atlassian.com/software/statuspage/features/notifications) — **Evidence class:** delivered operational workflow. It provides subscriber-facing exception notifications, though it does not prove optimal trigger selection.
- [CDC risk-communication principles](https://www.cdc.gov/public-health-risk-communication/php/about/understanding-and-applying-risk-communication-principles.html) — **Evidence class:** official evidence-based guidance. It recommends channels that reach the affected audience and fill consequential information gaps.

**Conflicting evidence:** Notifying every state change creates alert fatigue; routine events should be batched and preferences should be granular.

**Confidence:** Medium-high.

**Business-specific validation:** Interview customers on urgency and channel preferences, then measure delivery, open/action time, opt-outs, and missed decisions by event class.

**Second-opinion review:** GPT-5.4 — Agree. The notification triggers and channel split are production-applicable, and the alert-fatigue contrary evidence is already handled with batching and preference control.

### TRU-005 — How should delays, failed simulations, rejected designs, supplier problems, and scrap be communicated?

**State:** Exploring

**First-pass answer:** Communicate early through a named owner: what happened, observed impact, knowns and unknowns, containment, safety/data/payment implications, options and trade-offs, supported cost/date range, required customer decision, and next update time. Own supplier failures as the customer-facing provider and avoid premature root-cause claims.

**Evidence:**
- [Atlassian incident-communication guidance](https://support.atlassian.com/statuspage/docs/incident-communication-tips/) — **Evidence class:** delivered operational guidance. It recommends early, plain-language, consistent, empathetic updates and ownership of third-party failures.
- [CDC risk-communication principles](https://www.cdc.gov/public-health-risk-communication/php/about/understanding-and-applying-risk-communication-principles.html) — **Evidence class:** official evidence-based guidance. It recommends stating knowns, unknowns, corrective work, timing, and actionable choices.
- [NASA technical risk management](https://www.nasa.gov/reference/6-4-technical-risk-management/) — **Evidence class:** official guidance. It requires risk status, triggers, mitigation, contingency, and stakeholder communication.

**Conflicting evidence:** Premature root-cause or completion estimates can mislead; the first update should acknowledge impact without speculation.

**Confidence:** High on message structure; medium on timing thresholds and remedies.

**Business-specific validation:** Tabletop-test event templates with customers, operations, counsel, and engineering and measure comprehension, decision time, and perceived accountability.

**Second-opinion review:** GPT-5.4 — Agree. Early, owner-led incident communication with knowns, unknowns, options, and next-update time matches good operational communication practice and avoids premature root-cause claims.

### TRU-006 — How do we explain confidence, uncertainty, assumptions, and risk without overwhelming the customer?

**State:** Exploring

**First-pass answer:** Use layers: lead with the conclusion and recommended action; pair calibrated words with ranges or natural frequencies; state the main assumptions and unknowns; show best/expected/worst or threshold scenarios; and provide expandable evidence. Avoid unsupported precision and say what new evidence would reduce uncertainty.

**Evidence:**
- [CDC risk-communication principles](https://www.cdc.gov/public-health-risk-communication/php/about/understanding-and-applying-risk-communication-principles.html) — **Evidence class:** official evidence-based guidance. It recommends known/unknown statements, descriptive plus numeric information, natural frequencies, absolute risk, and consistent denominators.
- [FDA risk communication user guide](https://www.fda.gov/media/81597/download) — **Evidence class:** official communication guidance. It provides evidence-based guidance for communicating risks and benefits and supports adapting uncertainty presentation from health to customer decision contexts.
- [NASA decision analysis](https://www.nasa.gov/reference/6-8-decision-analysis/) — **Evidence class:** official guidance. It requires assumptions, limitations, uncertainty, and sensitivity to be documented.

**Conflicting evidence:** Numeric confidence can imply false precision, while qualitative labels are interpreted inconsistently; both need an evidence and consequence context.

**Confidence:** High.

**Business-specific validation:** Test risk displays across technical literacy levels and measure interpretation accuracy, decision quality, and recall of the main assumption.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. The layered explanation pattern is strong, but the human-factors evidence comes mainly from public-health communication and needs validation in B2B manufacturing decisions.

### TRU-007 — When can the customer communicate with a human, and what response-time commitment is realistic?

**State:** Exploring

**First-pass answer:** Provide human access during ambiguous discovery, consequential approvals, automation failure, safety/regulatory questions, revisions or disputes, significant delays, and paid-customer escalation. Publish acknowledgement targets by severity and staffed hours rather than promising universal 24/7 resolution; separate rapid acknowledgement from final resolution.

**Evidence:**
- [ISO 10002:2018](https://www.iso.org/standard/71580.html) — **Evidence class:** primary standards-body guidance. It addresses visible, accessible, responsive, and timely complaint handling but does not prescribe universal SLA values.
- [Protolabs manufacturing analysis](https://www.protolabs.com/resources/design-tips/navigating-manufacturing-analysis/) — **Evidence class:** delivered digital-manufacturing workflow. Application engineers supplement automated DFM when changes or clarification are required.
- [Atlassian incident-communication guidance](https://support.atlassian.com/statuspage/docs/incident-communication-tips/) — **Evidence class:** delivered operational guidance. It separates acknowledgement and recurring updates from final resolution.

**Conflicting evidence:** Immediate human access is expensive and can bypass efficient self-service; routine questions do not require an engineer.

**Confidence:** Medium; escalation conditions are defensible, but response times depend on staffing and demand.

**Business-specific validation:** Forecast contact volume and severity, run a staffed pilot, and set commitments from observed queue distributions and after-hours cost.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Severity-based acknowledgement targets are more credible than blanket 24/7 promises, but actual commitments depend on staffing model, customer tier, and escalation design.

### TRU-008 — What project information can be shared with customer collaborators, purchasing teams, or outside engineers?

**State:** Exploring

**First-pass answer:** Share by project, role, artifact, and purpose rather than blanket account access. Use viewer, commenter, technical contributor, approver, and purchaser roles with separate rights for CAD, requirements, quotes, payment, inspection, and downloads; support owner approval, authentication, expiry, revocation, and audit, subject to contract, export-control, privacy, and IP restrictions.

**Evidence:**
- [NIST SP 800-47 Rev. 1](https://csrc.nist.gov/pubs/sp/800/47/r1/final) — **Evidence class:** official security guidance. It requires protection commensurate with risk before, during, and after cross-organization information exchange.
- [NIST SP 800-53 Rev. 5](https://doi.org/10.6028/NIST.SP.800-53r5) — **Evidence class:** official control catalog. It covers account management, access enforcement, separation of duties, least privilege, and audit.
- [NASA configuration management](https://www.nasa.gov/reference/6-5-configuration-management/) — **Evidence class:** official operating guidance. It identifies secure external sharing, version control, history, and limited access as configuration-system attributes.

**Conflicting evidence:** Broad team access accelerates collaboration; excessive restrictions drive uncontrolled email copies and shadow systems.

**Confidence:** High on least privilege and audit; medium on default role boundaries.

**Business-specific validation:** Map representative customer organizations and procurement flows, obtain IP/export/privacy review, and tabletop-test accidental disclosure scenarios.

**Second-opinion review:** GPT-5.4 — Agree. Role-, artifact-, and purpose-based sharing is the right default for collaborative manufacturing work, especially once export, privacy, and IP limits are considered.
