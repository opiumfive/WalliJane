package com.iterika.walli.domain

import com.google.android.gms.maps.model.LatLng
import com.iterika.walli.repos.RoutesRepository
import javax.inject.Inject

class RoutesInteractor @Inject constructor(private val repository: RoutesRepository) {

    fun getRoutes(list: List<LatLng>) = repository.getRoutes(list)

}