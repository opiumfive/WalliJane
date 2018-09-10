package com.iterika.walli.presentation.request.filter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import com.iterika.walli.R
import com.iterika.walli.model.RequestCategory

class RequestCategoryAdapter(private val itemClick: (RequestCategory) -> Unit) : RecyclerView.Adapter<RequestCategoryAdapter.ViewHolder>() {

    private val requestCategoryList = mutableListOf<RequestCategory>()

    fun clearAndAddList(list: List<RequestCategory>) {
        requestCategoryList.clear()
        requestCategoryList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false) as Button, itemClick)

    private fun getItem(position: Int) = requestCategoryList[position]

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    override fun getItemCount() = requestCategoryList.size

    class ViewHolder(val button: Button, val listener: (RequestCategory) -> Unit) : RecyclerView.ViewHolder(button) {
        fun bind(item: RequestCategory) {
            button.text = item.title
            button.setOnClickListener { listener(item) }
            button.setCompoundDrawablesWithIntrinsicBounds( 0, 0, if (item.chosen) R.drawable.check else 0, 0);
        }
    }

    fun updateChosen(cat: RequestCategory) {
        requestCategoryList.forEach { it.chosen = false }
        requestCategoryList.find { it.equals(cat) }?.chosen = true
        notifyDataSetChanged()
    }

    fun getChosenItem() = requestCategoryList.find { it.chosen }
}
