package com.nakersolutionid.nakersolutionid.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp

/**
 * Data class to represent a menu item on the home screen.
 * This makes the grid structure clean and easy to manage.
 */
data class MenuItem(
    val id: Int,
    val title: String,
    val icon: ImageVector
)

/**
 * A reusable composable for the menu buttons.
 * This cleans up the main layout and makes the button style consistent.
 */
@Composable
fun MenuItemButton(
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