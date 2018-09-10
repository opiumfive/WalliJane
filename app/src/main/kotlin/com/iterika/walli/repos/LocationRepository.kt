package com.iterika.walli.repos

import android.app.Application
import android.content.Context
import android.location.Location
import com.iterika.walli.App
import com.iterika.walli.Prefs
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.rx.ObservableFactory
import javax.inject.Inject

class LocationRepository @Inject constructor(val application : Application, val prefs : Prefs) {

    fun getLocation() = ObservableFactory.from(SmartLocation.with(application).location())

    fun getLastLocation() : Location {
        val location = Location("me")
        location.latitude = prefs.getLastLat()?.toDouble() ?: 0.0
        location.longitude = prefs.getLastLng()?.toDouble() ?: 0.0
        return location
    }

    fun setLastLocation(location: Location) {
        prefs.setLat(location.latitude.toString())
        prefs.setLng(location.longitude.toString())
    }
}