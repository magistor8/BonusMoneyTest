package com.magistor8.bonusmoneytest

import android.app.Application
import com.magistor8.bonusmoneytest.di.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    val baseUri = "http://dev.bonusmoney.pro/mobileapp/"

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin{
            androidContext(this@App)
            modules(myModule)
        }
    }

}