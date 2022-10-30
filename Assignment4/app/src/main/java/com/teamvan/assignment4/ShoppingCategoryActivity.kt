package com.teamvan.assignment4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_shopping_category.*

class ShoppingCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_category)

        val username = intent.getStringExtra("username")
        welcomeTv.text = "Welcome $username"

        electronicsIv.setOnClickListener {
            Toast.makeText(this, "You have chosen the Electronics category of shopping",Toast.LENGTH_LONG).show()
        }
        clothingIv.setOnClickListener {
            Toast.makeText(this, "You have chosen the Clothing category of shopping",Toast.LENGTH_LONG).show()
        }
        beautyIv.setOnClickListener {
            Toast.makeText(this, "You have chosen the Beauty category of shopping",Toast.LENGTH_LONG).show()
        }
        foodIv.setOnClickListener {
            Toast.makeText(this, "You have chosen the Food category of shopping",Toast.LENGTH_LONG).show()
        }
    }
}