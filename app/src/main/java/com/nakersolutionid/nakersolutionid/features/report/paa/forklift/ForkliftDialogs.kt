package com.nakersolutionid.nakersolutionid.features.report.paa.forklift

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
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
fun AddNdeChainItemDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (ForkliftNdeChainItem) -> Unit
) {
    var item by remember { mutableStateOf(ForkliftNdeChainItem()) }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Tambah Data Rantai", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = item.chain, onValueChange = { item = item.copy(chain = it) }, label = { Text("Rantai") })
                OutlinedTextField(value = item.typeAndConstruction, onValueChange = { item = item.copy(typeAndConstruction = it) }, label = { Text("Jenis dan Konstruksi") })
                OutlinedTextField(value = item.standardPitchMm, onValueChange = { item = item.copy(standardPitchMm = it) }, label = { Text("Standard Pitch (mm)") })
                OutlinedTextField(value = item.measuredPitchMm, onValueChange = { item = item.copy(measuredPitchMm = it) }, label = { Text("Pengukuran Pitch (mm)") })
                OutlinedTextField(value = item.standardPinMm, onValueChange = { item = item.copy(standardPinMm = it) }, label = { Text("Standard Pin (mm)") })
                OutlinedTextField(value = item.measuredPinMm, onValueChange = { item = item.copy(measuredPinMm = it) }, label = { Text("Pengukuran Pin (mm)") })
                OutlinedTextField(value = item.remarks, onValueChange = { item = item.copy(remarks = it) }, label = { Text("Keterangan") })
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}

@Composable
fun AddNdeForkItemDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (ForkliftNdeForkItem) -> Unit
) {
    var item by remember { mutableStateOf(ForkliftNdeForkItem()) }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Tambah Data NDT Fork", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = item.partInspected, onValueChange = { item = item.copy(partInspected = it) }, label = { Text("Bagian yang Diperiksa") })
                OutlinedTextField(value = item.location, onValueChange = { item = item.copy(location = it) }, label = { Text("Lokasi") })
                OutlinedTextField(value = item.finding.result, onValueChange = { item = item.copy(finding = item.finding.copy(result = it)) }, label = { Text("Keterangan Temuan") })
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = item.finding.status, onCheckedChange = { isChecked -> item = item.copy(finding = item.finding.copy(status = isChecked)) })
                    Text("Ada Cacat")
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}


@Composable
fun AddLoadTestItemDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (ForkliftLoadTestItem) -> Unit
) {
    var item by remember { mutableStateOf(ForkliftLoadTestItem()) }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Tambah Data Uji Beban", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = item.forkLiftingHeight, onValueChange = { item = item.copy(forkLiftingHeight = it) }, label = { Text("Tinggi Angkat Garpu") })
                OutlinedTextField(value = item.testLoad, onValueChange = { item = item.copy(testLoad = it) }, label = { Text("Beban Uji") })
                OutlinedTextField(value = item.travelingSpeed, onValueChange = { item = item.copy(travelingSpeed = it) }, label = { Text("Traveling / Kecepatan") })
                OutlinedTextField(value = item.movement, onValueChange = { item = item.copy(movement = it) }, label = { Text("Gerakan (mm)") })
                OutlinedTextField(value = item.result, onValueChange = { item = item.copy(result = it) }, label = { Text("Hasil") })
                OutlinedTextField(value = item.remarks, onValueChange = { item = item.copy(remarks = it) }, label = { Text("Keterangan") })
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}

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
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = title, style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text(label) },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Button(onClick = { if (text.isNotBlank()) onConfirm(text) }) { Text("Tambah") }
                }
            }
        }
    }
}