package com.iterika.walli.presentation.request.routes

import com.google.android.gms.maps.model.LatLng
import com.google.maps.model.DirectionsResult
import com.iterika.walli.presentation.IView

interface IRoutesView : IView {
    fun showRoute(result: DirectionsResult)
    fun showMarkers(list: List<LatLng>)
    fun moveCamera(list: List<LatLng>)
}