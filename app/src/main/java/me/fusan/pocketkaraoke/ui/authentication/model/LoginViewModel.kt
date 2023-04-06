package me.fusan.pocketkaraoke.ui.authentication.model

import com.google.firebase.auth.FirebaseAuth
import me.fusan.pocketkaraoke.ui.authentication.presenter.LoginPresenter

class LoginViewModel(loginPresenter_: LoginPresenter) {
    private var loginPresenter: LoginPresenter = loginPresenter_
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
}