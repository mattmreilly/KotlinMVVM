package com.wengelef.kotlinmvvmtest.simple

import android.databinding.Bindable
import com.wengelef.kotlinmvvmtest.BR
import com.wengelef.kotlinmvvmtest.LcecViewModel
import com.wengelef.kotlinmvvmtest.model.User
import com.wengelef.kotlinmvvmtest.rest.repo.UserRepository
import com.wengelef.kotlinmvvmtest.util.networkErrorHandler
import javax.inject.Inject

class SimpleViewModel @Inject constructor(private val userRepository: UserRepository) : LcecViewModel<User>() {

    private var user: User? = null

    private var loading = false
    private var error = false
    private var errorMsg = ""

    init {
        loadData()
    }

    override fun onLoading() {
        loading = true
        error = false
        notifyPropertyChanged(BR.loading)
        notifyPropertyChanged(BR.error)
    }

    override fun onError(message: String) {
        errorMsg = message
        loading = false
        error = true
        notifyPropertyChanged(BR.loading)
        notifyPropertyChanged(BR.error)
    }

    override fun onContent(content: User) {
        user = content
        loading = false
        error = false
        notifyChange()
    }

    override fun onConnectivity(connected: Boolean) {
        // NOP
    }

    fun loadData() {
        onLoading()
        userRepository.getData()
                .subscribe(
                        { onContent(it[0]) },
                        { error: Throwable -> networkErrorHandler(error, { onError() }) })
    }

    @Bindable fun getError(): Boolean = error
    @Bindable fun getLoading(): Boolean = loading

    fun getUserName(): String = user?.login ?: ""
    fun getAvatarUrl() = user?.avatar_url ?: ""
    fun getWebUrl(): String = user?.html_url ?: ""

    fun getUsernameFont() = "coolvetica.ttf"
    fun getWebUrlFont() = "GeosansLight.ttf"
}