package com.iterika.walli.presentation.faq

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iterika.walli.R
import com.iterika.walli.presentation.BaseFragment
import org.jetbrains.anko.startActivity
import com.iterika.walli.presentation.RecyclerItemDecorator
import com.iterika.walli.model.Faq
import kotlinx.android.synthetic.main.fragment_faq.*
import org.jetbrains.anko.dip

class FaqFragment : BaseFragment() {

    private lateinit var adapter: FaqAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    companion object {

        val TAG = FaqFragment::class.java.simpleName

        fun newInstance(): Fragment {
            return FaqFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        faqList.layoutManager = LinearLayoutManager(activity)
        faqList.addItemDecoration(RecyclerItemDecorator(bottomOffset = activity?.dip(8) ?: 0))

        adapter = FaqAdapter {
            activity?.startActivity<FaqDetailActivity>("title" to it?.title, "content" to it?.content)
        }
        faqList.adapter = adapter

        adapter.addList(listOf(Faq("hoho", "haha"), Faq("haha", "hoho"))) // dumb data
    }

    override fun beforeDestroy() {
    }
}
