package com.iterika.walli.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import com.iterika.walli.App
import com.iterika.walli.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.okButton
import ru.terrakok.cicerone.Navigator
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity(), IView {

    lateinit var activityScope : Scope
    @Inject lateinit var navigation: Navigation
    abstract val navigator: Navigator?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        activityScope = Toothpick.openScopes(application, this)
        Toothpick.inject(this, activityScope)
    }

    fun getDisplayWidth(): Int {
        val metrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(metrics)
        return metrics.widthPixels
    }

    fun getDisplayHeight(): Int {
        val metrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(metrics)
        return metrics.heightPixels
    }

    override fun onDestroy() {
        beforeDestroy()
        super.onDestroy()
    }

    abstract fun beforeDestroy()

    override fun application(): App {
        return application as App
    }

    override fun showMessage(message: String) {
        alert(message) {
            okButton {  }
        }.show()
    }

    override fun showMessage(messageResId: Int) {
        alert(messageResId) {
            okButton {  }
        }.show()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigation.getNaviHolder().setNavigator(navigator)
    }

    override fun onPause() {
        navigation.getNaviHolder().removeNavigator()
        super.onPause()
    }
}