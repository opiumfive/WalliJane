package com.iterika.walli.presentation.request.detail

import android.location.Location
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iterika.walli.AVERAGE_COURIER_SPEED
import com.iterika.walli.R
import com.iterika.walli.domain.LocationDistanceCalculator
import com.iterika.walli.ext.reformatDate
import com.iterika.walli.ext.setVisibility
import com.iterika.walli.ext.toggleVisibility
import com.iterika.walli.model.Product
import com.iterika.walli.model.Request
import com.iterika.walli.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_request_detail.*
import javax.inject.Inject

class RequestDetailFragment : BaseFragment(), IRequestDetView {

    @Inject lateinit var presenter: RequestDetPresenter

    private var request: Request? = null
    private var suitcase: ArrayList<Product>? = null
    private var productAdapter: ProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        request = arguments?.getParcelable(ARG_REQUEST)
        suitcase = arguments?.getParcelableArrayList(ARG_SUITCASE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_request_detail, container, false)
    }

    companion object {

        val TAG = RequestDetailFragment::class.java.simpleName
        private val ARG_REQUEST = "request"
        private val ARG_SUITCASE = "suitcase"

        fun newInstance(request: Request?, suitcase: ArrayList<Product>): RequestDetailFragment {
            val fragment = RequestDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_REQUEST, request)
            args.putParcelableArrayList(ARG_SUITCASE, suitcase)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.takeView(this)

        onBack.setOnClickListener { presenter.onBack() }

        onAccept.setOnClickListener { presenter.onAccept(request) }

        onDecline.setOnClickListener { presenter.onDecline(request) }

        //onCall.setOnClickListener { presenter.onPhone(request?.phone) }

        onProducts.setOnClickListener { productRecycler.toggleVisibility() }

        productRecycler.layoutManager = LinearLayoutManager(activity)

        val existing = mutableListOf<Product>()
        val missing = mutableListOf<Product>()

        for (product in request?.products ?: emptyList()) {
            val sameProductInSuitCase = suitcase?.firstOrNull { it.equals(product) }

            if (sameProductInSuitCase != null) {
                val diff = sameProductInSuitCase.quantity.toInt() - product.quantity.toInt()
                if (diff >= 0) {
                    existing.add(product)
                } else {
                    existing.add(Product(product.id, product.name, product.price, sameProductInSuitCase.quantity))
                    missing.add(Product(product.id, product.name, product.price, diff.toString()))
                }
            } else {
                missing.add(Product(product.id, product.name, product.price, "-${product.quantity}"))
            }
        }

        if (missing.isNotEmpty()) {
            productsText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.alert_circle, 0)
            existing.add(Product("", "Missing", "", ""))
            existing.addAll(missing)
        }

        val prods = mutableListOf<Product>()
        prods.add(Product("", getString(R.string.item), getString(R.string.price), getString(R.string.quantity)))
        prods.addAll(existing)
        productAdapter = ProductAdapter(prods)
        productRecycler.adapter = productAdapter

        presenter.onReadyToView(request)
    }

    override fun showRequestDetails(request: Request?, location: Location) {
        val reqLoc = Location("req")
        reqLoc.latitude = request?.lat ?: 0.0
        reqLoc.longitude = request?.lng ?: 0.0

        val distanceCalculator = LocationDistanceCalculator(AVERAGE_COURIER_SPEED, location, reqLoc)

        travelTime.text = distanceCalculator.getTimeToLocation()
        miles.text = distanceCalculator.getDistance()
        address.text = request?.address
        price.text = request?.price
        applicationNumber.text = request?.id
        date.text = request?.date?.reformatDate()
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }
}
