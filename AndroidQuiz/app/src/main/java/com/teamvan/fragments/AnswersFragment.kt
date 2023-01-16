package com.teamvan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamvan.androidquiz.R
import com.teamvan.database.QuizDatabase
import com.teamvan.pokos.AnswerResult
import kotlinx.android.synthetic.main.answers_results_item.view.*
import kotlinx.android.synthetic.main.fragment_answers.*
import kotlinx.coroutines.launch

class AnswersFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_answers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        answers_resultsRV.layoutManager = LinearLayoutManager(context)
        launch {
            context?.let{
                val questions = QuizDatabase(it).getQuestionDao().getAllQuestions()
                val answerResults = ArrayList<AnswerResult>()
                for (question in questions) {
                    val answer = QuizDatabase(it).getAnswerDao().getAnswerByQuestionId(question.id)
                    val answerOptions = listOf(answer.option1, answer.option2, answer.option3, answer.option4)
                    val result = if (question.passed == 1) {
                        "Passed"
                    } else {
                        if (question.is_answered == 1) {
                            if (question.selected_answer_index == -1) {
                                "Timed out"
                            } else {
                                "Failed"
                            }
                        } else {
                            "Skipped"
                        }
                    }
                    val selectedAnswer = if (question.selected_answer_index == -1) {
                        "You didn't answer"
                    } else {
                        answerOptions[question.selected_answer_index]
                    }

                    val answerResult = AnswerResult(question.number, question.question, selectedAnswer, answerOptions[question.answer_index], result)
                    answerResults.add(answerResult)
                }
                val adapter = MyAdapter(answerResults)
                answers_resultsRV.adapter = adapter
            }
        }
    }

    inner class MyAdapter(private val results: ArrayList<AnswerResult>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.answers_results_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.questionNumTv.text = "Question: ${results[position].questionNumber}"
            holder.itemView.resultTv.text = results[position].result
            holder.itemView.questionTextTv.text = results[position].questionText
            holder.itemView.answerGivenTv.text = results[position].answerGiven
            holder.itemView.correctAnswerTv.text = results[position].actualAnswer
        }

        override fun getItemCount(): Int {
            return results.size
        }

    }
}