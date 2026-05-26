package com.myai.smartreplyai.data.local.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myai.smartreplyai.data.local.entity.AnalyticsEntity

@Dao
interface AnalyticsDao {
    @Insert
    suspend fun insert(event: AnalyticsEntity): Long

    @Query("SELECT COUNT(*) FROM analytics_events WHERE eventType = :type")
    suspend fun countByType(type: String): Int

    @Query(
        """
        SELECT eventValue, COUNT(*) as cnt FROM analytics_events 
        WHERE eventType = 'common_question' 
        GROUP BY eventValue ORDER BY cnt DESC LIMIT :limit
        """
    )
    suspend fun topQuestions(limit: Int): List<QuestionCount>

    @Query("SELECT COALESCE(SUM(CAST(metadata AS INTEGER)), 0) FROM analytics_events WHERE eventType = 'time_saved'")
    suspend fun totalTimeSavedMinutes(): Int
}

data class QuestionCount(
    @ColumnInfo(name = "eventValue") val eventValue: String,
    @ColumnInfo(name = "cnt") val cnt: Int
)
