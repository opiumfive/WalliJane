package com.iterika.walli.domain

import android.location.Location

class LocationDistanceCalculator(private val averageSpeed: Float = 0f, // mph
                                 private val locationFrom: Location? = Location("me"),
                                 private val locationTo: Location = Location("to")) {

    val dist by lazy { (locationFrom?.distanceTo(locationTo) ?: 0f) / 1.6 / 1000 }

    fun getDistance() = dist.toInt().toString()

    fun getTimeToLocation(): String {
        val mins = (dist / averageSpeed * 60).toInt()
        return "${mins / 60}:${mins % 60}"
    }

}