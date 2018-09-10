package com.iterika.walli.model

import android.os.Parcel
import android.os.Parcelable

data class Workday(var time: String) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) = parcel.writeString(time)

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Workday> {
        override fun createFromParcel(parcel: Parcel) = Workday(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Workday?>(size)
    }
}
