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
import me.fusan.pocketkaraoke.ui.authentication.presenter.LoginPresenter


class LoginActivity : AppCompatActivity() {
    lateinit var loginPresenter: LoginPresenter
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var signinButton: Button
    private lateinit var forgotPasswordButton: Button
    private lateinit var goToSignupButton: Button
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mEmail = findViewById<EditText>(R.id.email)
        mPassword = findViewById<EditText>(R.id.password)
        loginPresenter = LoginPresenter(this)
        progressBar = findViewById(R.id.progressBar)
        signinButton = findViewById(R.id.signin_button)
        forgotPasswordButton = findViewById(R.id.forgot_password_button)
        goToSignupButton = findViewById(R.id.goto_signup_button)
        signinButton.setOnClickListener{
            when(loginPresenter.doSignin(mEmail.text.toString(), mPassword.text.toString())) {
                1 -> progressbarStatus(true)
                -1 -> Toast.makeText(applicationContext, "Enter email!", Toast.LENGTH_SHORT).show()
                -2 -> Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                -3 -> Toast.makeText(applicationContext, "Incorrect password!", Toast.LENGTH_SHORT).show()
                -4 -> Toast.makeText(applicationContext, "Invalid Email Address!", Toast.LENGTH_SHORT).show()
            }
        }
        forgotPasswordButton.setOnClickListener{
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        goToSignupButton.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        loginPresenter.bypassLoginForExistingUser()
    }

    fun gotoMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun returnSuccessfulToast() {
        Toast.makeText(applicationContext, "Signed In.", Toast.LENGTH_SHORT).show()
    }

    fun returnUnsuccessfulToast(taskException: String) {
        Toast.makeText(applicationContext, "Sign In Failed. $taskException", Toast.LENGTH_LONG).show()
    }

    fun progressbarStatus(visible: Boolean) {
        if (visible) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }
}