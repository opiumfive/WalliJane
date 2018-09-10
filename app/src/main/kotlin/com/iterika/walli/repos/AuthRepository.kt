package com.iterika.walli.repos

import com.iterika.walli.Prefs
import com.iterika.walli.net.Api
import io.reactivex.Single
import javax.inject.Inject

class AuthRepository @Inject constructor(val api: Api, val prefs: Prefs) {

    fun saveToken(token: String) = prefs.setToken(token)

    fun getToken() = Single.just(prefs.getToken())

    fun login(login: String, pass: String) = api.service.login(login, pass)

    fun signUp(login: String, pass: String) = api.service.login(login, pass)

    fun recover(login: String) = Single.just(Any())
}
