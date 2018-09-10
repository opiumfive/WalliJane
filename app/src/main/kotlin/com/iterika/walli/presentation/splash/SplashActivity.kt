package com.iterika.walli.presentation.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iterika.walli.R
import com.iterika.walli.presentation.BaseActivity
import com.iterika.walli.presentation.Screens
import com.iterika.walli.presentation.auth.AuthActivity
import com.iterika.walli.presentation.main.MainActivity
import org.jetbrains.anko.intentFor
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject

class SplashActivity : BaseActivity(), ISplashView {

    @Inject lateinit var presenter: SplashPresenter

    override val navigator: Navigator
        get() = object : SupportAppNavigator(this, 0) {
            override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? = when (screenKey) {
                Screens.AUTH     -> intentFor<AuthActivity>()
                Screens.PROFILE -> intentFor<MainActivity>()
                else            -> null
            }

            override fun createFragment(screenKey: String?, data: Any?) = null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.takeView(this)
        presenter.onScreenStart()
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }
}
