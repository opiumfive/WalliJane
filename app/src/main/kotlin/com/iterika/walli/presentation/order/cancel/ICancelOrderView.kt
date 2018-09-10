package com.iterika.walli.presentation.order.cancel

import com.iterika.walli.presentation.IView

interface ICancelOrderView : IView {
    fun enableButton(b: Boolean)
    fun close()
}