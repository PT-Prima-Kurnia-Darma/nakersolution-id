package com.nakersolutionid.nakersolutionid.features.report.paa.gondola

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
fun AddGondolaNdeSteelWireRopeItemDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (GondolaSteelWireRopeItem) -> Unit
) {
    var item by remember { mutableStateOf(GondolaSteelWireRopeItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah TKB", style = MaterialTheme.typography.titleLarge)
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
                FormTextField(label = "Cacat", value = item.defect, onValueChange = { item = item.copy(defect = it) })
                FormTextField(label = "Keterangan", value = item.description, onValueChange = { item = item.copy(description = it) })

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
fun AddGondolaNdeItemDialog(
    title: String,
    onDismissRequest: () -> Unit,
    onConfirm: (GondolaSuspensionStructureItem) -> Unit
) {
    var item by remember { mutableStateOf(GondolaSuspensionStructureItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(title, style = MaterialTheme.typography.titleLarge)
                FormTextField(label = "Bagian yang diperiksa", value = item.inspectedPart, onValueChange = { item = item.copy(inspectedPart = it) })
                FormTextField(label = "Lokasi", value = item.location, onValueChange = { item = item.copy(location = it) })
                FormTextField(label = "Cacat", value = item.defect, onValueChange = { item = item.copy(defect = it) })
                FormTextField(label = "Keterangan", value = item.description, onValueChange = { item = item.copy(description = it) })

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
fun AddGondolaLoadTestDialog(
    title: String,
    onDismissRequest: () -> Unit,
    onConfirm: (GondolaLoadTest) -> Unit
) {
    var item by remember { mutableStateOf(GondolaLoadTest()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(title, style = MaterialTheme.typography.titleLarge)
                FormTextField(label = "Beban (%)", value = item.loadPercentage, onValueChange = { item = item.copy(loadPercentage = it) })
                FormTextField(label = "Beban (Ton/Kg)", value = item.load, onValueChange = { item = item.copy(load = it) })
                FormTextField(label = "Hasil", value = item.result, onValueChange = { item = item.copy(result = it) })
                FormTextField(label = "Keterangan", value = item.description, onValueChange = { item = item.copy(description = it) })

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}