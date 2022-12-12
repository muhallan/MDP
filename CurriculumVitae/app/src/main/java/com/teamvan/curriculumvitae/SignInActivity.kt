package com.teamvan.curriculumvitae

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.teamvan.data.Globals
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInBt.setOnClickListener(this)

        linkSignUpTv.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.linkSignUpTv -> startActivity(
                Intent(
                    this@SignInActivity,
                    SignUpActivity::class.java
                )
            )
            R.id.signInBt -> login()
        }
    }

    private fun login() {
        if (!validate()) {
            onLoginFailed()
            return
        }

        val username = usernameLoginInEt.text.toString().trim()
        val password = passwordLoginInEt.text.toString().trim()

        val storedUser = MainActivity.getLoggedInUser(this)

        if (!(storedUser.username == username && storedUser.password == password)) {
            Toast.makeText(this@SignInActivity, "Incorrect password or username", Toast.LENGTH_SHORT).show()
        } else {
            Globals.signedInUser = storedUser
            val intent = Intent(this@SignInActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // prevent going back to the sign up screens
            startActivity(intent)
            signInBt.isEnabled = false
        }
    }


    private fun onLoginFailed() {
        signInBt.isEnabled = true
    }

    private fun validate(): Boolean {
        var valid = true
        val username = usernameLoginInEt.text.toString().trim()
        val password = passwordLoginInEt.text.toString().trim()
        if (TextUtils.isEmpty(username)) {
            usernameLoginInEt.error = "enter your username"
            valid = false
        } else {
            usernameLoginInEt.error = null
        }
        if (TextUtils.isEmpty(password)) {
            passwordLoginInEt.error = "enter your password"
            valid = false
        } else {
            passwordLoginInEt.error = null
        }
        return valid
    }

}