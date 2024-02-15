package com.example.gymgrove.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.gymgrove.R
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    actions: ImmutableList<AppBar.AppBarAction>,
    titleAlphaProvider: (() -> Float)? = null,
    onNavigateBack: (() -> Unit)? = null
) {
    TopAppBar(
        navigationIcon = {
            if (onNavigateBack != null) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.navigate_back_action
                        )
                    )
                }
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = LocalContentColor.current.copy(titleAlphaProvider?.invoke() ?: 1f)
            )
        },
        actions = {
            actions.onEach { action ->
                if (action.isDisplayed) {
                    IconButton(onClick = action.onClick) {
                        Icon(imageVector = action.icon, contentDescription = action.title)
                    }
                }
            }
        }
    )
}

sealed interface AppBar {
    data class AppBarAction(
        val title: String,
        val icon: ImageVector,
        val onClick: () -> Unit,
        val isDisplayed: Boolean = true
    )
}
