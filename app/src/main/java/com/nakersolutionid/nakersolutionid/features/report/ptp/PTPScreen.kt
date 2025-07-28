package com.nakersolutionid.nakersolutionid.features.report.ptp

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningScreen
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.MachineScreen
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.MotorDieselScreen
import com.nakersolutionid.nakersolutionid.ui.components.InspectionTopAppBar
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PTPScreen(
    modifier: Modifier = Modifier,
    viewModel: PTPViewModel = koinViewModel(),
    menuTitle: String = "Pesawat Tenaga dan Produksi",
    reportId: Long? = null,
    onBackClick: () -> Unit,
    editMode: Boolean = false
) {
    val ptpUiState by viewModel.ptpUiState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var selectedFilter by remember { mutableStateOf<SubInspectionType>(SubInspectionType.Machine) }
    val listMenu = listOf(
        SubInspectionType.Machine,
        SubInspectionType.Motor_Diesel
    )

    LaunchedEffect(ptpUiState.result) {
        when (val result = ptpUiState.result) {
            is Resource.Error -> {
                scope.launch { snackbarHostState.showSnackbar("${result.message}") }
                viewModel.onUpdatePTPState { it.copy(isLoading = false, result = null) }
            }

            is Resource.Loading -> {
                viewModel.onUpdatePTPState { it.copy(isLoading = true) }
            }

            is Resource.Success -> {
                viewModel.onUpdatePTPState { it.copy(isLoading = false, result = null) }
                onBackClick()
            }

            null -> null
        }
    }

    // Load existing report data for edit mode
    LaunchedEffect(reportId) {
        viewModel.onUpdatePTPState { it.copy(editMode = editMode) }
        reportId?.let { id ->
            viewModel.loadReportForEdit(id)
        }
    }

    // Update selected filter when equipment type is loaded for edit mode
    LaunchedEffect(ptpUiState.loadedEquipmentType) {
        ptpUiState.loadedEquipmentType?.let { equipmentType ->
            selectedFilter = equipmentType
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
                actionEnable = !ptpUiState.isLoading,
                onSaveClick = { viewModel.onSaveClick(selectedFilter, hasInternetConnection(context)) }
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
                SubInspectionType.Machine -> {
                    MachineScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                            .imePadding(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    )
                }
                SubInspectionType.Motor_Diesel -> {
                    MotorDieselScreen(
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

private fun hasInternetConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PAAScreenPreview() {
    KoinApplicationPreview(application = {
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            PTPScreen(onBackClick = {})
        }
    }
}