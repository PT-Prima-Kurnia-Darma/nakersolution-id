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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nakersolutionid.nakersolutionid.R
import com.nakersolutionid.nakersolutionid.features.settings.SettingsUiState
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme

@Composable
fun ChangePasswordDialog(
    modifier: Modifier = Modifier,
    uiState: SettingsUiState,
    onValueChangeOldPassword: (String) -> Unit,
    onValueChangeNewPassword: (String) -> Unit,
    onValueChangeConfirmNewPassword: (String) -> Unit,
    toggleOldPasswordVisibility: () -> Unit,
    toggleNewPasswordVisibility: () -> Unit,
    toggleConfirmNewPasswordVisibility: () -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    onCancellation: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        ChangePasswordContent(
            modifier = modifier,
            uiState = uiState,
            onValueChangeOldPassword = { onValueChangeOldPassword(it) },
            onValueChangeNewPassword = { onValueChangeNewPassword(it) },
            onValueChangeConfirmNewPassword = { onValueChangeConfirmNewPassword(it) },
            toggleOldPasswordVisibility = { toggleOldPasswordVisibility() },
            toggleNewPasswordVisibility = { toggleNewPasswordVisibility() },
            toggleConfirmNewPasswordVisibility = { toggleConfirmNewPasswordVisibility() },
            onConfirmation = { onConfirmation() },
            onCancellation = { onCancellation() }
        )
    }
}

@Composable
fun ChangePasswordContent(
    modifier: Modifier,
    uiState: SettingsUiState,
    onValueChangeOldPassword: (String) -> Unit,
    onValueChangeNewPassword: (String) -> Unit,
    onValueChangeConfirmNewPassword: (String) -> Unit,
    toggleOldPasswordVisibility: () -> Unit,
    toggleNewPasswordVisibility: () -> Unit,
    toggleConfirmNewPasswordVisibility: () -> Unit,
    onConfirmation: () -> Unit,
    onCancellation: () -> Unit
) {
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
                text = "Ganti Kata Sandi",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            // Old password field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.oldPassword,
                onValueChange = { onValueChangeOldPassword(it) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                label = { Text("Kata sandi lama") },
                visualTransformation = if (uiState.isOldPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { toggleOldPasswordVisibility() }) {
                        Icon(
                            imageVector = if (uiState.isOldPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = if (uiState.isOldPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.oldPasswordError != null
            )

            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = uiState.oldPasswordError != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = uiState.oldPasswordError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                    textAlign = TextAlign.Left
                )
            }

            // New password field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.newPassword,
                onValueChange = { onValueChangeNewPassword(it) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                label = { Text("Kata sandi baru") },
                visualTransformation = if (uiState.isNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { toggleNewPasswordVisibility() }) {
                        Icon(
                            imageVector = if (uiState.isNewPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = if (uiState.isNewPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.newPasswordError != null
            )

            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = uiState.newPasswordError != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = uiState.newPasswordError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                    textAlign = TextAlign.Left
                )
            }

            // Confirm new password field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.confirmNewPassword,
                onValueChange = { onValueChangeConfirmNewPassword(it) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                label = { Text("Konfirmasi kata sandi baru") },
                visualTransformation = if (uiState.isConfirmNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { toggleConfirmNewPasswordVisibility() }) {
                        Icon(
                            imageVector = if (uiState.isConfirmNewPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = if (uiState.isConfirmNewPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.confirmNewPasswordError != null
            )

            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = uiState.confirmNewPasswordError != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = uiState.confirmNewPasswordError ?: "",
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
                    onClick = { onCancellation() }
                ) {
                    Text(
                        "Batal",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Spacer(Modifier.width(4.dp))

                Button(
                    modifier = Modifier,
                    onClick = { onConfirmation() }
                ) {
                    Text(
                        "Simpan",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ChangePasswordContentPreview() {
    NakersolutionidTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceDim)
                .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            ChangePasswordContent(
                modifier = Modifier,
                uiState = SettingsUiState(),
                onValueChangeOldPassword = {},
                onValueChangeNewPassword = {},
                onValueChangeConfirmNewPassword = {},
                toggleOldPasswordVisibility = {},
                toggleNewPasswordVisibility = {},
                toggleConfirmNewPasswordVisibility = {},
                onConfirmation = {},
                onCancellation = {}
            )
        }
    }
}