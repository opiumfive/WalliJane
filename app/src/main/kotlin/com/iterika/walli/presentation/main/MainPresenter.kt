package com.iterika.walli.presentation.main

import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import com.iterika.walli.presentation.Screens
import javax.inject.Inject

class MainPresenter @Inject constructor() : BasePresenter<IMainView>() {

    @Inject lateinit var navigation: Navigation
    var currentFragment: CurrentFragment? = null

    fun onProfile() {
        if (currentFragment == CurrentFragment.PROFILE) return
        currentFragment = CurrentFragment.PROFILE
        navigation.getRouter().newRootScreen(Screens.TAB_PROFILE)
        view?.selectTab(currentFragment)
    }

    fun onRequests() {
        if (currentFragment == CurrentFragment.HOME) return
        currentFragment = CurrentFragment.HOME
        navigation.getRouter().newRootScreen(Screens.TAB_HOME)
        view?.selectTab(currentFragment)
    }

    fun onFaq() {
        if (currentFragment == CurrentFragment.FAQ) return
        currentFragment = CurrentFragment.FAQ
        navigation.getRouter().newRootScreen(Screens.TAB_FAQ)
        view?.selectTab(currentFragment)
    }

    fun onShowOnlyFaq() {
        view?.enableTabs(false)
        view?.showBack(true)
    }

    fun onBack() {
        navigation.getRouter().exit()
    }
}
