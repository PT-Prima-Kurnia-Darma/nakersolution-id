package com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
fun AddFireProtectionStringDialog(
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
fun AddPumpTestDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (FireProtectionPumpFunctionTestItem) -> Unit
) {
    var item by remember { mutableStateOf(FireProtectionPumpFunctionTestItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah Test Fungsi Pompa", style = MaterialTheme.typography.titleLarge)
                OutlinedTextField(label = { Text("Jenis Pompa") }, value = item.pumpType, onValueChange = { item = item.copy(pumpType = it) })
                OutlinedTextField(label = { Text("Start (Kg/cm²)") }, value = item.startPressure, onValueChange = { item = item.copy(startPressure = it) })
                OutlinedTextField(label = { Text("Stop (Kg/cm²)") }, value = item.stopPressure, onValueChange = { item = item.copy(stopPressure = it) })
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
fun AddHydrantTestDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (FireProtectionHydrantOperationalTestItem) -> Unit
) {
    var item by remember { mutableStateOf(FireProtectionHydrantOperationalTestItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            // Use LazyColumn for scrollable content in dialog
            LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item { Text("Tambah Percobaan Hydran", style = MaterialTheme.typography.titleLarge) }
                item { OutlinedTextField(label = { Text("Titik Percobaan") }, value = item.testPoint, onValueChange = { item = item.copy(testPoint = it) }, modifier = Modifier.fillMaxWidth()) }
                item { OutlinedTextField(label = { Text("Tekanan (Bar)") }, value = item.pressure, onValueChange = { item = item.copy(pressure = it) }, modifier = Modifier.fillMaxWidth()) }
                item { OutlinedTextField(label = { Text("Daya Pancar (m)") }, value = item.jetLength, onValueChange = { item = item.copy(jetLength = it) }, modifier = Modifier.fillMaxWidth()) }
                item { OutlinedTextField(label = { Text("Posisi Nozzle") }, value = item.nozzlePosition, onValueChange = { item = item.copy(nozzlePosition = it) }, modifier = Modifier.fillMaxWidth()) }
                item { OutlinedTextField(label = { Text("Status") }, value = item.status, onValueChange = { item = item.copy(status = it) }, modifier = Modifier.fillMaxWidth()) }
                item { OutlinedTextField(label = { Text("Keterangan") }, value = item.remarks, onValueChange = { item = item.copy(remarks = it) }, modifier = Modifier.fillMaxWidth()) }
                item {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = onDismissRequest) { Text("Batal") }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                    }
                }
            }
        }
    }
}

@Composable
fun AddAlarmInstallationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (FireProtectionAlarmInstallationItem) -> Unit
) {
    var item by remember { mutableStateOf(FireProtectionAlarmInstallationItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Tambah Data Pemasangan Alarm", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                LazyColumn(modifier = Modifier.heightIn(max = 400.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { OutlinedTextField(label = { Text("Lokasi") }, value = item.location, onValueChange = { item = item.copy(location = it) }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(label = { Text("Zone") }, value = item.zone, onValueChange = { item = item.copy(zone = it) }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(label = { Text("ROR") }, value = item.ror, onValueChange = { item = item.copy(ror = it) }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(label = { Text("Fixed") }, value = item.fixed, onValueChange = { item = item.copy(fixed = it) }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(label = { Text("Smoke") }, value = item.smoke, onValueChange = { item = item.copy(smoke = it) }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(label = { Text("TPM") }, value = item.tpm, onValueChange = { item = item.copy(tpm = it) }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(label = { Text("FLSw") }, value = item.flsw, onValueChange = { item = item.copy(flsw = it) }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(label = { Text("Alarm Bell") }, value = item.bell, onValueChange = { item = item.copy(bell = it) }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(label = { Text("Alarm Lamp") }, value = item.lamp, onValueChange = { item = item.copy(lamp = it) }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(label = { Text("Status") }, value = item.status, onValueChange = { item = item.copy(status = it) }, modifier = Modifier.fillMaxWidth()) }
                }
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}