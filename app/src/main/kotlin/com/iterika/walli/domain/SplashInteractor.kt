package com.iterika.walli.domain

import com.iterika.walli.repos.AuthRepository
import javax.inject.Inject

class SplashInteractor @Inject constructor(private val authRepository: AuthRepository) {

    fun loadData() = authRepository.getToken()
}
