package com.wengelef.kotlinmvvmtest.advanced

import android.databinding.BaseObservable
import com.wengelef.kotlinmvvmtest.model.User
import rx.subjects.PublishSubject

class UserItemViewModel(private val user: User, private val userClicks: PublishSubject<User>) : BaseObservable() {

    fun getUserName(): String = user.login
    fun getAvatarUrl() = user.avatar_url
    fun getWebUrl(): String = user.html_url

    fun getUsernameFont() = "coolvetica.ttf"
    fun getWebUrlFont() = "GeosansLight.ttf"

    fun userClick() {
        userClicks.onNext(user)
    }
}