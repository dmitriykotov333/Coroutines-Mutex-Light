package com.laba.lab.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun TaskItem(
    item: String,
    eventRemove: (String) -> Unit,
    eventRefresh: (String) -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f), text = "Value $item"
            )
            IconButton(onClick = { eventRefresh(item) }) {
                Icon(Icons.Filled.Refresh, "refresh")
            }
            IconButton(onClick = { eventRemove(item) }) {
                Icon(Icons.Filled.Delete, "remove")
            }
        }
    }
}