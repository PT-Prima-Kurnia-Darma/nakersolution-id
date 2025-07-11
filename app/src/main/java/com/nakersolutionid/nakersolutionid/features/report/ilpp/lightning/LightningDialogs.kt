package com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning

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
fun AddLightningStringDialog(
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
fun AddGroundingMeasurementDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (LightningProtectionGroundingMeasurementItem) -> Unit
) {
    var item by remember { mutableStateOf(LightningProtectionGroundingMeasurementItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah Data Pengukuran", style = MaterialTheme.typography.titleLarge)
                OutlinedTextField(label = { Text("Jarak E-C (m)") }, value = item.ecDistance, onValueChange = { item = item.copy(ecDistance = it) })
                OutlinedTextField(label = { Text("Jarak E-P (m)") }, value = item.epDistance, onValueChange = { item = item.copy(epDistance = it) })
                OutlinedTextField(label = { Text("Nilai R (Ω)") }, value = item.rValueOhm, onValueChange = { item = item.copy(rValueOhm = it) })
                OutlinedTextField(label = { Text("Keterangan") }, value = item.remarks, onValueChange = { item = item.copy(remarks = it) })

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}