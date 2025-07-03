package com.nakersolutionid.nakersolutionid.features.report.elevator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import org.koin.compose.KoinApplicationPreview

@Composable
fun ElevatorScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {

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
            ElevatorScreen()
        }
    }
}
