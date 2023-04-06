package me.fusan.pocketkaraoke.ui.authentication.presenter

import android.text.TextUtils
import android.util.Patterns
import me.fusan.pocketkaraoke.ui.authentication.LoginActivity
import me.fusan.pocketkaraoke.ui.authentication.model.LoginViewModel

class LoginPresenter (loginActivity_: LoginActivity) {
    var loginViewModel: LoginViewModel = LoginViewModel(this)
    private var loginActivity: LoginActivity = loginActivity_

    fun doSignin(email: String, password: String) {
        if (TextUtils.isEmpty(email)) {
            loginActivity.returnEmailEmptyToast()
        } else if (TextUtils.isEmpty(password)) {
            loginActivity.returnPasswordEmptyToast()
        } else if (password.length < 6) {
            loginActivity.returnPasswordLessThanSixToast()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginActivity.returnEmailNotValidToast()
        } else {        //FireBase Login
            loginActivity.progressbarStatus(true)
            loginViewModel.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(loginActivity) { task ->
                loginActivity.progressbarStatus(false)
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    loginActivity.returnSuccessfulToast()
                    loginActivity.gotoMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    loginActivity.returnUnsuccessfulToast(task.exception.toString())
                }
            }
        }
    }

    fun bypassLoginForExistingUser() {
        val currentUser = loginViewModel.auth.currentUser
        if(currentUser != null) {
            loginActivity.returnSuccessfulToast()
            loginActivity.gotoMainActivity()
        }
    }
}