package me.fusan.pocketkaraoke.ui.authentication.presenter

import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import me.fusan.pocketkaraoke.ui.authentication.LoginActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(value = Parameterized::class)
@SmallTest
class LoginPresenterTest(val email: String, val password: String, val expectedResult: Int) {
    private lateinit var loginPresenter: LoginPresenter

    @JvmField
    @Rule
    var loginActivity: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)
    @Before
    fun createLoginPresenter() {
        loginPresenter = loginActivity.activity.loginPresenter
    }

    @Test
    fun doSigninTest() {
        val result = loginPresenter.doSignin(email, password)
        assertEquals(expectedResult, result)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "Email: {0} Password: {1} Status: {2}")
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf("","",-1),
                arrayOf("sadas","",-2),
                arrayOf("sadas","sdaw",-3),
                arrayOf("sadas","saASDdsdWsf",-4),
                arrayOf("sadas@gmail.com","saASDdsdWsf",1)
            )
        }
    }
}
