package com.wengelef.kotlinmvvmtest.rest.repo

import com.wengelef.kotlinmvvmtest.db.UserDB
import com.wengelef.kotlinmvvmtest.model.User
import com.wengelef.kotlinmvvmtest.rest.service.UserService
import com.wengelef.kotlinmvvmtest.util.ConnectionObserver
import rx.Observable
import javax.inject.Inject

class UserRepository @Inject constructor(
        private val userService: UserService,
        private val userDB: UserDB,
        connectionObserver: ConnectionObserver): CacheRepository<List<User>>(connectionObserver) {

    private var memory: List<User>? = null

    override val memoryCall: Observable<List<User>>
        get() = Observable.just(memory)

    override val cacheCall: Observable<List<User>>
        get() = Observable.just(userDB.get())

    override val restCall: Observable<List<User>>
        get() = userService.getUsers()

    override fun serialize(response: List<User>) {
        userDB.put(response)
    }

    override fun keepMemory(response: List<User>?) {
        memory = response
    }
}