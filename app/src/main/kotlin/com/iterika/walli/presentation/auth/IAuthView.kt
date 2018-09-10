package com.iterika.walli.presentation.auth

import com.iterika.walli.presentation.IView

interface IAuthView : IView {
    fun setVisibleViews(flags: Int)
    fun applyAuthState(state: AuthState)
    fun checkViewsEnabled()
    fun enableView(viewNum: Int, enable: Boolean)
    fun showSentEmailDialog(login: String)
}