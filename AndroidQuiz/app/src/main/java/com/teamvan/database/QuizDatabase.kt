package com.teamvan.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Question::class, Answer::class, Results::class], version = 1)
abstract class QuizDatabase(): RoomDatabase() {

    abstract fun getQuestionDao(): QuestionDAO
    abstract fun getAnswerDao(): AnswerDAO
    abstract fun getResultsDao(): ResultsDAO

    // Build RoomDB
    companion object {
        @Volatile private var instance : QuizDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            // Create a instance also return the instance
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        // Function to build database
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            QuizDatabase::class.java,
            "quiz_db"
        ).build()
    }
}
