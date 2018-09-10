package com.iterika.walli.presentation.request.list

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.iterika.walli.R
import com.iterika.walli.ext.setVisibility
import com.iterika.walli.presentation.BaseFragment
import com.iterika.walli.presentation.RecyclerItemDecorator
import com.iterika.walli.model.Request
import kotlinx.android.synthetic.main.fragment_requests.*
import org.jetbrains.anko.dip
import javax.inject.Inject

class RequestFragment : BaseFragment(), IRequestView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Inject lateinit var presenter: RequestPresenter

    private lateinit var adapter: RequestAdapter
    private var mGoogleApiClient: GoogleApiClient? = null
    private var gpsOn = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_requests, container, false)
    }

    companion object {

        val TAG = RequestFragment::class.java.simpleName

        fun newInstance(): Fragment {
            return RequestFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mGoogleApiClient = GoogleApiClient.Builder(activity!!)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
        mGoogleApiClient?.connect()

        presenter.takeView(this)

        orderList.layoutManager = LinearLayoutManager(activity)
        orderList.addItemDecoration(RecyclerItemDecorator(bottomOffset = activity?.dip(8) ?: 0))

        adapter = RequestAdapter { presenter.onRequestClick(it) }
        orderList.adapter = adapter

        onFilter.setOnClickListener { presenter.onFilter() }

        swipeRefresh.setOnRefreshListener {
            if (gpsOn) {
                presenter.onReadyToLoadRequests()
            } else {
                presenter.onGpsTurnedOff()
            }
        }

        onRoutes.setOnClickListener { presenter.onRoutes() }
    }

    override fun showRefreshing(b: Boolean) {
        swipeRefresh?.isRefreshing = b
    }

    override fun showList(list: List<Request>, location: Location) {
        adapter.setLocation(location)
        adapter.clearAndAddList(list)

        noRequests?.setVisibility(list.isEmpty())
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        presenter.onGpsTurnedOff()
    }

    override fun onConnected(p0: Bundle?) {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = (30 * 1000).toLong()
        locationRequest.fastestInterval = (5 * 1000).toLong()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build())
        result.setResultCallback { locationSettingsResult ->
            val status = locationSettingsResult.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {
                    presenter.onReadyToLoadRequests()
                    gpsOn = true;
                }

                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                    try {
                        status.startResolutionForResult(activity, 5)
                    } catch (e: IntentSender.SendIntentException) {
                        e.printStackTrace()
                    }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> presenter.onGpsTurnedOff()
            }
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        presenter.onGpsTurnedOff()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                presenter.onReadyToLoadRequests()
                gpsOn = true;
            } else {
                presenter.onGpsTurnedOff()
            }
        }
    }
}
