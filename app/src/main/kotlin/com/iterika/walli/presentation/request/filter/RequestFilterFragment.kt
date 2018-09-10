package com.iterika.walli.presentation.request.filter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iterika.walli.R
import com.iterika.walli.presentation.BaseFragment
import com.iterika.walli.presentation.RecyclerItemDecorator
import com.iterika.walli.model.RequestCategory
import kotlinx.android.synthetic.main.fragment_request_filter.*
import org.jetbrains.anko.dip
import javax.inject.Inject

class RequestFilterFragment : BaseFragment(), IRequestFilterView {

    @Inject lateinit var presenter: RequestFilterPresenter

    private lateinit var adapter: RequestCategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_request_filter, container, false)
    }

    companion object {

        val TAG = RequestFilterFragment::class.java.simpleName

        fun newInstance(): Fragment {
            return RequestFilterFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.takeView(this)

        filterList.layoutManager = LinearLayoutManager(activity)
        filterList.addItemDecoration(RecyclerItemDecorator(bottomOffset = activity?.dip(8) ?: 0))

        adapter = RequestCategoryAdapter { presenter.onRequestCatClick(it) }
        filterList.adapter = adapter

        onBack.setOnClickListener { presenter.onBack() }

        presenter.onReadyToLoadRequestsCats()
    }

    override fun showFilterList(list: List<RequestCategory>) {
        adapter.clearAndAddList(list)
    }

    override fun updateChosenItem(item: RequestCategory) {
        adapter.updateChosen(item)
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }
}
