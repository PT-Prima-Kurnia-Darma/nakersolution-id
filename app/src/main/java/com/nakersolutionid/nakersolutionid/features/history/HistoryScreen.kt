package com.nakersolutionid.nakersolutionid.features.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    val topics = listOf("Semua", "ILPP", "IPK", "PAA", "PUBT", "PTP", "EE")
    val subTopics = listOf("Semua", "Laporan", "BAP", "Sertifikat Sementara", "Surat Keterangan")
    var selectedTopic by remember { mutableStateOf(topics.first()) }
    var selectedSubTopics by remember { mutableStateOf(subTopics.first()) }

    val sheetState = rememberModalBottomSheetState()
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
                    key = { it.id } // Kunci ini penting untuk performa animasi
                ) { history ->
                    HistoryItem(
                        // Menambahkan modifier ini akan menganimasikan perubahan item
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
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                topics = topics,
                subTopics = subTopics,
                selectedTopic = selectedTopic,
                selectedSubTopics = selectedSubTopics,
                onFilterSelected = {
                    selectedTopic = it
                },
                onSubFilterSelected = {
                    selectedSubTopics = it
                }
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
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon")
        },
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    topics: List<String>,
    subTopics: List<String>,
    selectedTopic: String,
    selectedSubTopics: String,
    onFilterSelected: (String) -> Unit,
    onSubFilterSelected: (String) -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Jenis Inspeksi",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        FilterChipRow(
            filters = topics,
            selectedFilter = selectedTopic,
            onFilterSelected = onFilterSelected
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Jenis Alat",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        FilterChipRow(
            filters = topics,
            selectedFilter = selectedTopic,
            onFilterSelected = onFilterSelected
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Jenis Dokumen",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        FilterChipRow(
            filters = subTopics,
            selectedFilter = selectedSubTopics,
            onFilterSelected = onSubFilterSelected
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipRow(
    filters: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(filters) { filter ->
            FilterChip(
                selected = (filter == selectedFilter),
                onClick = { onFilterSelected(filter) },
                label = { Text(filter) }
            )
        }
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