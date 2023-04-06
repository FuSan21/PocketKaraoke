package me.fusan.pocketkaraoke.ui.authentication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.authentication.presenter.ForgotPasswordPresenter

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var forgotPasswordPresenter: ForgotPasswordPresenter
    private lateinit var resetButton: Button
    private lateinit var mEmail: EditText
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        forgotPasswordPresenter = ForgotPasswordPresenter(this)
        mEmail = findViewById<EditText>(R.id.email)
        progressBar = findViewById(R.id.progressBar)
        resetButton = findViewById(R.id.reset_button)
        resetButton.setOnClickListener{
            forgotPasswordPresenter.doPasswordReset(mEmail.text.toString())
        }
    }

    fun returnSuccessfulToast() {
        Toast.makeText(applicationContext, "Reset Successful!", Toast.LENGTH_SHORT).show()
    }

    fun returnUnsuccessfulToast(taskException: String) {
        Toast.makeText(applicationContext, "Reset Unsuccessful. $taskException", Toast.LENGTH_LONG).show()
    }

    fun returnEmailEmptyToast() {
        Toast.makeText(applicationContext, "Enter email!", Toast.LENGTH_SHORT).show()
    }

    fun returnEmailNotValidToast() {
        Toast.makeText(applicationContext, "Invalid Email Address!", Toast.LENGTH_SHORT).show()
    }

    fun progressbarStatus(visible: Boolean) {
        if (visible) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

}