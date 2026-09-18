package com.visacoach.data

enum class QuestionCategory(val id: String, val title: String) {
    ALL("ALL", "All Questions"),
    PURPOSE("PURPOSE", "Travel Purpose"),
    EMPLOYMENT("EMPLOYMENT", "Employment & Career"),
    FINANCES("FINANCES", "Finances & Funding"),
    TIES_214B("TIES_214B", "Ties & Section 214(b)"),
    FAMILY_RELATIVES("FAMILY_RELATIVES", "Family & U.S. Relatives"),
    TRAVEL_HISTORY("TRAVEL_HISTORY", "Travel History & Prior Visas"),
    RED_FLAGS("RED_FLAGS", "Tricky Traps & Red Flags")
}

data class VisaQuestionItem(
    val id: String,
    val category: QuestionCategory,
    val question: String,
    val shortSummary: String,
    val officerIntent: String,
    val sampleAnswer: String,
    val redFlags: List<String>,
    val tips: List<String>
)

data class VisaGuideItem(
    val id: String,
    val title: String,
    val category: String,
    val readTime: String,
    val summary: String,
    val keyPoints: List<String>,
    val content: List<String>
)

object VisaQuestionsRepository {

    val questions: List<VisaQuestionItem> = listOf(
        // ─── 1. Travel Purpose ──────────────────────────────────────────
        VisaQuestionItem(
            id = "tp-01",
            category = QuestionCategory.PURPOSE,
            question = "What is the purpose of your trip to the United States?",
            shortSummary = "Direct statement of travel reason matching DS-160.",
            officerIntent = "The officer wants a clear, direct, and unscripted 1-2 sentence response that matches your DS-160 application without hesitation.",
            sampleAnswer = "I am visiting New York and Washington D.C. for a two-week vacation to see the Smithsonian museums and celebrate my 30th birthday.",
            redFlags = listOf(
                "Giving vague answers like 'Just traveling' or 'To look around'.",
                "Mentioning that you might explore job prospects or universities while on a tourist visa.",
                "Contradicting the purpose stated on your DS-160."
            ),
            tips = listOf(
                "Answer in 1-2 concise sentences.",
                "State specific cities and activities.",
                "Maintain steady eye contact and confidence."
            )
        ),
        VisaQuestionItem(
            id = "tp-02",
            category = QuestionCategory.PURPOSE,
            question = "Why are you choosing to travel at this specific time?",
            shortSummary = "Justify timing with work leave, season, or an event.",
            officerIntent = "Officers check whether your travel timing makes sense with your job commitments and annual leave schedule.",
            sampleAnswer = "My company has an annual plant shutdown during the first two weeks of August, and I have official approved annual leave from my employer.",
            redFlags = listOf(
                "Not knowing your own leave dates.",
                "Claiming to leave during your peak busy season at work without plausible explanation."
            ),
            tips = listOf(
                "Align your answer with official leave approvals from work.",
                "If visiting for an event (wedding, conference), state the exact dates."
            )
        ),
        VisaQuestionItem(
            id = "tp-03",
            category = QuestionCategory.PURPOSE,
            question = "Which cities or states do you plan to visit?",
            shortSummary = "Provide a realistic, budgeted itinerary.",
            officerIntent = "Tests whether you have a realistic geographic plan matching the duration of your trip and your budget.",
            sampleAnswer = "I will spend 6 days in Orlando visiting Universal Studios and Kennedy Space Center, and then 4 days in Miami before flying back home.",
            redFlags = listOf(
                "Listing 10 states across the country for a 1-week trip.",
                "Saying 'I haven\\'t decided yet, anywhere is fine'."
            ),
            tips = listOf(
                "Stick to 1 to 3 cities for a typical 2-week vacation.",
                "Know the travel distance between your chosen cities."
            )
        ),
        VisaQuestionItem(
            id = "tp-04",
            category = QuestionCategory.PURPOSE,
            question = "How long do you intend to stay in the United States?",
            shortSummary = "State precise duration matching approved leave.",
            officerIntent = "Officers look for disproportionately long stays (e.g., 3-6 months), which suggest intent to work or abandon residence in Kenya.",
            sampleAnswer = "I will be in the U.S. for exactly 14 days, arriving on October 12th and returning on October 26th.",
            redFlags = listOf(
                "Saying 'Between 3 to 6 months' for a casual vacation.",
                "Saying 'I\\'ll stay as long as my visa allows'."
            ),
            tips = listOf(
                "A standard tourist vacation is typically 10 to 21 days.",
                "Make sure this matches the duration on your DS-160 exactly."
            )
        ),
        VisaQuestionItem(
            id = "tp-05",
            category = QuestionCategory.PURPOSE,
            question = "Have you already booked your flight tickets or hotels?",
            shortSummary = "Clarify planning without premature purchases.",
            officerIntent = "The U.S. Department of State explicitly advises NOT buying non-refundable tickets prior to visa approval. They want to see smart planning.",
            sampleAnswer = "I have researched flights on Kenya Airways and reserved a refundable hotel room in Orlando, but I have not bought non-refundable flights as advised by the embassy.",
            redFlags = listOf(
                "Saying you purchased non-refundable tickets to pressure the officer into approving.",
                "Having zero knowledge of typical airfare costs or hotel options."
            ),
            tips = listOf(
                "It is perfectly fine to have tentative or refundable reservations.",
                "Never claim you must be approved because you bought tickets."
            )
        ),
        VisaQuestionItem(
            id = "tp-06",
            category = QuestionCategory.PURPOSE,
            question = "Are you traveling alone or with someone else?",
            shortSummary = "Explain companions and their visa status.",
            officerIntent = "Assesses group dynamics and whether companions hold valid visas or are applying together.",
            sampleAnswer = "I am traveling alone for this sightseeing trip; my spouse will stay in Nairobi managing our family business.",
            redFlags = listOf(
                "Inconsistency regarding who is traveling.",
                "Concealing travel companions."
            ),
            tips = listOf(
                "If traveling alone, explain why dependents remain in Kenya (demonstrates ties).",
                "If traveling with a group or colleague, state their full names and roles."
            )
        ),
        VisaQuestionItem(
            id = "tp-07",
            category = QuestionCategory.PURPOSE,
            question = "What attractions or landmarks are on your itinerary?",
            shortSummary = "Mention 2-3 genuine places you look forward to.",
            officerIntent = "Verifies whether you are a genuine tourist who has researched your trip or someone memorizing generic talking points.",
            sampleAnswer = "In New York, I plan to visit the Metropolitan Museum of Art, Central Park, and the Statue of Liberty.",
            redFlags = listOf(
                "Naming attractions in California when your DS-160 says you are visiting New York.",
                "Total inability to name any tourist attraction."
            ),
            tips = listOf(
                "Mention landmarks relevant to your personal interests (e.g., museums, nature parks, architecture)."
            )
        ),
        VisaQuestionItem(
            id = "tp-08",
            category = QuestionCategory.PURPOSE,
            question = "Is this trip for personal leisure or do you have business meetings scheduled?",
            shortSummary = "Clearly distinguish B1 (business) from B2 (tourism).",
            officerIntent = "Determines whether B1 or B2 annotations are needed and checks for prohibited employment activities.",
            sampleAnswer = "This is purely personal leisure and tourism. I have no business meetings or professional engagements scheduled during this visit.",
            redFlags = listOf(
                "Confusing business consultations (allowed under B1) with doing productive work/freelancing (strictly prohibited).",
                "Claiming tourism while carrying a briefcase full of product samples."
            ),
            tips = listOf(
                "If it is business, state the conference name, hosting company, and your corporate role clearly."
            )
        ),
        VisaQuestionItem(
            id = "tp-09",
            category = QuestionCategory.PURPOSE,
            question = "If attending a conference or business meeting, what is the agenda?",
            shortSummary = "Explain your role and benefits to your Kenyan employer.",
            officerIntent = "Validates genuine commercial/professional necessity under B1 classification.",
            sampleAnswer = "I am representing my company at the Global Tech Summit in Chicago for 4 days to evaluate cloud security software for our Nairobi infrastructure.",
            redFlags = listOf(
                "Not knowing who organized the conference.",
                "Inability to explain why you were chosen to attend."
            ),
            tips = listOf(
                "Have your corporate invitation letter ready in your folder.",
                "Emphasize how attending benefits your employer in Kenya."
            )
        ),
        VisaQuestionItem(
            id = "tp-10",
            category = QuestionCategory.PURPOSE,
            question = "If you enjoy your stay, will you consider extending your trip?",
            shortSummary = "Firm, clear refusal due to home obligations.",
            officerIntent = "Classic test of nonimmigrant intent. The officer wants to verify you have non-negotiable obligations pulling you back to Kenya.",
            sampleAnswer = "No, I cannot extend. My approved leave ends on October 27th, and I have mandatory quarterly audits at my firm that require my physical presence.",
            redFlags = listOf(
                "Saying 'Maybe if I like it' or 'If my relatives invite me to stay longer'.",
                "Showing flexibility that suggests you have no binding commitments at home."
            ),
            tips = listOf(
                "Give a firm, polite 'No' followed by the specific work or family date requiring your presence."
            )
        ),

        // ─── 2. Employment & Career ─────────────────────────────────────
        VisaQuestionItem(
            id = "emp-01",
            category = QuestionCategory.EMPLOYMENT,
            question = "What do you do for a living in Kenya?",
            shortSummary = "Concise job title, company name, and core function.",
            officerIntent = "Assesses career stability, professional ties, and economic standing in Kenya.",
            sampleAnswer = "I am a Senior Network Engineer at Safaricom PLC in Nairobi, where I oversee fiber connectivity and network uptime for enterprise clients.",
            redFlags = listOf(
                "Giving an abstract title like 'Consultant' without explaining what you actually do.",
                "Inconsistency with the job title listed on your DS-160."
            ),
            tips = listOf(
                "Use plain English, avoiding overly dense corporate jargon.",
                "Name your company clearly and state your main responsibility in 10 seconds."
            )
        ),
        VisaQuestionItem(
            id = "emp-02",
            category = QuestionCategory.EMPLOYMENT,
            question = "How long have you been working with your current employer?",
            shortSummary = "State tenure to show career stability.",
            officerIntent = "Officers look for stability. Long tenure (2+ years) shows strong roots; very recent employment (<2 months) requires extra explanation.",
            sampleAnswer = "I have been with Safaricom for four and a half years, having joined in March 2022 after three years at Equity Bank.",
            redFlags = listOf(
                "Not knowing your start date.",
                "Having a resume of jumping jobs every 2 months with unexplained gaps."
            ),
            tips = listOf(
                "Mention steady career growth or promotions if applicable."
            )
        ),
        VisaQuestionItem(
            id = "emp-03",
            category = QuestionCategory.EMPLOYMENT,
            question = "What are your primary day-to-day duties at work?",
            shortSummary = "Authentic explanation of your actual daily work.",
            officerIntent = "Checks if you actually perform the job you claimed on paper or if your role was fabricated.",
            sampleAnswer = "I lead a team of 5 technicians. On a typical day, I monitor network incident escalations, manage infrastructure updates, and report to the IT Director.",
            redFlags = listOf(
                "Robotic recitation of a generic internet job description.",
                "Inability to describe what you did yesterday at work."
            ),
            tips = listOf(
                "Describe 2-3 concrete tasks you perform regularly."
            )
        ),
        VisaQuestionItem(
            id = "emp-04",
            category = QuestionCategory.EMPLOYMENT,
            question = "Has your employer officially approved your leave for this trip?",
            shortSummary = "Confirm formal leave approval and resumption date.",
            officerIntent = "Ensures you have an active job waiting for you upon return.",
            sampleAnswer = "Yes, my HR department approved 15 working days of annual leave from October 10th to October 30th. I have the signed leave authorization letter.",
            redFlags = listOf(
                "Saying 'I told my boss informally' or 'I will resign before traveling'.",
                "Conflicting leave dates."
            ),
            tips = listOf(
                "Keep the physical signed leave letter in your supporting document folder."
            )
        ),
        VisaQuestionItem(
            id = "emp-05",
            category = QuestionCategory.EMPLOYMENT,
            question = "What is your monthly or annual salary?",
            shortSummary = "State exact gross or net earnings matching payslips.",
            officerIntent = "Verifies financial viability and checks consistency with your DS-160 salary field and bank statements.",
            sampleAnswer = "My gross salary is 280,000 Kenyan Shillings per month, which is approximately 2,150 U.S. Dollars.",
            redFlags = listOf(
                "Hesitating or guessing your own salary.",
                "Stating an amount drastically different from your DS-160."
            ),
            tips = listOf(
                "Know both your KES figure and its rough USD equivalent.",
                "Ensure it matches the payslips and bank deposits in your documentation."
            )
        ),
        VisaQuestionItem(
            id = "emp-06",
            category = QuestionCategory.EMPLOYMENT,
            question = "If you are self-employed, what does your business do?",
            shortSummary = "Detail your company, client base, and physical location.",
            officerIntent = "Validates that you run a legitimate, registered enterprise with ongoing contracts and physical presence.",
            sampleAnswer = "I own an agricultural logistics company registered in 2019 in Nakuru. We operate 4 refrigerated trucks transporting fresh produce from Rift Valley farms to Nairobi.",
            redFlags = listOf(
                "Claiming a business with no physical location, zero employees, and no tax registration.",
                "Saying 'I do online business' without verifiable specifics."
            ),
            tips = listOf(
                "Mention your registration year, number of staff, and physical premises."
            )
        ),
        VisaQuestionItem(
            id = "emp-07",
            category = QuestionCategory.EMPLOYMENT,
            question = "Who will manage your business operations while you are away?",
            shortSummary = "Demonstrate operational delegation.",
            officerIntent = "Checks whether the business is real and can survive your temporary vacation without you abandoning it.",
            sampleAnswer = "My full-time operations manager and accountant will handle day-to-day logistics and client dispatches, as they do during my regular annual leave.",
            redFlags = listOf(
                "Saying 'The business will pause until I return' (suggests low viability).",
                "Saying you have nobody back home."
            ),
            tips = listOf(
                "Name your key staff members and their roles."
            )
        ),
        VisaQuestionItem(
            id = "emp-08",
            category = QuestionCategory.EMPLOYMENT,
            question = "Will your salary continue while you are away in the U.S.?",
            shortSummary = "Confirm standard paid leave.",
            officerIntent = "Verifies formal, permanent employment benefits versus precarious daily-wage labor.",
            sampleAnswer = "Yes, this is part of my standard 21 days of paid annual leave, so my salary will be credited as usual.",
            redFlags = listOf(
                "Saying you took unpaid leave or quit your job to travel."
            ),
            tips = listOf(
                "State that this is standard paid vacation time."
            )
        ),
        VisaQuestionItem(
            id = "emp-09",
            category = QuestionCategory.EMPLOYMENT,
            question = "What did you do before joining your present company?",
            shortSummary = "Demonstrate career continuity and progression.",
            officerIntent = "Reviews your historical career trajectory and background consistency.",
            sampleAnswer = "Before this, I worked for three years as an Associate Systems Administrator at Standard Chartered Bank in Nairobi.",
            redFlags = listOf(
                "Inability to remember prior employer names or dates.",
                "Discrepancy with the employment history in Section 3 of DS-160."
            ),
            tips = listOf(
                "Review your DS-160 previous employment entries before the interview."
            )
        ),
        VisaQuestionItem(
            id = "emp-10",
            category = QuestionCategory.EMPLOYMENT,
            question = "What projects or responsibilities await your return?",
            shortSummary = "Concrete professional tie requiring your return.",
            officerIntent = "Tests for binding professional commitments that necessitate your return to Kenya.",
            sampleAnswer = "I am the lead engineer for our Q4 core switch migration scheduled for November 15th, which requires my direct on-site management.",
            redFlags = listOf(
                "Saying 'Nothing special' or 'I have no urgent projects'."
            ),
            tips = listOf(
                "Mention a specific upcoming milestone, audit, client delivery, or team project."
            )
        ),

        // ─── 3. Finances & Funding ──────────────────────────────────────
        VisaQuestionItem(
            id = "fin-01",
            category = QuestionCategory.FINANCES,
            question = "Who is paying for your trip to the United States?",
            shortSummary = "Direct identification of self or sponsor.",
            officerIntent = "Officers want to know the financial foundation of your trip and whether the funds are legitimately yours.",
            sampleAnswer = "I am entirely self-sponsoring my trip using my personal accumulated savings from my engineering salary.",
            redFlags = listOf(
                "Hesitating or saying 'A friend of a friend will sponsor me'.",
                "Unclear or contradictory sponsorship statements."
            ),
            tips = listOf(
                "Self-funding from legitimate career savings is the strongest profile for tourist visas.",
                "If company sponsored, state 'My employer is covering all travel, lodging, and per diem expenses'."
            )
        ),
        VisaQuestionItem(
            id = "fin-02",
            category = QuestionCategory.FINANCES,
            question = "How much do you estimate this entire trip will cost?",
            shortSummary = "Realistic itemized estimate in USD.",
            officerIntent = "Tests whether you have realistic financial expectations regarding international travel and U.S. living costs.",
            sampleAnswer = "I have budgeted approximately $3,800, which includes $1,400 for return flights, $1,500 for hotel lodging, and $900 for food, local transport, and sightseeing.",
            redFlags = listOf(
                "Giving an unrealistic budget like '$500 for 3 weeks'.",
                "Having no idea how much hotels or flights cost in the U.S."
            ),
            tips = listOf(
                "Budget between $150 to $250 per day for lodging and expenses plus airfare.",
                "Know the total in both KES and USD."
            )
        ),
        VisaQuestionItem(
            id = "fin-03",
            category = QuestionCategory.FINANCES,
            question = "How long have you been saving for this trip?",
            shortSummary = "Show disciplined, gradual accumulation of funds.",
            officerIntent = "Checks for sudden, suspicious lump-sum deposits that may have been borrowed just to show in the embassy interview.",
            sampleAnswer = "I have been setting aside 40,000 shillings monthly into my dedicated savings account for the past 14 months specifically for this vacation.",
            redFlags = listOf(
                "A huge lump sum deposited into your account 3 days before the interview without clear paper trail.",
                "Borrowing money from a money lender to pad your bank balance."
            ),
            tips = listOf(
                "Consular officers examine transaction history, not just the ending balance.",
                "Consistent monthly salary credits over 6 months are crucial."
            )
        ),
        VisaQuestionItem(
            id = "fin-04",
            category = QuestionCategory.FINANCES,
            question = "If your trip is sponsored by a relative or employer, why are they paying?",
            shortSummary = "Establish plausible, credible motivation.",
            officerIntent = "Evaluates the sponsor’s legal relationship and authentic motivation to spend thousands of dollars on you.",
            sampleAnswer = "My employer is sponsoring me because I am representing the company at our international partner summit, which directly impacts our 2027 product rollout.",
            redFlags = listOf(
                "Distantly related 'sponsors' or casual acquaintances claiming to pay $5,000 for your vacation.",
                "No formal affidavit of support or official company guarantee."
            ),
            tips = listOf(
                "Strongest sponsors: Your formal employer (B1), parents (for young students), or your spouse."
            )
        ),
        VisaQuestionItem(
            id = "fin-05",
            category = QuestionCategory.FINANCES,
            question = "Do you own property, land, or significant assets in Kenya?",
            shortSummary = "Highlight immovable economic anchors.",
            officerIntent = "Assesses financial ties and physical roots that anchor you to Kenya under INA Section 214(b).",
            sampleAnswer = "Yes, I own a half-acre residential plot in Kiambu with a registered title deed, and I own my family vehicle.",
            redFlags = listOf(
                "Exaggerating or claiming assets you do not own.",
                "Throwing land documents at the officer when they haven't asked to inspect them."
            ),
            tips = listOf(
                "State ownership calmly.",
                "Keep title deeds or car logbooks in your folder, ready if requested."
            )
        ),
        VisaQuestionItem(
            id = "fin-06",
            category = QuestionCategory.FINANCES,
            question = "What would you do in case of a medical emergency in the U.S.?",
            shortSummary = "Mention international travel insurance coverage.",
            officerIntent = "Checks that you will not become a public charge or leave unpaid hospital bills in the U.S.",
            sampleAnswer = "I will purchase comprehensive travel medical insurance covering up to $100,000 through Jubilee Insurance, and I carry an international credit card for emergencies.",
            redFlags = listOf(
                "Saying 'I\\'ll just visit a free clinic' or 'God will protect me'."
            ),
            tips = listOf(
                "Mentioning international travel health insurance shows maturity and responsible planning."
            )
        ),
        VisaQuestionItem(
            id = "fin-07",
            category = QuestionCategory.FINANCES,
            question = "Can you show me your bank statement?",
            shortSummary = "Hand over documents only when specifically requested.",
            officerIntent = "Verifies liquidity, salary consistency, and absence of suspicious 'show money'.",
            sampleAnswer = "Certainly, here is my certified 6-month bank statement showing regular salary deposits and my current balance of 680,000 Kenyan Shillings.",
            redFlags = listOf(
                "Fumbling through an unorganized pile of papers.",
                "Submitting forged or altered statements (leads to permanent fraud bans)."
            ),
            tips = listOf(
                "Keep bank statements neatly tabbed in a transparent folder.",
                "Only hand documents across the counter when the officer asks for them."
            )
        ),
        VisaQuestionItem(
            id = "fin-08",
            category = QuestionCategory.FINANCES,
            question = "Does this trip represent a major percentage of your total net worth?",
            shortSummary = "Demonstrate that the vacation is financially prudent.",
            officerIntent = "If a trip costs 100% of an applicant’s life savings, officers suspect they are moving permanently rather than vacationing.",
            sampleAnswer = "No. The estimated $3,800 cost represents about a quarter of my liquid emergency savings, and my monthly salary will continue upon my return.",
            redFlags = listOf(
                "Spending your last shilling on a tourist vacation."
            ),
            tips = listOf(
                "Vacations should look proportional to your income bracket."
            )
        ),
        VisaQuestionItem(
            id = "fin-09",
            category = QuestionCategory.FINANCES,
            question = "Do you have additional income sources besides your primary job?",
            shortSummary = "Mention verified secondary income (rent, farming, investments).",
            officerIntent = "Assesses financial stability and diversification of economic ties to Kenya.",
            sampleAnswer = "In addition to my engineering salary, I earn approximately 45,000 shillings monthly from leasing commercial retail space in Thika.",
            redFlags = listOf(
                "Claiming undocumented cash income that cannot be substantiated."
            ),
            tips = listOf(
                "Only mention income that you can back up with lease agreements, statements, or bank records."
            )
        ),
        VisaQuestionItem(
            id = "fin-10",
            category = QuestionCategory.FINANCES,
            question = "What is your sponsor’s occupation and annual income?",
            shortSummary = "Accurate knowledge of sponsor credentials.",
            officerIntent = "Ensures the sponsor has genuine capability to fund you without suffering financial hardship.",
            sampleAnswer = "My sponsor is my father, who is a senior surgeon at Aga Khan University Hospital earning approximately 700,000 shillings monthly.",
            redFlags = listOf(
                "Not knowing how your sponsor earns their money or where they work."
            ),
            tips = listOf(
                "Know your sponsor’s job title, company name, and roughly how long they have worked there."
            )
        ),

        // ─── 4. Ties & Section 214(b) ───────────────────────────────────
        VisaQuestionItem(
            id = "tie-01",
            category = QuestionCategory.TIES_214B,
            question = "What ties bind you to return to Kenya at the end of your visit?",
            shortSummary = "Synthesize family, employment, economic, and community anchors.",
            officerIntent = "Under Section 214(b) of the INA, every applicant is legally presumed to have immigrant intent until they prove otherwise. This is the single most important test.",
            sampleAnswer = "My strong ties include my 4-year tenure as Lead Engineer at Safaricom, my wife and two young children who reside in Nairobi, our family home in Kiambu, and my ongoing community leadership at our local youth sports foundation.",
            redFlags = listOf(
                "Saying 'I love my country' or emotional statements without concrete economic or social anchors.",
                "Having no dependents, no property, and no stable employment."
            ),
            tips = listOf(
                "Combine at least three pillars: Professional (job/career), Family (spouse/children/parents), and Financial (property/assets)."
            )
        ),
        VisaQuestionItem(
            id = "tie-02",
            category = QuestionCategory.TIES_214B,
            question = "Why wouldn’t you look for a job or work while in the United States?",
            shortSummary = "Emphasize career fulfillment in Kenya and legal respect.",
            officerIntent = "Tests for economic migration motives and knowledge of U.S. visa regulations.",
            sampleAnswer = "Working in the U.S. on a B2 visa is illegal, and I have no interest in doing so. I have spent seven years building a respected, well-compensated career in Kenya that I value and plan to grow further.",
            redFlags = listOf(
                "Saying 'If a great offer comes along, maybe I\\'ll consider it'.",
                "Showing curiosity about U.S. wages."
            ),
            tips = listOf(
                "Express genuine pride in your career achievements in Kenya."
            )
        ),
        VisaQuestionItem(
            id = "tie-03",
            category = QuestionCategory.TIES_214B,
            question = "What are your long-term career goals in Kenya over the next 3 to 5 years?",
            shortSummary = "Articulate future promotion or business expansion plans.",
            officerIntent = "Officers want to see a future roadmap firmly situated in your home country.",
            sampleAnswer = "I am on track for promotion to Head of Infrastructure at our firm by 2028, and I am currently completing an Executive MBA at Strathmore Business School.",
            redFlags = listOf(
                "Saying you have no plans or that you want to move abroad."
            ),
            tips = listOf(
                "Mention ongoing studies, professional certifications, or career milestones in Kenya."
            )
        ),
        VisaQuestionItem(
            id = "tie-04",
            category = QuestionCategory.TIES_214B,
            question = "Do you have elderly parents or family members depending on you in Kenya?",
            shortSummary = "Highlight family caretaking responsibilities.",
            officerIntent = "Demonstrates personal and moral obligations requiring your return.",
            sampleAnswer = "Yes, my elderly mother lives with us in Nairobi, and I am her primary caregiver coordinating her medical care and daily household support.",
            redFlags = listOf(
                "Fabricating family situations that contradict previous visa forms."
            ),
            tips = listOf(
                "Family care duties are recognized as genuine personal ties."
            )
        ),
        VisaQuestionItem(
            id = "tie-05",
            category = QuestionCategory.TIES_214B,
            question = "What exact date do you return to your office in Nairobi?",
            shortSummary = "State precise return date.",
            officerIntent = "Checks if your timeline is realistic and concrete.",
            sampleAnswer = "I land back at JKIA on Sunday, October 29th, and I report to my desk at 8:00 AM on Monday, October 30th.",
            redFlags = listOf(
                "Saying 'Sometime next month or maybe later'."
            ),
            tips = listOf(
                "Giving a specific date and time conveys certainty and preparation."
            )
        ),
        VisaQuestionItem(
            id = "tie-06",
            category = QuestionCategory.TIES_214B,
            question = "Are you enrolled in university or any higher education program in Kenya?",
            shortSummary = "Mention ongoing degree or coursework.",
            officerIntent = "Active enrollment is one of the strongest ties for young applicants.",
            sampleAnswer = "Yes, I am in my third year of a Bachelor of Commerce degree at the University of Nairobi, and our semester classes resume on November 3rd.",
            redFlags = listOf(
                "Traveling right before final exams with no explanation."
            ),
            tips = listOf(
                "Bring your official student ID and letter of enrollment from the registrar."
            )
        ),
        VisaQuestionItem(
            id = "tie-07",
            category = QuestionCategory.TIES_214B,
            question = "Do you hold any civic, church, or community leadership roles?",
            shortSummary = "Highlight social and community integration.",
            officerIntent = "People deeply woven into their local community are statistically less likely to abandon their homeland.",
            sampleAnswer = "I serve as the treasurer of our neighborhood residents association and mentor junior coders on weekends at the Nairobi iHub.",
            redFlags = listOf(
                "Over-embellishing minor activities into grand titles."
            ),
            tips = listOf(
                "Keep it modest, authentic, and factual."
            )
        ),
        VisaQuestionItem(
            id = "tie-08",
            category = QuestionCategory.TIES_214B,
            question = "What guarantees can you give me that you will come back?",
            shortSummary = "Confidently recap the non-negotiable pillars of your life.",
            officerIntent = "A direct challenge question to see how you respond under pressure.",
            sampleAnswer = "My entire life, family, and investments are here in Kenya. My wife, children, pension, property, and career are all based here. I have no reason or desire to live anywhere else.",
            redFlags = listOf(
                "Getting defensive or emotional ('I swear on the Bible').",
                "Arguing with the officer."
            ),
            tips = listOf(
                "Stay completely calm. Answer with firm, steady conviction based on facts, not emotions."
            )
        ),
        VisaQuestionItem(
            id = "tie-09",
            category = QuestionCategory.TIES_214B,
            question = "Why did you choose to visit the U.S. instead of neighboring African countries?",
            shortSummary = "Explain why the U.S. is the destination for this milestone.",
            officerIntent = "Probes the motivation behind traveling thousands of miles for a vacation.",
            sampleAnswer = "I have already visited Rwanda, Uganda, and South Africa on holiday. Visiting New York and seeing the Smithsonian museums has been a personal milestone on my travel bucket list for years.",
            redFlags = listOf(
                "Saying 'Because America is better than Africa'."
            ),
            tips = listOf(
                "Frame the U.S. as a specific travel destination of personal curiosity, not an escape."
            )
        ),
        VisaQuestionItem(
            id = "tie-10",
            category = QuestionCategory.TIES_214B,
            question = "If you were refused today, what would you do?",
            shortSummary = "Demonstrate respect for the law and ongoing focus on your Kenyan life.",
            officerIntent = "Measures your composure and desperation level. Desperation is a major warning sign of immigrant intent.",
            sampleAnswer = "I would respect your decision, return to my work at Safaricom on Monday, and reapply in the future when my travel circumstances or career profile has evolved further.",
            redFlags = listOf(
                "Begging, crying, arguing, or asking 'Why are you treating me like this?'.",
                "Showing panic or desperation."
            ),
            tips = listOf(
                "Demonstrating that a refusal will not derail your life shows you have a stable, fulfilling life in Kenya."
            )
        ),

        // ─── 5. Family & U.S. Relatives ─────────────────────────────────
        VisaQuestionItem(
            id = "fam-01",
            category = QuestionCategory.FAMILY_RELATIVES,
            question = "Are you married and do you have any children?",
            shortSummary = "State marital status and children accurately.",
            officerIntent = "Immediate family remaining at home is one of the strongest natural ties.",
            sampleAnswer = "Yes, I have been married for five years to my wife Sarah, and we have a 3-year-old daughter. Both will remain home in Nairobi.",
            redFlags = listOf(
                "Hiding a marriage or claiming to be single when married on DS-160."
            ),
            tips = listOf(
                "State their names and ages naturally."
            )
        ),
        VisaQuestionItem(
            id = "fam-02",
            category = QuestionCategory.FAMILY_RELATIVES,
            question = "Why isn’t your spouse traveling with you on this trip?",
            shortSummary = "Legitimate explanation (job, school, cost, child care).",
            officerIntent = "Checks whether the spouse is unable to get leave or if this is a planned reconnaissance trip for family migration.",
            sampleAnswer = "My wife works as a high school teacher and the school term is currently in session, so she cannot take leave at this time.",
            redFlags = listOf(
                "Saying 'She doesn\\'t want to' or giving conflicting reasons."
            ),
            tips = listOf(
                "Mention professional or educational commitments that anchor your spouse locally."
            )
        ),
        VisaQuestionItem(
            id = "fam-03",
            category = QuestionCategory.FAMILY_RELATIVES,
            question = "Do you have any relatives living in the United States?",
            shortSummary = "Absolute 100% honesty matching DS-160 records.",
            officerIntent = "Officers cross-check consular databases and family visa petitions. Lying about relatives is grounds for an immediate permanent fraud refusal (INA 212(a)(6)(C)(i)).",
            sampleAnswer = "Yes, I have a cousin who lives in Dallas, Texas. She is a registered nurse and a permanent resident.",
            redFlags = listOf(
                "Saying 'No' when you have siblings or parents in the U.S. who petitioned for you or claimed you on their applications.",
                "Concealing relatives out of fear that having family in the U.S. will hurt your chances."
            ),
            tips = listOf(
                "Always disclose relatives truthfully. Having relatives in the U.S. is not a crime; concealing them is an unforgivable red flag."
            )
        ),
        VisaQuestionItem(
            id = "fam-04",
            category = QuestionCategory.FAMILY_RELATIVES,
            question = "What legal immigration status do your relatives hold in the U.S.?",
            shortSummary = "Accurate knowledge of relative status (LPR, Citizen, H1B, F1).",
            officerIntent = "Tests whether your relatives adjusted status legally or entered illegally / overstayed visas.",
            sampleAnswer = "My uncle is a naturalized U.S. citizen who has lived in Maryland for over 15 years and works for the county public school system.",
            redFlags = listOf(
                "Saying 'I don\\'t know their legal status'.",
                "Relatives who traveled on tourist visas and never returned."
            ),
            tips = listOf(
                "Know whether your relative is on an F1 student visa, H1B, Green Card, or U.S. citizenship."
            )
        ),
        VisaQuestionItem(
            id = "fam-05",
            category = QuestionCategory.FAMILY_RELATIVES,
            question = "Will you be staying with your relatives during your visit?",
            shortSummary = "Clarify lodging and distance from relatives.",
            officerIntent = "If visiting Orlando for tourism, why stay with a cousin in Seattle? Checks geographic plausibility.",
            sampleAnswer = "No, my trip is focused on sightseeing in New York and Orlando, so I will stay in hotels. I might have dinner with my cousin if our schedules align.",
            redFlags = listOf(
                "Claiming tourist sightseeing while staying 2 months in a remote suburb with relatives."
            ),
            tips = listOf(
                "Keep your lodging consistent with your tourist itinerary."
            )
        ),
        VisaQuestionItem(
            id = "fam-06",
            category = QuestionCategory.FAMILY_RELATIVES,
            question = "Where do your parents and siblings currently reside?",
            shortSummary = "Confirm family center of gravity in Kenya.",
            officerIntent = "Checks if your entire immediate family has already relocated to the United States.",
            sampleAnswer = "My parents live in Nakuru where they run a dairy farm, and my two younger brothers both work in Nairobi.",
            redFlags = listOf(
                "Your entire family (parents, all siblings) already living in the U.S."
            ),
            tips = listOf(
                "Highlighting that your extended family is deeply rooted in Kenya supports your ties."
            )
        ),
        VisaQuestionItem(
            id = "fam-07",
            category = QuestionCategory.FAMILY_RELATIVES,
            question = "Did your relative in the U.S. write an invitation letter for you?",
            shortSummary = "Explain status of invitation letter.",
            officerIntent = "Many applicants believe an invitation letter guarantees a visa. Officers give little weight to letters and evaluate the applicant’s own merits.",
            sampleAnswer = "My cousin provided an informal letter of invitation confirming I can stay with her for a weekend, which I have here, but I am funding my own travel.",
            redFlags = listOf(
                "Believing a Congressman or relative’s letter 'forces' the embassy to give you a visa.",
                "Relying entirely on an invitation letter without personal financial ties."
            ),
            tips = listOf(
                "Invitation letters are optional and secondary. Your own job, income, and ties carry 90% of the weight."
            )
        ),
        VisaQuestionItem(
            id = "fam-08",
            category = QuestionCategory.FAMILY_RELATIVES,
            question = "Have any of your family members ever filed an immigrant petition for you?",
            shortSummary = "Truthful disclosure of I-130 / immigrant petitions.",
            officerIntent = "Checks if you have pending immigrant intent on official USCIS files.",
            sampleAnswer = "No, no family member has ever filed an immigrant petition on my behalf.",
            redFlags = listOf(
                "Saying 'No' when your sibling filed an I-130 petition for you 5 years ago (consular officers see this instantly on their screen!)."
            ),
            tips = listOf(
                "If an I-130 was filed, answer: 'Yes, my brother filed an I-130 in 2018, but the priority date is 10 years away and I am strictly making a temporary tourist visit now.'"
            )
        ),
        VisaQuestionItem(
            id = "fam-09",
            category = QuestionCategory.FAMILY_RELATIVES,
            question = "Do you have close friends or a romantic partner in the U.S.?",
            shortSummary = "Be transparent about romantic partners.",
            officerIntent = "Visiting a U.S. citizen fiancé/fiancée or romantic partner on a tourist visa often leads to adjustment of status and marriage, triggering high scrutiny.",
            sampleAnswer = "I have university friends living in Atlanta whom I communicate with on LinkedIn, but no romantic partner.",
            redFlags = listOf(
                "Concealing an American partner when traveling to meet them.",
                "Using a B2 tourist visa to enter and marry a U.S. citizen without intending to return."
            ),
            tips = listOf(
                "If visiting a romantic partner, be honest, but show exceptionally strong non-negotiable ties requiring you to return to Kenya."
            )
        ),
        VisaQuestionItem(
            id = "fam-10",
            category = QuestionCategory.FAMILY_RELATIVES,
            question = "How often do you communicate with your relatives in the U.S.?",
            shortSummary = "Normal, genuine communication cadence.",
            officerIntent = "Gauges whether you are genuinely close or using distant relatives as an excuse to enter.",
            sampleAnswer = "We chat on WhatsApp once every couple of weeks, mostly during holidays and family milestones.",
            redFlags = listOf(
                "Claiming your sponsor is a 'close relative' whose last name or workplace you don't even know."
            ),
            tips = listOf(
                "Keep it honest and proportional."
            )
        ),

        // ─── 6. Travel History & Prior Visas ────────────────────────────
        VisaQuestionItem(
            id = "th-01",
            category = QuestionCategory.TRAVEL_HISTORY,
            question = "Have you ever traveled outside of Kenya before?",
            shortSummary = "Summarize past international travel.",
            officerIntent = "A proven track record of international travel and punctual return establishes compliance with immigration laws.",
            sampleAnswer = "Yes, I have traveled to South Africa for a week in 2022, the United Arab Emirates for vacation in 2023, and Rwanda for a conference earlier this year.",
            redFlags = listOf(
                "Lying about countries you haven't visited.",
                "Having a fresh, completely blank passport with no prior travel while claiming to be an avid international tourist."
            ),
            tips = listOf(
                "List countries chronologically with dates.",
                "Highlight that you returned strictly on time on every trip."
            )
        ),
        VisaQuestionItem(
            id = "th-02",
            category = QuestionCategory.TRAVEL_HISTORY,
            question = "If you have never traveled abroad before, why is the U.S. your first destination?",
            shortSummary = "Explain why the U.S. is the chosen first overseas journey.",
            officerIntent = "First-time travelers face higher scrutiny under 214(b). The officer wants to ensure this is not an economic migration attempt.",
            sampleAnswer = "Now that I have reached senior management at my firm and accumulated sufficient savings, I wanted to fulfill my long-standing childhood dream of visiting the NASA Kennedy Space Center in Florida.",
            redFlags = listOf(
                "Acting like international travel is trivial when you have never left your home province.",
                "Inability to justify the high expense for a first-time trip."
            ),
            tips = listOf(
                "Focus on the specific attraction or milestone that justified the investment."
            )
        ),
        VisaQuestionItem(
            id = "th-03",
            category = QuestionCategory.TRAVEL_HISTORY,
            question = "Did you always return to Kenya on time during your previous trips abroad?",
            shortSummary = "Confirm 100% immigration compliance.",
            officerIntent = "Past compliance is the single best predictor of future compliance.",
            sampleAnswer = "Yes, without exception. On all my trips to Dubai, South Africa, and Kigali, I departed several days before my authorized entry permit expired.",
            redFlags = listOf(
                "Having an overstay or deportation stamp in your passport from another country."
            ),
            tips = listOf(
                "Emphasize your clean immigration history."
            )
        ),
        VisaQuestionItem(
            id = "th-04",
            category = QuestionCategory.TRAVEL_HISTORY,
            question = "Have you ever applied for a U.S. visa before?",
            shortSummary = "Truthful disclosure of all prior applications.",
            officerIntent = "Consular databases retain every prior application and fingerprint indefinitely. Denying a past application is an immediate denial for misrepresentation.",
            sampleAnswer = "Yes, I applied once before in June 2021 for a tourist visa.",
            redFlags = listOf(
                "Saying 'Never' when you were previously interviewed or denied."
            ),
            tips = listOf(
                "State the year and visa type straightforwardly."
            )
        ),
        VisaQuestionItem(
            id = "th-05",
            category = QuestionCategory.TRAVEL_HISTORY,
            question = "If you were previously refused under 214(b), what has changed since your last interview?",
            shortSummary = "Detail concrete, verifiable improvements in circumstances.",
            officerIntent = "Officers will NOT overturn a prior refusal unless your circumstances have materially changed since the last interview.",
            sampleAnswer = "When I applied in 2021, I was a recent graduate with only 6 months on the job earning entry-level pay. Since then, I have been promoted to Senior Engineer, my salary has tripled, I have completed four years of continuous tenure, and I have accumulated significant personal savings and property.",
            redFlags = listOf(
                "Saying 'Nothing changed, the last officer was just unfair'.",
                "Reapplying 2 weeks after a refusal with zero changes in life situation."
            ),
            tips = listOf(
                "Highlight 3 concrete changes: higher salary, longer stable tenure, new assets, or marriage."
            )
        ),
        VisaQuestionItem(
            id = "th-06",
            category = QuestionCategory.TRAVEL_HISTORY,
            question = "Have you ever had a visa canceled, revoked, or refused by any other country?",
            shortSummary = "Honest disclosure of UK, Schengen, or Canadian refusals.",
            officerIntent = "The U.S. shares intelligence with Five Eyes nations (UK, Canada, Australia, New Zealand) and can see foreign refusals.",
            sampleAnswer = "Yes, in 2019 I had a UK visa application refused due to incomplete bank documentation. I resolved the issue and was subsequently granted a visa for my subsequent travels.",
            redFlags = listOf(
                "Hiding a Canadian, UK, or Schengen visa refusal."
            ),
            tips = listOf(
                "Explain foreign refusals briefly without bitterness and highlight subsequent travel compliance."
            )
        ),
        VisaQuestionItem(
            id = "th-07",
            category = QuestionCategory.TRAVEL_HISTORY,
            question = "Do you hold active visas for any other countries in your passport?",
            shortSummary = "Mention current valid visas.",
            officerIntent = "A passport with active visas from countries like the UK, Canada, or Schengen states signals high credibility.",
            sampleAnswer = "Yes, I currently hold a valid 2-year multiple-entry visa for the United Kingdom and a South African tourist visa.",
            redFlags = listOf(
                "Claiming visas you do not possess."
            ),
            tips = listOf(
                "Show the physical pages with the valid visas if the officer flips through your passport."
            )
        ),
        VisaQuestionItem(
            id = "th-08",
            category = QuestionCategory.TRAVEL_HISTORY,
            question = "Have you ever traveled to the United States before, and how long did you stay?",
            shortSummary = "Confirm past U.S. visits and strict departure before I-94 expiration.",
            officerIntent = "Checks whether you complied with your authorized stay on your last U.S. trip or stayed for 5 months on a 2-week claimed visit.",
            sampleAnswer = "Yes, I visited in July 2018 for a 10-day vacation in Washington D.C., departing on day 10 as scheduled.",
            redFlags = listOf(
                "Telling the officer previously you would stay 2 weeks, but remaining for 5.5 months (breaks trust for future visas)."
            ),
            tips = listOf(
                "Always adhere to the length of stay you originally declared to consular officers."
            )
        ),
        VisaQuestionItem(
            id = "th-09",
            category = QuestionCategory.TRAVEL_HISTORY,
            question = "Have you ever lost a passport or had one stolen?",
            shortSummary = "Report passport replacement history truthfully.",
            officerIntent = "Checks whether lost passports were used by impostors or reported properly to police.",
            sampleAnswer = "No, this is my second standard passport, which was renewed normally upon expiration of my previous booklet in 2022.",
            redFlags = listOf(
                "Unreported lost passports or contradictory police statements."
            ),
            tips = listOf(
                "Bring your old expired passports to the interview to demonstrate travel history."
            )
        ),
        VisaQuestionItem(
            id = "th-10",
            category = QuestionCategory.TRAVEL_HISTORY,
            question = "Did someone else help you fill out your DS-160 application form?",
            shortSummary = "Confirm personal review and authorization.",
            officerIntent = "Checks if an 'agent' or 'consultant' filled your form with fake details that you are unaware of.",
            sampleAnswer = "I completed the DS-160 form entirely by myself, and I personally reviewed and verified every single answer before submitting.",
            redFlags = listOf(
                "Saying 'A cyber cafe agent did it, I don\\'t know what they wrote'.",
                "Having false employment or fake sponsor details inserted by an agent."
            ),
            tips = listOf(
                "You are legally responsible for everything on your DS-160, regardless of who typed it."
            )
        ),

        // ─── 7. Tricky Traps & Red Flags ────────────────────────────────
        VisaQuestionItem(
            id = "rf-01",
            category = QuestionCategory.RED_FLAGS,
            question = "What if you meet someone in the U.S. and decide to get married?",
            shortSummary = "Firm rejection of impulsive immigration.",
            officerIntent = "A classic trap question to see if marriage or romantic relocation is in your subconscious plan.",
            sampleAnswer = "That is not going to happen. I am happily married with a family in Kenya, and I am visiting solely for a planned 14-day holiday.",
            redFlags = listOf(
                "Laughing and saying 'Who knows! Love is blind!' or 'If it\\'s God\\'s plan'.",
                "Treating marriage as a viable visa pathway."
            ),
            tips = listOf(
                "Answer with zero hesitation, smiling politely but firmly dismissing the hypothesis."
            )
        ),
        VisaQuestionItem(
            id = "rf-02",
            category = QuestionCategory.RED_FLAGS,
            question = "Why shouldn’t I think you will just work informally to pay for your vacation?",
            shortSummary = "State career pride and sufficient savings.",
            officerIntent = "Probing whether your savings are genuine or if you plan to work 'under the table' in restaurants or construction.",
            sampleAnswer = "Because I earn a senior salary of 280,000 shillings every month, have over 700,000 shillings in liquid savings, and risking my legal standing and professional reputation for illegal casual work would be irrational.",
            redFlags = listOf(
                "Becoming angry or offended.",
                "Defensive replies like 'Do I look like a poor person?'."
            ),
            tips = listOf(
                "Use logical economic numbers to prove that working illegally makes no sense for your life."
            )
        ),
        VisaQuestionItem(
            id = "rf-03",
            category = QuestionCategory.RED_FLAGS,
            question = "You seem nervous. Why are you nervous if you have nothing to hide?",
            shortSummary = "Acknowledge nervousness naturally while staying focused.",
            officerIntent = "Tests emotional control under stress. Officers know normal people get nervous, but extreme panic signals deception.",
            sampleAnswer = "I am naturally a bit nervous because this interview is very important to me, but I am excited about my travel plans and happy to answer all your questions.",
            redFlags = listOf(
                "Refusing to make eye contact.",
                "Sweating profusely, trembling uncontrollably, or becoming combative."
            ),
            tips = listOf(
                "It is 100% normal to acknowledge nervousness with a calm smile. Take a deep breath and keep answers concise."
            )
        ),
        VisaQuestionItem(
            id = "rf-04",
            category = QuestionCategory.RED_FLAGS,
            question = "Your travel dates are in 3 days. Why did you wait until the last minute?",
            shortSummary = "Explain visa appointment booking constraints.",
            officerIntent = "Checks whether this was a genuine plan or a rushed emergency escape.",
            sampleAnswer = "I have had this travel plan for six months, but this was the earliest available interview appointment date open on the embassy scheduling portal.",
            redFlags = listOf(
                "Saying you decided to travel yesterday on a whim."
            ),
            tips = listOf(
                "Consular officers know wait times are long; referencing appointment availability is completely valid."
            )
        ),
        VisaQuestionItem(
            id = "rf-05",
            category = QuestionCategory.RED_FLAGS,
            question = "Why do you need a 5-year or 10-year visa for a 2-week vacation?",
            shortSummary = "Clarify that you are only asking for approval for this trip.",
            officerIntent = "Clarifies that applicants do not choose visa validity; the embassy issues reciprocity validity.",
            sampleAnswer = "I am only requesting permission for my planned two-week vacation. Whatever visa validity the consular section grants under reciprocity is at your discretion.",
            redFlags = listOf(
                "Insisting on a 10-year visa because you paid the fee.",
                "Demanding multi-entry privileges."
            ),
            tips = listOf(
                "Never argue about visa validity; focus exclusively on this specific trip."
            )
        ),
        VisaQuestionItem(
            id = "rf-06",
            category = QuestionCategory.RED_FLAGS,
            question = "Do you know anyone in the U.S. who could help you find a job?",
            shortSummary = "Clear, absolute refusal.",
            officerIntent = "Direct trap testing intent to violate visa status.",
            sampleAnswer = "No, and I am not looking for employment in the U.S. My career is based in Nairobi where I intend to continue advancing.",
            redFlags = listOf(
                "Saying 'Maybe my friend who works in tech could refer me'."
            ),
            tips = listOf(
                "A B1/B2 visa strictly forbids employment. Any hint of job-seeking leads to an immediate 214(b) refusal."
            )
        ),
        VisaQuestionItem(
            id = "rf-07",
            category = QuestionCategory.RED_FLAGS,
            question = "Why is your bank balance much higher this month than earlier months?",
            shortSummary = "Provide authentic explanation for any recent deposit.",
            officerIntent = "Tests for borrowed 'show money' deposited right before an interview.",
            sampleAnswer = "That deposit of 300,000 shillings in August was our company annual performance bonus, which is documented on my August payslip.",
            redFlags = listOf(
                "Inability to explain where a large lump sum came from.",
                "Borrowing money from a family member just to print a statement and returning it the next day."
            ),
            tips = listOf(
                "If you have large deposits, bring the source documentation (bonus letter, land sale agreement, dividend voucher)."
            )
        ),
        VisaQuestionItem(
            id = "rf-08",
            category = QuestionCategory.RED_FLAGS,
            question = "I am seeing some discrepancies in your application. Can you explain?",
            shortSummary = "Stay calm, listen closely, and explain without defensiveness.",
            officerIntent = "Tests honesty and clarity when confronted with conflicting information.",
            sampleAnswer = "I would be glad to clarify. Which specific detail or field would you like me to walk through?",
            redFlags = listOf(
                "Panicking and changing your story three times.",
                "Blaming a third-party cyber cafe agent."
            ),
            tips = listOf(
                "Ask politely which field the officer is referring to, then state the factual truth calmly."
            )
        )
    )

    val guides: List<VisaGuideItem> = listOf(
        VisaGuideItem(
            id = "guide-214b",
            title = "Understanding INA Section 214(b)",
            category = "Legal Foundations",
            readTime = "4 min read",
            summary = "The legal cornerstone of U.S. visa decisions: why every applicant is presumed to be an immigrant until proven otherwise.",
            keyPoints = listOf(
                "By federal law, the officer MUST assume you intend to abandon your home country.",
                "The burden of proof is 100% on the applicant, not the officer.",
                "Refusal under 214(b) is not permanent; it means ties were insufficient today.",
                "Documents rarely overcome weak verbal answers; your spoken confidence matters most."
            ),
            content = listOf(
                "Section 214(b) of the United States Immigration and Nationality Act (INA) is the most common reason for visa refusals worldwide.",
                "The law states: 'Every alien shall be presumed to be an immigrant until he establishes to the satisfaction of the consular officer, at the time of application for a visa, that he is entitled to a nonimmigrant status.'",
                "In simple terms: the officer starts the interview assuming you want to move to America permanently. Your task in the 90-second interview is to demonstrate that you have strong, unbreakable ties—employment, family, assets, and future—that will compel you to leave the United States at the end of your visit."
            )
        ),
        VisaGuideItem(
            id = "guide-golden-rules",
            title = "The 10 Golden Rules for Your Visa Interview",
            category = "Interview Strategy",
            readTime = "5 min read",
            summary = "Practical principles to follow at the consular window to maximize your chances of approval.",
            keyPoints = listOf(
                "Answer the specific question asked in 1-2 concise sentences.",
                "Never offer unrequested documents across the counter.",
                "Maintain natural eye contact through the glass.",
                "Never memorize a script word-for-word; be natural and conversational."
            ),
            content = listOf(
                "Rule 1: Keep answers under 30 seconds. Consular officers have only 2-3 minutes per applicant. Long, rambling answers trigger suspicion.",
                "Rule 2: Speak directly and loudly. The sound system through the ballistic glass window can be muffled. Speak with clear projection.",
                "Rule 3: Be 100% truthful. Inconsistencies between your DS-160 and your verbal answers result in immediate denials.",
                "Rule 4: Do not shove documents through the window. Consular officers decide visas based on your verbal credibility. Only hand over a document if the officer says: 'May I see your bank statement / letter?'",
                "Rule 5: Frame ties as your future. Don’t just talk about what you own today; talk about your upcoming projects, promotions, and ongoing family milestones in Kenya."
            )
        ),
        VisaGuideItem(
            id = "guide-documents",
            title = "Documents Checklist: Mandatory vs Supporting",
            category = "Checklist",
            readTime = "3 min read",
            summary = "What you MUST bring to the embassy versus what you should keep organized in your folder.",
            keyPoints = listOf(
                "Mandatory: Valid Passport, DS-160 confirmation page, Appointment confirmation letter, 2x2 inch color photo.",
                "Supporting: 6-month certified bank statements, Employer leave approval letter, 3 recent payslips, Property title deeds or vehicle logbooks.",
                "Keep documents organized in clear plastic sleeves so you can retrieve them in 3 seconds."
            ),
            content = listOf(
                "Only four items are strictly mandatory to enter the embassy: your passport (valid at least 6 months beyond intended stay), your printed DS-160 confirmation sheet with barcode, your appointment confirmation letter, and a compliant 2x2 inch passport photograph.",
                "Supporting documents should be organized in an accordion file: Employment letter on company letterhead stating salary, tenure, and approved leave dates; 6 months of official bank statements; property or land titles; business registration certificates (if self-employed).",
                "Remember: you might not be asked for a single supporting document. Over 70% of visa approvals occur purely based on the DS-160 and the applicant’s verbal interview!"
            )
        ),
        VisaGuideItem(
            id = "guide-day-of",
            title = "Day of the Interview: Step-by-Step Timeline",
            category = "Walkthrough",
            readTime = "4 min read",
            summary = "What actually happens inside the U.S. Embassy from security checkpoint to the final decision.",
            keyPoints = listOf(
                "Arrive only 15-20 minutes before your scheduled appointment time.",
                "Do not bring laptops, large bags, food, or smartwatches.",
                "Step 1: Outer queue & document check.",
                "Step 2: Airport-style security screening.",
                "Step 3: Biometric fingerprint scanning.",
                "Step 4: The Consular Window Interview."
            ),
            content = listOf(
                "Step 1: Arrival. Arriving 2 hours early does not get you inside faster and only causes fatigue. Arrive 15-20 minutes ahead.",
                "Step 2: Security. Mobile phones, smart electronics, flash drives, liquids, and large backpacks are prohibited. Travel light with only your document folder.",
                "Step 3: Biometrics. Inside the waiting hall, you will be directed to a window for fingerprint scanning (four left fingers, four right fingers, and two thumbs).",
                "Step 4: Window Interview. You will stand in front of a counter facing the consular officer through a window. Smile, say 'Good morning Officer', and listen carefully to each question. At the end, if approved, the officer will keep your passport and hand you a colored slip explaining passport collection via courier."
            )
        )
    )

    fun searchQuestions(query: String, category: QuestionCategory = QuestionCategory.ALL): List<VisaQuestionItem> {
        val trimmed = query.trim().lowercase()
        return questions.filter { item ->
            val matchesCategory = category == QuestionCategory.ALL || item.category == category
            val matchesQuery = trimmed.isEmpty() ||
                item.question.lowercase().contains(trimmed) ||
                item.sampleAnswer.lowercase().contains(trimmed) ||
                item.officerIntent.lowercase().contains(trimmed) ||
                item.shortSummary.lowercase().contains(trimmed)
            matchesCategory && matchesQuery
        }
    }
}
