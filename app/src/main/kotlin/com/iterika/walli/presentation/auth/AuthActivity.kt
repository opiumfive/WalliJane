package com.iterika.walli.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.iterika.walli.R
import com.iterika.walli.TAB_NUM_FAQ
import com.iterika.walli.ext.*
import com.iterika.walli.presentation.Navigation
import com.iterika.walli.presentation.Screens
import com.iterika.walli.presentation.BaseActivity
import com.iterika.walli.presentation.auth.AuthViewNum.BACK
import com.iterika.walli.presentation.auth.AuthViewNum.BOTTOM_BUT
import com.iterika.walli.presentation.auth.AuthViewNum.CONFIRM_EDIT
import com.iterika.walli.presentation.auth.AuthViewNum.LOGIN_BUTTON
import com.iterika.walli.presentation.auth.AuthViewNum.LOGIN_EDIT
import com.iterika.walli.presentation.auth.AuthViewNum.MIDDLE_BUT
import com.iterika.walli.presentation.auth.AuthViewNum.PASS_EDIT
import com.iterika.walli.presentation.auth.AuthViewNum.RECOVER
import com.iterika.walli.presentation.auth.AuthViewNum.SIGNUP_BUTTON
import com.iterika.walli.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.yesButton
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject

class AuthActivity : BaseActivity(), IAuthView {

    @Inject lateinit var presenter: AuthPresenter
    lateinit var views : List<View>

    override val navigator: Navigator
        get() = object : SupportAppNavigator(this, 0) {
            override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? = when (screenKey) {
                Screens.FAQ     -> intentFor<MainActivity>("pageNum" to TAB_NUM_FAQ, "showOnlyFaq" to true)
                Screens.PROFILE -> intentFor<MainActivity>()
                else            -> null
            }

            override fun createFragment(screenKey: String?, data: Any?) = null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        views = listOf<View>(onSignup, onLogin, loginEdit, passEdit, confirmPassEdit, onBack, onRecover, onMiddleButton, onBottomButton)

        presenter.takeView(this)

        registerListeners()
    }

    private fun registerListeners() {
        onLogin.setOnClickListener {
            presenter.onLogin(loginEdit.content(), passEdit.content())
        }

        onSignup.setOnClickListener {
            presenter.onSignUp(loginEdit.content(), passEdit.content())
        }

        onRecover.setOnClickListener {
            presenter.onRecover(loginEdit.content())
        }

        onBack.setOnClickListener { onBackPressed() }

        onBottomButton.setOnClickListener {
            presenter.onBottomButton()
        }

        onMiddleButton.setOnClickListener {
            presenter.onMiddleButton()
        }

        val watcher = { checkViewsEnabled() }

        loginEdit.onJustTextChanged(watcher)
        passEdit.onJustTextChanged(watcher)
        confirmPassEdit.onJustTextChanged(watcher)
    }

    override fun onBackPressed() {
        presenter.onBack()
    }

    override fun applyAuthState(state: AuthState) {
        when(state) {
            AuthState.COMMON -> {
                setVisibleViews(SIGNUP_BUTTON or LOGIN_BUTTON or BOTTOM_BUT)
                screenName.text = ""
                onBottomButton.text = getString(R.string.faq)
            }
            AuthState.AUTH -> {
                setVisibleViews(LOGIN_BUTTON or LOGIN_EDIT or PASS_EDIT or BOTTOM_BUT)
                screenName.text = getString(R.string.login)
                //onMiddleButton.text = getString(R.string.registraion)
                onBottomButton.text = getString(R.string.faq)
            }
            AuthState.REG -> {
                setVisibleViews(SIGNUP_BUTTON or LOGIN_EDIT or PASS_EDIT or CONFIRM_EDIT or BACK or BOTTOM_BUT)
                screenName.text = getString(R.string.registraion)
                onBottomButton.text = getString(R.string.login)
            }
            AuthState.RECOVER -> {
                setVisibleViews(LOGIN_EDIT or RECOVER or BACK)
                screenName.text = getString(R.string.recover_pass)
            }
        }
    }

    override fun checkViewsEnabled() {
        presenter.onTextChanged(loginEdit.content(), passEdit.content(), confirmPassEdit.content())
    }

    override fun enableView(viewNum: Int, enable: Boolean) {
        when (viewNum) {
            SIGNUP_BUTTON   -> onSignup.enable(enable)
            LOGIN_BUTTON    -> onLogin.enable(enable)
            RECOVER         -> onRecover.enable(enable)
        }
    }

    override fun setVisibleViews(flags: Int) {
        views.forEachIndexed { i, view ->  view?.setVisibility(flags.getKBit(i)) }
    }

    override fun showSentEmailDialog(login: String) {
        alert(getString(R.string.email_sent, login)) {
            yesButton { dialog ->
                dialog.dismiss()
                presenter.onOkFromSentEmailDialog()
            }
        }.show()
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }


}
