package me.fusan.pocketkaraoke.ui.authentication.model

import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import me.fusan.pocketkaraoke.ui.authentication.controller.SignupController

class SignupModel (signupController_: SignupController) {
    var signupController: SignupController = signupController_
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createDataBase(name: String?) {
//        val user = User(name, mEmail!!.text.toString())
//        val userID = auth!!.currentUser!!.uid
//        ref?.child("Users")?.child(userID)?.setValue(user)
//        ref?.child("UserChallenges")?.child(userID)
    }

    fun doSignup(email: String, password: String, name: String) {
        if (TextUtils.isEmpty(email)) {
            signupController.returnEmailEmptyMessage()
        } else if (TextUtils.isEmpty(password)) {
            signupController.returnPasswordEmptyMessage()
        } else if (TextUtils.isEmpty(name)) {
            signupController.returnNameEmptyMessage()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupController.returnEmailNotValidMessage()
        } else if (password.length < 6) {
            signupController.returnPasswordLessThanSixMessage()
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signupController.signupActivity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        signupController.returnSuccessfulMessage()
                        createDataBase(name)
                    } else {
                        // If sign in fails, display a message to the user.
                        signupController.returnUnsuccessfulMessage(task.exception.toString())
                    }
                }
        }
    }
}