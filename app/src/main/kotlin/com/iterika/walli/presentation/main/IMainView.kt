package com.iterika.walli.presentation.main

import com.iterika.walli.presentation.IView

interface IMainView : IView {
    fun selectTab(currentFragment: CurrentFragment?)
    fun enableTabs(enable: Boolean)
    fun showBack(enable: Boolean)
}
