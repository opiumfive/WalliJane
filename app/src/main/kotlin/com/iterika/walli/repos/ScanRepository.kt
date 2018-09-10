package com.iterika.walli.repos

import com.iterika.walli.net.Api
import com.iterika.walli.weak
import javax.inject.Inject
import com.iterika.walli.presentation.barcode.customScanView.IScanCustomView
import com.iterika.walli.presentation.barcode.customScanView.ScanCallback
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe

class ScanRepository @Inject constructor(val api: Api) {

    var weakScanner : IScanCustomView? by weak()

    fun getScanInfoAsync() = Flowable.create(FlowableOnSubscribe<String> { e ->
            val callback = ScanCallback().apply {
                success = { e.onNext(it) }
                error = { e.onError(IllegalStateException()) }
            }

            weakScanner?.scan(callback)

            e.setCancellable { weakScanner?.stopScan() }
        }, BackpressureStrategy.BUFFER)
}