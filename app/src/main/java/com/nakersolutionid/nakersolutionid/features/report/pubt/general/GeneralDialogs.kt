package com.nakersolutionid.nakersolutionid.features.report.pubt.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddGeneralStringDialog(
    title: String,
    label: String,
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(title, style = MaterialTheme.typography.titleLarge)
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text(label) },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onConfirm(text) }) { Text("Simpan") }
                }
            }
        }
    }
}

@Composable
fun AddMeasurementResultDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (GeneralMeasurementResultItem) -> Unit
) {
    var item by remember { mutableStateOf(GeneralMeasurementResultItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(
                Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Tambah Hasil Pengukuran", style = MaterialTheme.typography.titleLarge)
                OutlinedTextField(label = { Text("Posisi Pengukuran") }, value = item.position, onValueChange = { item = item.copy(position = it) }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(label = { Text("Nominal (mm)") }, value = item.nominalMm, onValueChange = { item = item.copy(nominalMm = it) }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(label = { Text("Titik 1") }, value = item.point1, onValueChange = { item = item.copy(point1 = it) }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(label = { Text("Titik 2") }, value = item.point2, onValueChange = { item = item.copy(point2 = it) }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(label = { Text("Titik 3") }, value = item.point3, onValueChange = { item = item.copy(point3 = it) }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(label = { Text("Minimum") }, value = item.minimum, onValueChange = { item = item.copy(minimum = it) }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(label = { Text("Maximum") }, value = item.maximum, onValueChange = { item = item.copy(maximum = it) }, modifier = Modifier.fillMaxWidth())
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}