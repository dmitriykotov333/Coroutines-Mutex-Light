package com.laba.lab.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.laba.lab.presentation.MainViewModel

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val state by viewModel.stateStream.collectAsState()

    val isNotEmpty by remember {
        derivedStateOf { state.size.isNotEmpty() }
    }

    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FieldView(
                isActive = state.isActive,
                numberOfThreads = state.numberOfThreads,
                onInputVariantEvent = viewModel::onInputVariantEvent,
                startThread = viewModel::startThread
            )

            if (isNotEmpty) {
                Text(
                    modifier = Modifier, text = "Size: ${state.size}"
                )
            }

            LazyColumn {
                items(items = state.threads,
                    key = {
                        it
                    }) {
                    TaskItem(
                        item = it,
                        eventRemove = viewModel::remove,
                        eventRefresh = viewModel::change
                    )
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = viewModel::add
        ) {
            Icon(Icons.Filled.Add, "add")
        }
    }
}

