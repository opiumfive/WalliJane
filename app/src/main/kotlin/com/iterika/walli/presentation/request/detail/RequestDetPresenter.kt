package com.iterika.walli.presentation.request.detail

import android.location.Location
import com.iterika.walli.domain.LocationInteractor
import com.iterika.walli.domain.RequestInteractor
import com.iterika.walli.ext.observeAsync
import com.iterika.walli.model.Request
import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import com.iterika.walli.presentation.Screens
import javax.inject.Inject

class RequestDetPresenter @Inject constructor() : BasePresenter<IRequestDetView>() {

    @Inject lateinit var navigation: Navigation
    @Inject lateinit var interactor: RequestInteractor
    @Inject lateinit var locationInteractor: LocationInteractor

    var location: Location? = null

    fun onPhone(phone: String?) {
        navigation.getRouter().navigateTo(Screens.PHONE, phone)
    }

    fun onBack() {
        navigation.getRouter().exit()
    }

    fun onDecline(request: Request?) {
        if (request?.id != null) {
            interactor.declineRequest(request.id)
                    .observeAsync()
                    .subscribe(
                            {
                                if (it.result?.data?.message != null) {
                                    view?.showMessage(it.result.data.message)
                                    navigation.getRouter().exit()
                                } else {
                                    view?.showMessage(it.result?.data?.error ?: "Error")
                                }
                            },
                            { view?.showMessage("Network error") }
                    )
                    .connect()
        }
    }

    fun onAccept(request: Request?) {
        if (request?.id != null) {
            interactor.acceptRequest(request.id)
                    .observeAsync()
                    .subscribe(
                            {
                                if (it.result?.data?.message != null) {
                                    view?.showMessage(it.result.data.message)
                                    navigation.getRouter().replaceScreen(Screens.ORDER, request)
                                } else {
                                    view?.showMessage(it.result?.data?.error ?: "Error")
                                }
                            },
                            { view?.showMessage("Network error") }
                    )
                    .connect()
        }
    }

    fun onWebPage(url: String?) {
        navigation.getRouter().navigateTo(Screens.WEB_PAGE, url)
    }

    fun onReadyToView(request: Request?) {
        view?.showRequestDetails(request, locationInteractor.getLastLocation())
    }
}
