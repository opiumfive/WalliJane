package com.iterika.walli.presentation.order.cancel

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iterika.walli.R
import com.iterika.walli.ext.enable
import com.iterika.walli.ext.onTextChanged
import com.iterika.walli.presentation.BaseDialogFragment
import kotlinx.android.synthetic.main.fragment_cancel_order.*
import javax.inject.Inject

class CancelOrderDialog : BaseDialogFragment(), ICancelOrderView {

    @Inject lateinit var presenter: CancelOrderPresenter

    private var orderId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cancel_order, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderId = arguments?.getString(ARG_ORDER)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.takeView(this)

        onButton.enable(false)

        onClose.setOnClickListener { dismiss() }

        reason.onTextChanged { presenter.onTextChanged(it) }

        onButton.setOnClickListener { presenter.onCancelOrder(orderId, reason.text.toString()) }
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }

    override fun enableButton(b: Boolean) {
        onButton.enable(b)
    }

    override fun close() {
        dismiss()
    }

    companion object {

        val ARG_ORDER = "order"

        fun newInstance(orderId: String?): CancelOrderDialog {
            val fragment = CancelOrderDialog()
            val args = Bundle()
            args.putString(ARG_ORDER, orderId)
            fragment.arguments = args
            fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TransparentDialog)
            return fragment
        }
    }
}