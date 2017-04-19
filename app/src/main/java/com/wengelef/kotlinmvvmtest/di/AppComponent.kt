package com.wengelef.kotlinmvvmtest.di

import com.wengelef.kotlinmvvmtest.advanced.AdvancedFragment
import com.wengelef.kotlinmvvmtest.main.MainActivity
import com.wengelef.kotlinmvvmtest.simple.SimpleFragment
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = arrayOf(AppModule::class, RestModule::class, UtilityModule::class))
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(simpleFragment: SimpleFragment)
    fun inject(advancedFragment: AdvancedFragment)
}