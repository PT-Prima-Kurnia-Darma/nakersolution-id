package com.nakersolutionid.nakersolutionid.features.bap

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BAPCreationAppBar(
    modifier: Modifier = Modifier,
    name: String,
    actionEnable: Boolean,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(name, fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackClick() }
            ) { Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back") }
        },
        actions = {
            TextButton(
                modifier = Modifier,
                onClick = onSaveClick,
                enabled = !actionEnable,
                colors = ButtonDefaults.textButtonColors(
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (actionEnable) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save"
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun BAPCreationAppBarPreview() {
    NakersolutionidTheme {
        BAPCreationAppBar(
            onBackClick = {},
            onSaveClick = {},
            name = "Elevator",
            actionEnable = true
        )
    }
}