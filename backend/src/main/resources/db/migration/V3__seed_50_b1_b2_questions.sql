-- =============================================================================
-- Migration V3: Seed 50+ Initial B1/B2 Interview Questions Across All 10 Categories
-- =============================================================================

INSERT INTO questions (id, visa_type_id, category, question_text, difficulty, follow_up_hint, evaluation_criteria) VALUES
-- TRAVEL_PURPOSE (1-6)
('q-tp-01', 'vt-b1b2', 'TRAVEL_PURPOSE', 'What is the purpose of your trip to the United States?', 1, 'Check if dates and specific destination match DS-160', 'Clear, direct answer stating tourist sights, business conference, or family visit without rambling.'),
('q-tp-02', 'vt-b1b2', 'TRAVEL_PURPOSE', 'Why are you choosing to travel at this specific time of year?', 2, 'Ask about work leave or seasonal event', 'Specific reason such as approved annual leave, wedding date, or trade show schedule.'),
('q-tp-03', 'vt-b1b2', 'TRAVEL_PURPOSE', 'Which cities or states are you planning to visit during your stay?', 1, 'Follow up on planned activities in each city', 'Logical itinerary that matches trip duration and budget.'),
('q-tp-04', 'vt-b1b2', 'TRAVEL_PURPOSE', 'Is this trip for personal leisure, or do you have business meetings scheduled?', 2, 'Request business invitation letter if business', 'Distinction between B1 (business) and B2 (tourism/pleasure) activities.'),
('q-tp-05', 'vt-b1b2', 'TRAVEL_PURPOSE', 'Have you made any flight or hotel reservations already?', 2, 'Clarify that non-refundable bookings are not required before visa approval', 'Demonstrates planning without over-committing prematurely.'),
('q-tp-06', 'vt-b1b2', 'TRAVEL_PURPOSE', 'What major landmarks or attractions are on your travel itinerary?', 1, 'Ask why those specific places appeal to the applicant', 'Realistic tourist destinations fitting the duration and budget.'),

-- TRIP_DURATION (7-11)
('q-td-07', 'vt-b1b2', 'TRIP_DURATION', 'How long do you intend to stay in the United States?', 1, 'Compare with permitted annual leave from work', 'Plausible duration (e.g., 2-3 weeks for typical vacation) consistent with job ties.'),
('q-td-08', 'vt-b1b2', 'TRIP_DURATION', 'Why do you need two weeks for this particular visit?', 2, 'Check breakdown of time per city', 'Plausible breakdown of days across sightseeing and relaxation.'),
('q-td-09', 'vt-b1b2', 'TRIP_DURATION', 'If your trip goes well, would you consider extending your stay beyond the planned date?', 3, 'Assess understanding of nonimmigrant intent and return obligations', 'Definite refusal to overstay due to mandatory commitments in Kenya.'),
('q-td-10', 'vt-b1b2', 'TRIP_DURATION', 'Can your employer afford to have you away from work for this entire duration?', 2, 'Ask about coverage at workplace', 'Confirmation of approved leave and operational coverage by colleagues.'),
('q-td-11', 'vt-b1b2', 'TRIP_DURATION', 'Do you plan to visit multiple countries or only the United States on this journey?', 2, 'Inquire about transit or return tickets', 'Clear route details and awareness of entry requirements.'),

-- EMPLOYMENT (12-18)
('q-emp-12', 'vt-b1b2', 'EMPLOYMENT', 'What do you do for a living in Kenya?', 1, 'Ask about daily responsibilities and tenure', 'Concise job title, company name, and core responsibilities demonstrating strong professional ties.'),
('q-emp-13', 'vt-b1b2', 'EMPLOYMENT', 'How long have you been employed with your current company?', 1, 'Check career stability', 'Stable employment history showing career progression and seniority.'),
('q-emp-14', 'vt-b1b2', 'EMPLOYMENT', 'What are your primary day-to-day responsibilities in your role?', 2, 'Assess consistency with declared occupation', 'Detailed and authentic explanation of work tasks, avoiding buzzwords.'),
('q-emp-15', 'vt-b1b2', 'EMPLOYMENT', 'Has your employer officially approved your leave for this trip?', 1, 'Request leave letter context', 'Clear confirmation of formal leave authorization and expected resumption date.'),
('q-emp-16', 'vt-b1b2', 'EMPLOYMENT', 'If you are self-employed, what is the nature of your business and how many employees do you have?', 2, 'Ask how business runs in absence', 'Legitimate registered enterprise with managerial succession during travel.'),
('q-emp-17', 'vt-b1b2', 'EMPLOYMENT', 'What did you do before joining your present employer?', 2, 'Review background continuity', 'Logical career trajectory and continuous legitimate earnings.'),
('q-emp-18', 'vt-b1b2', 'EMPLOYMENT', 'Do you have any ongoing projects or client commitments waiting for you when you return?', 2, 'Assess binding economic ties to Kenya', 'Concrete professional commitments requiring presence back home.'),

-- FINANCES (19-25)
('q-fin-19', 'vt-b1b2', 'FINANCES', 'What is your monthly income or salary?', 1, 'Compare against declared DS-160 range', 'Accurate, confident statement of earnings matching documentation.'),
('q-fin-20', 'vt-b1b2', 'FINANCES', 'How much do you estimate this trip to the United States will cost in total?', 2, 'Calculate airfare, lodging, daily expenses', 'Realistic budget estimate (e.g., $3,000 - $5,000 for 2 weeks) proportional to income.'),
('q-fin-21', 'vt-b1b2', 'FINANCES', 'Who is paying for your travel expenses, flights, and accommodation?', 1, 'If self-sponsored, check savings; if sponsored, check sponsor relationship', 'Clear statement of funding source without ambiguity or hesitation.'),
('q-fin-22', 'vt-b1b2', 'FINANCES', 'How have you saved or budgeted for this international trip?', 2, 'Inquire about regular savings habits', 'Demonstrates disciplined personal savings or company-backed travel allowance.'),
('q-fin-23', 'vt-b1b2', 'FINANCES', 'Do you own property, land, or assets in Kenya?', 2, 'Evaluate immovable ties', 'Ownership of land title, apartment, or business assets anchoring applicant locally.'),
('q-fin-24', 'vt-b1b2', 'FINANCES', 'Will your salary continue to be paid into your account while you are on leave?', 1, 'Verify paid leave status', 'Confirmation of standard paid annual leave benefit.'),
('q-fin-25', 'vt-b1b2', 'FINANCES', 'What would you do if an unexpected medical or travel expense arises while in the U.S.?', 2, 'Ask about international travel insurance', 'Awareness of travel medical insurance and emergency funds on credit/debit card.'),

-- FAMILY (26-30)
('q-fam-26', 'vt-b1b2', 'FAMILY', 'Are you married, and do you have any children in Kenya?', 1, 'Check social ties to home country', 'Statement of immediate family dependents remaining in Kenya during travel.'),
('q-fam-27', 'vt-b1b2', 'FAMILY', 'Why is your spouse or family not traveling with you on this trip?', 2, 'Ask about family commitments at home', 'Valid reasons like children school term, spouse job commitments, or solo business focus.'),
('q-fam-28', 'vt-b1b2', 'FAMILY', 'Do you have any relatives, close friends, or acquaintances living in the United States?', 1, 'Must answer with 100% honesty matching DS-160', 'Complete honesty regarding any relatives in the U.S. and their legal status.'),
('q-fam-29', 'vt-b1b2', 'FAMILY', 'What legal status do your relatives in the U.S. hold, and what do they do?', 2, 'Check knowledge of family status', 'Accurate knowledge of whether relative is on green card, citizen, or student visa.'),
('q-fam-30', 'vt-b1b2', 'FAMILY', 'Where do your parents and siblings currently reside?', 1, 'Evaluate geographic family cluster', 'Affirmation of family roots and social network in Kenya.'),

-- TRAVEL_HISTORY (31-35)
('q-th-31', 'vt-b1b2', 'TRAVEL_HISTORY', 'Have you traveled outside of Kenya before?', 1, 'Ask which countries and when', 'Chronological list of prior trips (e.g., East Africa, UAE, UK, Europe, South Africa).'),
('q-th-32', 'vt-b1b2', 'TRAVEL_HISTORY', 'What was the purpose of your previous international visits?', 2, 'Check compliance with visa terms', 'History of visiting foreign nations and returning strictly within permitted time.'),
('q-th-33', 'vt-b1b2', 'TRAVEL_HISTORY', 'Did you always return to Kenya on time during your past trips abroad?', 1, 'Confirm immigration compliance', 'Unblemished travel record respecting immigration laws worldwide.'),
('q-th-34', 'vt-b1b2', 'TRAVEL_HISTORY', 'If this is your first international journey, why did you pick the USA for your first trip?', 3, 'Assess plausibility for first-time traveler', 'Grounded explanation of milestone trip, conference, or childhood aspiration with solid backing.'),
('q-th-35', 'vt-b1b2', 'TRAVEL_HISTORY', 'Do you hold any active visas for other countries in your passport?', 2, 'Review passport stamps and validity', 'Demonstration of active travel passport in good standing.'),

-- ACCOMMODATION (36-40)
('q-acc-36', 'vt-b1b2', 'ACCOMMODATION', 'Where will you be staying during your time in the United States?', 1, 'Address match with DS-160', 'Specific hotel name, booked Airbnb, or host residence address.'),
('q-acc-37', 'vt-b1b2', 'ACCOMMODATION', 'If staying with a host, how do you know them and how long have you known each other?', 2, 'Test credibility of relationship', 'Authentic description of friendship or professional relationship.'),
('q-acc-38', 'vt-b1b2', 'ACCOMMODATION', 'Why did you choose this hotel or neighborhood for your stay?', 2, 'Connect to itinerary and safety', 'Proximity to conference center, metro lines, or tourist attractions.'),
('q-acc-39', 'vt-b1b2', 'ACCOMMODATION', 'How will you commute between your lodging and the attractions or meetings you plan to attend?', 2, 'Logistics awareness', 'Mention of subway, ride-share (Uber/Lyft), or rented car.'),
('q-acc-40', 'vt-b1b2', 'ACCOMMODATION', 'Do you know how much your lodging will cost per night?', 2, 'Budget reality check', 'Reasonable nightly rate estimate matching typical U.S. hospitality costs.'),

-- RETURN_PLANS (41-45)
('q-rp-41', 'vt-b1b2', 'RETURN_PLANS', 'What binds you to return to Kenya at the end of your visit?', 1, 'Primary INA 214(b) test', 'Clear synthesis of job, family, investments, community leadership, and personal future in Kenya.'),
('q-rp-42', 'vt-b1b2', 'RETURN_PLANS', 'When exactly do you plan to resume work at your office in Nairobi?', 1, 'Verify return timeline', 'Specific calendar date for returning to desk.'),
('q-rp-43', 'vt-b1b2', 'RETURN_PLANS', 'What would prevent you from accepting an informal job offer while in the United States?', 3, 'Direct nonimmigrant intent check', 'Clear understanding that working on B1/B2 is illegal, and strong loyalty to Kenyan career.'),
('q-rp-44', 'vt-b1b2', 'RETURN_PLANS', 'What long-term career goals do you have in Kenya over the next 3 to 5 years?', 2, 'Future roadmap in home country', 'Articulated career promotion, business expansion, or property development plans at home.'),
('q-rp-45', 'vt-b1b2', 'RETURN_PLANS', 'Do you have elderly dependents, school-going children, or community responsibilities awaiting your return?', 2, 'Social obligation ties', 'Tangible social ties that necessitate presence in Kenya.'),

-- PREVIOUS_VISA (46-50)
('q-pv-46', 'vt-b1b2', 'PREVIOUS_VISA', 'Have you ever applied for a U.S. visa in the past?', 1, 'Consistency with embassy records', 'Direct, truthful confirmation of prior applications or lack thereof.'),
('q-pv-47', 'vt-b1b2', 'PREVIOUS_VISA', 'If previously refused under 214(b), what has changed in your circumstances since your last interview?', 3, 'Crucial for previously refused applicants', 'Concrete improvements such as increased salary, new stable job, marriage, travel history, or assets.'),
('q-pv-48', 'vt-b1b2', 'PREVIOUS_VISA', 'Have you ever had a visa canceled or revoked by any foreign embassy?', 2, 'Immigration history audit', 'Honest and direct answer with context if applicable.'),
('q-pv-49', 'vt-b1b2', 'PREVIOUS_VISA', 'Have you ever traveled to the U.S. before, and if so, when and for how long?', 2, 'Verify past compliance', 'Truthful dates matching CBP entry/exit records.'),
('q-pv-50', 'vt-b1b2', 'PREVIOUS_VISA', 'Did someone else help you fill out your DS-160 application form?', 2, 'Application integrity check', 'Affirmation that applicant reviewed and signed all information personally for accuracy.'),

-- SPONSOR (51-55)
('q-sp-51', 'vt-b1b2', 'SPONSOR', 'Who is sponsoring your trip, and what is your relationship to them?', 1, 'Clarify financial sponsor', 'Clear declaration of employer sponsorship, self-sponsorship, or parent support.'),
('q-sp-52', 'vt-b1b2', 'SPONSOR', 'Why is this sponsor willing to cover the financial cost of your travel?', 2, 'Sponsor motivation', 'Legitimate justification (e.g. professional development, corporate expansion, family gift).'),
('q-sp-53', 'vt-b1b2', 'SPONSOR', 'What is your sponsor''s occupation and financial capacity to support this trip?', 2, 'Evaluate sponsor credibility', 'Concrete details of sponsor enterprise, title, or verified income.'),
('q-sp-54', 'vt-b1b2', 'SPONSOR', 'Do you have an official sponsorship letter or corporate guarantee for this travel?', 2, 'Documentary consistency', 'Confirmation of official letterhead and authorized signatories.'),
('q-sp-55', 'vt-b1b2', 'SPONSOR', 'Will you be responsible for any personal out-of-pocket expenses beyond what your sponsor covers?', 1, 'Self-reliance check', 'Personal allowance for souvenirs, personal dining, and incidentals.')
ON DUPLICATE KEY UPDATE question_text=VALUES(question_text);
