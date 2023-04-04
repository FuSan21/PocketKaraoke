package me.fusan.pocketkaraoke.ui.authentication.model

import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import me.fusan.pocketkaraoke.ui.authentication.controller.LoginController

class LoginModel(loginController_: LoginController) {
    var loginController: LoginController = loginController_
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun doSignin(email: String, password: String) {
        if (TextUtils.isEmpty(email)) {
            loginController.returnEmailEmptyMessage()
        } else if (TextUtils.isEmpty(password)) {
            loginController.returnPasswordEmptyMessage()
        } else if (password.length < 6) {
            loginController.returnPasswordLessThanSixMessage()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginController.returnEmailNotValidMessage()
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(loginController.loginActivity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    loginController.returnSuccessfulMessage()
                    loginController.gotoMainActivity()
                } else {
                    // If sign in fails, display a message to the user.

                    loginController.returnUnsuccessfulMessage(task.exception.toString())
                }
            }
        }
    }
}