package com.iterika.walli.presentation.splash

import com.iterika.walli.SPLASH_DELAY
import com.iterika.walli.domain.SplashInteractor
import com.iterika.walli.ext.observeAsync
import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import com.iterika.walli.presentation.Screens
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter @Inject constructor() : BasePresenter<ISplashView>() {

    @Inject lateinit var navigation: Navigation
    @Inject lateinit var interactor: SplashInteractor

    fun onScreenStart() {
        interactor.loadData()
                .delay(SPLASH_DELAY, TimeUnit.MILLISECONDS)
                .observeAsync()
                .subscribe(
                        {
                            if (it.isNullOrEmpty()) {
                                navigation.getRouter().replaceScreen(Screens.AUTH)
                            } else {
                                navigation.getRouter().replaceScreen(Screens.PROFILE)
                            }
                        },
                        { navigation.getRouter().replaceScreen(Screens.AUTH) }
                )
                .connect()
    }
}