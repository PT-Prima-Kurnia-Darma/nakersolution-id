package com.nakersolutionid.nakersolutionid.features.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme

// Data class to represent the information on the card
data class HistoryInfo(
    val name: String,
    val subName: String,
    val typeInspection: String,
    val type: String,
    val createdAt: String
)

@Composable
fun HistoryItem(
    info: HistoryInfo,
    onDeleteClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onEditClick: () -> Unit,
    onPreviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Main title of the inspectionEntity
            Text(
                text = info.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Sub-name or category
            Text(
                text = info.subName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Detailed information section
            Text(
                text = info.typeInspection,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = info.typeInspection,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = info.type,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Divider to separate info from actions
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))
            // Action buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    info.createdAt,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(1f)
                )
                ActionButton(icon = Icons.Default.Delete, description = "Delete", onClick = onDeleteClick)
                ActionButton(icon = Icons.Default.Download, description = "Download", onClick = onDownloadClick)
                ActionButton(icon = Icons.Default.Edit, description = "Edit", onClick = onEditClick)
                ActionButton(icon = Icons.Default.Visibility, description = "Preview", onClick = onPreviewClick)
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ActionButton(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
private fun HistoryItemPreview() {
    NakersolutionidTheme {
        HistoryItem(
            info = HistoryInfo("Elevator dan Eskalator", "Elevator", "Pemeriksaan dan Pengujian Berkala", "Elevator Penumpang", "2024-07-26T10:00:00Z"),
            onDeleteClick = {},
            onDownloadClick = {  },
            onEditClick = {},
            onPreviewClick = {}
        )
    }
}