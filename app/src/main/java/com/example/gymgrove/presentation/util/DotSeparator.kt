package com.example.gymgrove.presentation.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.gymgrove.R


@Composable
fun DotSeparator(
    modifier: Modifier = Modifier
) {
    Text(text = stringResource(id = R.string.dot_separator), modifier = modifier)
}