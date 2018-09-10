package com.iterika.walli.presentation.faq

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import com.iterika.walli.R
import com.iterika.walli.model.Faq

class FaqAdapter(private val itemClick: (Faq?) -> Unit) : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    private val faqList: MutableList<Faq>? = ArrayList<Faq>()

    fun addList(list: List<Faq>) {
        faqList?.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        faqList?.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false) as Button)
    }

    private fun getItem(position: Int): Faq? {
        return if (faqList == null) null else faqList[position]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(getItem(position)) {
            holder.button.text = this?.title ?: ""
            holder.button.setOnClickListener {
                itemClick(this)
            }
        }
    }

    override fun getItemCount(): Int {
        return faqList?.size ?: 0
    }

    class ViewHolder(val button: Button) : RecyclerView.ViewHolder(button)

    interface OnItemClickListener {
         operator fun invoke(faq: Faq?)
    }
}