package com.nakersolutionid.nakersolutionid.features.bap

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@Composable
fun BAPCreationScreen(
    id: Long,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BAPCreationViewModel = koinViewModel(),
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BAPCreationAppBar(
                onBackClick = onBackClick,
                onSaveClick = {},
                name = "Elevator",
                actionEnable = true
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {

        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BAPCreationScreenPreview() {
    KoinApplicationPreview(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            BAPCreationScreen(id = 0, onBackClick = {})
        }
    }
}