package com.iterika.walli.di.app

import android.app.Application
import com.iterika.walli.Prefs
import com.iterika.walli.di.PrefsProvider
import toothpick.config.Module

class AppModule(application : Application) : Module() {

    init {
        bind(Prefs::class.java).toProviderInstance(PrefsProvider(application))
    }
}
