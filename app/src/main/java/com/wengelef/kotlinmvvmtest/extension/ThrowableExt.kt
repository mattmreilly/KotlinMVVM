package com.wengelef.kotlinmvvmtest.extension

import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.isNetworkException(): Boolean {
    return (this is UnknownHostException || this is SocketTimeoutException)
}