package com.nakersolutionid.nakersolutionid.features.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
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

private val menuItems = listOf(
    MenuItem(1, "Instalasi Listrik dan Penyalur Petir", Icons.Outlined.Bolt),
    MenuItem(2, "Instalasi Proteksi Kebakaran", Icons.Outlined.LocalFireDepartment),
    MenuItem(3, "Pesawat Angkat dan Angkut", Icons.Outlined.FireTruck),
    MenuItem(4, "Pesawat Uap dan Bejana Tekan", Icons.Outlined.Factory),
    MenuItem(5, "Pesawat Tenaga dan Produksi", Icons.Outlined.Construction),
    MenuItem(6, "Elevator dan Eskalator", Icons.Outlined.DoorSliding)
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val topics = listOf("ALL", "ILPP", "IPK", "PAA", "PUBT", "PTP", "EE")
    var selectedTopic by remember { mutableStateOf(topics.first()) }
    val dummySearchResults = mutableListOf<String>()

    Scaffold(
        topBar = { HistoryAppBar(onBackClick = { onBackClick() }) },
        modifier = Modifier.fillMaxSize()
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
            FilterChipRow(
                filters = topics,
                selectedFilter = selectedTopic,
                onFilterSelected = { newTopic ->
                    selectedTopic = newTopic
                    // TODO: Add logic here to actually filter your content based on the newTopic
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
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
private fun SearchResults(onResultClick: (String) -> Unit, modifier: Modifier = Modifier, searchResult: List<String>) {
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
            /*ListItem(
                headlineContent = { Text(resultText) },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                modifier =
                    Modifier.clickable { onResultClick(resultText) }
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 16.dp),
            )*/
        }
    }

    Column(modifier.verticalScroll(rememberScrollState())) {

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