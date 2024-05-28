package com.laba.lab.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laba.lab.data.ConcurrentHash
import com.laba.lab.state.MainViewState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class MainViewModel : ViewModel() {

    private val _stateStream = MutableStateFlow(MainViewState())
    val stateStream = _stateStream.asStateFlow()

    private var state: MainViewState
        get() = _stateStream.value
        set(newState) {
            _stateStream.update { newState }
        }

    private val table = ConcurrentHash<UUID, String>()

    fun change(key: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(((1 until 2).random() * 1000L))
                table.findKeyByValue(key)?.let {
                    table.change(
                        it,
                        UUID.randomUUID().toString()
                    )
                }
                val rst = table.getMap().values.toImmutableList()
                val size = table.size()
                withContext(Dispatchers.Main) {
                    state = state.copy(threads = rst, size = size.toString())
                }
            }
        }
    }

    fun remove(key: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(((1 until 2).random() * 1000L))
                table.findKeyByValue(key)?.let {
                    table.remove(it)
                }
                val rst = table.getMap().values.toImmutableList()
                val size = table.size()
                withContext(Dispatchers.Main) {
                    state = state.copy(threads = rst, size = size.toString())
                }
            }
        }
    }

    fun add() {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            delay(((1 until 2).random() * 1000L))
            val key = UUID.randomUUID()
            table.put(key, "$key")
            val rst = table.getMap().values.toImmutableList()
            val size = table.size()
            withContext(Dispatchers.Main) {
                state = state.copy(threads = rst, size = size.toString())
            }
        }
    }

    fun startThread() {
        if (state.numberOfThreads.isEmpty()) return
        viewModelScope.launch {
            state = state.copy(isActive = true)
            table.clear()
            val jobs = List((state.numberOfThreads.toInt() % 10) + 5) {
                CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                    val key = UUID.randomUUID()
                    table.put(key, "$key")
                    val rst = table.getMap().values.toImmutableList()
                    val size = table.size()
                    withContext(Dispatchers.Main) {
                        state = state.copy(threads = rst, size = size.toString())
                    }
                }
            }
            jobs.forEach { it.join() }
            state = state.copy(isActive = false)
        }
    }

    fun onInputVariantEvent(value: String) {
        state = state.copy(numberOfThreads = value)
    }


}