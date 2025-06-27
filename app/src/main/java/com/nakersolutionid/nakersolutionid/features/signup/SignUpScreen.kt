package com.nakersolutionid.nakersolutionid.features.signup

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nakersolutionid.nakersolutionid.R
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.di.networkModule
import com.nakersolutionid.nakersolutionid.di.repositoryModule
import com.nakersolutionid.nakersolutionid.di.useCaseModule
import com.nakersolutionid.nakersolutionid.di.viewModelModule
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    viewModel: SignUpViewModel = koinViewModel()
) {
//    var firstName by rememberSaveable { mutableStateOf("") }
//    var lastName by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    // 1. Add state to hold potential error messages for each field.
//    var firstNameError by rememberSaveable { mutableStateOf<String?>(null) }
//    var lastNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var nameError by rememberSaveable { mutableStateOf<String?>(null) }
    var usernameError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
    var confirmPasswordError by rememberSaveable { mutableStateOf<String?>(null) }

    val registrationState by viewModel.registrationState.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Keeping the logo and titles consistent with the Login screen
            Image(
                modifier = Modifier.size(220.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo),
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Buat Akun Baru",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                text = "Isi form di bawah untuk mendaftar",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            // Row for first and last name
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(10.dp)
//            ) {
//                OutlinedTextField(
//                    modifier = Modifier.weight(1f),
//                    value = firstName,
//                    onValueChange = {
//                        firstName = it
//                        firstNameError = null
//                    },
//                    shape = RoundedCornerShape(12.dp),
//                    singleLine = true,
//                    label = { Text(stringResource(R.string.first_name)) },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                    isError = firstNameError != null,
//                    supportingText = {
//                        if (firstNameError != null) {
//                            Text(text = firstNameError!!, color = MaterialTheme.colorScheme.error)
//                        }
//                    }
//                )
//
//                OutlinedTextField(
//                    modifier = Modifier.weight(1f),
//                    value = lastName,
//                    onValueChange = {
//                        lastName = it
//                        lastNameError = null
//                    },
//                    shape = RoundedCornerShape(12.dp),
//                    singleLine = true,
//                    label = { Text(stringResource(R.string.last_name)) },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                    isError = lastNameError != null,
//                    supportingText = {
//                        if (lastNameError != null) {
//                            Text(text = lastNameError!!, color = MaterialTheme.colorScheme.error)
//                        }
//                    }
//                )
//            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = {
                    name = it
                    nameError = null
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                label = { Text(stringResource(R.string.name)) },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.AccountCircle,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = nameError != null
            )

            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = nameError != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = nameError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                    textAlign = TextAlign.Left
                )
            }

            // Username field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = {
                    username = it
                    usernameError = null
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                label = { Text(stringResource(R.string.username)) },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.PersonOutline,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = usernameError != null
            )

            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = usernameError != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = usernameError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                    textAlign = TextAlign.Left
                )
            }

            // Password field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                label = { Text(stringResource(R.string.password)) },
                leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = if (isPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordError != null
            )

            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = passwordError != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = passwordError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                    textAlign = TextAlign.Left
                )
            }

            // Confirm password field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = null
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                label = { Text(stringResource(R.string.confirm_password)) },
                leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                        Icon(
                            imageVector = if (isConfirmPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = if (isConfirmPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = confirmPasswordError != null
            )

            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = confirmPasswordError != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = confirmPasswordError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                    textAlign = TextAlign.Left
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Sign-up Button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = {
                    var isFormValid = true

                    if (name.isBlank()) {
                        nameError = "Nama lengkap diperlukan"
                        isFormValid = false
                    }
                    if (username.isBlank()) {
                        usernameError = "Nama pengguna diperlukan"
                        isFormValid = false
                    }
                    if (password.isBlank()) {
                        passwordError = "Kata sandi diperlukan"
                        isFormValid = false
                    }
                    if (confirmPassword.isBlank()) {
                        confirmPasswordError = "Konfirmasi kata sandi diperlukan"
                        isFormValid = false
                    }
                    if (password != confirmPassword) {
                        confirmPasswordError = "Kata sandi tidak cocok"
                        isFormValid = false
                    }

                    if (isFormValid) {
                        val name = name
                        viewModel.registerUser(name, username, password)
                    }
                },
                enabled = registrationState !is Resource.Loading,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                if (registrationState is Resource.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = stringResource(R.string.sign_up),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            // Prompt to log in
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sudah memiliki akun?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = onLoginClick) {
                    Text(
                        text = stringResource(R.string.login),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }


    // Handle registration state
    when (val state = registrationState) {
        is Resource.Success -> {
            // Navigate to login or home screen
            LaunchedEffect(Unit) {
                onLoginClick()
            }
        }
        is Resource.Error -> {
            // Show error message
            val errorMessage = state.message ?: "An unknown error occurred"
            // You can show a Snackbar or a Toast here
            // For example:
             LaunchedEffect(errorMessage) {
                 scope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                 }
             }
        }
        else -> {
//            Log.i("PEPEW", "Success")
            // Do nothing
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Phone View")
@Preview(showBackground = true, device = Devices.TABLET, showSystemUi = true, name = "Tablet View")
@Composable
fun SignUpScreenPreview() {
    KoinApplication(application = {
        // your preview config here
        modules(
            listOf(
                networkModule,
                useCaseModule,
                viewModelModule,
                repositoryModule
            )
        )
    }) {
        NakersolutionidTheme {
            SignUpScreen(onLoginClick = {})
        }
    }
}
