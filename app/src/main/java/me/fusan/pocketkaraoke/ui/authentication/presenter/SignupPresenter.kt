package me.fusan.pocketkaraoke.ui.authentication.presenter

import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import me.fusan.pocketkaraoke.ui.authentication.SignupActivity
import me.fusan.pocketkaraoke.ui.authentication.model.SignupViewModel


class SignupPresenter (signupActivity_: SignupActivity) {
    var signupViewModel: SignupViewModel = SignupViewModel(this)
    private var signupActivity: SignupActivity = signupActivity_


    fun doSignup(email: String, password: String, name: String) {
        if (TextUtils.isEmpty(email)) {
            signupActivity.returnEmailEmptyToast()
        } else if (TextUtils.isEmpty(password)) {
            signupActivity.returnPasswordEmptyToast()
        } else if (TextUtils.isEmpty(name)) {
            signupActivity.returnNameEmptyToast()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupActivity.returnEmailNotValidToast()
        } else if (password.length < 6) {
            signupActivity.returnPasswordLessThanSixToast()
        } else {
            signupActivity.progressbarStatus(true)
            signupViewModel.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signupActivity) { task ->
                signupActivity.progressbarStatus(false)
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    signupActivity.returnSuccessfulToast()
                    signupViewModel.createDataBase(name, email)
                    signupActivity.gotoMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    signupActivity.returnUnsuccessfulToast(task.exception.toString())
                }
            }
        }
    }
}