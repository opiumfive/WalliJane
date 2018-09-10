package com.iterika.walli.presentation.barcode.customScanView

interface IScanCustomView {
    fun stopScan()
    fun scan(callback: ScanCallback)
}