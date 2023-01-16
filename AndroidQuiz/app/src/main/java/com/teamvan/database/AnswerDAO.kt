package com.teamvan.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnswerDAO {

    @Insert
    suspend fun addAnswer(answer: Answer)

    @Query("select * from answer")
    suspend fun getAllAnswers(): List<Answer>

    @Query("SELECT * FROM answer WHERE question_id = :questionId LIMIT 1")
    suspend fun getAnswerByQuestionId(questionId: Int): Answer

    @Query("DELETE FROM answer")
    suspend fun clearAnswers()
}