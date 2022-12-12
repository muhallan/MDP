package com.teamvan.curriculumvitae

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.teamvan.data.Globals
import com.teamvan.data.LoggedInUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        signUpBt.setOnClickListener(View.OnClickListener { signUp() })

        linkSignInTv.setOnClickListener {
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // prevent going back to the sign up screens
            startActivity(intent)
        }
    }

    private fun signUp() {
        if (!validate()) {
            signUpBt.isEnabled = true
            return
        }
        val name = nameEt.text.toString().trim()
        val username = usernameEt.text.toString().trim()
        val password = passwordEt.text.toString().trim()
        val user = LoggedInUser(name, username, password)
        Globals.signedInUser = user
        MainActivity.storeLoggedInUser(this, username, password, name)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // prevent going back to the sign up screens
        startActivity(intent)
        signUpBt.isEnabled = false
    }

    private fun validate(): Boolean {
        var valid = true
        val name = nameEt.text.toString().trim()
        val username = usernameEt.text.toString().trim()
        val password = passwordEt.text.toString().trim()
        val confirm_password = confirmPasswordEt.text.toString().trim()
        if (name.isEmpty() || name.length < 3) {
            nameEt.error = "at least 3 characters"
            valid = false
        } else {
            nameEt.error = null
        }
        if (username.isEmpty() || username.length < 3) {
            usernameEt.error = "at least 3 characters"
            valid = false
        } else {
            usernameEt.error = null
        }
        if (password.isEmpty()) {
            passwordEt.error = "enter your password"
            valid = false
        } else {
            if (confirm_password.isEmpty()) {
                confirmPasswordEt.error = "confirm your password"
                valid = false
            } else if (confirm_password != password) {
                confirmPasswordEt.error = "passwords don't match"
                passwordEt.error = "passwords don't match"
                valid = false
            } else {
                confirmPasswordEt.error = null
                passwordEt.error = null
            }
        }
        return valid
    }

}