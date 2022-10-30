package com.teamvan.assignment4

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        createAccountBt.setOnClickListener {
            val password = passwordEt.text.toString().trim()
            val email = emailEt.text.toString().trim()
            val firstName = firstNameEt.text.toString().trim()
            val lastName = lastNameEt.text.toString().trim()

            if (password.isNotEmpty() && email.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()) {
                val newUser = User(firstName, lastName, email, password)
                val resultIntent = intent
                resultIntent.putExtra("newUser", newUser as java.io.Serializable)
                setResult(Activity.RESULT_OK, resultIntent)

                Toast.makeText(this, "Account created successfully", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}