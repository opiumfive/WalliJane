package com.iterika.walli.presentation

import android.support.annotation.StringRes
import com.iterika.walli.App

interface IView {
    fun application(): App
    fun showMessage(message: String)
    fun showMessage(@StringRes messageResId: Int)
}

interface IPresenter<in V : IView> {
    fun takeView(view: V)
    fun dropView()
}
