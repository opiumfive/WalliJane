package com.iterika.walli.net

import com.iterika.walli.model.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IApi {

    @GET("authorize/?")
    fun login(@Query("login") login : String,
              @Query("password") password : String): Single<Login>

    @GET("profile/?")
    fun profile(@Query("token") token : String): Single<UserData>

    @GET("startwork/?")
    fun startWork(@Query("token") token : String): Single<MessageData>

    @GET("breakTime/?")
    fun breakTime(@Query("token") token : String): Single<MessageData>

    @GET("acceptOrder/?")
    fun acceptOrder(@Query("token") token : String,
                    @Query("orderId") orderId : String): Single<MessageData>

    @GET("declineOrder/?")
    fun declineOrder(@Query("token") token : String,
                     @Query("orderId") orderId : String): Single<MessageData>

    @GET("getCourierOrder/?")
    fun requests(@Query("token") token : String): Single<Requests>

    @GET("updateGeo/?")
    fun updateGeo(@Query("token") token : String,
                  @Query("lat") lat : String,
                  @Query("lng") lng : String): Single<MessageData>

    @GET("cancelOrder/?")
    fun cancelOrder(@Query("token") token : String,
                    @Query("orderId") orderId : String,
                    @Query("description") description : String): Single<MessageData>

    @GET("finishOrder/?")
    fun finishOrder(@Query("token") token : String,
                    @Query("orderId") orderId : String): Single<MessageDataShort>
}