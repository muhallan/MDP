package com.teamvan.assignment4

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val allUsers = arrayListOf<User>(
            User("Harry", "Lee", "harry.lee@gmail.com", "1234578"),
            User("Jon", "Snow", "jon.snow@gmail.com", "abcdefgh"),
            User("Ling", "Lamb", "ling.lamb@gmail.com", "12121212"),
            User("Allan", "Star", "muhallan1@gmail.com", "01010101"),
            User("Esther", "Poo", "esther.poo@gmail.com", "a3a3a3a3"),
        )

        val resulContracts = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val newUser = result.data?.getSerializableExtra("newUser") as User
                    allUsers.add(newUser)
                }
        }
        signInBt.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            var found = false
            for (user in allUsers) {
                if (user.username == email && user.password == password) {
                    found = true
                    val intent = Intent(this, ShoppingCategoryActivity::class.java)
                    intent.putExtra("username", user.username)
                    startActivity(intent)
                    break
                }
            }
            if (!found) {
                Toast.makeText(this, "Invalid email address or password", Toast.LENGTH_LONG).show()
            }
        }

        createAccountBt.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            resulContracts.launch(intent)
        }

        forgotPasswordTv.setOnClickListener {
            val email = emailEt.text.toString().trim()

            var found = false
            for (user in allUsers) {
                if (user.username == email) {
                    found = true
                    val intent = Intent()
                    intent.action = Intent.ACTION_SENDTO
                    intent.data = Uri.parse("mailto:")
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Forgot password")
                    intent.putExtra(Intent.EXTRA_TEXT, "Your password is: ${user.password}");
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "No app can send emails", Toast.LENGTH_LONG).show()
                    }
                    break
                }
            }
            if (!found) {
                Toast.makeText(this, "Email address not found.", Toast.LENGTH_LONG).show()
            }
        }
    }
}