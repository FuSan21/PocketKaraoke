package me.fusan.pocketkaraoke.ui.authentication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.authentication.controller.ForgotPasswordController

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var forgotPasswordController: ForgotPasswordController
    private lateinit var reset_button: Button
    lateinit var mEmail: EditText
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        forgotPasswordController = ForgotPasswordController(this)
        mEmail = findViewById<EditText>(R.id.email)
        progressBar = findViewById(R.id.progressBar)
        reset_button = findViewById(R.id.reset_button)
        reset_button.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            forgotPasswordController.doPasswordReset(mEmail.text.toString())
        }
    }

    fun returnSuccessfulToast() {
        progressBar.visibility = View.GONE
        Toast.makeText(applicationContext, "Reset Successful!", Toast.LENGTH_SHORT).show()
    }

    fun returnUnsuccessfulToast(taskException: String) {
        progressBar.visibility = View.GONE
        Toast.makeText(applicationContext, "Reset Unsuccessful. $taskException", Toast.LENGTH_LONG).show()
    }

    fun returnEmailEmptyToast() {
        Toast.makeText(applicationContext, "Enter email!", Toast.LENGTH_SHORT).show()
    }

    fun returnEmailNotValidToast() {
        Toast.makeText(applicationContext, "Invalid Email Address!", Toast.LENGTH_SHORT).show()
    }

}