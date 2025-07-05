package com.nakersolutionid.nakersolutionid.features.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material.icons.outlined.DoorSliding
import androidx.compose.material.icons.outlined.Factory
import androidx.compose.material.icons.outlined.FireTruck
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.ui.components.MenuItem
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val topics = listOf("Semua", "ILPP", "IPK", "PAA", "PUBT", "PTP", "EE")
    val subTopics = listOf("Semua", "Laporan", "BAP", "Sertifikat", "Suket")
    var selectedTopic by remember { mutableStateOf(topics.first()) }
    var selectedSubTopics by remember { mutableStateOf(subTopics.first()) }
    val dummySearchResults = mutableListOf<String>()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
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
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                dummySearchResults
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
            ) {
                items(
                    items = uiState.reports,
                    key = { it.id }
                ) { report ->
                    HistoryItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        info = HistoryInfo(
                            name = report.name,
                            subName = report.subName,
                            typeInspection = report.typeInspection,
                            type = report.type,
                            createdAt = report.createdAt
                        ),
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
            text = "Jenis",
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
            text = "Tipe",
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HistorySearchBar(modifier: Modifier = Modifier, searchResult: MutableList<String>) {
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()


    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                modifier = Modifier,
                searchBarState = searchBarState,
                textFieldState = textFieldState,
                onSearch = {
                    searchResult.add(it)
                    scope.launch { searchBarState.animateToCollapsed() }
                },
                placeholder = { Text("Search...") },
                leadingIcon = {
                    if (searchBarState.currentValue == SearchBarValue.Expanded) {
                        IconButton(
                            onClick = { scope.launch { searchBarState.animateToCollapsed() } }
                        ) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                        }
                    } else {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                },
            )
        }

    SearchBar(
        modifier = modifier,
        state = searchBarState,
        inputField = inputField
    )
    ExpandedFullScreenSearchBar(state = searchBarState, inputField = inputField) {
        SearchResults(
            onResultClick = { result ->
                textFieldState.setTextAndPlaceCursorAtEnd(result)
                scope.launch { searchBarState.animateToCollapsed() }
            },
            searchResult = searchResult
        )
    }
}

@Composable
private fun SearchResults(
    onResultClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    searchResult: List<String>
) {
    LazyColumn {
        items(searchResult.reversed()) { result ->
            val resultText = result
            Text(
                resultText,
                modifier = Modifier
                    .clickable { onResultClick(resultText) }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            )
        }
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
    // Use LazyRow for a horizontally scrollable row, which is efficient for
    // a potentially long list of filters.
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Spacing between chips
        contentPadding = PaddingValues(horizontal = 16.dp) // Padding at the start and end of the row
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