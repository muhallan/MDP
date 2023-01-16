package com.teamvan.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDAO {

    @Insert
    suspend fun addQuestion(question: Question): Long

    @Query("SELECT * FROM question")
    suspend fun getAllQuestions(): List<Question>

    @Query("SELECT * FROM question WHERE number = :number LIMIT 1")
    suspend fun getQuestionByNumber(number: Int): Question

    @Update
    suspend fun updateQuestion(question: Question)

    @Query("DELETE FROM question")
    suspend fun clearQuestions()

    @Query("UPDATE question SET is_answered = 0, passed = 0, selected_answer_index = -1")
    suspend fun resetQuestions()
}