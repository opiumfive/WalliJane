package com.iterika.walli.presentation.request.detail

import android.location.Location
import com.iterika.walli.model.Request
import com.iterika.walli.presentation.IView

interface IRequestDetView : IView {
    fun showRequestDetails(request: Request?, location: Location)
}