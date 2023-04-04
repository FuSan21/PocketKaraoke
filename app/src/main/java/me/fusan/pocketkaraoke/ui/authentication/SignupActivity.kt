package me.fusan.pocketkaraoke.ui.authentication

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
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

class SignupActivity : AppCompatActivity() {
    private var mEmail: EditText? = null
    private var mPassword: EditText? = null
    private var mName: EditText? = null
    private var auth: FirebaseAuth? = null
    private var database: FirebaseDatabase? = null
    private var ref: DatabaseReference? = null
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mEmail = findViewById<EditText>(R.id.email)
        mPassword = findViewById<EditText>(R.id.password)
        mName = findViewById<EditText>(R.id.name)
        auth = FirebaseAuth.getInstance()
        database = Firebase.database
        ref = database!!.reference
        progressBar = findViewById(R.id.progressBar)
    }

    fun createDataBase(name: String?) {
//        val user = User(name, mEmail!!.text.toString())
//        val userID = auth!!.currentUser!!.uid
//        ref?.child("Users")?.child(userID)?.setValue(user)
//        ref?.child("UserChallenges")?.child(userID)
    }

    fun doSignup() {
        progressBar!!.visibility = View.VISIBLE
        val email: String = mEmail!!.text.toString()
        val password: String = mPassword!!.text.toString()
        val name: String = mName!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(name)) {
            Toast.makeText(applicationContext, "Enter Name!", Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(applicationContext, "Invalid Email Address!", Toast.LENGTH_SHORT).show()
        } else if (password.length < 6) {
            Toast.makeText(
                applicationContext,
                "Password too short, enter minimum 6 characters!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    progressBar!!.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(
                            this@SignupActivity,
                            "Sign Up Successful!",
                            Toast.LENGTH_SHORT
                        ).show()
                        createDataBase(name)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this@SignupActivity,
                            "Authentication Failed. " + task.exception,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}