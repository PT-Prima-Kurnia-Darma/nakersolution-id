package com.nakersolutionid.nakersolutionid.features.example

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyUpdatedSearchScreen() {
    var searchText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // Mengganti 'active' menjadi 'expanded'
    val searchHistory = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .safeDrawingPadding()
            ) {
                DockedSearchBar(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier.fillMaxWidth(),
                    inputField = {
                        // Di sinilah kita menyediakan TextField kustom kita
                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            placeholder = { Text("Cari sesuatu...") },
                            leadingIcon = {
                                IconButton(onClick = {
                                    expanded = if (expanded) {
                                        false // Tutup search bar
                                    } else {
                                        // Aksi lain jika tidak expanded (misal, buka drawer)
                                        true // Atau langsung buka search
                                    }
                                }) {
                                    Icon(Icons.Default.Search, contentDescription = "Cari")
                                }
                            },
                            trailingIcon = {
                                if (searchText.isNotEmpty()) {
                                    IconButton(onClick = {
                                        searchText = ""
                                    }) {
                                        Icon(Icons.Default.Close, contentDescription = "Clear search")
                                    }
                                }
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                ) {
                    // Konten saat search bar expanded
                    LazyColumn {
                        if (searchHistory.isNotEmpty()) {
                            item { Text("Riwayat Pencarian:", modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)) }
                        }
                        items(searchHistory) { historyItem ->
                            ListItem(
                                headlineContent = { Text(historyItem) },
                                leadingContent = { Icon(Icons.Default.History, contentDescription = null) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        searchText = historyItem
                                        expanded = false
                                        println("Mencari dari histori: $historyItem")
                                    }
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        // Konten utama layar Anda
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("Konten Utama Aplikasi Anda")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyUpdatedSearchScreen() {
    MyUpdatedSearchScreen()
}