package com.iterika.walli.presentation.ink

import com.iterika.walli.presentation.IView

interface IInkView : IView {

    fun clearInk()
    fun enableNextButton(enable : Boolean)
}
