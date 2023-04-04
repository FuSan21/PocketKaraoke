package me.fusan.pocketkaraoke.ui.authentication.controller

import me.fusan.pocketkaraoke.ui.authentication.LoginActivity
import me.fusan.pocketkaraoke.ui.authentication.model.LoginModel

class LoginController (loginactivity_: LoginActivity) {
    var loginModel: LoginModel = LoginModel(this)
    var loginActivity: LoginActivity = loginactivity_

    fun doSignin(email: String, password: String) {
        loginModel.doSignin(email, password)
    }

    fun returnEmailEmptyMessage() {
        loginActivity.returnEmailEmptyToast()
    }

    fun returnPasswordEmptyMessage() {
        loginActivity.returnPasswordEmptyToast()
    }

    fun returnEmailNotValidMessage() {
        loginActivity.returnEmailNotValidToast()
    }

    fun returnPasswordLessThanSixMessage() {
        loginActivity.returnPasswordLessThanSixToast()
    }

    fun returnSuccessfulMessage() {
        loginActivity.returnSuccessfulToast()
    }

    fun returnUnsuccessfulMessage(taskException: String) {
        loginActivity.returnUnsuccessfulToast(taskException)
    }

    fun gotoMainActivity() {
        loginActivity.gotoMainActivity()
    }
}