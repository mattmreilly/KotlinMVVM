package com.wengelef.kotlinmvvmtest.di

import android.content.Context
import com.wengelef.kotlinmvvmtest.util.ConnectionObserver
import com.wengelef.kotlinmvvmtest.util.SimpleConnectionObserver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton @Module
class UtilityModule {

    @Singleton @Provides fun provideSimpleConnectionObserver(context: Context): ConnectionObserver = SimpleConnectionObserver(context)
}