package com.teamvan.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.teamvan.androidquiz.R
import com.teamvan.androidquiz.SplashActivity
import com.teamvan.database.QuizDatabase
import com.teamvan.database.Results
import kotlinx.android.synthetic.main.fragment_last.*
import kotlinx.coroutines.launch

class LastFragment : BaseFragment(), OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Receive the arguments
        arguments?.let {
            launch {
                context?.let{
                    val results = QuizDatabase(it).getResultsDao().getResults()
                    val totalQns = results.num_attempted
                    val correctAnswers = results.num_passed
                    val timeOuts = results.num_timed_out

                    val score = correctAnswers.toDouble() / totalQns.toDouble()

                    val percentage = (score * 100).toInt()

                    bt_qns_all.text = totalQns.toString()
                    bt_answered.text = correctAnswers.toString()
                    bt_time_outs.text = timeOuts.toString()

                    bt_percentage.text = percentage.toString() + "%"

                    val response = if (percentage == 100) {
                        "Congratulations!"
                    } else if (percentage >= 90) {
                        "Excellent!"
                    } else if (percentage >= 70) {
                        "Good! Wish to improve?"
                    } else if (percentage >= 50) {
                        "You can do better!"
                    } else {
                        "Sorry! Try again."
                    }

                    tv_response.text = response

                    if (totalQns == 20 && percentage == 100) {
                        bt_play_again.visibility = View.GONE
                    }

                    if (totalQns == 20) {
                        tv_over.text = "Quiz completed!"
                    }
                }
            }
        }

        bt_exit.setOnClickListener(this)
        bt_play_again.setOnClickListener(this)
        bt_result_analysis.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.bt_exit -> {
                val intent = Intent(context, SplashActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            R.id.bt_play_again -> {
                launch {
                    context?.let {
                        QuizDatabase(it).getResultsDao().clearResults()
                        val emptyResults = Results(0, 0, 0)
                        QuizDatabase(it).getResultsDao().addResults(emptyResults)
                        QuizDatabase(it).getQuestionDao().resetQuestions()
                    }
                }

                val directions = LastFragmentDirections.actionLastFragmentToQuestionFragment()
                Navigation.findNavController(view).navigate(directions)
            }
            R.id.bt_result_analysis -> {
                val directions = LastFragmentDirections.actionLastFragmentToAnswersFragment()
                Navigation.findNavController(view).navigate(directions)
            }
        }
    }
}