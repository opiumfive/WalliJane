package com.iterika.walli.presentation.request.filter

import com.iterika.walli.domain.RequestInteractor
import com.iterika.walli.ext.observeAsync
import com.iterika.walli.model.RequestCategory
import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import javax.inject.Inject

class RequestFilterPresenter @Inject constructor() : BasePresenter<IRequestFilterView>() {

    @Inject lateinit var navigation: Navigation
    @Inject lateinit var interactor: RequestInteractor

    fun onReadyToLoadRequestsCats() {
        interactor.getFilterCats()
                .observeAsync()
                .subscribe(
                        { view?.showFilterList(it) },
                        {  }
                )
                .connect()
    }

    fun onRequestCatClick(cat: RequestCategory) {
        //apply filter
        view?.updateChosenItem(cat)
        interactor.applyFilter(cat)
    }

    fun onBack() {
        navigation.getRouter().exit()
    }
}