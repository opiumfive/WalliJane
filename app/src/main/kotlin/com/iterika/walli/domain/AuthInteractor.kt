package com.iterika.walli.domain

import com.iterika.walli.repos.AuthRepository
import javax.inject.Inject

class AuthInteractor @Inject constructor(private val repository: AuthRepository) {

    fun setToken(token: String) = repository.saveToken(token)

    fun getToken() = repository.getToken()

    fun login(login: String, pass: String) = repository.login(login, pass)

    fun signUp(login: String, pass: String) = repository.signUp(login, pass)

    fun recover(login: String) = repository.recover(login)
}
