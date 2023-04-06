package me.fusan.pocketkaraoke.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import me.fusan.pocketkaraoke.MainActivity
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.authentication.presenter.SignupPresenter

class SignupActivity : AppCompatActivity() {
    private lateinit var signupPresenter: SignupPresenter
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var mName: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var progressBar: ProgressBar
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signupPresenter = SignupPresenter(this)
        mEmail = findViewById<EditText>(R.id.email)
        mPassword = findViewById<EditText>(R.id.password)
        mName = findViewById<EditText>(R.id.name)
        auth = FirebaseAuth.getInstance()
        database = Firebase.database
        ref = database.reference
        progressBar = findViewById(R.id.progressBar)
        signupButton = findViewById(R.id.signup_button)
        signupButton.setOnClickListener{
            signupPresenter.doSignup(mEmail.text.toString(), mPassword.text.toString(), mName.text.toString())
        }

    }

    fun gotoMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun returnEmailEmptyToast() {
        Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
    }

    fun returnPasswordEmptyToast() {
        Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
    }
    fun returnNameEmptyToast() {
        Toast.makeText(applicationContext, "Enter Name!", Toast.LENGTH_SHORT).show()
    }
    fun returnEmailNotValidToast() {
        Toast.makeText(applicationContext, "Invalid Email Address!", Toast.LENGTH_SHORT).show()
    }

    fun returnPasswordLessThanSixToast() {
        Toast.makeText(applicationContext, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show()
    }

    fun returnSuccessfulToast() {
        Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
    }

    fun returnUnsuccessfulToast(taskException: String) {
        Toast.makeText(this, "Authentication Failed. $taskException", Toast.LENGTH_SHORT).show()
    }

    fun progressbarStatus(visible: Boolean) {
        if (visible) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }
}