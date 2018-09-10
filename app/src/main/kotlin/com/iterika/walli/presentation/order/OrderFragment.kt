package com.iterika.walli.presentation.order

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iterika.walli.R
import com.iterika.walli.ext.reformatDate
import com.iterika.walli.model.Request
import com.iterika.walli.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_order.*
import javax.inject.Inject

class OrderFragment : BaseFragment(), IOrderView {

    @Inject lateinit var presenter: OrderPresenter

    private var order: Request? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        order = arguments?.getParcelable(ARG_ORDER)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    companion object {

        val TAG = OrderFragment::class.java.simpleName
        private val ARG_ORDER = "request"

        fun newInstance(request: Request?): OrderFragment {
            val fragment = OrderFragment()
            val args = Bundle()
            args.putParcelable(ARG_ORDER, request)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.takeView(this)

        onBack.setOnClickListener { presenter.onBack() }

        onComplete.setOnClickListener { presenter.onComplete(order) }

        onCancel.setOnClickListener { presenter.onCancel(order?.id) }

        onPassport.setOnClickListener { presenter.onPassport(order?.photo) }

        presenter.onReadyToView(order)
    }

    override fun viewOrder(order: Request?, location: Location) {
        val reqLoc = Location("req")
        reqLoc.latitude = order?.lat ?: 0.0
        reqLoc.longitude = order?.lng ?: 0.0
        val dist = location.distanceTo(reqLoc)

        val mins = (dist / 10 / 60).toInt() // 10ms = 36kmh
        val time = "${mins / 60}:${mins % 60}"


        travelTime.text = time
        miles.text = (dist / 1.6 / 1000).toInt().toString()
        address.text = order?.address
        price.text = order?.price
        applicationNumber.text = order?.id
        date.text = order?.date?.reformatDate()
        orderStatus.text = order?.status
    }

    override fun beforeDestroy() {
        presenter.onDestroy()
        presenter.dropView()
    }
}
