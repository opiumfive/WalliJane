package com.iterika.walli.presentation.barcode

import com.iterika.walli.presentation.IView

interface IBarcodeView : IView {

    fun showErrorAndRepeat()

    fun showSuccess()

    fun showScanning()
}