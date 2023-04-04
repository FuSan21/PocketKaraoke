package me.fusan.pocketkaraoke.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.fusan.pocketkaraoke.MainActivity
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.authentication.controller.LoginController


class LoginActivity : AppCompatActivity() {
    private lateinit var loginController: LoginController
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var signinButton: Button
    private lateinit var forgotPasswordButton: Button
    private lateinit var goToSignupButton: Button
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginController = LoginController(this)
        mEmail = findViewById<EditText>(R.id.email)
        mPassword = findViewById<EditText>(R.id.password)
        progressBar = findViewById(R.id.progressBar)
        signinButton = findViewById(R.id.signin_button)
        forgotPasswordButton = findViewById(R.id.forgot_password_button)
        goToSignupButton = findViewById(R.id.goto_signup_button)
        signinButton.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            loginController.doSignin(mEmail.text.toString(), mPassword.text.toString())
        }
        forgotPasswordButton.setOnClickListener{
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        goToSignupButton.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }


    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    fun gotoMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun returnEmailEmptyToast() {
        Toast.makeText(applicationContext, "Enter email!", Toast.LENGTH_SHORT).show()
    }

    fun returnPasswordEmptyToast() {
        Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
    }

    fun returnEmailNotValidToast() {
        Toast.makeText(applicationContext, "Invalid Email Address!", Toast.LENGTH_SHORT).show()
    }

    fun returnPasswordLessThanSixToast() {
        Toast.makeText(applicationContext, "Incorrect password!", Toast.LENGTH_SHORT).show()
    }

    fun returnSuccessfulToast() {
        progressBar.visibility = View.GONE
        Toast.makeText(applicationContext, "Signed In.", Toast.LENGTH_SHORT).show()
    }

    fun returnUnsuccessfulToast(taskException: String) {
        progressBar.visibility = View.GONE
        Toast.makeText(applicationContext, "Sign In Failed. $taskException", Toast.LENGTH_LONG).show()
    }
}