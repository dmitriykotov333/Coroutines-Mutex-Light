package com.laba.lab.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun FieldView(
    isActive: Boolean,
    numberOfThreads: String,
    onInputVariantEvent: (String) -> Unit,
    startThread: () -> Unit,
) {
    Spacer(modifier = Modifier.height(16.dp))

    TextField(
        value = numberOfThreads,
        onValueChange = onInputVariantEvent,
        label = { Text(text = "Variant") },
        placeholder = { Text(text = "") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
    )

    Button(
        modifier = Modifier.fillMaxWidth(),
        enabled = !isActive,
        onClick = startThread
    ) {
        Text(text = "Enter variant")
    }
    Spacer(modifier = Modifier.height(16.dp))
}