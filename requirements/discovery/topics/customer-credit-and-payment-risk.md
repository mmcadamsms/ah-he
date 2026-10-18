# Customer Credit and Payment Risk Questions

## Purpose

This topic tracks whether customers pay reliably, how much unsecured exposure
the business should accept, and how payment behavior affects deposits, credit
limits, milestone billing, production release, shipment, and collections.

Use objective payment and credit evidence. Do not characterize customers with
derogatory labels or infer payment reliability from protected characteristics,
geography, personal identity, or unrelated reputation.

## Question Supplied in Conversation

### CRD-001 — How should a customer's past payment history affect future work and payment terms?

**State:** Exploring

**First-pass answer:** Use documented payment behavior to move customers among
prepaid, deposit/milestone, limited-net, and expanded-net tiers. Weight recent,
repeated, high-value behavior most; separate valid disputes and business errors
from credit delinquency; and cap new unsecured exposure at demonstrated payment
capacity. Broken promises, failed payments, collection-only payment, material
deterioration, or insolvency evidence should tighten terms or trigger hold.

**Evidence:**
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Bank controls use aging, dilution, concentration, collectibility, and verified collateral rather than invoice face value alone.
- [Federal Reserve 2024 Report on Employer Firms](https://www.fedsmallbusiness.org/reports/survey/2024/2024-report-on-employer-firms) — **Evidence class:** empirical survey. The report includes measured business-payment conditions and shows that operating-expense pressure is common among small firms.
- [Regulation B](https://www.consumerfinance.gov/rules-policy/regulations/1002/) — **Evidence class:** primary official law/guidance. Business-credit decisions remain subject to applicable fair-lending and notice requirements.

**Conflicting evidence:** A late payment can reflect a valid invoice defect rather
than weak capacity; conversely, several small on-time invoices do not prove
capacity for a much larger order.

**Confidence:** High on policy direction; medium on tier thresholds.

**Business-specific validation:** Analyze at least 12-24 months of invoice-level
history and have counsel review the credit policy, notices, guarantees, fees,
and jurisdiction-specific collection remedies.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms recent, repeated, amount-weighted payment behavior is a valid
commercial-credit input, but valid dispute amounts and business-caused errors
must stay separate from delinquency. Changes: keep demonstrated payment
capacity, current exposure, and dispute-adjusted lateness as distinct controls.

**Original wording:** "Are they able to pay on time or are they slackers when it comes to paying us."

The eventual answer should distinguish:

- paid on time;
- paid early;
- paid late but predictably;
- repeatedly broke promised payment dates;
- disputed invoices for valid reasons;
- disputed invoices without timely evidence;
- paid only after collection activity;
- returned or failed payments;
- partial payments;
- write-offs;
- bankruptcy or insolvency events;
- no internal history;
- strong external business-credit evidence; and
- customers whose order size exceeds prior demonstrated payment capacity.

## Payment-History Metrics

### CRD-002 — Which internal payment metrics should be retained?

**State:** Exploring

**First-pass answer:** Retain immutable invoice, delivery/acceptance, due,
dispute, promise, collection, credit-memo, and cleared-payment events; derive
days to pay, percent within terms, aging buckets, roll rates, dispute-adjusted
lateness, failed/partial payments, write-offs, open exposure, deposits, and
highest successfully cleared balance. Keep original and corrected values with
reason, approver, and timestamp.

**Evidence:**
- [SBA 7(a) Working Capital Pilot](https://www.sba.gov/loans/7a-loans/#wcp-program) — **Evidence class:** observed financing practice. Borrowers are expected to produce timely receivable/payable agings and inventory reports.
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Receivable lenders evaluate aging, disputes/dilution, concentration, returns, credits, and collection performance.
- [IRS Recordkeeping](https://www.irs.gov/businesses/small-businesses-self-employed/recordkeeping) — **Evidence class:** primary official guidance. Records must support financial statements, transactions, deductions, and tax reporting.

**Conflicting evidence:** Averages are easy to report but can hide a few severe
late payments; retain distributions, amounts, and event history rather than one
score.

**Confidence:** High.

**Business-specific validation:** Map fields to invoice, bank, shipping, quality,
CRM, and accounting systems; validate cleared-funds dates and correction audit.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms event-
level history is more defensible than a single score and matches common
receivables, audit, and lender practice. Changes: none.

Potential metrics include:

- invoice amount and date;
- contractual due date;
- actual payment date;
- days early or late;
- average and median days to pay;
- percent paid within terms;
- aging by 1-30, 31-60, 61-90, and over 90 days;
- partial payment;
- returned payment;
- dispute reason and resolution;
- promise-to-pay date and whether it was kept;
- collection contacts;
- credit memo;
- write-off;
- deposit and milestone compliance;
- current open exposure; and
- highest successfully paid balance.

### CRD-003 — How should legitimate invoice disputes affect payment history?

**State:** Exploring

**First-pass answer:** Pause delinquency scoring only for the documented,
good-faith disputed amount while the undisputed amount remains due. Classify
root cause—business billing/quality failure, freight/tax issue, customer
authorization problem, or unsupported delay—record resolution time, and restore
the customer's score when the business caused the error.

**Evidence:**
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Disputes, returns, allowances, and credits reduce receivable collectibility and borrowing value.
- [Regulation B adverse-action rules](https://www.consumerfinance.gov/rules-policy/regulations/1002/9/) — **Evidence class:** primary official law/guidance. Credit decisions requiring notice must give specific, accurate reasons rather than an unsupported label.

**Conflicting evidence:** Customers may dispute weakly to delay payment, while
suppliers may misclassify real nonconformance as delinquency; evidence and
resolution SLAs are required.

**Confidence:** High on separation principle; medium on scoring impact.

**Business-specific validation:** Define contract acceptance, quality, freight,
tax, and documentation evidence with sales, quality, accounting, and counsel.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms legitimate disputes should pause negative scoring only for the
supported disputed amount, while the undisputed balance stays due. Changes:
none beyond requiring evidence standards and resolution timing.

Separate customer-caused nonpayment from billing errors, incorrect quantity,
quality nonconformance, missing documentation, unauthorized changes, freight
damage, tax error, or the business failing to meet contractual requirements.

### CRD-004 — How should related customer entities be treated?

**State:** Exploring

**First-pass answer:** Keep each legal obligor's exposure and history separate
unless a signed guarantee, cross-default, centralized payment agreement, merger,
or other enforceable evidence links them. Related-entity history may inform
review but should not automatically transfer liability, credit capacity, or
adverse treatment.

**Evidence:**
- [Uniform Commercial Code](https://www.uniformlaws.org/acts/ucc) — **Evidence class:** primary model-law evidence. Commercial rights arise through state-adopted transaction and secured-credit law, making the actual obligor and agreement material.
- [Regulation B](https://www.consumerfinance.gov/rules-policy/regulations/1002/) — **Evidence class:** primary official law/guidance. Credit evaluation and guarantee practices must comply with applicable credit law and use supportable applicant-specific information.

**Conflicting evidence:** Centralized treasury can make group history predictive,
but corporate separateness can prevent collection from an affiliate.

**Confidence:** High.

**Business-specific validation:** Verify exact legal names, ownership, payment
process, authority, and guarantees; obtain commercial counsel review in the
governing jurisdictions.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms legal obligors should not be merged casually, even when group
history is commercially informative. Changes: keep predictive use of affiliate
history separate from enforceable liability, guarantees, or cross-default
rights.

Determine when parent, subsidiary, division, franchise, owner, purchasing
agent, or successor payment history may be considered and what evidence is
required.

### CRD-005 — How long should payment history remain relevant?

**State:** Exploring

**First-pass answer:** Use all legally retained history but decay its decision
weight with time: recent repeated behavior and current exposure matter most;
older severe events remain flags but should be reconsidered after sustained
improvement, ownership/control change, restructuring, or a verified cause.
Retention and decision relevance are separate policies.

**Evidence:**
- [IRS record-retention guidance](https://www.irs.gov/businesses/small-businesses-self-employed/how-long-should-i-keep-records) — **Evidence class:** primary official guidance. Tax-support records commonly have multi-year periods, bad-debt records may require seven years, employment records at least four, and property records through disposition limitations.
- [Regulation B record and notice requirements](https://www.consumerfinance.gov/rules-policy/regulations/1002/) — **Evidence class:** primary official law/guidance. Applicable credit records and adverse-action processes have legal retention and accuracy obligations.

**Conflicting evidence:** Permanent scoring can ignore rehabilitation and changed
control, while very short windows miss low-frequency defaults; severity,
frequency, amount, and recency should all remain visible.

**Confidence:** High on method; low on one universal period.

**Business-specific validation:** Counsel must set a retention schedule by state,
contract, litigation hold, tax, privacy, and credit-law requirements.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms retention and decision weighting are separate questions and
that severe but old events should be visible without dominating forever.
Changes: none beyond setting a decay policy and a separate legal-retention
schedule.

Consider recency, frequency, amount, improved behavior, ownership change,
management change, restructuring, and legal retention requirements.

## New Customers and External Credit

### CRD-006 — What terms apply to a customer with no history?

**State:** Exploring

**First-pass answer:** Default to exposure-limited terms: verified legal identity,
credit application, enough deposit to cover nonrecoverable commitments, and
milestones before exposure increases. Grant a small net limit only after
external evidence and references are verified; increase it after cleared
payments on comparable orders.

**Evidence:**
- [SBA 7(a) Working Capital Pilot](https://www.sba.gov/loans/7a-loans/#wcp-program) — **Evidence class:** observed financing practice. Contract financing relies on current receivable, payable, inventory, and financial information rather than an unsupported promise.
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Eligible receivables are controlled through verification, concentration and dilution analysis, and conservative advance treatment.

**Conflicting evidence:** Strict prepayment can lose good customers; unsecured
net terms can transfer startup and custom-inventory risk to the supplier.

**Confidence:** High on staged exposure; medium on deposit size.

**Business-specific validation:** Test competitor/customer expectations, payment
method cost, resale value, lead time, and contract enforceability with counsel.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms staged exposure is the right default for no-history customers,
with deposits and milestones keyed to nonrecoverable commitment rather than a
flat label like "net 30." Changes: none.

Options include:

- full prepayment;
- material deposit;
- design deposit;
- milestone billing;
- credit card;
- cash on delivery;
- limited initial credit;
- personal or corporate guarantee where appropriate;
- trade references; and
- external business-credit review.

### CRD-007 — Which external credit sources are appropriate?

**State:** Exploring

**First-pass answer:** Use verified business credit reports, customer financial
statements, bank/trade references with authorization, secretary-of-state and
court records, UCC/lien and bankruptcy searches, and ownership/guarantee
documents. Treat scores as leads, verify identity and date, and never import
personal consumer data without a lawful purpose, consent where required, and
legal review.

**Evidence:**
- [U.S. Courts Bankruptcy Basics](https://www.uscourts.gov/court-programs/bankruptcy/bankruptcy-basics) — **Evidence class:** primary official legal guidance. Bankruptcy status and process are governed by federal law and court records, with professional advice recommended.
- [CFPB Fair Credit Reporting Act resources](https://www.consumerfinance.gov/compliance/compliance-resources/other-applicable-requirements/fair-credit-reporting-act/) — **Evidence class:** primary official law/guidance. Consumer-report use and furnishing are regulated and subject to permissible-purpose, accuracy, and notice rules.
- [Regulation B](https://www.consumerfinance.gov/rules-policy/regulations/1002/) — **Evidence class:** primary official law/guidance. Credit decisions and guarantees are subject to ECOA/Regulation B where applicable.

**Conflicting evidence:** External scores broaden evidence but may be stale,
opaque, or attached to the wrong entity; current statements and direct payment
experience can be more probative.

**Confidence:** High on source categories; medium on provider quality.

**Business-specific validation:** Counsel must approve sources, authorizations,
notices, permissible purposes, privacy controls, and state-specific searches.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms these are the right external source categories, but FCRA risk
usually enters only when the report is about an individual guarantor or sole
proprietor rather than a business entity. Changes: keep business-report review
and consumer-report review as separate legal paths.

Consider business credit reports, financial statements, bank or trade
references, public filings, liens, judgments, bankruptcy, corporate status,
years in business, payment indexes, and customer-provided credit applications.

Legal review is required before using personal consumer credit or guarantees.

### CRD-008 — How should a customer's requested order size compare with demonstrated credit capacity?

**State:** Exploring

**First-pass answer:** Compare maximum unsecured exposure—not order price—with
the customer's highest comparable cleared balance, current open exposure,
verified liquidity/cash flow, external limits, and collateral/guarantees. Any
step-change should be prepaid, deposited, milestone-billed, insured, or approved
as a documented exception.

**Evidence:**
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Lenders haircut and limit receivables for concentration, aging, disputes, and collectibility rather than advancing against invoice amount without limit.
- [FDIC Commercial and Industrial Lending](https://www.fdic.gov/risk-management-manual-examination-policies/commercial-and-industrial-lending.pdf) — **Evidence class:** bank supervisory practice. Repayment capacity, borrower condition, collateral, guarantees, and monitoring jointly determine credit risk.

**Conflicting evidence:** Fast-growing customers may legitimately exceed history,
but growth itself can consume cash and increase default risk.

**Confidence:** High.

**Business-specific validation:** Set step-change thresholds and verify current
financials, backlog funding, parent support, deposits, and total group exposure.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms a large
step-up in unsecured exposure should not be approved solely from a few smaller
on-time invoices. Changes: none.

Avoid granting a large unsecured balance solely because the customer paid
several much smaller invoices on time.

## Terms, Deposits, and Credit Limits

### CRD-009 — How is a customer credit limit established?

**State:** Exploring

**First-pass answer:** Set the limit as the lowest supportable amount from
payment history, verified financial capacity, external evidence, concentration
appetite, recoverable collateral, and the supplier's own loss tolerance.
Calculate exposure as receivables plus committed material, WIP, finished goods,
outside processing, and contractual cancellation risk less cleared deposits.

**Evidence:**
- [FDIC Commercial and Industrial Lending](https://www.fdic.gov/risk-management-manual-examination-policies/commercial-and-industrial-lending.pdf) — **Evidence class:** bank supervisory practice. Commercial underwriting evaluates cash-flow repayment, financial condition, collateral, guarantees, covenants, and ongoing monitoring.
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Borrowing bases exclude or haircut risky, concentrated, delinquent, and disputed receivables.

**Conflicting evidence:** Sales forecasts favor larger limits, while loss
capacity and concentration may require less; approval must be independent of
commission pressure.

**Confidence:** High on framework; medium on formula weights.

**Business-specific validation:** Back-test proposed limits against historical
losses and late payments; approve limits under a documented authority matrix.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms the
lowest-supportable-limit approach and the exposure definition are directionally
correct for custom manufacturing risk. Changes: none.

Consider payment history, business-credit evidence, financial condition,
order size, material commitment, work in process, finished inventory,
resalability, customer concentration, contract remedies, and business cash
reserves.

### CRD-010 — Which work should require deposits or milestone payments?

**State:** Exploring

**First-pass answer:** Require staged cash for work that creates material
unsecured exposure: new or deteriorating customers, custom engineering,
noncancelable material, special tooling, outside services, long lead time,
multi-month work, low-resale parts, large orders, or concentration beyond the
credit limit. Milestones should precede each major irreversible commitment.

**Evidence:**
- [SBA 7(a) Working Capital Pilot](https://www.sba.gov/loans/7a-loans/#wcp-program) — **Evidence class:** observed financing practice. Contract/project working-capital finance depends on monitored receivables, payables, inventory, and project needs.
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Custom, disputed, concentrated, or otherwise less collectible assets receive reduced lending value.

**Conflicting evidence:** Deposits reduce supplier risk but can burden a strong
customer's cash; a guarantee, letter of credit, card authorization, or
credit-insurance structure may sometimes substitute.

**Confidence:** High on triggers; medium on commercial acceptance.

**Business-specific validation:** Measure nonrecoverable exposure at each routing
step and have counsel define refund, cancellation, title, tax, and milestone
acceptance terms.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms
deposits and milestones should align with irreversible commitments such as
custom engineering, noncancelable material, tooling, and long lead times.
Changes: none.

Potential triggers include:

- custom engineering;
- noncancelable material;
- special tooling;
- long lead times;
- large orders;
- low-resale custom parts;
- outside processing;
- new customers;
- weak payment history;
- financial deterioration; and
- work spanning multiple months.

### CRD-011 — What portion should be collected before purchasing material or releasing production?

**State:** Exploring

**First-pass answer:** Collect enough cleared funds to keep worst-case net
exposure within the approved credit limit. Before material release, cover
noncancelable material, tooling, freight, tax, and outside commitments; before
later production stages, cover accumulated WIP plus the next irreversible step.
There is no defensible universal deposit percentage.

**Evidence:**
- [SBA 7(a) Working Capital Pilot](https://www.sba.gov/loans/7a-loans/#wcp-program) — **Evidence class:** observed financing practice. Working-capital needs are supported by detailed receivable, payable, and inventory information.
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Advance value changes with collectibility, concentration, inventory type, and liquidation risk.

**Conflicting evidence:** A flat percentage is simple but can underfund a
material-heavy job and overcharge a service-heavy one.

**Confidence:** High.

**Business-specific validation:** Build exposure curves by routing and test
refund, tax, chargeback, and cancellation treatment with CPA, processor, and
commercial counsel.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms there
is no defensible universal deposit percentage and that the right test is worst-
case net exposure at each routing step. Changes: none.

Separate material, tooling, engineering, outside services, work in process,
profit, freight, and tax rather than applying one arbitrary deposit percentage.

### CRD-012 — When should terms move from prepaid to net terms or vice versa?

**State:** Exploring

**First-pass answer:** Grant net terms only after verified identity, complete
credit review, and multiple cleared payments on comparable exposure; increase
limits gradually. Reduce or suspend terms after material delinquency, broken
promises, failed payment, adverse financial/legal change, exceeded limit,
stale review, or concentration breach; restore only after documented cure and
new successful performance.

**Evidence:**
- [FDIC Commercial and Industrial Lending](https://www.fdic.gov/risk-management-manual-examination-policies/commercial-and-industrial-lending.pdf) — **Evidence class:** bank supervisory practice. Credit risk is monitored continuously and terms/covenants respond to deterioration, not only origination data.
- [Regulation B adverse-action rules](https://www.consumerfinance.gov/rules-policy/regulations/1002/9/) — **Evidence class:** primary official law/guidance. Where applicable, changes or denials require timely, specific, supportable notices.

**Conflicting evidence:** One late payment may be operational noise, but waiting
for repeated default can create avoidable loss; materiality and documented cause
should control.

**Confidence:** High on direction; medium on thresholds.

**Business-specific validation:** Back-test upgrade/downgrade triggers and have
counsel review notices, cure rules, and consistent application.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms terms should expand or contract only after documented review
and comparable cleared performance, with deterioration triggers applied
consistently. Changes: keep notice/record procedures explicit where Regulation
B or contract terms require them.

Define evidence and thresholds for granting, increasing, reducing, suspending,
and restoring credit.

### CRD-013 — Should early-payment discounts or late-payment charges be offered?

**State:** Exploring

**First-pass answer:** Offer either only when a customer-segment model shows net
benefit after margin, financing cost, card/ACH cost, administration, tax, and
behavioral response. State discounts and charges expressly in the accepted
contract and invoice; do not impose a late fee until counsel confirms governing
law, rate/fee limits, notice, sales-tax treatment, and enforceability.

**Evidence:**
- [Uniform Commercial Code](https://www.uniformlaws.org/acts/ucc) — **Evidence class:** primary model-law evidence. Commercial payment rights depend on state-adopted contract and transaction law.
- [Federal Reserve 2024 Report on Employer Firms](https://www.fedsmallbusiness.org/reports/survey/2024/2024-report-on-employer-firms) — **Evidence class:** empirical survey. Small firms report operating-expense and payment pressure, so accelerating cash can have value but customer response is uncertain.

**Conflicting evidence:** Discounts may accelerate marginal payers but simply
reduce margin for customers who would have paid early; late charges may
compensate cost but worsen disputes or be legally limited.

**Confidence:** Medium because economics and enforceability are jurisdiction-
and customer-specific.

**Business-specific validation:** Run a controlled segment test and obtain
state-specific CPA/tax and commercial-counsel review before launch.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms discounts or late charges should be offered only where the net
economics and enforceability are proven, not assumed. Changes: none beyond
separate review of margin impact, tax treatment, and jurisdiction-specific fee
limits.

Model economics, enforceability, customer expectations, administrative cost,
sales tax, usury or fee restrictions, and whether discounts merely reduce
margin from customers who would already pay on time.

## Order and Production Controls

### CRD-014 — When should quoting, material purchase, production, or shipment be placed on credit hold?

**State:** Exploring

**First-pass answer:** Use staged holds. Permit quoting but flag terms; block new
commitments when required deposits, review, or documents are missing; block
production when exposure would exceed limit; and block shipment for material
overdue balances, failed payments, broken promises, insolvency evidence, or
unresolved authority. Safety, legal, and customer-property duties remain.

**Evidence:**
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Aging, disputes, concentration, and collectibility drive eligibility and control.
- [U.S. Courts Bankruptcy Basics](https://www.uscourts.gov/court-programs/bankruptcy/bankruptcy-basics) — **Evidence class:** primary official legal guidance. Bankruptcy can change collection and possession rights and requires competent legal advice.

**Conflicting evidence:** Immediate hold protects cash but can prevent completion
of paid or value-preserving work; controls should stop incremental unsecured
exposure, not blindly stop every activity.

**Confidence:** High on staged controls; medium on legal shipment rights.

**Business-specific validation:** Counsel must review contracts, setoff, lien,
bankruptcy-stay, customer-property, and notice rules in each jurisdiction.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms staged holds are the right control because the goal is to stop
new unsecured exposure, not blindly stop every activity. Changes: keep
bankruptcy-stay, lien, and customer-property issues explicitly subject to
counsel review.

Potential triggers include overdue balance, exceeded credit limit, broken
payment promise, failed payment, unresolved financial concern, expired credit
review, or external insolvency evidence.

### CRD-015 — Which activities may continue while a customer is on credit hold?

**State:** Exploring

**First-pass answer:** Continue only work that is fully paid, prevents injury or
property damage, preserves customer-owned assets/data, completes required
inspection/corrective obligations, or reduces loss without increasing unsecured
exposure. Segregate costs and require approval before any discretionary work.

**Evidence:**
- [Uniform Commercial Code](https://www.uniformlaws.org/acts/ucc) — **Evidence class:** primary model-law evidence. State-adopted commercial law governs possession, performance, security interests, and remedies.
- [U.S. Courts Bankruptcy Basics](https://www.uscourts.gov/court-programs/bankruptcy/bankruptcy-basics) — **Evidence class:** primary official legal guidance. Insolvency proceedings may limit collection or disposition actions and require legal advice.

**Conflicting evidence:** Finishing a nearly complete order may improve recovery,
but it can deepen loss if shipment/payment is uncertain.

**Confidence:** High on exposure-neutral principle; low on specific legal duties.

**Business-specific validation:** Create a hold checklist with operations,
quality, safety, insurance, and commercial/bankruptcy counsel.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms only exposure-neutral, safety, preservation, or already-paid
work should continue on hold. Changes: none beyond forcing documented approval
for any discretionary continuation.

Distinguish safety, preservation of customer property, inspection, engineering,
work already paid, corrective work, and activities that increase unsecured
exposure.

### CRD-016 — Who may override payment terms or a credit hold?

**State:** Exploring

**First-pass answer:** Only a role independent of the salesperson and senior
enough for the incremental exposure should override. Require customer, amount,
reason, evidence, duration, compensating control, approver, and automatic
expiry; prohibit self-approval and silent overrides.

**Evidence:**
- [FDIC Commercial and Industrial Lending](https://www.fdic.gov/risk-management-manual-examination-policies/commercial-and-industrial-lending.pdf) — **Evidence class:** bank supervisory practice. Sound commercial-credit administration uses approval authorities, covenants, documentation, monitoring, and exception controls.
- [IRS Recordkeeping](https://www.irs.gov/businesses/small-businesses-self-employed/recordkeeping) — **Evidence class:** primary official guidance. Transaction records must support financial statements, income/expense reporting, and substantiation.

**Conflicting evidence:** Central approval improves control but can delay urgent
orders; preapproved exposure bands and time-limited emergency authority can
preserve speed without removing accountability.

**Confidence:** High.

**Business-specific validation:** Set dollar bands from loss capacity and test
workflow, separation of duties, audit reporting, and after-the-fact review.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms
override authority must be independent, exposure-banded, time-limited, and
auditable. Changes: none.

Require authorization, reason, amount, duration, compensating controls, and
audit history.

### CRD-017 — How are customer-owned tools, material, data, and finished parts handled during nonpayment?

**State:** Exploring

**First-pass answer:** Identify ownership, segregate and preserve property, stop
unauthorized use, maintain inventory and condition records, and follow the
contract and governing law for access, storage, liens, return, shipment, or
disposition. Do not assume a right to hold, sell, copy, disable, or destroy
customer property as self-help.

**Evidence:**
- [Uniform Commercial Code](https://www.uniformlaws.org/acts/ucc) — **Evidence class:** primary model-law evidence. State-adopted UCC provisions govern commercial transactions, security interests, and remedies, but local enactments and facts control.
- [U.S. Courts Bankruptcy Basics](https://www.uscourts.gov/court-programs/bankruptcy/bankruptcy-basics) — **Evidence class:** primary official legal guidance. Bankruptcy rights and restrictions are federal legal matters requiring competent counsel.

**Conflicting evidence:** Possession can create leverage or a statutory lien in
some jurisdictions, but wrongful retention or disposition can create conversion,
contract, privacy, or bankruptcy exposure.

**Confidence:** High that legal review is mandatory; low on any universal remedy.

**Business-specific validation:** Counsel must review governing law, contract,
perfected interests, liens, bailment, IP/data, storage, notice, and bankruptcy
status before action.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms the answer correctly rejects assumed self-help rights over
customer property, data, or finished goods. Changes: none beyond requiring
contract and governing-law analysis before any retention, shipment, or
disposition step.

Legal and contract review must determine possession, lien rights, storage,
preservation, access, return, shipment, disposal, and prohibited self-help.

## Collections and Customer Communication

### CRD-018 — What is the collections sequence?

**State:** Exploring

**First-pass answer:** Confirm invoice delivery and acceptance before due date;
send factual reminder; contact the authorized payer promptly after due date;
resolve documented errors; record promises; escalate to credit hold and formal
notice; then use a documented payment plan, demand letter, agency, counsel,
claim, or write-off based on amount, evidence, relationship, limitation period,
and expected recovery.

**Evidence:**
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Aging, verification, dispute/dilution monitoring, and collection controls are central to receivable quality.
- [U.S. Courts Bankruptcy Basics](https://www.uscourts.gov/court-programs/bankruptcy/bankruptcy-basics) — **Evidence class:** primary official legal guidance. Bankruptcy changes creditor procedures and is not suitable for informal self-help.

**Conflicting evidence:** Fast escalation protects limitation and recovery rights,
but aggressive collection before confirming invoice quality can damage a good
relationship and strengthen a dispute.

**Confidence:** High on sequence; medium on timing.

**Business-specific validation:** Counsel must set compliant notices, agency
terms, interest/fees, statutes of limitation, claim deadlines, and bankruptcy
procedures by jurisdiction.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms the
sequence is commercially sound: verify delivery/acceptance, correct errors,
track promises, escalate deliberately, and match remedy cost to recovery odds.
Changes: none.

Define invoice delivery, reminders, confirmation, overdue notices, phone
contact, promise-to-pay tracking, production hold, demand letter, payment plan,
collection agency, legal action, write-off, and relationship review.

### CRD-019 — How should payment concerns be communicated without damaging good customer relationships?

**State:** Exploring

**First-pass answer:** Use one accountable contact, cite invoice/PO/acceptance
facts, ask whether delivery or documentation is missing, correct errors quickly,
state the exact amount/date/action needed, and distinguish routine process from
judgment about the customer. Keep tone and escalation consistent across
customers.

**Evidence:**
- [Regulation B adverse-action rules](https://www.consumerfinance.gov/rules-policy/regulations/1002/9/) — **Evidence class:** primary official law/guidance. Applicable credit notices require timely, specific reasons, reinforcing factual rather than vague or biased communication.
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Verification, dispute resolution, aging, and collection documentation support accurate action.

**Conflicting evidence:** Soft reminders preserve rapport but can normalize late
payment; rigid scripts can ignore real invoice failures. Use consistent stages
with case-specific facts.

**Confidence:** High.

**Business-specific validation:** Review templates with sales, accounting, and
counsel; sample calls and track disputes, promises, payment, and churn.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms
fact-based, invoice-specific communication is the best way to protect both
collection discipline and customer relationships. Changes: none.

Use factual invoice and agreement language, prompt error correction, one
account owner, clear escalation, and consistent treatment.

### CRD-020 — When is a payment plan appropriate?

**State:** Exploring

**First-pass answer:** Use a written plan only when the customer acknowledges the
debt, provides credible viability evidence, makes a meaningful initial payment,
and the scheduled recovery exceeds likely alternatives. Define dates, method,
security/guarantee if lawful, treatment of future work, interest/fees, default,
acceleration, releases, and reporting; do not keep adding unsecured exposure.

**Evidence:**
- [FDIC Commercial and Industrial Lending](https://www.fdic.gov/risk-management-manual-examination-policies/commercial-and-industrial-lending.pdf) — **Evidence class:** bank supervisory practice. Restructuring should be based on realistic repayment capacity, documentation, collateral/guarantor support, and monitoring.
- [U.S. Courts Bankruptcy Basics](https://www.uscourts.gov/court-programs/bankruptcy/bankruptcy-basics) — **Evidence class:** primary official legal guidance. Formal insolvency may alter repayment, collection, and creditor rights.

**Conflicting evidence:** A plan can maximize recovery from a viable customer or
merely delay default and preference/claim issues.

**Confidence:** Medium because viability and legal effect are fact-specific.

**Business-specific validation:** Obtain current financial evidence and
commercial/bankruptcy-counsel review before material plans or releases.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms payment plans are appropriate only when they improve expected
recovery and are supported by fresh viability evidence. Changes: none beyond
prohibiting silent expansion of new unsecured exposure while a plan is running.

Consider customer viability, acknowledgment of debt, initial payment, schedule,
security, future work, interest or fees, default, and legal documentation.

## Risk and Customer Economics

### CRD-021 — How does payment behavior affect customer profitability?

**State:** Exploring

**First-pass answer:** Charge each customer/job for actual financing days,
collections labor, payment fees, dispute/rework administration, expected credit
loss, storage, hold/rescheduling cost, and opportunity cost of capacity and
cash. Report contribution both before and after payment-risk cost; revenue and
gross margin alone overstate a slow or loss-prone customer's value.

**Evidence:**
- [Federal Reserve 2024 Report on Employer Firms](https://www.fedsmallbusiness.org/reports/survey/2024/2024-report-on-employer-firms) — **Evidence class:** empirical survey. Small firms report operating-expense and payment pressure, demonstrating that collection timing affects survival, not just accounting presentation.
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Aging, dilution, disputes, concentration, and collection cost reduce receivable quality and financing value.

**Conflicting evidence:** A strategically valuable customer may justify lower
near-term margin, but learning value should be explicit and capped rather than
used to hide recurring credit losses.

**Confidence:** High on cost categories; medium on allocation.

**Business-specific validation:** Measure staff time, cost of capital, days to
cleared cash, disputes, storage, and write-offs by customer and order.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms slow or
fragile payment behavior should be charged to customer/job economics rather
than hidden outside profitability reporting. Changes: none.

Include financing cost, collections labor, bad-debt risk, delayed purchasing,
administrative effort, production holds, storage, and opportunity cost in
customer and job profitability.

### CRD-022 — How should customer concentration interact with credit exposure?

**State:** Exploring

**First-pass answer:** Set a combined concentration limit covering receivables,
committed material, WIP, finished goods, tooling, and dedicated capacity.
On-time history does not remove the need for deposits, insurance, guarantees,
diversification, or a lower limit when one customer's failure or pause would
breach liquidity.

**Evidence:**
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Customer concentration is a core receivable-finance risk even when invoices are current.
- [FDIC Commercial and Industrial Lending](https://www.fdic.gov/risk-management-manual-examination-policies/commercial-and-industrial-lending.pdf) — **Evidence class:** bank supervisory practice. Commercial underwriting considers borrower cash flow, industry/business risk, collateral, and ongoing risk concentrations.

**Conflicting evidence:** Concentrated customers can improve efficiency and
forecastability, but dependence increases correlated revenue, capacity, and
credit loss.

**Confidence:** High.

**Business-specific validation:** Stress immediate loss or 90-day delay of each
top customer and set limits from resulting cash/covenant headroom.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms
concentration risk is separate from invoice punctuality and should cap exposure
even for apparently good payers. Changes: none.

A customer can pay on time yet still create unacceptable risk if too much
revenue, receivables, work in process, tooling, or capacity depends on that one
organization.

### CRD-023 — Which customers should be prepaid even if they offer valuable learning work?

**State:** Exploring

**First-pass answer:** Require prepayment or fully risk-covering milestones when
the customer has no verified capacity, weak/broken payment behavior, insolvency
signals, prohibited concentration, unusually custom/nonresalable work, or an
order far above demonstrated capacity. Learning value can justify lower margin,
not unbounded unsecured financing.

**Evidence:**
- [Federal Reserve 2025 Report on Employer Firms](https://www.fedsmallbusiness.org/reports/survey/2025/2025-report-on-employer-firms) — **Evidence class:** empirical survey. Revenue declines and incomplete financing access remain common small-firm risks.
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Concentrated, disputed, aged, or weakly collectible receivables receive limited or no advance value.

**Conflicting evidence:** Strict prepayment can block high-value market entry;
the alternative is a consciously budgeted learning investment with a hard loss
cap, not ordinary net terms.

**Confidence:** High.

**Business-specific validation:** Approve learning spend separately from credit,
with a named hypothesis, maximum loss, milestone, and stop date.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms
learning value can justify a deliberate capped investment, but not ordinary
unsecured credit. Changes: none.

Learning value does not justify unbounded collection risk. Coordinate with:

- [Business and Commercial Strategy](business-and-commercial-strategy.md)
- [Customer Demand and Market Geography](customer-demand-and-market-geography.md)

### CRD-024 — How should credit risk affect a quote?

**State:** Exploring

**First-pass answer:** The quote should state payment method, deposit and
milestones, credit limit, material-release and shipment conditions, quote
validity, financing charge or price where lawful, cancellation exposure,
required guarantee/security, and tax/freight treatment. Credit approval should
be a condition, not an assumption embedded after acceptance.

**Evidence:**
- [Uniform Commercial Code](https://www.uniformlaws.org/acts/ucc) — **Evidence class:** primary model-law evidence. State-adopted commercial law makes agreed transaction terms and remedies central.
- [OCC Accounts Receivable and Inventory Financing](https://www.occ.gov/publications-and-resources/publications/comptrollers-handbook/files/accts-rec-inventory-financing/index-accs-rec-inventory-financing.html) — **Evidence class:** bank supervisory practice. Receivable value depends on contract quality, disputes, offsets, aging, concentration, and collectibility.

**Conflicting evidence:** Raising price for risk may not protect cash if exposure
occurs before payment; deposits and milestones are stronger first controls.

**Confidence:** High.

**Business-specific validation:** Counsel and CPA must approve standard terms,
fees, taxes, guarantees, security interests, acceptance, and jurisdiction.

**Second-opinion review:** GPT-5.4 — Agree. Independent review confirms the
quote should surface payment structure, conditions, and credit approval rather
than leaving financing risk implicit. Changes: none.

Possible effects include deposit, milestone schedule, credit limit, quote
validity, material-release condition, price for financing cost, payment method,
personal or corporate guarantee, and shipment condition.

## Data, Privacy, and Fairness

### CRD-025 — Who may view or change customer credit information?

**State:** Exploring

**First-pass answer:** Use least-privilege roles: sales sees approved terms and
hold status; credit/accounting sees evidence and exposure; designated approvers
change limits/holds; administrators maintain systems but do not approve.
Guarantees, personal reports, bank data, and collection notes need stricter
access, encryption, logging, and periodic review.

**Evidence:**
- [NIST Privacy Framework](https://www.nist.gov/privacy-framework) — **Evidence class:** primary official framework. NIST provides risk-based privacy governance and control practices for managing personal data.
- [CFPB Fair Credit Reporting Act resources](https://www.consumerfinance.gov/compliance/compliance-resources/other-applicable-requirements/fair-credit-reporting-act/) — **Evidence class:** primary official law/guidance. Consumer-report data is regulated and cannot be treated as unrestricted business information.
- [IRS Recordkeeping](https://www.irs.gov/businesses/small-businesses-self-employed/recordkeeping) — **Evidence class:** primary official guidance. Financial records must remain supportable and controlled for reporting and substantiation.

**Conflicting evidence:** Broad visibility can speed sales decisions but increases
privacy, bias, tampering, and breach risk.

**Confidence:** High.

**Business-specific validation:** Inventory data elements and legal bases, then
test role access, logs, segregation of duties, incident response, and deletion.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms least-privilege access is the right baseline, especially for
guarantees, bank data, and collections notes. Changes: keep consumer-report
controls separate from ordinary business-credit data, but do not relax access
controls for either.

Restrict financial records, payment history, credit reports, guarantees,
collection notes, and overrides by role and business need.

### CRD-026 — How are incorrect payment or credit records disputed and corrected?

**State:** Exploring

**First-pass answer:** Provide a documented intake, evidence hold, independent
review, time-bound decision, correction of source and derived scores, customer
notice where appropriate, restoration of terms, and immutable audit trail.
Never delete the fact of correction or retaliate for a supported dispute.

**Evidence:**
- [Regulation B adverse-action rules](https://www.consumerfinance.gov/rules-policy/regulations/1002/9/) — **Evidence class:** primary official law/guidance. Applicable credit decisions require specific reasons and retained records, making accuracy and correction material.
- [CFPB Fair Credit Reporting Act resources](https://www.consumerfinance.gov/compliance/compliance-resources/other-applicable-requirements/fair-credit-reporting-act/) — **Evidence class:** primary official law/guidance. Consumer-report regimes impose accuracy, dispute, and notice duties when such data is used or furnished.

**Conflicting evidence:** Silent deletion may appear customer-friendly but
destroys auditability; retaining an uncorrected adverse score is unfair and can
create legal exposure.

**Confidence:** High.

**Business-specific validation:** Counsel must map business-report, consumer-
report, privacy, notice, and litigation-hold duties to the workflow.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms correction workflows need intake, evidence review, source-data
repair, derived-score repair, and audit history. Changes: keep additional
notice/dispute controls available when consumer-report or personal-guarantee
data is involved.

Provide evidence, review, correction, notification, audit history, and
restoration of terms when the business made an error.

### CRD-027 — Which factors are prohibited from credit decisions?

**State:** Exploring

**First-pass answer:** Prohibit protected characteristics and proxies, religion
or political belief, retaliation, unrelated reputation or geography,
stereotypes, unsupported assumptions, and data lacking a lawful purpose.
Decisions should use verified entity identity, payment behavior, capacity,
exposure, collateral/guarantee, and contract risk. Personal consumer reports
and spousal/guarantee requests require counsel-approved procedures.

**Evidence:**
- [Regulation B](https://www.consumerfinance.gov/rules-policy/regulations/1002/) — **Evidence class:** primary official law/guidance. ECOA/Regulation B restricts prohibited-basis discrimination and regulates credit evaluation, notices, and certain guarantee practices.
- [CFPB Fair Credit Reporting Act resources](https://www.consumerfinance.gov/compliance/compliance-resources/other-applicable-requirements/fair-credit-reporting-act/) — **Evidence class:** primary official law/guidance. Consumer information use requires a permissible purpose and compliance with report-related duties.

**Conflicting evidence:** Some location or industry facts may predict commercial
risk, but broad proxies can encode prohibited or unsupported assumptions; use
transaction-specific evidence and legal review.

**Confidence:** High that objective, lawful factors are required; medium because
coverage and remedies vary by applicant and jurisdiction.

**Business-specific validation:** Have credit counsel approve factors, model
inputs, guarantee forms, notices, exception monitoring, and periodic fairness
testing.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms prohibited-basis and proxy controls are necessary, but lawful
commercial factors like verified entity, payment history, exposure, collateral,
and contract risk should remain usable. Changes: none beyond keeping sole-
proprietor, guarantor, and consumer-report cases under counsel-approved rules.

Do not use protected characteristics, unrelated personal beliefs, stereotypes,
retaliation, or unsupported assumptions. Sole proprietors, personal guarantees,
and consumer reports require additional legal review.

### CRD-028 — How long are credit and collections records retained?

**State:** Exploring

**First-pass answer:** Use a record-class schedule, not one period. Retain
invoices, payments, bad-debt support, contracts, credit decisions, reports,
guarantees, collection communications, disputes, overrides, and property records
for the longest applicable tax, accounting, contract, credit-law, privacy,
limitation, litigation-hold, lender, and insurance requirement; securely delete
when no duty or active need remains.

**Evidence:**
- [IRS record-retention guidance](https://www.irs.gov/businesses/small-businesses-self-employed/how-long-should-i-keep-records) — **Evidence class:** primary official guidance. IRS periods vary by record and event, including seven years for a bad-debt deduction and property records through the disposition limitation period.
- [Regulation B](https://www.consumerfinance.gov/rules-policy/regulations/1002/) — **Evidence class:** primary official law/guidance. Applicable credit decisions have specific record-retention and notice obligations.
- [NIST Privacy Framework](https://www.nist.gov/privacy-framework) — **Evidence class:** primary official framework. Privacy risk management supports limiting retained personal data to governed purposes.

**Conflicting evidence:** Longer retention helps claims and trend analysis but
increases privacy, breach, discovery, and stale-data risk.

**Confidence:** High on schedule method; low on a universal duration.

**Business-specific validation:** Counsel, CPA, insurer, and lender must approve
the jurisdiction-specific schedule, holds, access, archive, and destruction
controls.

**Second-opinion review:** GPT-5.4 — Agree with qualifications. Independent
review confirms a record-class schedule is correct and that one retention
period is not defensible. Changes: keep Regulation B business-credit minimums
as floor rules only, not as the full retention answer for tax, contract,
litigation, lender, or insurance records.

Coordinate accounting, tax, contract, litigation, privacy, security, and
customer-relationship needs.

## Expected Outputs

- customer credit policy;
- new-customer terms;
- credit application and review procedure;
- credit-limit model;
- deposit and milestone rules;
- credit-hold workflow;
- collections procedure;
- customer profitability adjustment for payment behavior;
- privacy and access controls;
- audit and override reporting; and
- formal quote, order-release, production, shipment, and payment requirements.
