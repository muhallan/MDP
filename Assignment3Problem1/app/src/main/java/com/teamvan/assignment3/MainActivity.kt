package com.teamvan.assignment3

import android.graphics.Color
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBt.setOnClickListener {
            val tableRow = TableRow(this)
            val layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(16,32,32,32)
            tableRow.layoutParams = layoutParams

            val version = androidVersionEt.text.toString()
            val codeName = androidCodeEt.text.toString()

            val versionTextView = TextView(this)
            versionTextView.text = version
            tableRow.addView(versionTextView)

            val codeNameTextView = TextView(this)
            codeNameTextView.text = codeName
            tableRow.addView(codeNameTextView)

            tableTL.addView(tableRow, layoutParams)
        }
    }
}