package com.nakersolutionid.nakersolutionid.features.bap

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.features.bap.electric.ElectricalBAPScreen
import com.nakersolutionid.nakersolutionid.features.bap.elevator.ElevatorBAPScreen
import com.nakersolutionid.nakersolutionid.features.bap.escalator.EscalatorBAPScreen
import com.nakersolutionid.nakersolutionid.features.bap.fireprotection.FireProtectionBAPScreen
import com.nakersolutionid.nakersolutionid.features.bap.forklift.ForkliftBAPScreen
import com.nakersolutionid.nakersolutionid.features.bap.gantrycrane.GantryCraneBAPScreen
import com.nakersolutionid.nakersolutionid.features.bap.gondola.GondolaBAPScreen
import com.nakersolutionid.nakersolutionid.features.bap.lightning.LightningBAPScreen
import com.nakersolutionid.nakersolutionid.features.bap.mobilecrane.MobileCraneBAPScreen
import com.nakersolutionid.nakersolutionid.features.bap.overheadcrane.OverheadCraneBAPScreen
import com.nakersolutionid.nakersolutionid.features.bap.ptp.PtpBAPScreen
import com.nakersolutionid.nakersolutionid.features.bap.pubt.PubtBAPScreen
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@Composable
fun BAPCreationScreen(
    id: Long,
    subInspectionType: SubInspectionType,
    documentType: DocumentType,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BAPCreationViewModel = koinViewModel(),
    editMode: Boolean = false,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(id) {
        viewModel.onUpdateState { it.copy(editMode = editMode) }
        viewModel.getInspectionDetail(id)
    }

    LaunchedEffect(uiState.result) {
        when (val result = uiState.result) {
            is Resource.Error -> {
                scope.launch { snackbarHostState.showSnackbar("${result.message}") }
                viewModel.onUpdateState { it.copy(isLoading = false, result = null) }
            }
            is Resource.Loading -> {
                viewModel.onUpdateState { it.copy(isLoading = true) }
            }
            is Resource.Success -> {
                viewModel.onUpdateState { it.copy(isLoading = false, result = null) }
                onBackClick()
            }
            null -> null
        }
    }

    /*LaunchedEffect(uiState.createResult) {
        when (val result = uiState.createResult) {
            is Resource.Error -> {
                scope.launch { snackbarHostState.showSnackbar("${result.message}") }
                viewModel.onUpdateState { it.copy(isLoading = false, createResult = null) }
            }
            is Resource.Loading -> {
                viewModel.onUpdateState { it.copy(isLoading = true) }
            }
            is Resource.Success -> {
                viewModel.onUpdateState { it.copy(isLoading = false, createResult = null) }
                onBackClick()
            }
            null -> null
        }
    }*/

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BAPCreationAppBar(
                onBackClick = onBackClick,
                onSaveClick = {
                    if (documentType == DocumentType.BAP) {
                        // Edit
                        viewModel.onSaveClick(subInspectionType, hasInternetConnection(context), id)
                    } else {
                        // Create
                        viewModel.onSaveClick(subInspectionType, hasInternetConnection(context))
                    }
                },
                onCopyClick = {
                    viewModel.onCopyClick(subInspectionType, hasInternetConnection(context))
                },
                name = subInspectionType.toDisplayString(),
                actionEnable = uiState.isLoading,
                editMode = editMode
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        },
    ) { paddingValues ->
        when (subInspectionType) {
            SubInspectionType.Elevator -> {
                ElevatorBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.Escalator -> {
                EscalatorBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.Forklift -> {
                ForkliftBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.Mobile_Crane -> {
                MobileCraneBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.Overhead_Crane -> {
                OverheadCraneBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.Gantry_Crane -> {
                GantryCraneBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.Gondola -> {
                GondolaBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.Electrical -> {
                ElectricalBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.Lightning_Conductor -> {
                LightningBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.General_PUBT -> {
                PubtBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.Fire_Protection -> {
                FireProtectionBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.Motor_Diesel -> {
                PtpBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
            SubInspectionType.Machine -> {
                PtpBAPScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
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
private fun BAPCreationScreenPreview() {
    KoinApplicationPreview(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            BAPCreationScreen(
                id = 0,
                subInspectionType = SubInspectionType.Elevator,
                DocumentType.LAPORAN,
                onBackClick = {})
        }
    }
}