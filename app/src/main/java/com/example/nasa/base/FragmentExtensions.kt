package com.example.nasa.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

fun <T> Fragment.observe(flow: Flow<T>, body: (T) -> Unit = {}) {
    flow.onStart { }
        .onCompletion { }
        .onEach { body(it) }
        .launchIn(viewLifecycleOwner.lifecycleScope)
}