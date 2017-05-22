package com.wengelef.kotlinmvvmtest.advanced.state

interface State<T> {

    fun <U> convert(converter: (State<T>) -> State<U>): State<U> = converter(this)

    fun value(): State<T> = this
}