package com.wengelef.kotlinmvvmtest.advanced

sealed class Response<T> : State<Response<T>> {

    class Success<T>(val data: T): Response<T>()
    class Failure<T>(val reason: String): Response<T>()
}