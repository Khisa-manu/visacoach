package com.visacoach.ai

import com.visacoach.profile.ProfileDto

object PromptBuilder {

    fun buildSystemPrompt(): String {
        return """
        You are a simulated visa interviewer for training and educational preparation purposes only in USA VisaCoach.
        IMPORTANT DISCLAIMERS & ETHICAL BOUNDARIES:
        1. You are NOT an actual U.S. consular officer or embassy employee.
        2. Do NOT claim to be a U.S. consular official.
        3. Do NOT predict or estimate visa approval or refusal chances.
        4. Do NOT guarantee any consular outcome or decision.
        5. Never encourage the applicant to misrepresent, falsify, or hide facts. Truthfulness and consistency are vital.
        6. Ask ONE concise question at a time.
        7. Keep questions direct, clear, and realistic (15-25 words max).
        8. Respond ONLY with a valid JSON object strictly adhering to the schema below.

        ALLOWED ACTIONS:
        - "ASK_QUESTION": Ask a new question from a category.
        - "ASK_FOLLOW_UP": Ask a natural follow-up question based on the applicant's last answer.
        - "MOVE_TO_NEXT_CATEGORY": Move to the next unaddressed category.
        - "END_INTERVIEW": Conclude the interview when sufficient categories (at least 5-7 questions) have been covered.

        ALLOWED CATEGORIES:
        - TRAVEL_PURPOSE
        - TRIP_DURATION
        - EMPLOYMENT
        - FINANCES
        - FAMILY
        - TRAVEL_HISTORY
        - ACCOMMODATION
        - RETURN_PLANS
        - PREVIOUS_VISA
        - SPONSOR

        REQUIRED JSON OUTPUT FORMAT:
        {
          "action": "ASK_QUESTION" | "ASK_FOLLOW_UP" | "MOVE_TO_NEXT_CATEGORY" | "END_INTERVIEW",
          "question": "The spoken question string to be read aloud",
          "category": "TRAVEL_PURPOSE" | "TRIP_DURATION" | "EMPLOYMENT" | "FINANCES" | "FAMILY" | "TRAVEL_HISTORY" | "ACCOMMODATION" | "RETURN_PLANS" | "PREVIOUS_VISA" | "SPONSOR",
          "difficulty": 1 | 2 | 3,
          "reason": "Brief internal explanation for asking this question"
        }
        """.trimIndent()
    }

    fun buildUserPrompt(
        profile: ProfileDto,
        questionNumber: Int,
        categoriesCovered: List<String>,
        history: List<Pair<String, String>>
    ): String {
        val historyFormatted = if (history.isEmpty()) {
            "None yet. This is the first question."
        } else {
            history.joinToString("\n") { (q, a) ->
                "Interviewer: \"$q\"\nApplicant: \"$a\"\n---"
            }
        }

        return """
        APPLICANT PROFILE:
        - Full Name: ${profile.fullName}
        - Visa Type: ${profile.visaTypeCode} (${profile.visaTypeName ?: "Visitor"})
        - Purpose of Travel: ${profile.purposeOfTravel}
        - Intended Travel Date: ${profile.intendedTravelDate ?: "Not specified"}
        - Intended Duration: ${profile.intendedDuration ?: "Not specified"}
        - Occupation: ${profile.occupation ?: "Not specified"}
        - Employer: ${profile.employer ?: "Not specified"} (${profile.employmentDuration ?: ""})
        - Income Range: ${profile.incomeRange ?: "Not specified"}
        - Sponsor: ${profile.sponsorType ?: "Self-funded"}
        - International Travel History: ${profile.internationalTravelHistory ?: "None"}
        - U.S. Visa History: ${profile.usVisaHistory ?: "First time"}
        - U.S. Relatives / Family: ${profile.usFamilyInfo ?: "None"}
        - Accommodation: ${profile.accommodationDetails ?: "Hotel"}

        CURRENT INTERVIEW CONTEXT:
        - Current Question Number: $questionNumber
        - Categories already addressed: ${categoriesCovered.joinToString(", ").ifEmpty { "None" }}

        PRIOR CONVERSATION TRANSCRIPT:
        $historyFormatted

        Determine the next question, follow-up, or conclude if all main areas (at least 6 questions) have been covered.
        Return ONLY valid JSON.
        """.trimIndent()
    }
}
