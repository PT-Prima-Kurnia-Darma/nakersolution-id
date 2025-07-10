package com.nakersolutionid.nakersolutionid.features.report.paa.overheadcrane

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
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
fun AddNdeChainItemDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (OverheadCraneNdeChainItem) -> Unit
) {
    var item by remember { mutableStateOf(OverheadCraneNdeChainItem()) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Tambah Data Pemeriksaan Rantai", style = MaterialTheme.typography.titleLarge)
                FormTextField(label = "Nama", value = item.name, onValueChange = { item = item.copy(name = it) })
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    FormTextField(label = "Spesifikasi (mm)", value = item.specificationMm, onValueChange = { item = item.copy(specificationMm = it) }, modifier = Modifier.weight(1f))
                    FormTextField(label = "Hasil Ukur (mm)", value = item.measurementMm, onValueChange = { item = item.copy(measurementMm = it) }, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    FormTextField(label = "Pertambahan Panjang (%)", value = item.lengthIncrease, onValueChange = { item = item.copy(lengthIncrease = it) }, modifier = Modifier.weight(1f))
                    FormTextField(label = "Pengausan (%)", value = item.wear, onValueChange = { item = item.copy(wear = it) }, modifier = Modifier.weight(1f))
                }
                FormTextField(label = "Faktor Keamanan", value = item.safetyFactor, onValueChange = { item = item.copy(safetyFactor = it) })

                // REPLACED with InspectionResultInput
                InspectionResultInput(
                    label = "Temuan",
                    value = item.finding,
                    onValueChange = { item = item.copy(finding = it) }
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onConfirm(item) }) { Text("Simpan") }
                }
            }
        }
    }
}

// This composable is now defined here to be used in the dialog
@Composable
private fun InspectionResultInput(
    label: String,
    value: OverheadCraneInspectionResult,
    onValueChange: (OverheadCraneInspectionResult) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = value.result,
            onValueChange = { onValueChange(value.copy(result = it)) },
            label = { Text(label) },
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.medium,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable { onValueChange(value.copy(status = !(value.status ?: false))) }
                .padding(start = 4.dp, end = 4.dp)
        ) {
            Checkbox(
                checked = value.status ?: false,
                onCheckedChange = null
            )
            Text("Cacat")
        }
    }
}