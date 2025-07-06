package com.nakersolutionid.nakersolutionid.features.history

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text("Riwayat", fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackClick() }
            ) { Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back") }
        },
        actions = {
            IconButton(
                onClick = { onFilterClick() },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) { Icon(Icons.Default.FilterList, contentDescription = "More") }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun SearchAppBarPreview() {
    NakersolutionidTheme {
        HistoryAppBar(onBackClick = {}, onFilterClick = {})
    }
}
