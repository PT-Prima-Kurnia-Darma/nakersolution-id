package com.nakersolutionid.nakersolutionid.features.report.ilpp

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
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
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricScreen
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningScreen
import com.nakersolutionid.nakersolutionid.ui.components.InspectionTopAppBar
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ILPPScreen(
    modifier: Modifier = Modifier,
    viewModel: ILPPViewModel = koinViewModel(),
    menuTitle: String = "Instalasi Listrik dan Penyalur Petir",
    onBackClick: () -> Unit
) {
    val paaUiState by viewModel.ilppUiState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var selectedFilter by remember { mutableStateOf<SubInspectionType>(SubInspectionType.Electrical) }
    val listMenu = listOf(
        SubInspectionType.Electrical,
        SubInspectionType.Lightning_Conductor
    )

    /*LaunchedEffect(uiState.elevatorResult) {
        when (val result = uiState.elevatorResult) {
            is Resource.Error -> {
                scope.launch { snackbarHostState.showSnackbar("${result.message}") }
                viewModel.onUpdateState { it.copy(isLoading = false, elevatorResult = null) }
            }

            is Resource.Loading -> {
                viewModel.onUpdateState { it.copy(isLoading = true) }
            }

            is Resource.Success -> {
                viewModel.onUpdateState { it.copy(isLoading = false, elevatorResult = null) }
                onBackClick()
            }

            null -> null
        }
    }

    LaunchedEffect(uiState.eskalatorResult) {
        when (val result = uiState.eskalatorResult) {
            is Resource.Error -> {
                scope.launch { snackbarHostState.showSnackbar("${result.message}") }
                viewModel.onUpdateState { it.copy(isLoading = false, eskalatorResult = null) }
            }

            is Resource.Loading -> {
                viewModel.onUpdateState { it.copy(isLoading = true) }
            }

            is Resource.Success -> {
                viewModel.onUpdateState { it.copy(isLoading = false, eskalatorResult = null) }
            }

            null -> null
        }
    }*/

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
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                listMenu.forEach { filterType ->
                    val isSelected = selectedFilter == filterType
                    val filterName = filterType.toDisplayString()

                    FilterChip(
                        modifier = Modifier.weight(1f),
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
                SubInspectionType.Electrical -> {
                    ElectricScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                            .imePadding(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    )
                }
                SubInspectionType.Lightning_Conductor -> {
                    LightningScreen(
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
            ILPPScreen(onBackClick = {})
        }
    }
}