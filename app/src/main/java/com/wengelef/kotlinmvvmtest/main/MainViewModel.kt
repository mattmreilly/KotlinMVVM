package com.wengelef.kotlinmvvmtest.main

import android.databinding.BaseObservable
import android.view.View
import rx.Observable
import rx.subjects.PublishSubject
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseObservable() {

    private val simpleClicks = PublishSubject.create<View>()
    private val lessSimpleClicks = PublishSubject.create<View>()
    private val evenLessSimpleClicks = PublishSubject.create<View>()

    fun getSimpleButtonText(): String = "Simple"
    val lessSimpleButtonText = "Less Simple"
    fun getEvenLessSimpleButtonText(): String = "Even Less Simple"

    fun onSimpleClicks(view: View) {
        simpleClicks.onNext(view)
    }

    fun onLessSimpleClicks(view: View) {
        lessSimpleClicks.onNext(view)
    }

    fun onEvenLessSimpleClicks(view: View) {
        evenLessSimpleClicks.onNext(view)
    }

    fun getSimpleFragmentEvents(): Observable<View> = simpleClicks.asObservable()
    fun getLessSimpleFragmentEvents(): Observable<View> = lessSimpleClicks.asObservable()
    fun getEvenLessSimpleFragmentEvents(): Observable<View> = evenLessSimpleClicks.asObservable()
}