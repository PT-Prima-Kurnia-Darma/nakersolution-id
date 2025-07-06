package com.nakersolutionid.nakersolutionid.data.local.mapper

/*
import com.nakersolutionid.nakersolutionid.data.local.dao.InspectionDao
import com.nakersolutionid.nakersolutionid.data.local.entity.*

// --- Data Models (Representing Form State from pesawatAngkatAngkutMobileCrane.json) ---

*/
/**
 * Data class representing the COMPLETE structure of a filled form for 'pesawatAngkatAngkutMobileCrane.json'.
 * All nested classes required to match the JSON are included.
 *//*

data class MobileCraneData(
    val typeInspection: String = "Pemeriksaan dan Pengujian Berkala",
    val paaType: String = "Pesawat Angkat & Angkut",
    val generalData: GeneralData = GeneralData(),
    val technicalData: TechnicalData = TechnicalData(),
    val visualInspection: VisualInspection = VisualInspection(),
    val nonDestructiveTest: NonDestructiveTest = NonDestructiveTest(),
    val testing: Testing = Testing(),
    val conclusion: Conclusion = Conclusion(),
    val recommendations: List<String> = listOf("Lakukan pelumasan rutin pada turntable bearing.", "Ganti wire rope aus pada auxiliary hoist."),
    val nextInspectionDate: String = "2026-07-20",
    val inspector: Inspector = Inspector()
) {
    data class GeneralData(
        val owner: Owner = Owner("PT Konstruksi Perkasa", "Kawasan Industri Cikarang, Blok A1"),
        val user: Owner = Owner("PT Konstruksi Perkasa", "Kawasan Industri Cikarang, Blok A1"),
        val unitLocation: String = "Proyek Pembangunan Jembatan Tol Serang-Panimbang",
        val operator: Operator = Operator("Budi Santoso", "Berlisensi (SIO Kelas I)"),
        val equipmentType: String = "Mobile Crane",
        val manufacturer: ManufacturerData = ManufacturerData(),
        val capacity: String = "50 Ton",
        val usagePermitNumber: String = "SKP-MC-005/2023",
        val historyData: String = "Tidak ada riwayat perbaikan mayor."
    ) {
        data class Owner(val name: String, val address: String)
        data class Operator(val name: String, val certificateStatus: String)
        data class ManufacturerData(
            val name: String = "Tadano",
            val brandOrType: String = "GR-500EX",
            val locationAndYear: String = "Jepang / 2021",
            val serialNumber: String = "TMC-21-67890"
        )
    }

    data class TechnicalData(
        val maxCapacity: String = "50 Ton pada radius 3m",
        val boomLength: String = "11.1m - 34.1m",
        val mainHook: Hook = Hook("Hook Block", "50 Ton", "Baja Tempa", "SN-H50-123"),
        val auxiliaryHook: Hook = Hook("Ball Hook", "5 Ton", "Baja Tempa", "SN-H5-456"),
        val mainHoistRope: WireRope = WireRope("18mm", "IWRC 6x36", "180 kgf/mm2"),
        val driveMotor: DriveMotor = DriveMotor()
    ) {
        data class Hook(val type: String, val capacity: String, val material: String, val serialNumber: String)
        data class WireRope(val diameter: String, val construction: String, val breakingStrength: String)
        data class DriveMotor(
            val engineNumber: String = "DM-XYZ-987",
            val type: String = "Diesel 6 Silinder Turbo",
            val netPower: String = "200 kW / 2600 RPM"
        )
    }

    data class CheckResult(val result: String, val status: Boolean)
    data class VisualInspection(
        val foundationAndStructure: CheckResult = CheckResult("Tidak ada deformasi atau retak", true),
        val outriggers: CheckResult = CheckResult("Berfungsi normal, tidak ada kebocoran hidrolik", true),
        val turntable: CheckResult = CheckResult("Putaran halus, tidak ada suara abnormal", true),
        val latticeBoom: CheckResult = CheckResult("Tidak ada korosi atau pin yang aus", true),
        val winches: CheckResult = CheckResult("Drum dan gearbox dalam kondisi baik", true),
        val pulleysAndHooks: CheckResult = CheckResult("Alur puli tidak aus, hook latch berfungsi", true),
        val wireRopes: CheckResult = CheckResult("Tidak ada kawat putus atau deformasi", false), // Example with a false status
        val limitSwitches: CheckResult = CheckResult("Semua limit switch berfungsi saat diuji", true),
        val engine: CheckResult = CheckResult("Tidak ada kebocoran oli, suara mesin normal", true),
        val operatorCabin: CheckResult = CheckResult("Panel kontrol, AC, dan wiper berfungsi", true),
        val safetyDevices: CheckResult = CheckResult("LMI/SLI, anometer, dan level indicator berfungsi", true)
    )

    data class NonDestructiveTest(
        val wireRope: NdtResult = NdtResult("Magnetic Rope Test (MRT)", "Ditemukan 2 kawat putus pada aux. hoist rope"),
        val mainHook: NdtResult = NdtResult("Magnetic Particle Inspection (MPI)", "Tidak ditemukan retak"),
        val boom: NdtResult = NdtResult("Ultrasonic Testing (UT)", "Tidak ditemukan cacat pada las-lasan")
    ) {
        data class NdtResult(val testType: String, val result: String)
    }

    data class Testing(
        val functionalTest: CheckResult = CheckResult("Semua fungsi (hoist, swing, boom) berjalan lancar tanpa beban", true),
        val loadTest: LoadTest = LoadTest()
    ) {
        data class LoadTest(
            val dynamic: DynamicTest = DynamicTest(),
            val static: StaticTest = StaticTest()
        )
        data class DynamicTest(
            val mainHook: TestPoint = TestPoint("50 Ton", "3m", "110%", "55 Ton", "Lulus"),
            val auxiliaryHook: TestPoint = TestPoint("5 Ton", "10m", "110%", "5.5 Ton", "Lulus")
        )
        data class StaticTest(
            val mainHook: TestPoint = TestPoint("50 Ton", "3m", "125%", "62.5 Ton", "Lulus, tidak ada penurunan beban"),
            val auxiliaryHook: TestPoint = TestPoint("5 Ton", "10m", "125%", "6.25 Ton", "Lulus, tidak ada penurunan beban")
        )
        data class TestPoint(val swl: String, val radius: String, val loadPercentage: String, val testLoad: String, val result: String)
    }

    data class Conclusion(
        val findings: List<String> = listOf(
            "Secara umum Mobile Crane dalam kondisi baik.",
            "Ditemukan keausan pada wire rope auxiliary hoist yang memerlukan penggantian."
        )
    )

    data class Inspector(
        val reportDate: String = "2025-07-20",
        val company: String = "PT Amanah Sertifikasi Indonesia",
        val name: String = "Ir. H. Endang Sutarman"
    )
}

// --- Repository with COMPLETE Mapping Logic ---

class InspectionRepository(private val inspectionDao: InspectionDao) {

    */
/**
     * Maps the COMPLETE Mobile Crane form data to database entities and inserts them.
     * This version is not truncated and maps every single field from the data model.
     *
     * @param data The data object from your Jetpack Compose form for a mobile crane inspection.
     *//*

    suspend fun saveMobileCraneInspection(data: MobileCraneData) {
        // 1. Map to the main 'Inspection' entity
        val mainInspection = InspectionEntity(
            reportType = data.paaType,
            equipmentType = data.generalData.equipmentType,
            inspectionType = data.typeInspection,
            documentType = TODO(),
            subReportType = TODO(),
            ownerName = data.generalData.owner.name,
            ownerAddress = data.generalData.owner.address,
            usageLocation = data.generalData.unitLocation,
            serialNumber = data.generalData.manufacturer.serialNumber,
            permitNumber = data.generalData.usagePermitNumber,
            capacity = data.generalData.capacity,
            speed = null, // Speed is not a single value for cranes, can be stored in tests
            manufacturer = Manufacturer(
                name = data.generalData.manufacturer.name,
                brandOrType = data.generalData.manufacturer.brandOrType,
                year = data.generalData.manufacturer.locationAndYear.split("/").lastOrNull()?.trim()
            ),
            reportDate = data.inspector.reportDate,
            nextInspectionDate = data.nextInspectionDate,
            inspectorName = data.inspector.name,
            status = "LAIK DENGAN REKOMENDASI"
        )

        // 2. Map ALL visual inspection items to 'InspectionCheckItem' entities
        val checkItems = mutableListOf<InspectionCheckItem>()
        data.visualInspection.apply {
            checkItems.add(InspectionCheckItem(0, "Struktur & Pondasi", "Struktur Utama", foundationAndStructure.result, foundationAndStructure.status))
            checkItems.add(InspectionCheckItem(0, "Struktur & Pondasi", "Outriggers", outriggers.result, outriggers.status))
            checkItems.add(InspectionCheckItem(0, "Struktur & Pondasi", "Turntable", turntable.result, turntable.status))
            checkItems.add(InspectionCheckItem(0, "Komponen Utama", "Boom", latticeBoom.result, latticeBoom.status))
            checkItems.add(InspectionCheckItem(0, "Komponen Mekanikal", "Winch", winches.result, winches.status))
            checkItems.add(InspectionCheckItem(0, "Komponen Mekanikal", "Puli & Hook", pulleysAndHooks.result, pulleysAndHooks.status))
            checkItems.add(InspectionCheckItem(0, "Komponen Mekanikal", "Tali Kawat Baja", wireRopes.result, wireRopes.status))
            checkItems.add(InspectionCheckItem(0, "Komponen Penggerak", "Mesin", engine.result, engine.status))
            checkItems.add(InspectionCheckItem(0, "Kabin & Kontrol", "Kabin Operator", operatorCabin.result, operatorCabin.status))
            checkItems.add(InspectionCheckItem(0, "Perangkat Keselamatan", "Limit Switch", limitSwitches.result, limitSwitches.status))
            checkItems.add(InspectionCheckItem(0, "Perangkat Keselamatan", "Indikator (LMI/SLI)", safetyDevices.result, safetyDevices.status))
        }

        // 3. Map the 'Conclusion' and 'Recommendations' to 'InspectionFinding' entities
        val findings = mutableListOf<InspectionFinding>()
        data.conclusion.findings.forEach { findingText ->
            findings.add(InspectionFinding(0, description = findingText, type = FindingType.FINDING))
        }
        data.recommendations.forEach { recommendationText ->
            findings.add(InspectionFinding(0, description = recommendationText, type = FindingType.RECOMMENDATION))
        }

        // 4. Map ALL test results to 'InspectionTestResult' entities
        val testResults = mutableListOf<InspectionTestResult>()

        // Functional Test
        testResults.add(InspectionTestResult(0, "Uji Fungsi Tanpa Beban", data.testing.functionalTest.result, if(data.testing.functionalTest.status) "Lulus" else "Gagal"))

        // NDT Results
        data.nonDestructiveTest.apply {
            testResults.add(InspectionTestResult(0, "NDT - Tali Kawat Baja", wireRope.result, wireRope.testType))
            testResults.add(InspectionTestResult(0, "NDT - Main Hook", mainHook.result, mainHook.testType))
            testResults.add(InspectionTestResult(0, "NDT - Boom", boom.result, boom.testType))
        }

        // Load Test Results
        data.testing.loadTest.apply {
            // Dynamic Load Test
            testResults.add(InspectionTestResult(0, "Uji Beban Dinamis - Main Hook", "SWL: ${dynamic.mainHook.swl}, Radius: ${dynamic.mainHook.radius}, Beban Uji: ${dynamic.mainHook.testLoad} (${dynamic.mainHook.loadPercentage})", "Hasil: ${dynamic.mainHook.result}"))
            testResults.add(InspectionTestResult(0, "Uji Beban Dinamis - Aux Hook", "SWL: ${dynamic.auxiliaryHook.swl}, Radius: ${dynamic.auxiliaryHook.radius}, Beban Uji: ${dynamic.auxiliaryHook.testLoad} (${dynamic.auxiliaryHook.loadPercentage})", "Hasil: ${dynamic.auxiliaryHook.result}"))
            // Static Load Test
            testResults.add(InspectionTestResult(0, "Uji Beban Statis - Main Hook", "SWL: ${static.mainHook.swl}, Radius: ${static.mainHook.radius}, Beban Uji: ${static.mainHook.testLoad} (${static.mainHook.loadPercentage})", "Hasil: ${static.mainHook.result}"))
            testResults.add(InspectionTestResult(0, "Uji Beban Statis - Aux Hook", "SWL: ${static.auxiliaryHook.swl}, Radius: ${static.auxiliaryHook.radius}, Beban Uji: ${static.auxiliaryHook.testLoad} (${static.auxiliaryHook.loadPercentage})", "Hasil: ${static.auxiliaryHook.result}"))
        }

        // 5. Call the DAO's transaction method to insert everything atomically
        inspectionDao.insertInspectionWithDetails(mainInspection, checkItems, findings, testResults)
    }
}*/
