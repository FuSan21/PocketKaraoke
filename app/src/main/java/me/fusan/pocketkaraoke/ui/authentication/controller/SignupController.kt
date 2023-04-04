package me.fusan.pocketkaraoke.ui.authentication.controller

import me.fusan.pocketkaraoke.ui.authentication.SignupActivity
import me.fusan.pocketkaraoke.ui.authentication.model.SignupModel


class SignupController (signupActivity_: SignupActivity) {
    var signupModel: SignupModel = SignupModel(this)
    var signupActivity: SignupActivity = signupActivity_



    fun returnEmailEmptyMessage() {
        signupActivity.returnEmailEmptyToast()
    }

    fun returnPasswordEmptyMessage() {
        signupActivity.returnPasswordEmptyToast()
    }

    fun returnNameEmptyMessage() {
        signupActivity.returnNameEmptyToast()
    }

    fun returnEmailNotValidMessage() {
        signupActivity.returnEmailNotValidToast()
    }

    fun returnPasswordLessThanSixMessage() {
        signupActivity.returnPasswordLessThanSixToast()
    }

    fun returnSuccessfulMessage() {
        signupActivity.returnSuccessfulToast()
    }

    fun returnUnsuccessfulMessage(taskException: String) {
        signupActivity.returnUnsuccessfulToast(taskException)
    }

    fun doSignup(email: String, password: String, name: String) {
        signupModel.doSignup(email, password, name)
    }


}