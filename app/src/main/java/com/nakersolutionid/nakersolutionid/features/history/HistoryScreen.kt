package com.nakersolutionid.nakersolutionid.features.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

// Data class untuk menampung status filter yang dipilih
data class FilterState(
    val inspectionType: InspectionType? = null,
    val documentType: DocumentType? = null,
    val subInspectionType: SubInspectionType? = null
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    // State untuk filter yang aktif
    var activeFilters by remember { mutableStateOf(FilterState()) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            HistoryAppBar(
                onBackClick = { onBackClick() },
                onFilterClick = { showBottomSheet = true }
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HistorySearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onClear = { viewModel.onSearchQueryChange("") }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(
                    items = uiState.histories,
                    key = { it.id }
                ) { history ->
                    HistoryItem(
                        modifier = Modifier.animateItem(),
                        history = history,
                        onDeleteClick = {},
                        onDownloadClick = {},
                        onEditClick = {},
                        onPreviewClick = {}
                    )
                }
            }
        }

        if (showBottomSheet) {
            FilterSheet(
                initialState = activeFilters,
                onDismissRequest = { showBottomSheet = false },
                onApplyFilters = { newFilters ->
                    activeFilters = newFilters
                    // Di sini Anda akan memanggil fungsi ViewModel untuk filter, jika sudah ada
                    showBottomSheet = false
                },
                sheetState = sheetState
            )
        }
    }
}

@Composable
fun HistorySearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("Cari riwayat...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear search")
                }
            }
        },
        singleLine = true,
        shape = MaterialTheme.shapes.extraLarge,
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterSheet(
    initialState: FilterState,
    onDismissRequest: () -> Unit,
    onApplyFilters: (FilterState) -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    // State sementara untuk menampung perubahan sebelum diterapkan
    var tempFilters by remember { mutableStateOf(initialState) }
    // ✅ Ambil coroutine scope untuk menjalankan animasi
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // == HEADER ==
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Filter Riwayat",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    // ✅ Jalankan animasi lalu panggil onDismissRequest
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismissRequest()
                        }
                    }
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Tutup Filter")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // == KONTEN FILTER (BISA DI-SCROLL) ==
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                // -- Filter Jenis Dokumen (DocumentType) --
                FilterSection(title = "Jenis Dokumen") {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DocumentType.entries.forEach { type ->
                            FilterChip(
                                selected = tempFilters.documentType == type,
                                onClick = {
                                    tempFilters =
                                        tempFilters.copy(documentType = if (tempFilters.documentType == type) null else type)
                                },
                                label = { Text(type.toDisplayString()) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }

                // -- Filter Jenis Laporan (InspectionType) --
                FilterSection(title = "Jenis Bidang") {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        InspectionType.entries.forEach { type ->
                            FilterChip(
                                selected = tempFilters.inspectionType == type,
                                onClick = {
                                    tempFilters =
                                        tempFilters.copy(inspectionType = if (tempFilters.inspectionType == type) null else type)
                                },
                                label = { Text(type.toDisplayString()) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }

                // -- Filter Detail Jenis Laporan (SubInspectionType) dengan Dropdown --
                FilterSection(title = "Sub Bidang") {
                    var isDropdownExpanded by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = isDropdownExpanded,
                        onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
                    ) {
                        TextField(
                            value = tempFilters.subInspectionType?.toDisplayString()
                                ?: "Pilih Opsi...",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                            modifier = Modifier
                                .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false }
                        ) {
                            SubInspectionType.entries.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type.toDisplayString()) },
                                    onClick = {
                                        tempFilters = tempFilters.copy(subInspectionType = type)
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
            }

            // == TOMBOL AKSI ==
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        tempFilters = FilterState()
                    }, // Reset state sementara
                    modifier = Modifier.weight(1f)
                ) { Text("Reset") }
                Button(
                    onClick = {
                        // ✅ Jalankan animasi lalu panggil onApplyFilters
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onApplyFilters(tempFilters)
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Terapkan") }
            }
        }
    }
}

@Composable
private fun FilterSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        content()
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "Phone View")
@Preview(showBackground = true, device = Devices.TABLET, showSystemUi = true, name = "Tablet View")
@Composable
fun HistoryScreenPreview() {
    KoinApplicationPreview(application = {
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            HistoryScreen(onBackClick = {})
        }
    }
}