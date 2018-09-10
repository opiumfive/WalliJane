package com.iterika.walli.presentation.main

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.ImageViewCompat
import com.iterika.walli.*
import com.iterika.walli.ext.setVisibility
import com.iterika.walli.model.Request
import com.iterika.walli.model.Workday
import kotlinx.android.synthetic.main.activity_main.*
import com.iterika.walli.presentation.BaseActivity
import com.iterika.walli.presentation.profile.ProfileFragment
import com.iterika.walli.presentation.faq.FaqFragment
import com.iterika.walli.presentation.Screens
import com.iterika.walli.presentation.auth.AuthActivity
import com.iterika.walli.presentation.barcode.BarcodeScanActivity
import com.iterika.walli.presentation.document.ImageFragment
import com.iterika.walli.presentation.ink.InkActivity
import com.iterika.walli.presentation.order.OrderFragment
import com.iterika.walli.presentation.order.cancel.CancelOrderDialog
import com.iterika.walli.presentation.profile.EndWorkdayFragment
import com.iterika.walli.presentation.request.detail.RequestDetailFragment
import com.iterika.walli.presentation.request.filter.RequestFilterFragment
import com.iterika.walli.presentation.request.list.RequestFragment
import com.iterika.walli.presentation.request.routes.RoutesFragment
import com.iterika.walli.repos.SuitcaseRepository
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import javax.inject.Inject

class MainActivity : BaseActivity(), IMainView {

    @Inject lateinit var presenter: MainPresenter

    override val navigator: Navigator
        get() = object : SupportAppNavigator(this, R.id.container) {
            override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? = when (screenKey) {
                Screens.AUTH     -> intentFor<AuthActivity>()
                Screens.PROFILE  -> intentFor<MainActivity>()
                Screens.BARCODE  -> intentFor<BarcodeScanActivity>("id" to data as String)
                Screens.INK      -> intentFor<InkActivity>()
                Screens.WEB_PAGE -> Intent(Intent.ACTION_VIEW).setData(Uri.parse(data as String)).newTask()
                Screens.PHONE    -> Intent(Intent.ACTION_DIAL, Uri.parse("tel:${data as String}"))
                else             -> null
            }

            override fun createFragment(screenKey: String?, data: Any?) = when (screenKey) {
                Screens.TAB_FAQ     -> FaqFragment.newInstance()
                Screens.TAB_HOME    -> RequestFragment.newInstance()
                Screens.TAB_PROFILE -> ProfileFragment.newInstance()
                Screens.END_WORKDAY -> EndWorkdayFragment.newInstance(data as Workday)
                Screens.DOCUMENT    -> ImageFragment.newInstance(data as String)
                Screens.ORDER       -> OrderFragment.newInstance(data as Request)
                Screens.FILTER      -> RequestFilterFragment.newInstance()
                Screens.ROUTES      -> RoutesFragment.newInstance()
                Screens.REQUEST_DET -> RequestDetailFragment.newInstance(data as Request, SuitcaseRepository.suitcase)
                else -> null
            }

            override fun unknownScreen(command: Command?) {
                if (command is Forward) {
                    val c = command
                    if (c.screenKey.equals(Screens.CANCEL_ORDER)) {
                        CancelOrderDialog.newInstance(c.transitionData as String).show(supportFragmentManager, null)
                        return
                    }
                }
                super.unknownScreen(command)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.takeView(this)

        if (savedInstanceState == null) {
            when(intent.getIntExtra("pageNum", TAB_NUM_PROFILE)) {
                TAB_NUM_HOME -> presenter.onRequests()
                TAB_NUM_PROFILE -> presenter.onProfile()
                TAB_NUM_FAQ -> presenter.onFaq()
            }
        }

        if (intent.getBooleanExtra("showOnlyFaq", false)) {
            presenter.onShowOnlyFaq()
        }

        registerListeners()
    }

    private fun registerListeners() {
        onHome.setOnClickListener { presenter.onRequests() }

        onProfile.setOnClickListener { presenter.onProfile() }

        onFaq.setOnClickListener { presenter.onFaq() }

        onBack.setOnClickListener { presenter.onBack() }
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }

    override fun selectTab(currentFragment: CurrentFragment?) {
        ImageViewCompat.setImageTintList(onHomeImage, ColorStateList.valueOf(Color.parseColor("#517b93")));
        ImageViewCompat.setImageTintList(onFaqImage, ColorStateList.valueOf(Color.parseColor("#517b93")));
        ImageViewCompat.setImageTintList(onProfileImage, ColorStateList.valueOf(Color.parseColor("#517b93")));

        when(currentFragment) {
            CurrentFragment.HOME -> ImageViewCompat.setImageTintList(onHomeImage, ColorStateList.valueOf(Color.parseColor("#032e52")));
            CurrentFragment.FAQ -> ImageViewCompat.setImageTintList(onFaqImage, ColorStateList.valueOf(Color.parseColor("#032e52")));
            CurrentFragment.PROFILE -> ImageViewCompat.setImageTintList(onProfileImage, ColorStateList.valueOf(Color.parseColor("#032e52")));
        }
    }

    override fun enableTabs(enable: Boolean) {
        tabBar.setVisibility(enable)
    }

    override fun showBack(enable: Boolean) {
        onBack.setVisibility(enable)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5) {
            try {
                val requestFragment = supportFragmentManager.fragments[1] as RequestFragment
                requestFragment.onActivityResult(requestCode, resultCode, data)
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }
}
