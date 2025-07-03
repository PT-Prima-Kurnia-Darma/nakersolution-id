package com.nakersolutionid.nakersolutionid.features.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.LocalAutofillHighlightColor
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.R
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val focusManager = LocalFocusManager.current
    val autofillManager = LocalAutofillManager.current

    val autoFillHighlightColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.5f)

    // Handle side-effects from registrationResult
    val loginResult = uiState.loginResult
    LaunchedEffect(loginResult) {
        when (loginResult) {
            is Resource.Success -> {
                // Navigate on success
                viewModel.toggleLoading(false)
                viewModel.onStateHandledSuccess()
                onLoginClick()
            }
            is Resource.Error -> {
                // Show error message
                val errorMessage = loginResult.message ?: "An unknown error occurred"
                scope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
                viewModel.toggleLoading(false)
                viewModel.onStateHandledFailed()
            }
            is Resource.Loading -> {
                viewModel.toggleLoading(true)
            }
            else -> { /* Do nothing for Loading or null */ }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        },
    ) { innerPadding ->
        // Using a Column with verticalArrangement.Center to better position the content
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding) // Apply Scaffold padding first
                .imePadding() // 1. Apply keyboard padding to shrink the layout's bounds
                .verticalScroll(rememberScrollState()) // 2. NOW, make the resized area scrollable
                .padding(horizontal = 24.dp), // Apply content padding inside the scroll
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Reduced the size of the logo for a more balanced look
            Spacer(Modifier.height(32.dp))
            Image(
                modifier = Modifier.size(172.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo),
            )
            Spacer(Modifier.height(32.dp))

            // Added a welcome message for better UX
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Selamat Datang Kembali",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                text = "Masuk untuk melanjutkan",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            CompositionLocalProvider(LocalAutofillHighlightColor provides autoFillHighlightColor) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .semantics { contentType = ContentType.Username }
                    ,
                    value = uiState.username,
                    onValueChange = { viewModel.onUsernameChange(it) },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    label = { Text(stringResource(R.string.username)) }, // Using label for better UX
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.PersonOutline,
                            contentDescription = null // Decorative icon
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    isError = uiState.usernameError != null
                )
            }

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
            CompositionLocalProvider(LocalAutofillHighlightColor provides autoFillHighlightColor) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .semantics { contentType = ContentType.Password },
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    label = { Text(stringResource(R.string.password)) }, // Using label
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = null // Decorative icon
                        )
                    },
                    visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                            Icon(
                                imageVector = if (uiState.isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = if (uiState.isPasswordVisible) stringResource(R.string.hide_password) else stringResource(
                                    R.string.show_password
                                )
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    isError = uiState.passwordError != null
                )
            }

            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = uiState.passwordError != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = uiState.passwordError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                    textAlign = TextAlign.Left
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp), // Consistent button height
                onClick = {
                    // Use this if you want to explicitly commit autofill save
                    // autofillManager?.commit()
                    focusManager.clearFocus()
                    viewModel.onLoginClicked()
                },
                enabled = uiState.loginResult !is Resource.Loading,
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
                        text = stringResource(R.string.login),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Combined the "Sign up" text and button for better accessibility and UX
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Belum memiliki akun?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                TextButton(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.onStateHandledSuccess()
                        onSignUpClick()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.sign_up),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Phone View")
@Preview(showBackground = true, device = Devices.TABLET, showSystemUi = true, name = "Tablet View")
@Preview(
    name = "Tablet Portrait",
    device = "spec:width=800dp,height=1280dp,dpi=240,orientation=portrait"
)
@Composable
fun LoginScreenPreview() {
    KoinApplicationPreview(application = {
        // Use only the preview module
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            LoginScreen(onLoginClick = {}, onSignUpClick = {})
        }
    }
}
