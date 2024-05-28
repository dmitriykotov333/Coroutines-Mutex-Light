package com.laba.lab.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class MainViewState(
    val isActive: Boolean = false,
    val numberOfThreads: String = "",
    val threads: ImmutableList<String> = persistentListOf(),
    val size: String = ""
)