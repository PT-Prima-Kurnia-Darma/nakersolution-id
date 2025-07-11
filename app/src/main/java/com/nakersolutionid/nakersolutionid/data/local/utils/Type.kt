package com.nakersolutionid.nakersolutionid.data.local.utils

enum class DocumentType {
    LAPORAN,
    BAP,
    SURAT_KETERANGAN_SEMENTARA,
    SERTIFIKAT_SEMENTARA,
}

enum class InspectionType {
    ILPP,
    IPK,
    PAA,
    PUBT,
    PTP,
    EE
}

enum class SubInspectionType {
    Elevator,
    Escalator,
    Travelator,

    Forklift,
    Bulldozer,
    Excavator,
    Wheel_Loader,
    Farm_Tractor,
    Motor_Grader,
    Mobile_Crane,
    Hoist_Crane,
    Tower_Crane,
    Truck_Crane,
    Overhead_Crane,
    Gantry_Crane,
    Gondola,
    Jib_Crane,

    Electrical,
    Lightning_Conductor,

    Boiler,
    Steam_Vessel,
    BPV,
    Storage_Tank,
    Compressor_BT,
    Pressure_Powered_Pipe,

    Automatic_Fire_Alarm,
    Hydrant_And_Sprinkler,
    Flooding_System,

    Motor_Diesel,
    Turbin,
    Perkakas,
    Produksi,
    Tanur
}

// Extension function untuk mengonversi SubInspectionType ke string yang lebih mudah dibaca
fun SubInspectionType.toDisplayString(): String {
    return when (this) {
        SubInspectionType.Elevator -> "Elevator"
        SubInspectionType.Escalator -> "Escalator"
        SubInspectionType.Travelator -> "Travelator"

        SubInspectionType.Forklift -> "Forklift"
        SubInspectionType.Bulldozer -> "Bulldozer"
        SubInspectionType.Excavator -> "Excavator"
        SubInspectionType.Wheel_Loader -> "Wheel Loader"
        SubInspectionType.Farm_Tractor -> "Farm Tractor"
        SubInspectionType.Motor_Grader -> "Motor Grader"
        SubInspectionType.Mobile_Crane -> "Mobile Crane"
        SubInspectionType.Hoist_Crane -> "Hoist Crane"
        SubInspectionType.Tower_Crane -> "Tower Crane"
        SubInspectionType.Truck_Crane -> "Truck Crane"
        SubInspectionType.Overhead_Crane -> "Overhead Crane"
        SubInspectionType.Gantry_Crane -> "Gantry Crane"
        SubInspectionType.Gondola -> "Gondola"
        SubInspectionType.Jib_Crane -> "Jib Crane"

        SubInspectionType.Electrical -> "Instalasi Listrik"
        SubInspectionType.Lightning_Conductor -> "Instalasi Penyalur Petir"

        SubInspectionType.Boiler -> "Ketel Uap"
        SubInspectionType.Steam_Vessel -> "Bejana Uap"
        SubInspectionType.BPV -> "BPV"
        SubInspectionType.Storage_Tank -> "Tangki Timbun"
        SubInspectionType.Compressor_BT -> "Compressor BT"
        SubInspectionType.Pressure_Powered_Pipe -> "Pipa Bertenaga/Bertekanan"

        SubInspectionType.Automatic_Fire_Alarm -> "Alarm Kebakaran Otomatis"
        SubInspectionType.Hydrant_And_Sprinkler -> "Instalasi Hydrant dan Sprinkler"
        SubInspectionType.Flooding_System -> "Instalasi Flooding System"

        SubInspectionType.Motor_Diesel -> "Motor Diesel"
        SubInspectionType.Turbin -> "Turbin"
        SubInspectionType.Perkakas -> "Perkakas"
        SubInspectionType.Produksi -> "Produksi"
        SubInspectionType.Tanur -> "Tanur"
    }
}

fun InspectionType.toDisplayString(): String {
    return when (this) {
        InspectionType.ILPP -> "Instalasi Listrik dan Penyalur Petir"
        InspectionType.IPK -> "Instalasi Proteksi Kebakaran"
        InspectionType.PAA -> "Pesawat Angkat dan Angkut"
        InspectionType.PUBT -> "Pesawat Uap dan Bejana Tekan"
        InspectionType.PTP -> "Pesawat Tenaga dan Produksi"
        InspectionType.EE -> "Elevator dan Escalator"
    }
}

fun DocumentType.toDisplayString(): String {
    return when (this) {
        DocumentType.LAPORAN -> "Laporan"
        DocumentType.BAP -> "Berita Acara dan Pemeriksaan Pengujian"
        DocumentType.SURAT_KETERANGAN_SEMENTARA -> "Surat Keterangan Sementara"
        DocumentType.SERTIFIKAT_SEMENTARA -> "Sertifikat Sementara"
    }
}

fun String.toDocumentType(): DocumentType? {
    // Membandingkan langsung dengan string display yang tepat, peka huruf besar/kecil
    return when (this) {
        "Laporan" -> DocumentType.LAPORAN
        "Berita Acara dan Pemeriksaan Pengujian" -> DocumentType.BAP
        "Surat Keterangan Sementara" -> DocumentType.SURAT_KETERANGAN_SEMENTARA
        "Sertifikat Sementara" -> DocumentType.SERTIFIKAT_SEMENTARA
        else -> null
    }
}

fun String.toInspectionType(): InspectionType? {
    // Membandingkan langsung dengan string display yang tepat, peka huruf besar/kecil
    return when (this) {
        "Instalasi Listrik dan Penyalur Petir" -> InspectionType.ILPP
        "Instalasi Proteksi Kebakaran" -> InspectionType.IPK
        "Pesawat Angkat dan Angkut" -> InspectionType.PAA
        "Pesawat Uap dan Bejana Tekan" -> InspectionType.PUBT
        "Pesawat Tenaga dan Produksi" -> InspectionType.PTP
        "Elevator dan Escalator" -> InspectionType.EE
        else -> null
    }
}

fun String.toSubInspectionType(): SubInspectionType? {
    // Membandingkan langsung dengan string display yang tepat, peka huruf besar/kecil
    return when (this) {
        "Elevator" -> SubInspectionType.Elevator
        "Escalator" -> SubInspectionType.Escalator
        "Travelator" -> SubInspectionType.Travelator

        "Forklift" -> SubInspectionType.Forklift
        "Bulldozer" -> SubInspectionType.Bulldozer
        "Excavator" -> SubInspectionType.Excavator
        "Wheel Loader" -> SubInspectionType.Wheel_Loader
        "Farm Tractor" -> SubInspectionType.Farm_Tractor
        "Motor Grader" -> SubInspectionType.Motor_Grader
        "Mobil Crane" -> SubInspectionType.Mobile_Crane
        "Hoist Crane" -> SubInspectionType.Hoist_Crane
        "Tower Crane" -> SubInspectionType.Tower_Crane
        "Truck Crane" -> SubInspectionType.Truck_Crane
        "Overhead Crane" -> SubInspectionType.Overhead_Crane
        "Gantry Crane" -> SubInspectionType.Gantry_Crane
        "Gondola" -> SubInspectionType.Gondola
        "Jib Crane" -> SubInspectionType.Jib_Crane

        "Instalasi Petir" -> SubInspectionType.Electrical
        "Instalasi Penyalur Petir" -> SubInspectionType.Lightning_Conductor

        "Ketel Uap" -> SubInspectionType.Boiler
        "Bejana Uap" -> SubInspectionType.Steam_Vessel
        "BPV" -> SubInspectionType.BPV
        "Tangki Timbun" -> SubInspectionType.Storage_Tank
        "Compressor BT" -> SubInspectionType.Compressor_BT
        "Pipa Bertenaga/Bertekanan" -> SubInspectionType.Pressure_Powered_Pipe

        "Alarm Kebakaran Otomatis" -> SubInspectionType.Automatic_Fire_Alarm
        "Instalasi Hydrant dan Sprinkler" -> SubInspectionType.Hydrant_And_Sprinkler
        "Instalasi Flooding System" -> SubInspectionType.Flooding_System

        "Motor Diesel" -> SubInspectionType.Motor_Diesel
        "Turbin" -> SubInspectionType.Turbin
        "Perkakas" -> SubInspectionType.Perkakas
        "Produksi" -> SubInspectionType.Produksi
        "Tanur" -> SubInspectionType.Tanur
        else -> null
    }
}