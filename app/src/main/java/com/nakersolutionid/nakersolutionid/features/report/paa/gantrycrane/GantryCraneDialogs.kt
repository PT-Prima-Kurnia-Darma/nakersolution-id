package com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane

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
fun AddStringDialog(
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
                FormTextField(label = label, value = text, onValueChange = { text = it })
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
fun AddNdeWireRopeItemDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (GantryCraneNdeWireRopeItem) -> Unit
) {
    var item by remember { mutableStateOf(GantryCraneNdeWireRopeItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah Data Wirerope", style = MaterialTheme.typography.titleLarge)
                FormTextField(label = "Penggunaan", value = item.usage, onValueChange = { item = item.copy(usage = it) })
                Row {
                    FormTextField(label = "Spec Diameter", value = item.specDiameter, onValueChange = { item = item.copy(specDiameter = it) }, modifier = Modifier.weight(1f))
                    Spacer(Modifier.width(4.dp))
                    FormTextField(label = "Actual Diameter", value = item.actualDiameter, onValueChange = { item = item.copy(actualDiameter = it) }, modifier = Modifier.weight(1f))
                }
                FormTextField(label = "Konstruksi", value = item.construction, onValueChange = { item = item.copy(construction = it) })
                FormTextField(label = "Type", value = item.type, onValueChange = { item = item.copy(type = it) })
                FormTextField(label = "Panjang", value = item.length, onValueChange = { item = item.copy(length = it) })
                GantryCraneResultStatusInput(label = "Temuan", value = item.finding, onValueChange = { item = item.copy(finding = it) })
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
fun AddNdeGirderItemDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (GantryCraneNdeGirderItem) -> Unit
) {
    var item by remember { mutableStateOf(GantryCraneNdeGirderItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah Data Girder", style = MaterialTheme.typography.titleLarge)
                FormTextField(label = "Lokasi", value = item.location, onValueChange = { item = item.copy(location = it) })
                GantryCraneResultStatusInput(label = "Temuan", value = item.finding, onValueChange = { item = item.copy(finding = it) })
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
fun AddDeflectionItemDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (GantryCraneDeflectionItem) -> Unit
) {
    var item by remember { mutableStateOf(GantryCraneDeflectionItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah Pengukuran Defleksi", style = MaterialTheme.typography.titleLarge)
                FormTextField(label = "Posisi", value = item.position, onValueChange = { item = item.copy(position = it) })
                FormTextField(label = "Pengukuran Defleksi", value = item.deflection, onValueChange = { item = item.copy(deflection = it) })
                FormTextField(label = "Standar Maksimal", value = item.maxStandard, onValueChange = { item = item.copy(maxStandard = it) })
                FormTextField(label = "Keterangan", value = item.remarks, onValueChange = { item = item.copy(remarks = it) })
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}
//endregion