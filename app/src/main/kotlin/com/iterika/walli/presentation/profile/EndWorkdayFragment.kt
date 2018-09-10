package com.iterika.walli.presentation.profile

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iterika.walli.R
import com.iterika.walli.ext.setVisibility
import com.iterika.walli.model.Workday
import com.iterika.walli.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_endworkday.*

class EndWorkdayFragment : BaseFragment() {

    private var workday: Workday? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            workday = arguments?.getParcelable(ARG_WORKDAY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_endworkday, container, false)
    }

    companion object {

        val TAG = EndWorkdayFragment::class.java.simpleName
        private val ARG_WORKDAY = "workday"

        fun newInstance(workday: Workday?): EndWorkdayFragment {
            val fragment = EndWorkdayFragment()
            val args = Bundle()
            args.putParcelable(ARG_WORKDAY, workday)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({ enableComplete(true) }, 2000)

        onComplete.setOnClickListener {
            //tabBar?.endedWorkday(true)
        }

        onCancel.setOnClickListener {
            //tabBar?.endedWorkday(false)
        }
    }

    fun enableComplete(enable: Boolean) {
        buttonView.setVisibility(enable)
        waitView.setVisibility(!enable)
    }

    override fun beforeDestroy() {
    }
}
