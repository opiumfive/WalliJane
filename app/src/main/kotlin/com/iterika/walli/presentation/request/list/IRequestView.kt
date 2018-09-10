package com.iterika.walli.presentation.request.list

import android.location.Location
import com.iterika.walli.model.Request
import com.iterika.walli.presentation.IView

interface IRequestView :IView {
    fun showList(list: List<Request>, location: Location)
    fun showRefreshing(b: Boolean)
}