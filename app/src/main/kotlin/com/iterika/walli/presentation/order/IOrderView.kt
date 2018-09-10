package com.iterika.walli.presentation.order

import android.location.Location
import com.iterika.walli.model.Request
import com.iterika.walli.presentation.IView

interface IOrderView : IView {
    fun viewOrder(order: Request?, location: Location)
}