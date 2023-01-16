package com.teamvan.androidquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.teamvan.pokos.AllAnswers
import com.teamvan.database.Answer
import com.teamvan.database.Question
import com.teamvan.database.QuizDatabase
import com.teamvan.database.Results
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            val questions = resources.getStringArray(R.array.questions)
            val allAnswers = getAnswersList()

            // First clear the tables
            QuizDatabase(this@SplashActivity).getResultsDao().clearResults()
            QuizDatabase(this@SplashActivity).getQuestionDao().clearQuestions()
            QuizDatabase(this@SplashActivity).getAnswerDao().clearAnswers()

            for (i in questions.indices) {
                val answerList = allAnswers[i]
                val answerIndex = answerList[4].toInt()

                val question = Question(i + 1, questions[i], answerIndex, 0, 0, -1)

                val qnId = QuizDatabase(this@SplashActivity).getQuestionDao().addQuestion(question)

                val answer = Answer(qnId, answerList[0], answerList[1], answerList[2], answerList[3])
                QuizDatabase(this@SplashActivity).getAnswerDao().addAnswer(answer)
            }
            val emptyResults = Results(0, 0, 0)
            QuizDatabase(this@SplashActivity).getResultsDao().addResults(emptyResults)
        }

        start_quiz.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getAnswersList(): Array<Array<String>> {
        return arrayOf(
            AllAnswers.q1,
            AllAnswers.q2,
            AllAnswers.q3,
            AllAnswers.q4,
            AllAnswers.q5,
            AllAnswers.q6,
            AllAnswers.q7,
            AllAnswers.q8,
            AllAnswers.q9,
            AllAnswers.q10,
            AllAnswers.q11,
            AllAnswers.q12,
            AllAnswers.q13,
            AllAnswers.q14,
            AllAnswers.q15,
            AllAnswers.q16,
            AllAnswers.q17,
            AllAnswers.q18,
            AllAnswers.q19,
            AllAnswers.q20
        )
    }

}