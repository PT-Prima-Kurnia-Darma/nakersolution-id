package com.nakersolutionid.nakersolutionid.features.report.elevator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.features.report.ReportViewModel
import com.nakersolutionid.nakersolutionid.ui.components.ElevatorTopAppBar
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElevatorScreen(
    viewModel: ReportViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    menuTitle: String,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            ElevatorTopAppBar(
                name = menuTitle,
                scrollBehavior = scrollBehavior,
                onBackClick = { onBackClick() }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                viewModel.test()
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Phone View",
    group = "Phone"
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Phone Portrait",
    device = "spec:width=411dp,height=891dp,orientation=landscape",
    group = "Phone"
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Tablet View",
    device = Devices.TABLET,
    group = "Table"
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Tablet Portrait",
    device = "spec:width=800dp,height=1280dp,dpi=240,orientation=portrait",
    group = "Table"
)
@Composable
fun LoginScreenPreview() {
    KoinApplicationPreview(application = {
        // Use only the preview module
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            ElevatorScreen(onBackClick = {}, menuTitle = "Elevator dan Eskalator")
        }
    }
}
