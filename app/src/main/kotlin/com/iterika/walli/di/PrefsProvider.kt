package com.iterika.walli.di

import android.content.Context
import com.iterika.walli.Prefs
import javax.inject.Provider

class PrefsProvider(var context : Context) : Provider<Prefs> {

    override fun get(): Prefs {
        return Prefs(context)
    }
}
