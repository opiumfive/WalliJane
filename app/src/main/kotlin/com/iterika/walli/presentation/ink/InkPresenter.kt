package com.iterika.walli.presentation.ink

import com.iterika.walli.RESULT_CODE_SIGN
import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import javax.inject.Inject

class InkPresenter @Inject constructor() : BasePresenter<IInkView>() {

    @Inject lateinit var navigation: Navigation

    fun onBack() {
        navigation.getRouter().exit()
    }

    fun onDraw() {
        view?.enableNextButton(true)
    }

    fun onClear() {
        view?.clearInk()
    }

    fun onCleared() {
        view?.enableNextButton(false)
    }

    fun onNext() {
        navigation.getRouter().exitWithResult(RESULT_CODE_SIGN, true)
    }
}
