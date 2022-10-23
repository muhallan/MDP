package com.teamvan.assignment2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val foods = arrayListOf( "Hamburger", "Pizza", "Mexican", "American", "Chinese")
        decideBt.setOnClickListener {
            val randomIndex = Random.nextInt(foods.size);
            foodNameTv.text = foods[randomIndex]
        }

        addFoodBt.setOnClickListener {
            val newFood = addNewFoodEt.text.toString()
            foods.add(newFood)
            foodNameTv.text = newFood
            addNewFoodEt.setText("")
        }
    }
}
