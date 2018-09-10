package com.iterika.walli.presentation.request.list

import android.location.Location
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iterika.walli.AVERAGE_COURIER_SPEED
import com.iterika.walli.GlideApp
import com.iterika.walli.R
import com.iterika.walli.domain.LocationDistanceCalculator
import com.iterika.walli.model.Request
import kotlinx.android.synthetic.main.item_request.view.*

class RequestAdapter(private val itemClick: (Request?) -> Unit) : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    var requestList = mutableListOf<Request>()
    var loc: Location? = null

    fun clearAndAddList(list: List<Request>) {
        requestList.clear()
        requestList.addAll(list)
        notifyDataSetChanged()
    }

    fun setLocation(location: Location) {
        loc = location
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false), itemClick)

    private fun getItem(position: Int) = requestList[position]

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    override fun getItemCount() = requestList.size

    inner class ViewHolder(itemView: View, private val listener: (Request?) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(request: Request?) {
            val reqLoc = Location("req")
            reqLoc.latitude = request?.lat ?: 0.0
            reqLoc.longitude = request?.lng ?: 0.0

            val distanceCalculator = LocationDistanceCalculator(AVERAGE_COURIER_SPEED, loc, reqLoc)

            itemView.travelTime.text = distanceCalculator.getTimeToLocation()
            itemView.miles.text = distanceCalculator.getDistance()
            itemView.address.text = request?.address
            itemView.price.text = request?.price
            itemView.status.text = request?.status
            itemView.applicationNumber.text = request?.id
            GlideApp.with(itemView.context).load(request?.generateMapUrl()).centerCrop().into(itemView.mapImage)
            itemView.setOnClickListener { listener.invoke(request) }
        }
    }
}
