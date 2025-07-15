package com.nakersolutionid.nakersolutionid.features.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material.icons.outlined.DoorSliding
import androidx.compose.material.icons.outlined.Factory
import androidx.compose.material.icons.outlined.FireTruck
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.ui.components.MenuItem
import com.nakersolutionid.nakersolutionid.ui.components.MenuItemButton
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

private val menuItems = listOf(
    MenuItem(1, "Instalasi Listrik dan Penyalur Petir", Icons.Outlined.Bolt),
    MenuItem(2, "Instalasi Proteksi Kebakaran", Icons.Outlined.LocalFireDepartment),
    MenuItem(3, "Pesawat Angkat dan Angkut", Icons.Outlined.FireTruck),
    MenuItem(4, "Pesawat Uap dan Bejana Tekan", Icons.Outlined.Factory),
    MenuItem(5, "Pesawat Tenaga dan Produksi", Icons.Outlined.Construction),
    MenuItem(6, "Elevator dan Escalator", Icons.Outlined.DoorSliding)
)

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    onMenuTypeClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            // Header Row with Title and Logout Button
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Pushes items to ends
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(end = 16.dp),
                    onClick = { onBackClick() }
                ) {
                    Icon(
                        Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Navigate back",
                        modifier = Modifier.size(64.dp)
                    )
                }
                Column {
                    Text(
                        text = "Tipe Pemeriksaan",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Silakan pilih tipe pemeriksaan yang sesuai",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
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
                        onClick = {
                            onMenuTypeClick(item.id)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Phone View")
@Preview(showBackground = true, device = Devices.TABLET, showSystemUi = true, name = "Tablet View")
@Composable
fun ReportScreenPreview() {
    KoinApplicationPreview(application = {
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            ReportScreen(onMenuTypeClick = {}, onBackClick = {})
        }
    }
}
