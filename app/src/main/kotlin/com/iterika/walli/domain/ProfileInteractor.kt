package com.iterika.walli.domain

import com.iterika.walli.repos.ProfileRepository
import javax.inject.Inject

class ProfileInteractor @Inject constructor(private val repository: ProfileRepository) {

    fun getUserData() = repository.getUserData()

    fun start() = repository.startDay()

    fun stop() = repository.stopDay()
}