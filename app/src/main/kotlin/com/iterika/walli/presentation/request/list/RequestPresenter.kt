package com.iterika.walli.presentation.request.list

import com.iterika.walli.domain.LocationInteractor
import com.iterika.walli.domain.RequestInteractor
import com.iterika.walli.ext.observeAsync
import com.iterika.walli.model.Request
import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import com.iterika.walli.presentation.Screens
import com.iterika.walli.repos.SuitcaseRepository
import javax.inject.Inject

class RequestPresenter @Inject constructor() : BasePresenter<IRequestView>() {

    @Inject lateinit var navigation: Navigation
    @Inject lateinit var interactor: RequestInteractor
    @Inject lateinit var locationInteractor: LocationInteractor

    fun onFilter() {
        navigation.getRouter().navigateTo(Screens.FILTER)
    }

    fun onRoutes() {
        navigation.getRouter().navigateTo(Screens.ROUTES)
    }

    fun onReadyToLoadRequests() {
        view?.showRefreshing(true)
        interactor.getRequests()
                .observeAsync()
                .subscribe(
                    {
                        view?.showRefreshing(false)
                        SuitcaseRepository.suitcase = ArrayList(it.result?.suitcase)
                        val filter = interactor.getFilter()
                        val filteredList = it.result?.data?.filter {
                            when(filter) {
                                "Express", "Standart" -> it.deliveryType.startsWith(filter)
                                else -> it.status == filter || filter == "All"
                            }
                        }
                        if (filteredList != null) {
                            view?.showList(filteredList, locationInteractor.getLastLocation())
                        }  else {
                            view?.showMessage("List is empty")
                        }
                    },
                    {
                        view?.showRefreshing(false)
                        view?.showMessage("Network error")
                    }
                )
                .connect()
    }

    fun onGpsTurnedOff() {
        view?.showMessage("You must have GPS turned on")
    }

    fun onRequestClick(request: Request?) {
        if (request?.status.equals("Accepted")) {
            navigation.getRouter().navigateTo(Screens.ORDER, request)
        } else {
            navigation.getRouter().navigateTo(Screens.REQUEST_DET, request)
        }
    }
}