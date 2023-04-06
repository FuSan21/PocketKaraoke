package me.fusan.pocketkaraoke.ui.authentication.presenter

import android.text.TextUtils
import android.util.Patterns
import me.fusan.pocketkaraoke.ui.authentication.ForgotPasswordActivity
import me.fusan.pocketkaraoke.ui.authentication.model.ForgotPasswordViewModel

class ForgotPasswordPresenter(forgotPasswordActivity_: ForgotPasswordActivity) {
    var forgotPasswordViewModel: ForgotPasswordViewModel = ForgotPasswordViewModel(this)
    private var forgotPasswordActivity: ForgotPasswordActivity = forgotPasswordActivity_

    fun doPasswordReset(email: String) {
        if (TextUtils.isEmpty(email)) {
            forgotPasswordActivity.returnEmailEmptyToast()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgotPasswordActivity.returnEmailNotValidToast()
        } else {
            forgotPasswordActivity.progressbarStatus(true)
            forgotPasswordViewModel.auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                forgotPasswordActivity.progressbarStatus(false)
                if (task.isSuccessful) forgotPasswordActivity.returnSuccessfulToast()
                else forgotPasswordActivity.returnUnsuccessfulToast(task.exception.toString())
            }
        }
    }
}