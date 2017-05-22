package com.wengelef.kotlinmvvmtest.advanced.state

sealed class AdvancedViewState : State<AdvancedViewState> {

    class Idle: AdvancedViewState()
    class Loading: AdvancedViewState()
    data class Success(val users: List<com.wengelef.kotlinmvvmtest.model.User>): AdvancedViewState()
    class Error(val message: String): AdvancedViewState()
    class Connection(val connected: Boolean): AdvancedViewState()
}