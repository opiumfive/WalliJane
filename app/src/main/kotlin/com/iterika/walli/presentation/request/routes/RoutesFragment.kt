package com.iterika.walli.presentation.request.routes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.iterika.walli.R
import com.iterika.walli.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_routes.*
import javax.inject.Inject
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.PolyUtil
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.model.DirectionsResult

class RoutesFragment : BaseFragment(), IRoutesView, OnMapReadyCallback {

    @Inject lateinit var presenter: RoutesPresenter

    var map: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_routes, container, false)
    }

    companion object {

        val TAG = RoutesFragment::class.java.simpleName

        fun newInstance(): Fragment {
            return RoutesFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.takeView(this)

        onBack.setOnClickListener { presenter.onBack() }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }

    override fun showMarkers(list: List<LatLng>) {
        list.forEach { map?.addMarker(MarkerOptions().position(it)) }
    }

    override fun moveCamera(list: List<LatLng>) {
        val bounder = LatLngBounds.builder()
        list.forEach { bounder.include(it) }
        map?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounder.build(), getDisplayWidth(), getDisplayHeight(), getDisplayWidth() / 5))
    }

    override fun showRoute(result: DirectionsResult) {
        if (result.routes == null || result.routes.isEmpty()) return
        val decodedPath = PolyUtil.decode(result.routes[0].overviewPolyline.encodedPath)
        map?.addPolyline(PolylineOptions().addAll(decodedPath))
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0;
        presenter.onReadyToLoadRequests()
    }
}