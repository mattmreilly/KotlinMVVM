package com.wengelef.kotlinmvvmtest.util

import rx.Observable

interface ConnectionObserver {

    fun networkChanges(): Observable<Boolean>
    fun getConnected(): Boolean
}