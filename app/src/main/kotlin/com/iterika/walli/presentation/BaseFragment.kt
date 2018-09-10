package com.iterika.walli.presentation

import android.content.Context
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import com.iterika.walli.App
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject

abstract class BaseFragment : Fragment(), IView {

    lateinit var activityScope : Scope
    @Inject lateinit var navigation: Navigation

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        activityScope = Toothpick.openScopes(activity?.application, this)
        Toothpick.inject(this, activityScope)
    }

    override fun onDestroy() {
        beforeDestroy()
        super.onDestroy()
    }

    fun getDisplayWidth(): Int {
        val metrics = DisplayMetrics()
        activity?.getWindowManager()?.getDefaultDisplay()?.getMetrics(metrics)
        return metrics.widthPixels
    }

    fun getDisplayHeight(): Int {
        val metrics = DisplayMetrics()
        activity?.getWindowManager()?.getDefaultDisplay()?.getMetrics(metrics)
        return metrics.heightPixels
    }

    abstract fun beforeDestroy()

    override fun application() = activity?.application as App

    override fun showMessage(message: String) {
        activity?.let {
            it.alert(message) {
                okButton { }
            }.show()
        }
    }

    override fun showMessage(messageResId: Int) {
        activity?.let {
            it.alert(messageResId) {
                okButton { }
            }.show()
        }
    }
}
