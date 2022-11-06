package com.teamvan.assignment5problem1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var number1 = false
    var answer1Given = false
    private val number2Answers = arrayListOf<String>()
    var answer2Given = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitBt.setOnClickListener {
            if (!answer1Given || !answer2Given) {
                Toast.makeText(this, "Please answer both questions", Toast.LENGTH_LONG).show()
            } else {
                var marks = 0
                if (number1) {
                    marks += 50
                }
                val answer2 = setOf("repeat", "forEach")
                if (answer2 == number2Answers.toSet()) {
                    marks += 50
                }

                val currentDate = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Quiz submitted")
                builder.setMessage("Congratulations!\nYou submitted on $currentDate.\nYou achieved $marks%")
                builder.setPositiveButton("Okay") { dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

        resetBt.setOnClickListener {
            valRB.isChecked = false
            varRB.isChecked = false
            letRB.isChecked = false
            repeatCB.isChecked = false
            loopCB.isChecked = false
            foreachCB.isChecked = false
            answer1Given = false
            answer2Given = false
            number2Answers.clear()
        }

    }

    fun onRadioButtonClicked(view: View) {
        answer1Given = true
        if (view is RadioButton) {
            val checked  = view.isChecked
            when (view.getId()) {
                R.id.valRB ->
                    if (checked) {
                        number1 = false
                    }
                R.id.varRB ->
                    if (checked) {
                        number1 = false
                    }
                R.id.letRB ->
                    if (checked) {
                        number1 = true
                    }
            }
        }
    }

    fun onCheckBoxChecked(view: View) {
        answer2Given = true
        if (view is CheckBox) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.repeatCB ->
                    if (checked) {
                        number2Answers.add("repeat")
                    } else {
                        number2Answers.remove("repeat")
                    }
                R.id.loopCB ->
                    if (checked) {
                        number2Answers.add("loop")
                    } else {
                        number2Answers.remove("loop")
                    }
                R.id.foreachCB ->
                    if (checked) {
                        number2Answers.add("forEach")
                    } else {
                        number2Answers.remove("forEach")
                    }
            }
        }
    }
}