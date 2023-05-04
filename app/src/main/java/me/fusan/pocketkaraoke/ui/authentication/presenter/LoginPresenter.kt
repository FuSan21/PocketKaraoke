package me.fusan.pocketkaraoke.ui.authentication.presenter

import android.text.TextUtils
import android.util.Patterns
import me.fusan.pocketkaraoke.ui.authentication.LoginActivity
import me.fusan.pocketkaraoke.ui.authentication.model.LoginViewModel
import me.fusan.pocketkaraoke.ui.authentication.model.User

class LoginPresenter (loginActivity_: LoginActivity) {
    var loginViewModel: LoginViewModel = LoginViewModel(this)
    private var loginActivity: LoginActivity = loginActivity_

    fun doSignin(email: String, password: String):Int {
        if (TextUtils.isEmpty(email)) {
            return -1
        } else if (TextUtils.isEmpty(password)) {
            return -2
        } else if (password.length < 6) {
            return -3
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return -4
        } else {
            //FireBase Login
            loginViewModel.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(loginActivity) { task ->
                loginActivity.progressbarStatus(false)
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    User(email)
                    loginActivity.returnSuccessfulToast()
                    loginActivity.gotoMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    loginActivity.returnUnsuccessfulToast(task.exception.toString())
                }
            }
            return 1
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