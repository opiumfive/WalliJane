package com.iterika.walli.presentation.request.detail

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iterika.walli.R
import com.iterika.walli.model.Product
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(private var productList : List<Product>? = mutableListOf()) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false))

    private fun getItem(position: Int) = productList?.get(position)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    override fun getItemCount() = productList?.size ?: 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product?) {
            itemView.name.text = product?.name
            itemView.price.text = product?.price

            if (TextUtils.isEmpty(product?.quantity) || product?.quantity?.startsWith("-") == true) {
                itemView.name.setTextColor(Color.RED)
                itemView.quantity.setTextColor(Color.RED)
                itemView.price.setTextColor(Color.RED)

                if (!TextUtils.isEmpty(product?.quantity)) {
                    itemView.quantity.text = product?.quantity?.substring(1)
                } else {
                    itemView.quantity.text = ""
                }
            } else {
                itemView.name.setTextColor(Color.BLACK)
                itemView.quantity.setTextColor(Color.BLACK)
                itemView.price.setTextColor(Color.BLACK)

                itemView.quantity.text = product?.quantity
            }
        }
    }
}