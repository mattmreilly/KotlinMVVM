package com.wengelef.kotlinmvvmtest.rest.service

import com.wengelef.kotlinmvvmtest.model.User
import com.wengelef.kotlinmvvmtest.model.UserResponse
import retrofit2.http.GET
import rx.Observable

interface UserService {

    @GET("users")
    fun getUsers(): Observable<List<User>>
}