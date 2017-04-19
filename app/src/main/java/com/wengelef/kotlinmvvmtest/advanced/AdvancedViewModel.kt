package com.wengelef.kotlinmvvmtest.advanced

import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.wengelef.kotlinmvvmtest.BR
import com.wengelef.kotlinmvvmtest.LcecViewModel
import com.wengelef.kotlinmvvmtest.model.User
import com.wengelef.kotlinmvvmtest.rest.repo.UserRepository
import com.wengelef.kotlinmvvmtest.util.ConnectionObserver
import com.wengelef.kotlinmvvmtest.util.networkErrorHandler
import timber.log.Timber
import javax.inject.Inject

@BindingAdapter("dataSetChanged")
fun onDataSetChange(recycler: RecyclerView, users: List<User>?) {
    if (users != null) {
        recycler.adapter = UsersRecyclerAdapter(users)
    }
}

class AdvancedViewModel @Inject constructor(
        private val userRepository: UserRepository,
        connectionObserver: ConnectionObserver) : LcecViewModel<List<User>>() {

    private var users: List<User>? = null
    private var loading = false
    private var error = false
    private var errorMsg = ""
    private var connected = true

    init {
        loadData()
        connectionObserver.networkChanges()
                .subscribe { onConnectivity(it) }
    }

    override fun onContent(content: List<User>) {
        users = content
        loading = false
        error = false
        notifyChange()
    }

    override fun onLoading() {
        loading = true
        error = false
        notifyPropertyChanged(BR.loading)
        notifyPropertyChanged(BR.error)
    }

    override fun onError(message: String) {
        loading = false
        error = true
        errorMsg = message
        notifyPropertyChanged(BR.errorMessage)
        notifyPropertyChanged(BR.loading)
        notifyPropertyChanged(BR.error)
    }

    override fun onConnectivity(connected: Boolean) {
        this.connected = connected
        notifyPropertyChanged(BR.connected)
    }

    fun loadData() {
        onLoading()
        userRepository.getData({ onError("Network Error") })
                .subscribe(
                        { onContent(it) },
                        { error: Throwable -> networkErrorHandler(error, { onError() }) })
    }

    @Bindable fun getError(): Boolean = error
    @Bindable fun getErrorMessage(): String = errorMsg
    @Bindable fun getLoading(): Boolean = loading
    @Bindable fun getConnected(): Boolean = connected

    fun getUsers(): List<User>? = users
}