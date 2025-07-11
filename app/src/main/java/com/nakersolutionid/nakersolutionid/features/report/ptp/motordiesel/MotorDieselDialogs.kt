package com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun AddMotorDieselStringDialog(
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
fun AddNoisePointDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (DieselMotorNoiseMeasurementPoint) -> Unit
) {
    var item by remember { mutableStateOf(DieselMotorNoiseMeasurementPoint()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah Titik Pengukuran Kebisingan", style = MaterialTheme.typography.titleLarge)
                OutlinedTextField(label = { Text("Titik") }, value = item.point, onValueChange = { item = item.copy(point = it) })
                OutlinedTextField(label = { Text("Nilai (dB)") }, value = item.valueDb, onValueChange = { item = item.copy(valueDb = it) })
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}

@Composable
fun AddLightingPointDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (DieselMotorLightingMeasurementPoint) -> Unit
) {
    var item by remember { mutableStateOf(DieselMotorLightingMeasurementPoint()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah Titik Pengukuran Pencahayaan", style = MaterialTheme.typography.titleLarge)
                OutlinedTextField(label = { Text("Titik") }, value = item.point, onValueChange = { item = item.copy(point = it) })
                OutlinedTextField(label = { Text("Nilai (Lux)") }, value = item.valueLux, onValueChange = { item = item.copy(valueLux = it) })
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}