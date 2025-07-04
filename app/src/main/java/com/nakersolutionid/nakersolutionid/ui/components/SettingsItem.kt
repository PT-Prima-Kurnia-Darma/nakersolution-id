package com.nakersolutionid.nakersolutionid.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import com.nakersolutionid.nakersolutionid.utils.SettingsItemShape

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    position: SettingsItemShape = SettingsItemShape.MIDDLE,
    title: String,
    subtitle: String,
    enable: Boolean = true,
    icons: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    val cardCornerRadius = 12.dp

    val topShape = RoundedCornerShape(
        topStart = cardCornerRadius,
        topEnd = cardCornerRadius,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    val allShape = RoundedCornerShape(cardCornerRadius)

    val bottomShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = cardCornerRadius,
        bottomEnd = cardCornerRadius
    )

    val shape = when (position) {
        SettingsItemShape.TOP -> topShape
        SettingsItemShape.MIDDLE -> RectangleShape // No rounding for middle items
        SettingsItemShape.BOTTOM -> bottomShape
        SettingsItemShape.ALL -> allShape
    }

    ListItem(
        modifier = modifier
            .clip(shape)
            .clickable(enabled = enable) {
                onClick()
            },
        headlineContent = { Text(title) },
        supportingContent = { Text(subtitle) },
        trailingContent = { icons?.invoke() },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}

@Preview
@Composable
fun SettingsItemPreview() {
    NakersolutionidTheme {
        SettingsItem(
            title = "Full name",
            subtitle = "Muhammad Azka Naufal"
        )
    }
}