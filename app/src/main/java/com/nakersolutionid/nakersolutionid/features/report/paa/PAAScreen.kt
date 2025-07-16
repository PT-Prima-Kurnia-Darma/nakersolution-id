package com.nakersolutionid.nakersolutionid.features.report.paa

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftScreen
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneScreen
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaScreen
import com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane.MobileCraneScreen
import com.nakersolutionid.nakersolutionid.features.report.paa.overheadcrane.OverheadCraneScreen
import com.nakersolutionid.nakersolutionid.ui.components.InspectionTopAppBar
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PAAScreen(
    modifier: Modifier = Modifier,
    viewModel: PAAViewModel = koinViewModel(),
    menuTitle: String = "Pesawat Angkat dan Angkut",
    onBackClick: () -> Unit
) {
    val paaUiState by viewModel.paaUiState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var selectedFilter by remember { mutableStateOf<SubInspectionType>(SubInspectionType.Forklift) }
    val listMenu = listOf(
        SubInspectionType.Forklift,
        SubInspectionType.Gondola,
        SubInspectionType.Mobile_Crane,
        SubInspectionType.Gantry_Crane,
        SubInspectionType.Overhead_Crane
    )

    LaunchedEffect(paaUiState.forkliftResult) {
        when (val result = paaUiState.forkliftResult) {
            is Resource.Error -> {
                scope.launch { snackbarHostState.showSnackbar("${result.message}") }
                viewModel.onUpdatePAAState { it.copy(isLoading = false, forkliftResult = null) }
            }

            is Resource.Loading -> {
                viewModel.onUpdatePAAState { it.copy(isLoading = true) }
            }

            is Resource.Success -> {
                viewModel.onUpdatePAAState { it.copy(isLoading = false, forkliftResult = null) }
                onBackClick()
            }

            null -> null
        }
    }

    LaunchedEffect(paaUiState.gantryCraneResult) {
        when (val result = paaUiState.gantryCraneResult) {
            is Resource.Error -> {
                scope.launch { snackbarHostState.showSnackbar("${result.message}") }
                viewModel.onUpdatePAAState { it.copy(isLoading = false, gantryCraneResult = null) }
            }

            is Resource.Loading -> {
                viewModel.onUpdatePAAState { it.copy(isLoading = true) }
            }

            is Resource.Success -> {
                viewModel.onUpdatePAAState { it.copy(isLoading = false, gantryCraneResult = null) }
                onBackClick()
            }

            null -> null
        }
    }

    LaunchedEffect(paaUiState.gondolaResult) {
        when (val result = paaUiState.gondolaResult) {
            is Resource.Error -> {
                scope.launch { snackbarHostState.showSnackbar("${result.message}") }
                viewModel.onUpdatePAAState { it.copy(isLoading = false, gondolaResult = null) }
            }

            is Resource.Loading -> {
                viewModel.onUpdatePAAState { it.copy(isLoading = true) }
            }

            is Resource.Success -> {
                viewModel.onUpdatePAAState { it.copy(isLoading = false, gondolaResult = null) }
                onBackClick()
            }

            null -> null
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            InspectionTopAppBar(
                name = menuTitle,
                scrollBehavior = scrollBehavior,
                onBackClick = onBackClick,
                actionEnable = !paaUiState.isLoading,
                onSaveClick = { viewModel.onSaveClick(selectedFilter) }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = listMenu,
                    key = { it.toDisplayString() }
                ) { filterType ->
                    val isSelected = selectedFilter == filterType
                    val filterName = filterType.toDisplayString()

                    FilterChip(
                        modifier = Modifier,
                        selected = isSelected,
                        onClick = { selectedFilter = filterType },
                        label = {
                            Text(
                                filterName,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    )
                }
            }

            when (selectedFilter) {
                SubInspectionType.Forklift -> {
                    ForkliftScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                            .imePadding(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    )
                }
                SubInspectionType.Mobile_Crane -> {
                    MobileCraneScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                            .imePadding(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    )
                }
                SubInspectionType.Overhead_Crane -> {
                    OverheadCraneScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                            .imePadding(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    )
                }
                SubInspectionType.Gantry_Crane -> {
                    GantryCraneScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                            .imePadding(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    )
                }
                SubInspectionType.Gondola -> {
                    GondolaScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                            .imePadding(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    )
                }
                else -> null
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PAAScreenPreview() {
    KoinApplicationPreview(application = {
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            PAAScreen(onBackClick = {})
        }
    }
}