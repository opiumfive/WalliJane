package com.iterika.walli.presentation.barcode

import com.iterika.walli.RESULT_CODE_SCAN
import com.iterika.walli.domain.ScanInteractor
import com.iterika.walli.ext.observeAsync
import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import com.iterika.walli.presentation.barcode.customScanView.IScanCustomView
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class BarcodePresenter @Inject constructor() : BasePresenter<IBarcodeView>() {

    @Inject lateinit var navigation: Navigation
    @Inject lateinit var interactor: ScanInteractor

    var passId: String? = null

    fun onRepeat() {
        view?.showScanning()
    }

    fun onComplete() {
        navigation.getRouter().exitWithResult(RESULT_CODE_SCAN, true)
    }

    fun onReadyToScan(scanner: IScanCustomView) {
        interactor.scanRepository.weakScanner = scanner
        interactor.getScanInfo()
                .observeAsync(scheduler = AndroidSchedulers.mainThread())
                .subscribe{
                    if (interactor.scanMatches(it, passId)) {
                        view?.showSuccess()
                    } else {
                        view?.showErrorAndRepeat()
                    }
                }.connect()
    }

    fun onBack() {
        navigation.getRouter().exit()
    }
}