package com.wengelef.kotlinmvvmtest

import android.support.multidex.MultiDexApplication
import com.wengelef.kotlinmvvmtest.di.*
import timber.log.Timber

class MainApplication : MultiDexApplication() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .restModule(RestModule())
                .utilityModule(UtilityModule())
                .build()
    }

    fun getAppComponent() = appComponent
}