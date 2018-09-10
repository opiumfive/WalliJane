package com.iterika.walli.presentation.request.routes

import com.google.android.gms.maps.model.LatLng
import com.iterika.walli.domain.LocationInteractor
import com.iterika.walli.domain.RequestInteractor
import com.iterika.walli.domain.RoutesInteractor
import com.iterika.walli.ext.observeAsync
import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import javax.inject.Inject

class RoutesPresenter @Inject constructor() : BasePresenter<IRoutesView>() {

    @Inject lateinit var navigation: Navigation
    @Inject lateinit var requestInteractor: RequestInteractor
    @Inject lateinit var routesInteractor: RoutesInteractor
    @Inject lateinit var locationInteractor: LocationInteractor

    fun onReadyToLoadRequests() {
        requestInteractor.getRequests()
                .observeAsync()
                .subscribe(
                        {
                            val filteredList = it.result?.data?.filter { it.status == "Accepted" }
                            if (filteredList != null && !filteredList.isEmpty()) {

                                val routeList = mutableListOf<LatLng>()

                                filteredList.forEach { routeList.add(LatLng(it.lat, it.lng)) }

                                view?.showMarkers(routeList)

                                routeList.add(0, LatLng(locationInteractor.getLastLocation().latitude, locationInteractor.getLastLocation().longitude))

                                view?.moveCamera(routeList)

                                routesInteractor.getRoutes(routeList)
                                        .observeAsync()
                                        .subscribe(
                                                { view?.showRoute(it) },
                                                { view?.showMessage("Routes error") }
                                        )
                                        .connect()
                            }  else {
                                view?.showMessage("List is empty")
                            }
                        },
                        {
                            view?.showMessage("Network error")
                        }
                )
                .connect()
    }

    fun onBack() {
        navigation.getRouter().exit()
    }
}