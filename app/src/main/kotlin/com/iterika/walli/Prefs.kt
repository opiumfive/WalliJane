package com.iterika.walli

import android.content.Context
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences
import com.iterika.walli.presentation.profile.WorkdayState

class Prefs(val context: Context?) {

    var preferences: Preferences? = null

    init {
       preferences = BinaryPreferencesBuilder(context).build()
    }

    fun getToken() = preferences?.getString("token", "")

    fun setToken(token : String) = preferences?.edit()?.putString("token", token)?.commit()

    fun getLastLat() = preferences?.getString("lat", "0.0")

    fun getLastLng() = preferences?.getString("lng", "0.0")

    fun setLat(lat : String) = preferences?.edit()?.putString("lat", lat)?.commit()

    fun setLng(lng : String) = preferences?.edit()?.putString("lng", lng)?.commit()

    fun setFilter(filter : String) = preferences?.edit()?.putString("filter", filter)?.commit()

    fun getFilter() = preferences?.getString("filter", "All")
}