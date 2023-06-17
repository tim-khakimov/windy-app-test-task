package com.timkhakimov.mywindytesttask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class SumViewModel : ViewModel() {

    private val _resultFlow = MutableStateFlow(0)
    val resultFlow: Flow<Int> = _resultFlow.asStateFlow()

    fun startSummation(n: Int) {
        _resultFlow.update { 0 }
        createFlows(n).forEach { flow ->
            collectFlow(flow)
        }
    }

    private fun collectFlow(flow: Flow<Int>) {
        viewModelScope.launch {
            flow.collect { value ->
                _resultFlow.value += value
            }
        }
    }

    private fun createFlows(n: Int): List<Flow<Int>> {
        return List(n) { index ->
            flow {
                delay((index + 1) * 100L)
                emit(index + 1)
            }
        }
    }
}