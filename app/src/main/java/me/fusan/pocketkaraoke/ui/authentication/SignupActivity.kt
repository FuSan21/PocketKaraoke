package me.fusan.pocketkaraoke.ui.authentication

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
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.authentication.controller.SignupController

class SignupActivity : AppCompatActivity() {
    private lateinit var signupController: SignupController
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
        signupController = SignupController(this)
        mEmail = findViewById<EditText>(R.id.email)
        mPassword = findViewById<EditText>(R.id.password)
        mName = findViewById<EditText>(R.id.name)
        auth = FirebaseAuth.getInstance()
        database = Firebase.database
        ref = database.reference
        progressBar = findViewById(R.id.progressBar)
        signupButton = findViewById(R.id.signup_button)
        signupButton.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            signupController.doSignup(mEmail.text.toString(), mPassword.text.toString(), mName.text.toString())
        }

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
        progressBar.visibility = View.GONE
        Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
    }

    fun returnUnsuccessfulToast(taskException: String) {
        progressBar.visibility = View.GONE
        Toast.makeText(this, "Authentication Failed. $taskException", Toast.LENGTH_SHORT).show()
    }
}