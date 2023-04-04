package me.fusan.pocketkaraoke.ui.authentication.controller

import me.fusan.pocketkaraoke.ui.authentication.ForgotPasswordActivity
import me.fusan.pocketkaraoke.ui.authentication.model.ForgotPasswordModel

class ForgotPasswordController(forgotPasswordActivity_: ForgotPasswordActivity) {
    var forgotPasswordModel: ForgotPasswordModel = ForgotPasswordModel(this)
    var forgotPasswordActivity: ForgotPasswordActivity = forgotPasswordActivity_

    fun doPasswordReset(email: String) {
        forgotPasswordModel.doReset(email)
    }

    fun returnSuccessfulMessage() {
        forgotPasswordActivity.returnSuccessfulToast()
    }

    fun returnUnsuccessfulMessage(taskException: String) {
        forgotPasswordActivity.returnUnsuccessfulToast(taskException)
    }

    fun returnEmailEmptyMessage() {
        forgotPasswordActivity.returnEmailEmptyToast()
    }

    fun returnEmailNotValidMessage() {
        forgotPasswordActivity.returnEmailNotValidToast()
    }
}