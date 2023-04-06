package me.fusan.pocketkaraoke.ui.authentication.model

import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import me.fusan.pocketkaraoke.ui.authentication.presenter.ForgotPasswordPresenter

class ForgotPasswordViewModel(forgotPasswordPresenter_: ForgotPasswordPresenter) {
    private var forgotPasswordPresenter: ForgotPasswordPresenter = forgotPasswordPresenter_
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
}