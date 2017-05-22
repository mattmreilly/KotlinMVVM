package com.wengelef.kotlinmvvmtest.advanced.state

import com.wengelef.kotlinmvvmtest.model.User

sealed class AdvancedViewState : State<AdvancedViewState> {

    class Idle: AdvancedViewState()
    class Loading: AdvancedViewState()
    data class Success(val users: List<User>): AdvancedViewState()
    class Error(val message: String): AdvancedViewState()
    class Connection(val connected: Boolean): AdvancedViewState()
}