package com.wengelef.kotlinmvvmtest.rest.repo

import com.wengelef.kotlinmvvmtest.extension.isNetworkException
import com.wengelef.kotlinmvvmtest.util.ConnectionObserver
import rx.Observable
import rx.schedulers.Schedulers

abstract class CacheRepository<T> (private val connectionObserver: ConnectionObserver) {

    private val forceNetworkMap: (T, Boolean) -> Observable<T> = { data, forceNetwork ->
        if (forceNetwork) Observable.just(null) else Observable.just(data)
    }

    protected open val memoryCall: Observable<T> = Observable.just(null)
    protected open val cacheCall: Observable<T> = Observable.just(null)
    abstract protected val restCall: Observable<T>

    protected open fun serialize(response: T) {}
    protected open fun keepMemory(response: T?) {}

    fun getData(networkErrorHandler: () -> Unit = {}, forceNetwork: Boolean = false): Observable<T> {
        return Observable.concat(
                memoryCall.flatMap { forceNetworkMap(it, forceNetwork) },
                cacheCall.subscribeOn(Schedulers.computation())
                        .flatMap { forceNetworkMap(it, forceNetwork) }
                        .doOnNext { keepMemory(it) },
                restCall.subscribeOn(Schedulers.io())
                        .retryWhen { errors -> errors.flatMap { error: Throwable ->
                            if (error.isNetworkException()) {
                                networkErrorHandler()
                                connectionObserver.networkChanges().filter { it }.first()
                            } else {
                                Observable.error<Throwable>(error)
                            }
                        } }
                        .doOnNext { serialize(it) })
                .takeFirst { it != null }
    }
}