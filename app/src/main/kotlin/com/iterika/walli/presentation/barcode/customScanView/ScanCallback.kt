package com.iterika.walli.presentation.barcode.customScanView

import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult

class ScanCallback : BarcodeCallback {
    var success: ((String) -> Unit)? = null
    var error: (()-> Unit)? = null

    override fun barcodeResult(result: BarcodeResult?) {
        if (result?.text == null) error?.invoke() else success?.invoke(result.text)
    }
    override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) = Unit
}