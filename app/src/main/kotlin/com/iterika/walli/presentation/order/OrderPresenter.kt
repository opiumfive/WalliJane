package com.iterika.walli.presentation.order

import com.iterika.walli.RESULT_CODE_SCAN
import com.iterika.walli.RESULT_CODE_SIGN
import com.iterika.walli.domain.LocationInteractor
import com.iterika.walli.domain.RequestInteractor
import com.iterika.walli.ext.observeAsync
import com.iterika.walli.model.Request
import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import com.iterika.walli.presentation.Screens
import javax.inject.Inject

class OrderPresenter @Inject constructor() : BasePresenter<IOrderView>() {

    @Inject lateinit var navigation: Navigation
    @Inject lateinit var interactor: RequestInteractor
    @Inject lateinit var locationInteractor: LocationInteractor

    fun onBack() {
        navigation.getRouter().exit()
    }

    fun onComplete(request: Request?) {
        if (request?.id != null) {
            navigation.getRouter().navigateTo(Screens.BARCODE, request.passportId)
            navigation.getRouter().setResultListener(RESULT_CODE_SCAN, { _ -> onGotScanResult(request) })
        }
    }

    fun onGotScanResult(request: Request) {
        navigation.getRouter().navigateTo(Screens.INK)
        navigation.getRouter().setResultListener(RESULT_CODE_SIGN, { data -> onGotInkResult(request) })
    }

    fun onGotInkResult(request: Request) {

        interactor.finishOrder(request.id)
                .observeAsync()
                .subscribe(
                        {
                            if (it.result?.message != null) {
                                navigation.getRouter().backTo(Screens.TAB_HOME)
                            } else {
                                view?.showMessage(it.result?.error ?: "Error")
                            }
                        },
                        { view?.showMessage("Network error") }
                )
                .connect()

    }

    fun onPassport(url: String?) {
        navigation.getRouter().navigateTo(Screens.DOCUMENT, url)
    }

    fun onCancel(id: String?) {
        navigation.getRouter().navigateTo(Screens.CANCEL_ORDER, id)
    }

    fun onReadyToView(order: Request?) {
        view?.viewOrder(order, locationInteractor.getLastLocation())
    }

    fun onDestroy() {
        navigation.getRouter().removeResultListener(RESULT_CODE_SIGN)
        navigation.getRouter().removeResultListener(RESULT_CODE_SCAN)
    }
}