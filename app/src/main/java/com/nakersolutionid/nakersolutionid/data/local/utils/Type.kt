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
    // EE
    Elevator,
    Escalator,
    // PAA
    Forklift,
    Mobile_Crane,
    Overhead_Crane,
    Gantry_Crane,
    Gondola,
    // ILPP
    Electrical,
    Lightning_Conductor,
    // PUBT
    General_PUBT,
    // IPK
    Fire_Protection,
    // PTP
    Motor_Diesel,
    Machine
}

// Extension function untuk mengonversi SubInspectionType ke string yang lebih mudah dibaca
fun SubInspectionType.toDisplayString(): String {
    return when (this) {
        SubInspectionType.Elevator -> "Elevator"
        SubInspectionType.Escalator -> "Eskalator"

        SubInspectionType.Forklift -> "Forklift"
        SubInspectionType.Mobile_Crane -> "Mobile Crane"
        SubInspectionType.Overhead_Crane -> "Overhead Crane"
        SubInspectionType.Gantry_Crane -> "Gantry Crane"
        SubInspectionType.Gondola -> "Gondola"

        SubInspectionType.Electrical -> "Instalasi Listrik"
        SubInspectionType.Lightning_Conductor -> "Instalasi Penyalur Petir"

        SubInspectionType.General_PUBT -> "Pesawat Uap dan Bejana Tekan"

        SubInspectionType.Fire_Protection -> "Instalasi Proteksi Kebakaran"

        SubInspectionType.Motor_Diesel -> "Motor Diesel"
        SubInspectionType.Machine -> "Mesin"
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
        "Eskalator" -> SubInspectionType.Escalator

        "Forklift" -> SubInspectionType.Forklift
        "Mobile Crane" -> SubInspectionType.Mobile_Crane
        "Overhead Crane" -> SubInspectionType.Overhead_Crane
        "Gantry Crane" -> SubInspectionType.Gantry_Crane
        "Gondola" -> SubInspectionType.Gondola

        "Instalasi Petir" -> SubInspectionType.Electrical
        "Instalasi Penyalur Petir" -> SubInspectionType.Lightning_Conductor

        "Pesawat Uap dan Bejana Tekan" -> SubInspectionType.General_PUBT

        "Instalasi Proteksi Kebakaran" -> SubInspectionType.Fire_Protection

        "Motor Diesel" -> SubInspectionType.Motor_Diesel
        "Mesin" -> SubInspectionType.Machine
        else -> null
    }
}