package com.nakersolutionid.nakersolutionid.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.UploadFile
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.ui.components.MenuItem
import com.nakersolutionid.nakersolutionid.ui.components.MenuItemButton
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

// A list of all menu items. This is now the single source of truth for the grid.
private val menuItems = listOf(
    MenuItem(1, "Buat Laporan", Icons.Outlined.Create),
    MenuItem(2, "Buat Berita Acara", Icons.Outlined.Newspaper),
    MenuItem(3, "Buat Sertifikat", Icons.Outlined.Description),
    MenuItem(4, "Buat Surat Permohonan", Icons.Outlined.UploadFile),
    MenuItem(5, "Buat Surat Keterangan", Icons.AutoMirrored.Outlined.Article),
    MenuItem(6, "Riwayat", Icons.Outlined.History),
    MenuItem(7, "Setting", Icons.Outlined.Settings),
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onLogoutClick: () -> Unit,
    onMenuItemClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle side-effects from registrationResult
    val logoutResult = uiState.logoutResult
    LaunchedEffect(logoutResult) {
        when (logoutResult) {
            is Resource.Success -> {
                // Navigate on success
                viewModel.toggleLoading(false)
                viewModel.onStateHandled()
                onLogoutClick()
            }
            is Resource.Error -> {
                // Show error message
                val errorMessage = logoutResult.message ?: "An unknown error occurred"
                scope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
                viewModel.toggleLoading(false)
                viewModel.onStateHandled()
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
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header Row with Title and Logout Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 24.dp, end = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Pushes items to ends
            ) {
                Column {
                    Text(
                        text = "Dashboard",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Silakan pilih menu yang tersedia",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                IconButton(
                    onClick = { viewModel.onLogoutClicked() },
                    enabled = uiState.logoutResult !is Resource.Loading,
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.error
                        )
                    } else {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Logout,
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.error // Use error color for destructive actions
                        )
                    }
                }
            }

            // LazyVerticalGrid automatically arranges items and is scrollable.
            // GridCells.Adaptive makes the layout responsive to screen size changes.
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp), // Each item will be at least 150.dp wide
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp), // Padding around the entire grid
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(menuItems) { item ->
                    MenuItemButton(
                        menuItem = item,
                        onClick = { onMenuItemClick(item.id) }
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
fun HomeScreenPreview() {
    KoinApplicationPreview(application = {
        // Use only the preview module
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            HomeScreen(onLogoutClick = {}, onMenuItemClick = {})
        }
    }
}