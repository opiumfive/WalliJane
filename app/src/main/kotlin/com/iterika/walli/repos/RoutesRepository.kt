package com.iterika.walli.repos

import android.app.Application
import com.google.android.gms.maps.model.LatLng
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode
import com.iterika.walli.R
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle
import org.joda.time.DateTime
import javax.inject.Inject

class RoutesRepository @Inject constructor(val application : Application) {

    fun getRoutes(list: List<LatLng>): Single<DirectionsResult> {

        val size = list.size

        val request = DirectionsApi.newRequest(GeoApiContext.Builder().apiKey(application.getString(R.string.google_api_key_maps)).build())
                .mode(TravelMode.DRIVING)
                .departureTime(DateTime())

        when(size) {
            0, 1 -> return Single.error(Exception("Empty"))
            2    -> return request
                        .origin(com.google.maps.model.LatLng(list[0].latitude, list[0].longitude))
                        .destination(com.google.maps.model.LatLng(list[1].latitude, list[1].longitude))
                        .await()
                        .toSingle()
            else -> {
                val newList = mutableListOf<com.google.maps.model.LatLng>()
                list.forEachIndexed { index, latLng -> if (index != 0 && index != size - 1) newList.add(com.google.maps.model.LatLng(latLng.latitude, latLng.longitude)) }
                return request
                        .origin(com.google.maps.model.LatLng(list[0].latitude, list[0].longitude))
                        .waypoints(*newList.toTypedArray())
                        .destination(com.google.maps.model.LatLng(list[size - 1].latitude, list[size - 1].longitude))
                        .await()
                        .toSingle()
            }
        }
    }
}