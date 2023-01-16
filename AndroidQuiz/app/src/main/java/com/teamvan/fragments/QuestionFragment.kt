package com.teamvan.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.teamvan.androidquiz.R
import com.teamvan.database.Question
import com.teamvan.database.QuizDatabase
import com.teamvan.database.Results
import com.teamvan.pokos.toast
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.coroutines.launch

class QuestionFragment : BaseFragment() {

    private var questionNo: Int = 1
    private var currentScore: Int = 0
    private var timedOut: Int = 0
    private var timerHasStarted = false
    private var hasPassed = 0
    private var selectedAnswerPosition = -1
    private var question: Question? = null
    private var currentResults: Results? = null
    private lateinit var countDownTimer: MyCountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Receive the arguments
        arguments?.let {
            questionNo = QuestionFragmentArgs.fromBundle(it).qnNum
            if (questionNo == 0) { // means that this is the first time we are running this fragment
                questionNo = 1
            }
            currentScore = QuestionFragmentArgs.fromBundle(it).score
            timedOut = QuestionFragmentArgs.fromBundle(it).timeOut

            val score = currentScore.toDouble() / (questionNo - 1).toDouble()

            tv_score_no2.text = currentScore.toString() + "/" + (questionNo - 1).toString()

            val percentage = (score * 100).toInt()
            tv_score_percent.text = percentage.toString() + "%"
            bt_qn_no2.text = questionNo.toString()
        }

        val startTime = (15 * 1000).toLong()
        val interval = (1 * 1000).toLong()
        countDownTimer = MyCountDownTimer(startTime, interval)

        timerHasStarted = if (!timerHasStarted) {
            countDownTimer.start()
            true
        } else {
            countDownTimer.cancel()
            false
        }

        launch {
            context?.let{
                question = QuizDatabase(it).getQuestionDao().getQuestionByNumber(questionNo)
                val answer = QuizDatabase(it).getAnswerDao().getAnswerByQuestionId(question!!.id)
                currentResults = QuizDatabase(it).getResultsDao().getResults()
                tv_qn2.text = question?.question

                val answers = listOf(answer.option1, answer.option2, answer.option3, answer.option4)
                val adapter = AnswersListAdapter(it, answers, question!!)
                lv_answers2.adapter = adapter
            }
        }

        bt_next2.setOnClickListener { view
            val message: String
            if (selectedAnswerPosition == -1) {
                context?.toast("Please select an answer!")
            } else {
                if (hasPassed == 1) {
                    message = "Correct!"
                    currentScore++
                    currentResults?.num_passed = currentResults?.num_passed?.plus(1)!!
                } else {
                    message = "Wrong!"
                }
                countDownTimer.cancel()

                context?.toast(message)
                questionNo++

                question?.passed = hasPassed
                question?.selected_answer_index = selectedAnswerPosition
                question?.is_answered = 1

                currentResults?.num_attempted = currentResults?.num_attempted?.plus(1)!!

                launch {
                    context?.let {
                        QuizDatabase(it).getQuestionDao().updateQuestion(question!!)
                        QuizDatabase(it).getResultsDao().updateResults(currentResults!!)

                        if (questionNo <= 20) {
                            val directions = QuestionFragmentDirections.actionQuestionFragmentSelf()
                            directions.qnNum = questionNo
                            directions.score = currentScore
                            directions.timeOut = timedOut
                            Navigation.findNavController(view).navigate(directions)
                        }
                    }
                }
            }
        }

        if (questionNo == 20) {
            bt_next2.visibility = View.GONE
        }

        bt_finish.setOnClickListener { view
            val message: String
            if (selectedAnswerPosition == -1) {
                context?.toast("First select an answer!")
            } else {
                if (hasPassed == 1) {
                    message = "Correct!"
                    currentResults?.num_passed = currentResults?.num_passed?.plus(1)!!
                    currentScore++
                } else {
                    message = "Wrong!"
                }
                context?.toast(message)
                questionNo++
                countDownTimer.cancel()

                question?.passed = hasPassed
                question?.selected_answer_index = selectedAnswerPosition
                question?.is_answered = 1

                currentResults?.num_attempted = currentResults?.num_attempted?.plus(1)!!

                launch {
                    context?.let {
                        QuizDatabase(it).getQuestionDao().updateQuestion(question!!)
                        QuizDatabase(it).getResultsDao().updateResults(currentResults!!)

                        val directions = QuestionFragmentDirections.actionQuestionFragmentToLastFragment()
                        Navigation.findNavController(view).navigate(directions)
                    }
                }
            }
        }
    }

    private inner class AnswersListAdapter(context: Context, val answers: List<String>, val question: Question) :
        ArrayAdapter<String?>(context, R.layout.eachanswer, answers) {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        private inner class ViewHolder {
            lateinit var answer: CheckedTextView
            lateinit var radio: RadioButton
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View
            val holder: ViewHolder

            if (convertView == null) {
                view = inflater.inflate(R.layout.eachanswer, parent, false)

                holder = ViewHolder()
                holder.answer = view.findViewById(R.id.tv_selectedanswer) as CheckedTextView
                holder.radio = view.findViewById(R.id.rb_ans) as RadioButton
                view.tag = holder
            } else {
                view = convertView
                holder = convertView.tag as ViewHolder
            }

            val answer = holder.answer
            val radio = holder.radio

            // find the answer to work with
            val selectedAnswer = answers[position]

            // fill the view
            answer.text = selectedAnswer

            radio.isChecked = position == selectedAnswerPosition
            radio.tag = position
            radio.setOnClickListener(clickListener)
            return view
        }

        private val clickListener =
            View.OnClickListener { view ->
                selectedAnswerPosition = view.tag as Int
                hasPassed = if (selectedAnswerPosition == question.answer_index) {
                    1
                } else {
                    0
                }
                notifyDataSetInvalidated()
            }
    }

    private inner class MyCountDownTimer(startTime: Long, interval: Long) :
        CountDownTimer(startTime, interval) {

        override fun onFinish() {
            tv_time_no2.text = "Time's up!"
            val adb = AlertDialog.Builder(context)
            adb.setTitle("Time up!")
            adb.setMessage("Questions should be answered in less than 15s")
            adb.setPositiveButton(
                "Got it"
            ) { dialog, which ->
                questionNo++
                timedOut++

                currentResults?.num_timed_out = currentResults?.num_timed_out?.plus(1)!!
                currentResults?.num_attempted = currentResults?.num_attempted?.plus(1)!!

                question?.is_answered = 1

                launch {
                    context?.let {
                        QuizDatabase(it).getQuestionDao().updateQuestion(question!!)
                        QuizDatabase(it).getResultsDao().updateResults(currentResults!!)

                        if (questionNo > 20) {
                            val directions = QuestionFragmentDirections.actionQuestionFragmentToLastFragment()
                            findNavController().navigate(directions)
                        } else {
                            val directions = QuestionFragmentDirections.actionQuestionFragmentSelf()
                            directions.qnNum = questionNo
                            directions.score = currentScore
                            directions.timeOut = timedOut
                            findNavController().navigate(directions)
                        }
                    }
                }
            }
            val ad = adb.create()
            ad.setCancelable(false)
            ad.setCanceledOnTouchOutside(false)
            ad.show()
        }

        override fun onTick(millisUntilFinished: Long) {
            tv_time_no2.text = "" + millisUntilFinished / 1000 + "s"
        }
    }

    override fun onPause() {
        //don't start dialog when onPause
        countDownTimer.cancel()
        super.onPause()
    }

}