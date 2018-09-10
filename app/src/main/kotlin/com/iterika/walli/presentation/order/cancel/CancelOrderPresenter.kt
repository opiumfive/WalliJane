package com.iterika.walli.presentation.order.cancel

import com.iterika.walli.domain.RequestInteractor
import com.iterika.walli.ext.observeAsync
import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import com.iterika.walli.presentation.Screens
import javax.inject.Inject

class CancelOrderPresenter @Inject constructor() : BasePresenter<ICancelOrderView>() {

    @Inject lateinit var navigation: Navigation
    @Inject lateinit var interactor: RequestInteractor

    fun onCancelOrder(id: String?, text: String) {
        if (id != null) {
            interactor.cancelOrder(id, text)
                    .observeAsync()
                    .subscribe(
                            {
                                if (it.result?.data?.message != null) {
                                    view?.showMessage(it.result.data.message)
                                    view?.close()
                                    navigation.getRouter().backTo(Screens.TAB_HOME)
                                } else {
                                    view?.showMessage(it.result?.data?.error ?: "Error")
                                }
                            },
                            { view?.showMessage("Network error") }
                    )
                    .connect()
        }
    }

    fun onTextChanged(text: String) {
        view?.enableButton(text.isNotEmpty())
    }
}