package com.iterika.walli.presentation.barcode

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import com.fondesa.kpermissions.extension.onAccepted
import com.fondesa.kpermissions.extension.onDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.google.zxing.BarcodeFormat
import com.iterika.walli.R
import com.iterika.walli.ext.setVisibility
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import kotlinx.android.synthetic.main.activity_barcode_scan.*
import com.iterika.walli.presentation.BaseActivity
import com.iterika.walli.presentation.Screens
import com.iterika.walli.presentation.auth.AuthActivity
import com.iterika.walli.presentation.barcode.customScanView.IScanCustomView
import com.iterika.walli.presentation.ink.InkActivity
import com.iterika.walli.presentation.main.MainActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject

class BarcodeScanActivity : BaseActivity(), IBarcodeView {

    @Inject lateinit var presenter: BarcodePresenter

    override val navigator: Navigator
        get() = object : SupportAppNavigator(this, 0) {
            override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? = when (screenKey) {
                Screens.INK     -> Intent(context, InkActivity::class.java)
                else            -> null
            }

            override fun createFragment(screenKey: String?, data: Any?) = null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scan)

        presenter.takeView(this)

        onRepeat.setVisibility(false)
        onComplete.setVisibility(false)

        onComplete.setOnClickListener { presenter.onComplete() }

        onRepeat.setOnClickListener { presenter.onRepeat() }

        onBack.setOnClickListener { presenter.onBack() }

        setResult(Activity.RESULT_CANCELED)

        presenter.passId = intent.getStringExtra("id")

        barcodeView.stopDecoding()
        barcodeView.setDecoderFactory(DefaultDecoderFactory(listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)))

        permissionsBuilder(Manifest.permission.CAMERA).build()
                .onAccepted { presenter.onReadyToScan(barcodeView as IScanCustomView) }
                .onDenied { toast(R.string.camera_perm_hint) }
                .send()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent) = barcodeView.onKeyDown(keyCode, event) or super.onKeyDown(keyCode, event)

    override fun beforeDestroy() {
        presenter.dropView()
    }

    override fun showErrorAndRepeat() {
        scanHint.setVisibility(false)
        overlay.setLinesBad()
        scanMessage.text = getString(R.string.fail_scan)
        scanMessage.setTextColor(Color.parseColor("#f11b1a"))
        barcodeView.pause()
        onRepeat.setVisibility(true)
    }

    override fun showSuccess() {
        scanHint.setVisibility(false)
        scanMessage.text = getString(R.string.success_scan)
        scanMessage.setTextColor(Color.parseColor("#6eb43c"))
        barcodeView.pause()
        onComplete.setVisibility(true)
        overlay.setLinesOk()
    }

    override fun showScanning() {
        barcodeView.resume()
        scanMessage.text = getString(R.string.scan_the_qr_code)
        scanMessage.setTextColor(Color.parseColor("#032e52"))
        onRepeat.setVisibility(false)
        scanHint.setVisibility(true)
        overlay.setLinesNormal()
    }
}
