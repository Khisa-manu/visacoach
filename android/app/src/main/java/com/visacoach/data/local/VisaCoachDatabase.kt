package com.visacoach.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "cached_questions")
data class CachedQuestionEntity(
    @PrimaryKey val id: String,
    val category: String,
    val questionText: String,
    val difficulty: Int
)

@Entity(tableName = "interview_history_records")
data class InterviewHistoryEntity(
    @PrimaryKey val id: String,
    val dateFormatted: String,
    val visaType: String,
    val overallScore: Double,
    val totalQuestions: Int,
    val status: String
)

@Dao
interface CachedQuestionDao {
    @Query("SELECT * FROM cached_questions WHERE category = :category")
    fun getQuestionsByCategory(category: String): Flow<List<CachedQuestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<CachedQuestionEntity>)
}

@Dao
interface InterviewHistoryDao {
    @Query("SELECT * FROM interview_history_records ORDER BY dateFormatted DESC")
    fun getAllHistory(): Flow<List<InterviewHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: InterviewHistoryEntity)
}

@Database(
    entities = [CachedQuestionEntity::class, InterviewHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class VisaCoachDatabase : RoomDatabase() {
    abstract fun cachedQuestionDao(): CachedQuestionDao
    abstract fun interviewHistoryDao(): InterviewHistoryDao
}
