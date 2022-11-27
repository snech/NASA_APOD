package com.example.nasa.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

abstract class BaseViewModel<I : ViewModelIntent, S : ViewModelState> : ViewModel() {
    abstract fun sendIntent(intent: I)

    private val sharedFlow = MutableSharedFlow<S>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    fun getStateFlow(): Flow<S> = sharedFlow.map { it }

    private fun update(state: S) {
        sharedFlow.tryEmit(state)
    }

    protected fun S.updateState() = apply { update(this) }
}