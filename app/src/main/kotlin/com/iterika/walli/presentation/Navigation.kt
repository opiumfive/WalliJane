package com.iterika.walli.presentation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class Navigation @Inject constructor() {

    var cicerone : Cicerone<Router>

    init {
        cicerone = Cicerone.create()
    }

    fun getRouter() = cicerone.router
    fun getNaviHolder() = cicerone.navigatorHolder
}
