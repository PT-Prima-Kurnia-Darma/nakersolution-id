package com.nakersolutionid.nakersolutionid.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nakersolutionid.nakersolutionid.features.settings.SettingsUiState
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme

@Composable
fun ChangeUsernameDialog(
    modifier: Modifier = Modifier,
    uiState: SettingsUiState,
    onValueChangeUsername: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    onCancellation: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        ChangeUsernameContent(
            modifier = modifier,
            uiState = uiState,
            onValueChangeUsername = { onValueChangeUsername(it) },
            onConfirmation = { onConfirmation() },
            onCancellation = { onCancellation() }
        )
    }
}

@Composable
fun ChangeUsernameContent(
    modifier: Modifier,
    uiState: SettingsUiState,
    onValueChangeUsername: (String) -> Unit,
    onConfirmation: () -> Unit,
    onCancellation: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                text = "Ganti Nama Pengguna",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            // Name field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.newUsername,
                onValueChange = { onValueChangeUsername(it) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                label = { Text("Nama pengguna baru") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                isError = uiState.usernameError != null
            )

            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = uiState.usernameError != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = uiState.usernameError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                    textAlign = TextAlign.Left
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    modifier = Modifier,
                    enabled = !uiState.isLoading,
                    onClick = {
                        focusManager.clearFocus()
                        onCancellation()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(
                        "Batal",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.width(4.dp))

                Button(
                    modifier = Modifier
                        .width(100.dp),
                    onClick = {
                        focusManager.clearFocus()
                        onConfirmation()
                    },
                    enabled = !uiState.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = "Simpan",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ChangeUsernameContentPreview() {
    NakersolutionidTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceDim)
                .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            ChangeUsernameContent(
                modifier = Modifier,
                uiState = SettingsUiState(),
                onValueChangeUsername = {},
                onConfirmation = {},
                onCancellation = {}
            )
        }
    }
}