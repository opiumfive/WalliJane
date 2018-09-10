package com.iterika.walli.repos

import com.iterika.walli.Prefs
import com.iterika.walli.net.Api
import com.iterika.walli.presentation.profile.WorkdayState
import javax.inject.Inject

class ProfileRepository @Inject constructor(val api: Api, val authRepository: AuthRepository) {

    fun getUserData() = api.service.profile(authRepository.getToken().blockingGet() ?: "")

    fun startDay() = api.service.startWork(authRepository.getToken().blockingGet() ?: "")

    fun stopDay() = api.service.breakTime(authRepository.getToken().blockingGet() ?: "")
}