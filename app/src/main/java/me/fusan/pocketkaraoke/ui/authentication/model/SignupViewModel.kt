package me.fusan.pocketkaraoke.ui.authentication.model

import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import me.fusan.pocketkaraoke.ui.authentication.presenter.SignupPresenter

class SignupViewModel (signupPresenter_: SignupPresenter) {
    private var signupPresenter: SignupPresenter = signupPresenter_
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createDataBase(name: String, email: String) {
        val userID = auth.currentUser!!.uid
        val user = User(email)
        //ref?.child("Users")?.child(userID)?.setValue(user)
        //ref?.child("UserChallenges")?.child(userID)
    }
}