package com.nakersolutionid.nakersolutionid.features.history

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.ui.components.EmptyScreen
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import com.nakersolutionid.nakersolutionid.utils.DownloadState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview
import java.io.File

// Data class for holding the selected filter state
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
    onEditClick: (History) -> Unit = {},
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val activeFilters by viewModel.filterState.collectAsStateWithLifecycle()

    val lazyPagingItems: LazyPagingItems<History> =
        viewModel.historyPagingData.collectAsLazyPagingItems()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var historyToDelete by remember { mutableStateOf<History?>(null) }
    val lazyListState = rememberLazyListState()
    val refreshState = rememberPullToRefreshState()
    var isManualRefresh by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun openOrShareFile(context: Context, filePath: String) {
        val file = File(filePath)
        // Ensure the authority matches what's in your AndroidManifest.xml
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val mimeType = context.contentResolver.getType(uri) ?: "*/*"

        // 1. Create the Intent to VIEW the file (Open with...)
        val viewIntent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        // 2. Create the Intent to SEND the file (Share via...)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        // 3. Create a chooser that combines both intents
        // The system will show apps for the primary intent (viewIntent)
        // and add apps for the initial intents (shareIntent).
        val chooserIntent = Intent.createChooser(viewIntent, "Buka atau Bagikan Laporan").apply {
            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(shareIntent))
        }

        context.startActivity(chooserIntent)
    }

    LaunchedEffect(lazyPagingItems.loadState.refresh) {
        // Jika statusnya sudah tidak loading, reset flag manual refresh.
        if (lazyPagingItems.loadState.refresh !is LoadState.Loading) {
            isManualRefresh = false
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
            }
            viewModel.onUpdateUiState { it.copy(error = null) }
        }
    }

    // TAMBAHKAN BLOK LaunchedEffect INI
    LaunchedEffect(Unit) {
        viewModel.shareEvent.collect { filePath ->
            openOrShareFile(context, filePath) // Panggil fungsi share yang sudah ada
        }
    }

    LaunchedEffect(uiState.triggerSync) {
        viewModel.triggerSync()
    }

    LaunchedEffect(lazyPagingItems.loadState) {
        val errorState = lazyPagingItems.loadState.source.append as? LoadState.Error
            ?: lazyPagingItems.loadState.source.prepend as? LoadState.Error
            ?: lazyPagingItems.loadState.append as? LoadState.Error
            ?: lazyPagingItems.loadState.prepend as? LoadState.Error
        errorState?.let {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = it.error.message ?: "Terjadi kesalahan",
                    duration = SnackbarDuration.Short,
                )
            }
        }
    }

    Scaffold(
        topBar = {
            HistoryAppBar(
                onBackClick = { onBackClick() },
                onFilterClick = { showBottomSheet = true }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
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
            PullToRefreshBox(
                isRefreshing = isManualRefresh,
                onRefresh = {
                    isManualRefresh = true
                    scope.launch {
                        lazyPagingItems.refresh()
                    }
                },
                state = refreshState
            ) {
                // The LazyColumn is now the direct child, making the pull gesture always available.
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                ) {
                    // Condition 1: Initial loading state
                    if (lazyPagingItems.loadState.refresh is LoadState.Loading && lazyPagingItems.itemCount == 0) {
                        item {
                            Box(modifier = Modifier.fillParentMaxSize()) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                    }

                    // Condition 2: Empty state
                    if (lazyPagingItems.loadState.refresh is LoadState.NotLoading && lazyPagingItems.itemCount == 0) {
                        item {
                            Box(modifier = Modifier.fillParentMaxSize()) { // Use fillParentMaxSize
                                EmptyScreen(
                                    modifier = Modifier.align(Alignment.Center), // Align the content
                                    message = "Tidak ada riwayat yang ditemukan.\nCoba kata kunci atau filter yang berbeda."
                                )
                            }
                        }
                    }

                    // Condition 3: Display list items
                    items(
                        count = lazyPagingItems.itemCount,
                        key = lazyPagingItems.itemKey { it.id }
                    ) { index ->
                        val history = lazyPagingItems[index]
                        if (history != null) {
                            val downloadState =
                                uiState.downloadStates[history.id] ?: DownloadState.Idle
                            HistoryItem(
                                modifier = Modifier.animateItem(),
                                history = history,
                                downloadState = downloadState,
                                onDeleteClick = {
                                    historyToDelete = history
                                    showDeleteDialog = true
                                },
                                onDownloadClick = {
                                    viewModel.downloadReport(history)
                                },
                                onShareClick = { filePath ->
                                    openOrShareFile(context, filePath)
                                },
                                onEditClick = {
                                    onEditClick(history)
                                },
                            )
                        }
                    }

                    // Condition 4: Loading more items (pagination)
                    if (lazyPagingItems.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }

        if (showBottomSheet) {
            FilterSheet(
                initialState = activeFilters,
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                onApplyFilters = { newFilters ->
                    viewModel.applyFilters(newFilters)
                    showBottomSheet = false
                },
                onResetFilters = {
                    viewModel.clearFilters()
                },
            )
        }

        if (showDeleteDialog && historyToDelete != null) {
            DeleteConfirmationDialog(
                historyToDelete = historyToDelete!!,
                onConfirm = {
                    viewModel.deleteReport(historyToDelete!!) // Kirim seluruh objek
                    showDeleteDialog = false
                    historyToDelete = null
                },
                onDismiss = {
                    showDeleteDialog = false
                    historyToDelete = null
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
    onResetFilters: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    // Temporary state to hold changes before they are applied. Renamed for clarity.
    var selectedFilters by remember { mutableStateOf(initialState) }
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
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) onDismissRequest()
                    }
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Tutup Filter")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))


            // ✨ FIX: The filter content is wrapped in a Column with weight(1f).
            // This makes the Column expand to fill available space, enabling the verticalScroll
            // without pushing the action buttons at the bottom off-screen.
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // -- Filter Jenis Dokumen (DocumentType) --
                FilterSection(title = "Jenis Dokumen") {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DocumentType.entries.forEach { type ->
                            FilterChip(
                                selected = selectedFilters.documentType == type,
                                onClick = {
                                    selectedFilters = selectedFilters.copy(
                                        documentType = if (selectedFilters.documentType == type) null else type
                                    )
                                },
                                label = { Text(type.toDisplayString()) }
                            )
                        }
                    }
                }

                // -- Filter Jenis Laporan (InspectionType) --
                FilterSection(title = "Jenis Bidang") {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        InspectionType.entries.forEach { type ->
                            FilterChip(
                                selected = selectedFilters.inspectionType == type,
                                onClick = {
                                    selectedFilters = selectedFilters.copy(
                                        inspectionType = if (selectedFilters.inspectionType == type) null else type
                                    )
                                },
                                label = { Text(type.toDisplayString()) }
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
                            value = selectedFilters.subInspectionType?.toDisplayString()
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
                            // ✨ UX Improvement: Add an option to clear the selection for this dropdown.
                            DropdownMenuItem(
                                text = { Text("Hapus Pilihan", fontStyle = FontStyle.Italic) },
                                onClick = {
                                    selectedFilters =
                                        selectedFilters.copy(subInspectionType = null)
                                    isDropdownExpanded = false
                                }
                            )
                            SubInspectionType.entries.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type.toDisplayString()) },
                                    onClick = {
                                        selectedFilters =
                                            selectedFilters.copy(subInspectionType = type)
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            } // End of scrollable column

            // This spacer ensures there is always some space between the scrollable
            // content and the action buttons.
            Spacer(Modifier.height(16.dp))

            // == ACTION BUTTONS (Now always visible) ==
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp), // Padding for bottom navigation bar
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        selectedFilters = FilterState() // Reset temporary state
                        onResetFilters() // Call the ViewModel to clear permanent state
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Reset") }
                Button(
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onApplyFilters(selectedFilters)
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
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        content()
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun DeleteConfirmationDialog(
    historyToDelete: History,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Hapus Laporan",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = "Apakah Anda yakin ingin menghapus laporan ini?",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pemilik: ${historyToDelete.ownerName ?: "Tidak diketahui"}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Alat: ${historyToDelete.equipmentType}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tindakan ini tidak dapat dibatalkan.",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Hapus")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Batal")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true, name = "Phone View")
@Preview(showBackground = true, device = Devices.TABLET, showSystemUi = true, name = "Tablet View")
@Composable
fun HistoryScreenPreview() {
    KoinApplicationPreview(application = {
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            HistoryScreen(onBackClick = {}, onEditClick = {})
        }
    }
}