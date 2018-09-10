package com.iterika.walli.presentation.profile

import com.iterika.walli.model.Profile
import com.iterika.walli.presentation.IView

interface IProfileView : IView {
    fun setStarted()
    fun setNotStarted()
    fun setPaused()
    fun showProfile(profile : Profile)
    fun showTabs(b: Boolean)
}