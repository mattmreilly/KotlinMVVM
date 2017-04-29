package com.wengelef.kotlinmvvmtest.advanced

import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.wengelef.kotlinmvvmtest.BR
import com.wengelef.kotlinmvvmtest.LceViewModel
import com.wengelef.kotlinmvvmtest.model.User
import com.wengelef.kotlinmvvmtest.rest.repo.UserRepository
import com.wengelef.kotlinmvvmtest.util.ConnectionObserver
import rx.subjects.PublishSubject
import javax.inject.Inject

@BindingAdapter("dataSetChanged")
fun onDataSetChange(recycler: RecyclerView, users: List<User>?) {
    if (users != null) {
        recycler.adapter = UsersRecyclerAdapter(users)
    }
}

class AdvancedViewModel @Inject constructor(
        private val userRepository: UserRepository,
        connectionObserver: ConnectionObserver) : LceViewModel<List<User>>() {

    private val viewStates = PublishSubject.create<State<AdvancedViewState>>()

    private var users: List<User>? = null
    private var loading = false
    private var error = false
    private var errorMsg = ""
    private var connected = true

    init {
        viewStates.subscribe {
            when (it) {
                is AdvancedViewState.Loading -> onLoading()
                is AdvancedViewState.Success -> onContent(it.users)
                is AdvancedViewState.Error -> onError(it.message)
                is AdvancedViewState.Connection -> onConnectivity(it.connected)
            }
        }

        connectionObserver.networkChanges()
                .subscribe { viewStates.onNext(AdvancedViewState.Connection(it)) }

        loadData()
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
        notifyPropertyChanged(BR.reloadButtonEnabled)
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
        notifyPropertyChanged(BR.reloadButtonEnabled)
    }

    fun loadData() {
        viewStates.onNext(AdvancedViewState.Loading())

        userRepository.getData()
                .subscribe {
                    viewStates.onNext(when (it) {
                        is Response.Success -> AdvancedViewState.Success(it.data)
                        is Response.Failure -> AdvancedViewState.Error(it.reason)
                    })
                }
    }

    @Bindable fun getError(): Boolean = error
    @Bindable fun getErrorMessage(): String = errorMsg
    @Bindable fun getLoading(): Boolean = loading
    @Bindable fun getReloadButtonEnabled(): Boolean = connected && !loading

    fun getUsers(): List<User>? = users
}