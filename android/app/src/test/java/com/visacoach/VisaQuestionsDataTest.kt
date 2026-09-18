package com.visacoach

import com.visacoach.data.QuestionCategory
import com.visacoach.data.VisaQuestionsRepository
import org.junit.Assert.*
import org.junit.Test

class VisaQuestionsDataTest {

    @Test
    fun testTotalQuestionsCount() {
        val total = VisaQuestionsRepository.questions.size
        assertTrue("Should have at least 60 questions", total >= 60)
        assertEquals(68, total)
    }

    @Test
    fun testAllCategoriesPresent() {
        val categories = VisaQuestionsRepository.questions.map { it.category }.distinct()
        assertTrue(categories.contains(QuestionCategory.PURPOSE))
        assertTrue(categories.contains(QuestionCategory.EMPLOYMENT))
        assertTrue(categories.contains(QuestionCategory.FINANCES))
        assertTrue(categories.contains(QuestionCategory.TIES_214B))
        assertTrue(categories.contains(QuestionCategory.FAMILY_RELATIVES))
        assertTrue(categories.contains(QuestionCategory.TRAVEL_HISTORY))
        assertTrue(categories.contains(QuestionCategory.RED_FLAGS))
    }

    @Test
    fun testSearchQuestions() {
        val results = VisaQuestionsRepository.searchQuestions("salary")
        assertTrue(results.isNotEmpty())
        assertTrue(results.any { it.question.contains("salary", ignoreCase = true) || it.sampleAnswer.contains("salary", ignoreCase = true) })
    }

    @Test
    fun testGuidesAvailable() {
        val guides = VisaQuestionsRepository.guides
        assertTrue(guides.isNotEmpty())
        assertEquals(4, guides.size)
        assertTrue(guides.any { it.id == "guide-214b" })
    }
}
