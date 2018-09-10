package com.iterika.walli.presentation.auth

import com.iterika.walli.domain.AuthInteractor
import com.iterika.walli.ext.isEmailValid
import com.iterika.walli.ext.observeAsync
import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import com.iterika.walli.presentation.Screens
import javax.inject.Inject

class AuthPresenter @Inject constructor() : BasePresenter<IAuthView>() {

    @Inject lateinit var navigation: Navigation
    @Inject lateinit var interactor: AuthInteractor
    var currentState: AuthState = AuthState.AUTH

    override fun takeView(view: IAuthView) {
        super.takeView(view)
        applyStateToView(currentState)
    }

    fun onBack() {
        //if (!currentState.equals(AuthState.COMMON)) applyStateToView(AuthState.COMMON) else navigation.getRouter().exit()
        navigation.getRouter().exit()
    }

    fun onBottomButton() {
        when(currentState) {
            AuthState.REG -> applyStateToView(AuthState.AUTH)
            AuthState.COMMON -> navigation.getRouter().navigateTo(Screens.FAQ)
            AuthState.AUTH -> navigation.getRouter().navigateTo(Screens.FAQ)//applyStateToView(AuthState.RECOVER)
            else -> applyStateToView(AuthState.COMMON)
        }
    }

    fun onMiddleButton() {
        when(currentState) {
            AuthState.AUTH -> applyStateToView(AuthState.REG)
            else -> Unit
        }
    }

    fun onLogin(login: String, pass: String) {
        when(currentState) {
            AuthState.AUTH -> {
                interactor.login(login, pass)
                        .observeAsync()
                        .subscribe(
                                {
                                    if (it.result?.data?.token != null) {
                                        interactor.setToken(it.result.data.token)
                                        navigation.getRouter().replaceScreen(Screens.PROFILE)
                                    } else {
                                        view?.showMessage(it.result?.data?.error ?: "Error")
                                    }
                                },
                                {
                                    view?.showMessage("Network error")
                                }
                        )
                        .connect()
            }
            else -> applyStateToView(AuthState.AUTH)
        }
    }

    fun onRecover(login: String) {
        when(currentState) {
            AuthState.RECOVER -> {
                interactor.recover(login)
                        .observeAsync()
                        .subscribe(
                                { view?.showSentEmailDialog(login) },
                                { }
                        )
                        .connect()
            }
            else -> applyStateToView(AuthState.RECOVER)
        }
    }

    fun onSignUp(login: String, pass: String) {
        when(currentState) {
            AuthState.REG -> {
                interactor.signUp(login, pass)
                        .observeAsync()
                        .subscribe(
                                { navigation.getRouter().replaceScreen(Screens.PROFILE) },
                                { }
                        )
                        .connect()
            }
            else -> applyStateToView(AuthState.REG)
        }
    }

    fun applyStateToView(state: AuthState) {
        view?.applyAuthState(state)
        currentState = state
        view?.checkViewsEnabled()
    }

    fun onOkFromSentEmailDialog() {
        applyStateToView(AuthState.AUTH)
    }

    fun onTextChanged(login: String, pass: String, confirmPass: String) {
        val isLoginValid = login.isNotEmpty()
        val isPasswordValid = pass.isNotEmpty()
        val isPasswordConfirmed = pass.equals(confirmPass)

        when (currentState) {
            AuthState.COMMON -> {
                view?.enableView(AuthViewNum.SIGNUP_BUTTON, true)
                view?.enableView(AuthViewNum.LOGIN_BUTTON, true)
            }
            AuthState.AUTH -> {
                view?.enableView(AuthViewNum.LOGIN_BUTTON, isLoginValid and isPasswordValid)
            }
            AuthState.REG -> {
                view?.enableView(AuthViewNum.SIGNUP_BUTTON, isLoginValid and isPasswordValid and isPasswordConfirmed)
            }
            AuthState.RECOVER -> {
                view?.enableView(AuthViewNum.RECOVER, isLoginValid)
            }
        }
    }
}
