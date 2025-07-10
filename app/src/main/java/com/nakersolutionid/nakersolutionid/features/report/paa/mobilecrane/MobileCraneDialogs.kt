package com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
fun AddMobileCraneNdeWireRopeItemDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (MobileCraneNdeWireRopeItem) -> Unit
) {
    var item by remember { mutableStateOf(MobileCraneNdeWireRopeItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah Data Tali Kawat Baja", style = MaterialTheme.typography.titleLarge)
                FormTextField(label = "Penggunaan", value = item.usage, onValueChange = { item = item.copy(usage = it) })
                Row {
                    FormTextField(label = "Spec Diameter", value = item.specDiameter, onValueChange = { item = item.copy(specDiameter = it) }, modifier = Modifier.weight(1f))
                    Spacer(Modifier.width(4.dp))
                    FormTextField(label = "Actual Diameter", value = item.actualDiameter, onValueChange = { item = item.copy(actualDiameter = it) }, modifier = Modifier.weight(1f))
                }
                FormTextField(label = "Konstruksi", value = item.construction, onValueChange = { item = item.copy(construction = it) })
                FormTextField(label = "Jenis", value = item.type, onValueChange = { item = item.copy(type = it) })
                FormTextField(label = "Panjang", value = item.length, onValueChange = { item = item.copy(length = it) })
                FormTextField(label = "Umur", value = item.age, onValueChange = { item = item.copy(age = it) })
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = item.hasDefect, onCheckedChange = { item = item.copy(hasDefect = it) })
                    Text("Ada Cacat")
                }
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

@Composable
fun AddMobileCraneNdeBoomItemDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (MobileCraneNdeBoomItem) -> Unit
) {
    var item by remember { mutableStateOf(MobileCraneNdeBoomItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah Data Boom", style = MaterialTheme.typography.titleLarge)
                FormTextField(label = "Bagian yang diperiksa", value = item.partInspected, onValueChange = { item = item.copy(partInspected = it) })
                FormTextField(label = "Lokasi", value = item.location, onValueChange = { item = item.copy(location = it) })
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = item.hasDefect, onCheckedChange = { item = item.copy(hasDefect = it) })
                    Text("Ada Cacat")
                }
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

@Composable
fun AddMobileCraneLoadTestItemDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (MobileCraneLoadTestItem) -> Unit
) {
    var item by remember { mutableStateOf(MobileCraneLoadTestItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah Data Uji Beban", style = MaterialTheme.typography.titleLarge)
                FormTextField(label = "Panjang Boom (meter)", value = item.boomLength, onValueChange = { item = item.copy(boomLength = it) })
                FormTextField(label = "Radius (meter)", value = item.radius, onValueChange = { item = item.copy(radius = it) })
                FormTextField(label = "Sudut Boom (derajat)", value = item.boomAngle, onValueChange = { item = item.copy(boomAngle = it) })
                FormTextField(label = "Uji Beban (kg)", value = item.testLoadKg, onValueChange = { item = item.copy(testLoadKg = it) })
                FormTextField(label = "Beban Kerja Aman (kg)", value = item.safeWorkingLoadKg, onValueChange = { item = item.copy(safeWorkingLoadKg = it) })
                FormTextField(label = "Hasil", value = item.result, onValueChange = { item = item.copy(result = it) })

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}