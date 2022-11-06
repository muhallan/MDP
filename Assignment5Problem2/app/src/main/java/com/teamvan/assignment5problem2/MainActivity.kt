package com.teamvan.assignment5problem2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        electronicsIv.setOnClickListener {
            startActivity(Intent(this, CategoryListActivity::class.java))
        }
        clothingIv.setOnClickListener {
            Toast.makeText(this, "You have chosen the Clothing category of shopping", Toast.LENGTH_LONG).show()
        }
        beautyIv.setOnClickListener {
            Toast.makeText(this, "You have chosen the Beauty category of shopping", Toast.LENGTH_LONG).show()
        }
        foodIv.setOnClickListener {
            Toast.makeText(this, "You have chosen the Food category of shopping", Toast.LENGTH_LONG).show()
        }
    }
}