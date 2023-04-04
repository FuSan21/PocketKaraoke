package me.fusan.pocketkaraoke.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import me.fusan.pocketkaraoke.MainActivity
import me.fusan.pocketkaraoke.R


class LoginActivity : AppCompatActivity() {
    private var mEmail: EditText? = null
    private var mPassword: EditText? = null
    private var progressBar: ProgressBar? = null
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mEmail = findViewById<EditText>(R.id.email)
        mPassword = findViewById<EditText>(R.id.password)
        progressBar = findViewById(R.id.progressBar)
        auth = FirebaseAuth.getInstance()
    }

    fun gotoSignup() {
        startActivity(Intent(this, SignupActivity::class.java))
    }

    fun doForgotPassword() {
        startActivity(Intent(this, ForgotPasswordActivity::class.java))
    }

    fun doSignin() {
        progressBar!!.visibility = View.VISIBLE
        val email: String = mEmail!!.text.toString()
        val password: String = mPassword!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Enter email!", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
        } else if (password.length < 6) {
            Toast.makeText(applicationContext, "Incorrect password!", Toast.LENGTH_SHORT)
                .show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(applicationContext, "Invalid Email Address!", Toast.LENGTH_SHORT).show()
        } else {
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    progressBar!!.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(applicationContext, "Signed In.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            applicationContext,
                            "Sign In Failed. " + task.exception,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}