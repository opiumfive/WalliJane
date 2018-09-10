package com.iterika.walli.model

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.iterika.walli.STATIC_MAPS_KEY

data class Requests(val status: Int?, val result: RequestsResult?)

data class RequestsResult(val data: List<Request>?,
                          val suitcase: List<Product>)

data class Request(@SerializedName("ID") var id: String,
                   @SerializedName("NAME") var name: String,
                   @SerializedName("LAT") val lat: Double = 0.0,
                   @SerializedName("LNG") val lng: Double = 0.0,
                   @SerializedName("STATUS") val status: String,
                   @SerializedName("PRICE_FORMATED") val price: String,
                   @SerializedName("ADDRESS") val address: String,
                   @SerializedName("DATE_CREATE") val date: String,
                   @SerializedName("PRODUCTS") val products: List<Product>,
                   @SerializedName("PHOTO") val photo: String,
                   @SerializedName("PASSPORT_ID") val passportId: String,
                   @SerializedName("DELIVERY_TYPE") val deliveryType: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(Product),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    fun generateMapUrl() = "http://maps.google.com/maps/api/staticmap?center=$lat,$lng&zoom=15&size=600x200&sensor=true&key=$STATIC_MAPS_KEY&markers=color:0x517b93%7Clabel:%7C$lat,$lng"

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
        parcel.writeString(status)
        parcel.writeString(price)
        parcel.writeString(address)
        parcel.writeString(date)
        parcel.writeTypedList(products)
        parcel.writeString(photo)
        parcel.writeString(passportId)
        parcel.writeString(deliveryType)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Request> {
        override fun createFromParcel(parcel: Parcel) = Request(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Request?>(size)
    }
}

data class Product(@SerializedName("ID") var id: String,
                   @SerializedName("NAME") var name: String,
                   @SerializedName("PRICE") var price: String,
                   @SerializedName("QUANTITY") var quantity: String) :Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(quantity)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel) = Product(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Product>(size)
    }

    override fun equals(other: Any?) = if (other != null && other is Product) this.id.equals(other.id) else false
}
