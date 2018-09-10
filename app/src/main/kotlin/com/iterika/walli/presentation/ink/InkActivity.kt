package com.iterika.walli.presentation.ink

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.iterika.walli.R
import com.iterika.walli.ext.setVisibility
import com.iterika.walli.presentation.BaseActivity
import com.iterika.walli.presentation.Screens
import com.iterika.walli.presentation.faq.FaqFragment
import com.iterika.walli.presentation.profile.ProfileFragment
import com.iterika.walli.presentation.request.list.RequestFragment
import com.simplify.ink.InkView
import kotlinx.android.synthetic.main.activity_ink.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject

class InkActivity : BaseActivity(), IInkView {

    @Inject lateinit var presenter: InkPresenter

    override val navigator: Navigator
        get() = object : SupportAppNavigator(this, 0) {
            override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? = null
            override fun createFragment(screenKey: String?, data: Any?) = when (screenKey) {
                Screens.TAB_FAQ      -> FaqFragment.newInstance()
                Screens.TAB_HOME     -> RequestFragment.newInstance()
                Screens.TAB_PROFILE  -> ProfileFragment.newInstance()
                else -> null
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ink)

        presetInkView()

        presenter.takeView(this)

        presenter.onCleared()

        ink.addListener(object : InkView.InkListener {
            override fun onInkClear() {
                presenter.onCleared()
            }

            override fun onInkDraw() {
                presenter.onDraw()
            }
        })

        onBack.setOnClickListener { presenter.onBack() }

        onClear.setOnClickListener { presenter.onClear() }

        onNext.setOnClickListener { presenter.onNext() }
    }

    fun presetInkView() {
        ink.setColor(Color.parseColor("#517b93"))
        ink.setMinStrokeWidth(1.5f)
        ink.setMaxStrokeWidth(6f)
    }

    override fun clearInk() {
        ink.clear()
    }

    override fun enableNextButton(enable: Boolean) {
        onClear.setVisibility(enable)
        onNext.setVisibility(enable)

        if (enable) {
            onNext.post { onNext.setVisibility(enable) }
            onClear.post { onClear.setVisibility(enable) }
        }
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }
}
