package me.fusan.pocketkaraoke.ui.authentication.model

import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import me.fusan.pocketkaraoke.ui.authentication.controller.ForgotPasswordController

class ForgotPasswordModel(forgotPasswordController_: ForgotPasswordController) {
    var forgotPasswordController: ForgotPasswordController = forgotPasswordController_
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun doReset(email: String) {
        if (TextUtils.isEmpty(email)) {
            forgotPasswordController.returnEmailEmptyMessage()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgotPasswordController.returnEmailNotValidMessage()
        }
        else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (!task.isSuccessful) forgotPasswordController.returnUnsuccessfulMessage(task.exception.toString())
                    else forgotPasswordController.returnSuccessfulMessage()
            }
        }
    }
}