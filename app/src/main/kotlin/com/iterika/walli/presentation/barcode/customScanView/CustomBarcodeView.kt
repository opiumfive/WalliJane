package com.iterika.walli.presentation.barcode.customScanView

import android.content.Context
import android.util.AttributeSet
import com.journeyapps.barcodescanner.BarcodeView

class CustomBarcodeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BarcodeView(context, attrs, defStyleAttr), IScanCustomView {
    override fun stopScan() {
        stopDecoding()
    }

    override fun scan(callback: ScanCallback) {
        decodeContinuous(callback)
    }
}