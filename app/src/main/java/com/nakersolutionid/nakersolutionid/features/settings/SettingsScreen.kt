package com.nakersolutionid.nakersolutionid.features.settings

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.ui.components.ChangeNameDialog
import com.nakersolutionid.nakersolutionid.ui.components.ChangePasswordDialog
import com.nakersolutionid.nakersolutionid.ui.components.ChangeUsernameDialog
import com.nakersolutionid.nakersolutionid.ui.components.SettingsItem
import com.nakersolutionid.nakersolutionid.ui.components.SettingsTopAppBar
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import com.nakersolutionid.nakersolutionid.utils.SettingsItemShape
import com.nakersolutionid.nakersolutionid.utils.ThemeState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
    onLogoutClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var expanded by remember { mutableStateOf(false) }

    // Handle side-effects from logoutResult
    val logoutResult = uiState.logoutResult
    LaunchedEffect(logoutResult) {
        when (logoutResult) {
            is Resource.Success -> {
                // Navigate on success
                viewModel.toggleLoading(false)
                viewModel.onLogoutStateHandle()
                viewModel.clearUser()
                onLogoutClick()
            }
            is Resource.Error -> {
                // Show error message
                val errorMessage = logoutResult.message ?: "An unknown error occurred"
                scope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
                viewModel.toggleLoading(false)
                viewModel.onLogoutStateHandle()
            }
            is Resource.Loading -> {
                viewModel.toggleLoading(true)
            }
            else -> { /* Do nothing for Loading or null */ }
        }
    }

    // Handle side-effects from changePasswordResult
    val changePasswordResult = uiState.changePasswordResult
    LaunchedEffect(changePasswordResult) {
        when (changePasswordResult) {
            is Resource.Success -> {
                // Navigate on success
                viewModel.toggleLoading(false)
                viewModel.onChangePasswordStateHandleSuccess()
                viewModel.toggleChangePasswordDialog()
                onLogoutClick()
            }
            is Resource.Error -> {
                // Show error message
                val errorMessage = changePasswordResult.message ?: "An unknown error occurred"
                scope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
                viewModel.toggleLoading(false)
                viewModel.onChangePasswordStateHandleFailed()
            }
            is Resource.Loading -> {
                viewModel.toggleLoading(true)
            }
            else -> { /* Do nothing for Loading or null */ }
        }
    }

    // Handle side-effects from changeNameResult
    val changeNameResult = uiState.changeNameResult
    LaunchedEffect(changeNameResult) {
        when (changeNameResult) {
            is Resource.Success -> {
                // Navigate on success
                viewModel.toggleLoading(false)
                viewModel.onChangeNameStateHandleSuccess()
                viewModel.toggleChangeNameDialog()
            }
            is Resource.Error -> {
                // Show error message
                val errorMessage = changeNameResult.message ?: "An unknown error occurred"
                scope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
                viewModel.toggleLoading(false)
                viewModel.onChangeNameStateHandleFailed()
            }
            is Resource.Loading -> {
                viewModel.toggleLoading(true)
            }
            else -> { /* Do nothing for Loading or null */ }
        }
    }

    // Handle side-effects from changeUsernameResult
    val changeUsernameResult = uiState.changeUsernameResult
    LaunchedEffect(changeUsernameResult) {
        when (changeUsernameResult) {
            is Resource.Success -> {
                // Navigate on success
                viewModel.toggleLoading(false)
                viewModel.onChangeUsernameStateHandleSuccess()
                viewModel.toggleChangeUsernameDialog()
            }
            is Resource.Error -> {
                // Show error message
                val errorMessage = changeUsernameResult.message ?: "An unknown error occurred"
                scope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
                viewModel.toggleLoading(false)
                viewModel.onChangeUsernameStateHandleFailed()
            }
            is Resource.Loading -> {
                viewModel.toggleLoading(true)
            }
            else -> { /* Do nothing for Loading or null */ }
        }
    }

    if (uiState.showChangePasswordDialog) {
        ChangePasswordDialog(
            uiState = uiState,
            onValueChangeOldPassword = { viewModel.onOldPasswordChange(it) },
            onValueChangeNewPassword = { viewModel.onNewPasswordChange(it) },
            onValueChangeConfirmNewPassword = { viewModel.onConfirmNewPasswordChange(it) },
            toggleOldPasswordVisibility = { viewModel.toggleOldPasswordVisibility() },
            toggleNewPasswordVisibility = { viewModel.toggleNewPasswordVisibility() },
            toggleConfirmNewPasswordVisibility = { viewModel.toggleConfirmNewPasswordVisibility() },
            onDismissRequest = {
                viewModel.toggleChangePasswordDialog()
                viewModel.toggleChangePasswordDialog()
            },
            onConfirmation = { viewModel.onPasswordChangeSave() },
            onCancellation = {
                viewModel.onChangePasswordStateHandleSuccess()
                viewModel.toggleChangePasswordDialog()
            }
        )
    }

    if (uiState.showChangeNameDialog) {
        ChangeNameDialog(
            uiState = uiState,
            onValueChangeName = { viewModel.onNameChange(it) },
            onDismissRequest = {
                viewModel.onChangeNameStateHandleSuccess()
                viewModel.toggleChangeNameDialog()
            },
            onConfirmation = { viewModel.onNameChangeSave() },
            onCancellation = {
                viewModel.onChangeNameStateHandleSuccess()
                viewModel.toggleChangeNameDialog()
            }
        )
    }

    if (uiState.showChangeUsernameDialog) {
        ChangeUsernameDialog(
            uiState = uiState,
            onValueChangeUsername = { viewModel.onUsernameChange(it) },
            onDismissRequest = {
                viewModel.onChangeUsernameStateHandleSuccess()
                viewModel.toggleChangeUsernameDialog()
            },
            onConfirmation = { viewModel.onUsernameChangeSave() },
            onCancellation = {
                viewModel.onChangeUsernameStateHandleSuccess()
                viewModel.toggleChangeUsernameDialog()
            }
        )
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = { SettingsTopAppBar(onBackClick = { onBackClick() }) },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 4.dp),
                text = "Akun",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SettingsItem(
                        title = "Nama lengkap",
                        subtitle = uiState.name,
                        icons = {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit name",
                                modifier = Modifier
                                    .padding(12.dp)
                                    .size(24.dp))
                        },
                        enable = !uiState.isLoading,
                        position = SettingsItemShape.TOP,
                        onClick = { viewModel.toggleChangeNameDialog() }
                    )
                    HorizontalDivider()
                    SettingsItem(
                        title = "Nama pengguna",
                        subtitle = uiState.username,
                        icons = {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit username",
                                modifier = Modifier
                                    .padding(12.dp)
                                    .size(24.dp))
                        },
                        enable = !uiState.isLoading,
                        position = SettingsItemShape.BOTTOM,
                        onClick = { viewModel.toggleChangeUsernameDialog() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 4.dp),
                text = "Aplikasi",
                style = MaterialTheme.typography.titleMedium
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SettingsItem(
                        title = "Tema",
                        subtitle = uiState.currentThemeName ?: "Default sistem",
                        icons = {
                            Icon(
                                imageVector = Icons.Outlined.ExpandMore, // Inside let, 'it' refers to the non-null icon
                                contentDescription = "Edit theme",
                                modifier = Modifier
                                    .padding(12.dp)
                                    .size(24.dp)
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Terang") },
                                    leadingIcon = { Icon(Icons.Outlined.LightMode, contentDescription = null) },
                                    onClick = {
                                        viewModel.changeTheme(ThemeState.LIGHT)
                                        expanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Gelap") },
                                    leadingIcon = { Icon(Icons.Outlined.DarkMode, contentDescription = null) },
                                    onClick = {
                                        viewModel.changeTheme(ThemeState.DARK)
                                        expanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Default sistem") },
                                    leadingIcon = { Icon(Icons.Outlined.Contrast, contentDescription = null) },
                                    onClick = {
                                        viewModel.changeTheme(ThemeState.SYSTEM)
                                        expanded = false
                                    }
                                )
                            }
                        },
                        enable = !uiState.isLoading,
                        position = SettingsItemShape.MIDDLE,
                        onClick = { expanded = true }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = !uiState.isLoading,
                    onClick = { viewModel.toggleChangePasswordDialog() }
                ) {
                    Text(
                        text = "Ganti kata sandi",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.error,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = !uiState.isLoading,
                    onClick = { viewModel.onLogoutClicked() }
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = "Keluar",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
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
fun SettingsScreenPreview() {
    KoinApplicationPreview(application = {
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            SettingsScreen(onBackClick = {}, onLogoutClick = {})
        }
    }
}