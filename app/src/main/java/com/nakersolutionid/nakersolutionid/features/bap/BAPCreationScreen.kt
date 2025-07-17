package com.nakersolutionid.nakersolutionid.features.bap

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.features.bap.elevator.ElevatorBAPScreen
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@Composable
fun BAPCreationScreen(
    id: Long,
    subInspectionType: SubInspectionType,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BAPCreationViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val elevatorState by viewModel.elevatorBAPUiState.collectAsStateWithLifecycle()

    viewModel.getInspectionDetail(id)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BAPCreationAppBar(
                onBackClick = onBackClick,
                onSaveClick = {},
                name = subInspectionType.toDisplayString(),
                actionEnable = true
            )
        }
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
            SubInspectionType.Escalator -> {}
            SubInspectionType.Forklift -> {}
            SubInspectionType.Mobile_Crane -> {}
            SubInspectionType.Overhead_Crane -> {}
            SubInspectionType.Gantry_Crane -> {}
            SubInspectionType.Gondola -> {}
            SubInspectionType.Electrical -> {}
            SubInspectionType.Lightning_Conductor -> {}
            SubInspectionType.General_PUBT -> {}
            SubInspectionType.Fire_Protection -> {}
            SubInspectionType.Motor_Diesel -> {}
            SubInspectionType.Machine -> {}
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BAPCreationScreenPreview() {
    KoinApplicationPreview(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            BAPCreationScreen(id = 0, subInspectionType = SubInspectionType.Elevator, onBackClick = {})
        }
    }
}