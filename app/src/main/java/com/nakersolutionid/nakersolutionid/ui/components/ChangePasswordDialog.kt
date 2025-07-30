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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.LocalAutofillHighlightColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nakersolutionid.nakersolutionid.R
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.features.settings.SettingsUiState
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle side-effects from changePasswordResult
    val changePasswordResult = uiState.changePasswordResult
    LaunchedEffect(changePasswordResult) {
        when (changePasswordResult) {
            is Resource.Success -> {}
            is Resource.Error -> {
                // Show error message
                val errorMessage = changePasswordResult.message ?: "An unknown error occurred"
                scope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
            }
            is Resource.Loading -> {}
            else -> {}
        }
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
            ChangePasswordContent(
                modifier = modifier,
                uiState = uiState,
                onValueChangeOldPassword = onValueChangeOldPassword,
                onValueChangeNewPassword = onValueChangeNewPassword,
                onValueChangeConfirmNewPassword =
                    onValueChangeConfirmNewPassword,
                toggleOldPasswordVisibility = toggleOldPasswordVisibility,
                toggleNewPasswordVisibility = toggleNewPasswordVisibility,
                toggleConfirmNewPasswordVisibility =
                    toggleConfirmNewPasswordVisibility,
                onConfirmation = onConfirmation,
                onCancellation = onCancellation
            )
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Position it
                    .imePadding() // Let it move up when keyboard appears
            )
        }
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
    val focusManager = LocalFocusManager.current
    val autoFillHighlightColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.5f)

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
            CompositionLocalProvider(LocalAutofillHighlightColor provides autoFillHighlightColor) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentType = ContentType.Password },
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
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    isError = uiState.oldPasswordError != null
                )
            }

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
            CompositionLocalProvider(LocalAutofillHighlightColor provides autoFillHighlightColor) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentType = ContentType.NewPassword },
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
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    isError = uiState.newPasswordError != null
                )
            }

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
            CompositionLocalProvider(LocalAutofillHighlightColor provides autoFillHighlightColor) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentType = ContentType.NewPassword },
                    value = uiState.confirmNewPassword,
                    onValueChange = { onValueChangeConfirmNewPassword(it) },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    label = {
                        Text(
                            "Konfirmasi kata sandi baru",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    visualTransformation = if (uiState.isConfirmNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { toggleConfirmNewPasswordVisibility() }) {
                            Icon(
                                imageVector = if (uiState.isConfirmNewPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = if (uiState.isConfirmNewPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                            )
                        }
                    },
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    isError = uiState.confirmNewPasswordError != null
                )
            }

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