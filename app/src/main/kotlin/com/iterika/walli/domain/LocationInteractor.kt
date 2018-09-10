package com.iterika.walli.domain

import android.location.Location
import com.iterika.walli.repos.LocationRepository
import javax.inject.Inject

class LocationInteractor @Inject constructor(private val repository: LocationRepository) {

    fun initiateLocationUpdate() = repository.getLocation()

    fun getLastLocation() = repository.getLastLocation()

    fun setLastLocation(location: Location) = repository.setLastLocation(location)
}