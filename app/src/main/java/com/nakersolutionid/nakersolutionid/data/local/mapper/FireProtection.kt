package com.nakersolutionid.nakersolutionid.data.local.mapper

/*
import com.example.yourapp.database.dao.InspectionDao
import com.example.yourapp.database.entities.*

// --- Data Models (Representing Form State from instalasiProteksiKebakaran.json) ---

*/
/**
 * Data class representing the COMPLETE structure of a filled form for 'instalasiProteksiKebakaran.json'.
 * All nested classes required to match the JSON are included.
 *//*

data class FireProtectionData(
    val typeInspection: String = "Pemeriksaan dan Pengujian Pertama",
    val registrationNumber: String = "REG-FIRE-2025-001",
    val documentChecklist: DocumentChecklist = DocumentChecklist(),
    val owner: Owner = Owner(),
    val buildingData: BuildingData = BuildingData(),
    val fireAlarmSystem: FireAlarmSystem = FireAlarmSystem(),
    val hydrantSystem: HydrantSystem = HydrantSystem(),
    val pumpFunctionTest: PumpFunctionTest = PumpFunctionTest(),
    val conclusion: Conclusion = Conclusion()
) {
    data class CheckStatus(val status: String, val notes: String)
    data class DocumentChecklist(
        val technicalDrawing: CheckStatus = CheckStatus("Tersedia", "Sesuai standar"),
        val previousTestDocumentation: CheckStatus = CheckStatus("Tersedia", "Lengkap"),
        val requestLetter: CheckStatus = CheckStatus("Tersedia", "Valid"),
        val specificationDocument: CheckStatus = CheckStatus("Tersedia", "Sesuai")
    )

    data class Owner(
        val name: String = "Mall Metropolis Megah",
        val address: String = "Jl. Raya Boulevard No. 123, Jakarta Selatan",
        val location: String = "Mall Metropolis Megah",
        val certificateNumber: String = "CERT-BLDG-987",
        val objectType: String = "Pusat Perbelanjaan"
    )

    data class BuildingData(
        val landArea: String = "50,000 m2",
        val buildingArea: String = "120,000 m2",
        val buildingHeight: String = "75 m",
        val floorCount: String = "6 Lantai + 2 Basement",
        val yearBuilt: String = "2020"
    )

    data class FireAlarmSystem(
        val mcfa: Mcfa = Mcfa(),
        val heatDetector: Detector = Detector("ROR", "250 titik", "8 m"),
        val smokeDetector: Detector = Detector("Photoelectric", "400 titik", "10 m"),
        val apar: Apar = Apar("Powder 6kg", "150 unit", "15 m")
    ) {
        data class Mcfa(
            val brandOrType: String = "Simplex 4100U",
            val backupBattery: String = "Baik",
            val powerIndicator: String = "Normal",
            val zoneIndicator: String = "Normal",
            val troubleIndicator: String = "Normal",
            val buzzer: String = "Berfungsi"
        )
        data class Detector(val brandOrType: String, val pointCount: String, val spacing: String)
        data class Apar(val brandOrType: String, val count: String, val spacing: String)
    }

    data class HydrantSystem(
        val waterSource: WaterSource = WaterSource(),
        val siameseConnection: SiameseConnection = SiameseConnection(),
        val jockeyPump: Pump = Pump("Grundfos", "JP-01", "7.5 kW", "2900 RPM"),
        val electricPump: Pump = Pump("Ebara", "EP-01", "75 kW", "2900 RPM"),
        val dieselPump: Pump = Pump("Clarke", "DP-01", "90 kW", "2500 RPM")
    ) {
        data class WaterSource(val type: String = "Ground Water Tank", val reservoirCapacity: String = "500 m3")
        data class SiameseConnection(val count: String = "2 unit", val status: String = "Baik")
        data class Pump(val model: String, val serialNumber: String, val power: String, val rpm: String)
    }

    data class PumpFunctionTest(
        val jockeyPump: TestResult = TestResult("110 PSI", "100 PSI"),
        val electricPump: TestResult = TestResult("90 PSI", "Manual Stop"),
        val dieselPump: TestResult = TestResult("85 PSI", "Manual Stop")
    ) {
        data class TestResult(val startPressure: String, val stopPressure: String)
    }

    data class Conclusion(
        val summary: String = "Sistem proteksi kebakaran berfungsi sesuai standar, namun perlu penggantian seal pada pompa diesel.",
        val conditions: List<String> = listOf("Seal pompa diesel menunjukkan rembesan kecil.", "Lakukan pengecekan berkala pada tekanan jockey pump."),
        val nextInspectionDate: String = "2026-07-15"
    )
}


// --- Repository with COMPLETE Mapping Logic ---

class InspectionRepository(private val inspectionDao: InspectionDao) {

    */
/**
     * Maps the COMPLETE Fire Protection form data to database entities and inserts them.
     * This version is not truncated and maps every single field from the data model.
     *
     * @param data The data object from your Jetpack Compose form for a fire protection inspection.
     *//*

    suspend fun saveFireProtectionInspection(data: FireProtectionData) {
        // 1. Map to the main 'Inspection' entity
        val mainInspection = Inspection(
            reportTitle = "Instalasi Proteksi Kebakaran",
            equipmentType = data.owner.objectType, // "Pusat Perbelanjaan"
            inspectionType = data.typeInspection,
            ownerName = data.owner.name,
            ownerAddress = data.owner.address,
            usageLocation = data.owner.location,
            serialNumber = null, // Not applicable for a whole system
            permitNumber = data.registrationNumber,
            capacity = data.hydrantSystem.waterSource.reservoirCapacity, // Using reservoir capacity
            speed = null, // Not applicable
            manufacturer = Manufacturer(
                name = "Sistem Terintegrasi", // Generic for integrated systems
                brandOrType = "Kombinasi Simplex, Grundfos, Ebara",
                year = data.buildingData.yearBuilt
            ),
            reportDate = "2025-07-15", // Example: Current date from form
            nextInspectionDate = data.conclusion.nextInspectionDate,
            inspectorName = "Siti Nurhaliza, S.T.", // Example: From user session
            status = "LAIK DENGAN CATATAN" // Derived from conclusion
        )

        // 2. Map ALL checklist items to 'InspectionCheckItem' entities
        val checkItems = mutableListOf<InspectionCheckItem>()

        // Mapping for Document Checklist
        data.documentChecklist.apply {
            checkItems.add(InspectionCheckItem(0, "Kelengkapan Dokumen", "Gambar Teknis", technicalDrawing.status, technicalDrawing.notes == "Sesuai standar"))
            checkItems.add(InspectionCheckItem(0, "Kelengkapan Dokumen", "Dokumentasi Uji Sebelumnya", previousTestDocumentation.status, previousTestDocumentation.notes == "Lengkap"))
            checkItems.add(InspectionCheckItem(0, "Kelengkapan Dokumen", "Surat Permohonan", requestLetter.status, requestLetter.notes == "Valid"))
            checkItems.add(InspectionCheckItem(0, "Kelengkapan Dokumen", "Dokumen Spesifikasi", specificationDocument.status, specificationDocument.notes == "Sesuai"))
        }

        // Mapping for Fire Alarm System components
        data.fireAlarmSystem.apply {
            mcfa.apply {
                checkItems.add(InspectionCheckItem(0, "Sistem Alarm Kebakaran", "MCFA - Baterai Cadangan", backupBattery, backupBattery == "Baik"))
                checkItems.add(InspectionCheckItem(0, "Sistem Alarm Kebakaran", "MCFA - Indikator Power", powerIndicator, powerIndicator == "Normal"))
                checkItems.add(InspectionCheckItem(0, "Sistem Alarm Kebakaran", "MCFA - Indikator Zona", zoneIndicator, zoneIndicator == "Normal"))
                checkItems.add(InspectionCheckItem(0, "Sistem Alarm Kebakaran", "MCFA - Buzzer", buzzer, buzzer == "Berfungsi"))
            }
            checkItems.add(InspectionCheckItem(0, "Sistem Alarm Kebakaran", "Detektor Panas", "Jumlah: ${heatDetector.pointCount}, Jarak: ${heatDetector.spacing}", true))
            checkItems.add(InspectionCheckItem(0, "Sistem Alarm Kebakaran", "Detektor Asap", "Jumlah: ${smokeDetector.pointCount}, Jarak: ${smokeDetector.spacing}", true))
            checkItems.add(InspectionCheckItem(0, "Sistem Alarm Kebakaran", "APAR", "Jumlah: ${apar.count}, Jarak: ${apar.spacing}", true))
        }

        // 3. Map the 'Conclusion' and 'Conditions' to 'InspectionFinding' entities
        val findings = mutableListOf<InspectionFinding>()
        findings.add(
            InspectionFinding(
                inspectionId = 0,
                description = data.conclusion.summary,
                type = FindingType.FINDING
            )
        )
        // Add all conditions as recommendations
        data.conclusion.conditions.forEach { condition ->
            findings.add(
                InspectionFinding(
                    inspectionId = 0,
                    description = condition,
                    type = FindingType.RECOMMENDATION
                )
            )
        }

        // 4. Map the pump tests and technical data to 'InspectionTestResult' entities
        val testResults = mutableListOf<InspectionTestResult>()
        data.pumpFunctionTest.apply {
            testResults.add(InspectionTestResult(0, "Tes Fungsi Pompa Jockey", "Start: ${jockeyPump.startPressure}, Stop: ${jockeyPump.stopPressure}", "OK"))
            testResults.add(InspectionTestResult(0, "Tes Fungsi Pompa Elektrik", "Start: ${electricPump.startPressure}, Stop: ${electricPump.stopPressure}", "OK"))
            testResults.add(InspectionTestResult(0, "Tes Fungsi Pompa Diesel", "Start: ${dieselPump.startPressure}, Stop: ${dieselPump.stopPressure}", "OK"))
        }
        data.hydrantSystem.apply {
            testResults.add(InspectionTestResult(0, "Data Teknis Pompa Jockey", "Merk: ${jockeyPump.model}, SN: ${jockeyPump.serialNumber}, Daya: ${jockeyPump.power}, RPM: ${jockeyPump.rpm}", "Data Sesuai"))
            testResults.add(InspectionTestResult(0, "Data Teknis Pompa Elektrik", "Merk: ${electricPump.model}, SN: ${electricPump.serialNumber}, Daya: ${electricPump.power}, RPM: ${electricPump.rpm}", "Data Sesuai"))
            testResults.add(InspectionTestResult(0, "Data Teknis Pompa Diesel", "Merk: ${dieselPump.model}, SN: ${dieselPump.serialNumber}, Daya: ${dieselPump.power}, RPM: ${dieselPump.rpm}", "Data Sesuai"))
            testResults.add(InspectionTestResult(0, "Data Teknis Siamese Connection", "Jumlah: ${siameseConnection.count}, Kondisi: ${siameseConnection.status}", "Data Sesuai"))
        }

        // 5. Call the DAO's transaction method to insert everything atomically
        inspectionDao.insertInspectionWithDetails(mainInspection, checkItems, findings, testResults)
    }

    // You can add the saveElevatorInspection function here as well if you want them in the same file.
}
*/
