package com.iterika.walli

import android.app.Application
import android.support.multidex.MultiDex
import com.iterika.walli.di.app.AppModule
import toothpick.Toothpick
import toothpick.Toothpick.setConfiguration
import toothpick.configuration.Configuration.forDevelopment
import toothpick.configuration.Configuration.forProduction
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator
import toothpick.smoothie.module.SmoothieApplicationModule
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.Constraint
import com.firebase.jobdispatcher.RetryStrategy
import com.firebase.jobdispatcher.Trigger
import com.firebase.jobdispatcher.Lifetime
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val configuration = if (BuildConfig.DEBUG) forDevelopment() else forProduction()
        setConfiguration(configuration.disableReflection())
        FactoryRegistryLocator.setRootRegistry(FactoryRegistry())
        MemberInjectorRegistryLocator.setRootRegistry(MemberInjectorRegistry())

        val appScope = Toothpick.openScope(this)
        appScope.installModules(SmoothieApplicationModule(this), AppModule(this))

        if (Prefs(this).getToken()?.isNotEmpty() == true) {

            val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))

            val locationsJob = dispatcher.newJobBuilder()
                    .setService(LocationJobService::class.java)
                    .setTag("tag-job-location")
                    .setRecurring(true)
                    .setLifetime(Lifetime.FOREVER)
                    .setTrigger(Trigger.executionWindow(30, 60))
                    .setReplaceCurrent(true)
                    .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .build()

            dispatcher.mustSchedule(locationsJob)
        }

        Fabric.with(this, Crashlytics())

        MultiDex.install(this)
    }
}