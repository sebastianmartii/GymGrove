package com.example.gymgrove.presentation.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.gymgrove.R

@Composable
fun DashSeparator() {
    Text(text = stringResource(id = R.string.dash_separator))
}