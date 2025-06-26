package com.nakersolutionid.nakersolutionid.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme

/**
 * Data class to represent a menu item on the home screen.
 * This makes the grid structure clean and easy to manage.
 */
private data class MenuItem(
    val title: String,
    val icon: ImageVector
)

// A list of all menu items. This is now the single source of truth for the grid.
private val menuItems = listOf(
    MenuItem("Buat Laporan", Icons.Outlined.Create),
    MenuItem("Buat Berita Acara", Icons.Outlined.Newspaper),
    MenuItem("Buat Sertifikat", Icons.Outlined.Description),
    MenuItem("Buat Surat Permohonan", Icons.Outlined.UploadFile),
    MenuItem("Buat Surat Keterangan", Icons.AutoMirrored.Outlined.Article),
    MenuItem("Riwayat", Icons.Outlined.History),
)

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .systemBarsPadding() // Handles status and navigation bar padding
    ) {
        // A simple, clear title for the screen
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp)
        )
        Text(
            text = "Silakan pilih menu yang tersedia",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
        )

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
                    onClick = { /* Handle click for item.title */ }
                )
            }
        }
    }
}

/**
 * A reusable composable for the menu buttons.
 * This cleans up the main layout and makes the button style consistent.
 */
@Composable
private fun MenuItemButton(
    menuItem: MenuItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f), // Enforces a square 1:1 aspect ratio
        shape = RoundedCornerShape(16.dp), // Slightly more rounded corners
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        contentPadding = PaddingValues(12.dp)
    ) {
        // This column arranges the icon and text vertically and centers them.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = menuItem.icon,
                contentDescription = menuItem.title, // Important for accessibility
                modifier = Modifier.size(48.dp) // A good size for the icon
            )
            Spacer(modifier = Modifier.height(12.dp)) // Adds space between icon and text
            Text(
                text = menuItem.title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium, // Adjusted for better fit
                lineHeight = MaterialTheme.typography.bodyMedium.fontSize * 1.2
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Phone View")
@Preview(showBackground = true, device = Devices.TABLET, showSystemUi = true, name = "Tablet View")
@Composable
fun HomeScreenPreview() {
    NakersolutionidTheme {
        HomeScreen()
    }
}