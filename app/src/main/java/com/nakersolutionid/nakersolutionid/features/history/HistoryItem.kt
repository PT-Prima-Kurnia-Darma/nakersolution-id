package com.nakersolutionid.nakersolutionid.features.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SyncProblem
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import com.nakersolutionid.nakersolutionid.utils.DownloadState
import com.nakersolutionid.nakersolutionid.utils.Utils

@Composable
fun HistoryItem(
    history: History,
    downloadState: DownloadState, // TERIMA STATE BARU
    onDeleteClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onShareClick: (String) -> Unit, // TERIMA LAMBDA BARU
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Section 1: Header - Identitas Utama Laporan
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically // Align items vertically in the center
            ) {
                // Add weight(1f) to this Column to make it fill all available space
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = history.ownerName ?: "Nama Pemilik Tidak Tersedia",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = history.equipmentType,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                // This Icon will now be pushed to the far right
                if (history.isSynced) {
                    Icon(
                        modifier = Modifier
                            .size(42.dp),
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Synced",
                        tint = MaterialTheme.colorScheme.primary// Changed for clarity
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .size(42.dp),
                        imageVector = Icons.Filled.SyncProblem,
                        contentDescription = "Not synced",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section 2: Details - Informasi Spesifik Laporan
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                InfoRow(label = "Jenis Pemeriksaan", value = history.examinationType)
                InfoRow(label = "Jenis Laporan", value = history.inspectionType.toDisplayString())
                InfoRow(label = "Jenis Alat", value = history.subInspectionType.toDisplayString())
                InfoRow(label = "Jenis Dokumen", value = history.documentType.toDisplayString())
                InfoRow(label = "Tanggal Laporan", value = history.reportDate ?: "-")
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Section 3: Footer - Metadata dan Tombol Aksi
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Info tanggal pembuatan laporan
                Text(
                    text = history.createdAt?.let { "Dibuat: ${Utils.formatIsoDate(it)}" } ?: run { "Tanggal Tidak Tersedia" },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )

                // Tombol Aksi
                ActionButton(icon = Icons.Default.Delete, description = "Delete", onClick = onDeleteClick)
                DownloadActionButton(
                    downloadState = downloadState,
                    onDownloadClick = onDownloadClick,
                    onShareClick = onShareClick
                )
                ActionButton(icon = Icons.Default.Edit, description = "Edit", onClick = onEditClick)
            }
        }
    }
}

/**
 * Composable untuk menampilkan baris informasi dengan format "Label: Nilai".
 */
@Composable
private fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

/**
 * Composable untuk tombol aksi di dalam card.
 */
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

// Bisa diletakkan di file HistoryItem.kt atau file terpisah
@Composable
fun DownloadActionButton(
    downloadState: DownloadState,
    onDownloadClick: () -> Unit,
    onShareClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    /*val scope = rememberCoroutineScope()
    var hasTriggeredInitialShare by remember { mutableStateOf(false) }

    // Trigger share otomatis HANYA saat transisi dari Downloading ke Downloaded
    LaunchedEffect(downloadState) {
        if (downloadState is DownloadState.Downloaded && !hasTriggeredInitialShare) {
            onShareClick(downloadState.filePath)
            hasTriggeredInitialShare = true
        }
        // Reset trigger jika state kembali ke idle/downloading
        if (downloadState !is DownloadState.Downloaded) {
            hasTriggeredInitialShare = false
        }
    }*/

    Box(
        modifier = modifier.size(48.dp), // Beri ukuran tetap agar UI tidak "loncat"
        contentAlignment = Alignment.Center
    ) {
        when (downloadState) {
            is DownloadState.Idle -> {
                IconButton(onClick = onDownloadClick) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            is DownloadState.Downloading -> {
                CircularProgressIndicator(
                    progress = { downloadState.progress / 100f },
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            }
            is DownloadState.Downloaded -> {
                IconButton(onClick = { onShareClick(downloadState.filePath) }) {
                    Icon(
                        imageVector = Icons.Filled.Share, // Gunakan Share Icon
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HistoryItemPreview() {
    val sampleHistory = History(
        id = 1L,
        extraId = "ext-001",
        moreExtraId = "Wakawe",
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.EE,
        subInspectionType = SubInspectionType.Elevator,
        equipmentType = "Elevator Penumpang",
        examinationType = "Pemeriksaan dan Pengujian Berkala",
        ownerName = "PT Gedung Sejahtera",
        createdAt = "2025-07-06T15:22:10.123Z",
        reportDate = "25 Oktober 2024",
        isSynced = false
    )

    NakersolutionidTheme {
        HistoryItem(
            history = sampleHistory,
            onDeleteClick = {},
            onDownloadClick = {},
            onEditClick = {},
            downloadState = DownloadState.Idle,
            onShareClick = {},
        )
    }
}