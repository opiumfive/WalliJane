package com.iterika.walli.presentation.profile

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fondesa.kpermissions.extension.onAccepted
import com.fondesa.kpermissions.extension.onDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.iterika.walli.GlideApp
import com.iterika.walli.R
import com.iterika.walli.model.Profile
import com.iterika.walli.presentation.BaseFragment
import com.iterika.walli.presentation.main.MainActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment(), IProfileView {

    @Inject lateinit var presenter: ProfilePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {

        val TAG = ProfileFragment::class.java.simpleName

        fun newInstance() = ProfileFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.takeView(this)

        registerListeners()

        presenter.onReady()

        permissionsBuilder(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).build()
                .onAccepted {  }
                .onDenied { showMessage(getString(R.string.perm_loc_needed)) }
                .send()
    }

    override fun showProfile(profile: Profile) {
        profileId.text = "ID${profile.id}"
        profileName.text = "${profile.name} ${profile.surname}"
        GlideApp.with(this)
                .load(profile.getProfileUrl())
                .placeholder(R.drawable.walli_gray)
                .error(R.drawable.walli_gray)
                .centerCrop()
                .circleCrop()
                .into(profileImage)
    }

    override fun setStarted() {
        onWorkingDay.text = getString(R.string.end_workday)
        onBreak.visibility = View.VISIBLE
        //timerView.visibility = View.VISIBLE
        onBreak.text = getString(R.string.break_string)
        timer.setTextColor(Color.parseColor("#032e52"))
    }

    override fun setNotStarted() {
        onWorkingDay.text = getString(R.string.start_working_day)
        onBreak.visibility = View.INVISIBLE
        //timerView.visibility = View.INVISIBLE
    }

    override fun setPaused() {
        onBreak.text = getString(R.string.continue_string)
        timer.setTextColor(Color.parseColor("#eb534b"))
        onWorkingDay.text = getString(R.string.end_workday)
        onBreak.visibility = View.VISIBLE
        //timerView.visibility = View.VISIBLE
    }

    private fun registerListeners() {
        onBreak.setOnClickListener { presenter.onBreakContinue() }

        onWorkingDay.setOnClickListener { presenter.onStartEndDay() }
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }

    override fun showTabs(b: Boolean) {
        val mainActivity = activity as MainActivity
        mainActivity.enableTabs(b)
    }
}
