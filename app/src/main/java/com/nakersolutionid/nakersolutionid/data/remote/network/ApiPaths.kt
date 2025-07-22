package com.nakersolutionid.nakersolutionid.data.remote.network

/**
 * Berisi konstanta untuk semua path endpoint API.
 * Path ini tidak diawali dengan slash ('/') agar fleksibel saat digunakan
 * dengan base URL Retrofit.
 */
object ApiPaths {

    // Auth
    const val AUTH_REGISTER = "auth/register"
    const val AUTH_LOGIN = "auth/login"
    const val AUTH_CHANGE = "auth/change"
    const val AUTH_LOGOUT = "auth/logout"
    const val AUTH_DELETE = "auth/delete/{id}"
    const val AUTH_VALIDATE_TOKEN = "auth/validateToken"

    // Audits
    const val AUDITS_ALL = "audits/all"

    // --- Elevator ---
    const val LAPORAN_ELEVATOR = "elevatorEskalator/elevator/laporan"
    const val BAP_ELEVATOR = "elevatorEskalator/elevator/bap"

    // --- Eskalator ---
    const val LAPORAN_ESKALATOR = "elevatorEskalator/eskalator/laporan"
    const val BAP_ESKALATOR = "elevatorEskalator/eskalator/bap"

    // --- Forklift ---
    const val LAPORAN_FORKLIFT = "paa/forklift/laporan"
    const val BAP_FORKLIFT = "paa/forklift/bap"

    // --- Mobile Crane ---
    const val LAPORAN_MOBILE_CRANE = "paa/mobileCrane/laporan"
    const val BAP_MOBILE_CRANE = "paa/mobileCrane/bap"

    // --- Gantry Crane ---
    const val LAPORAN_GANTRY_CRANE = "paa/gantryCrane/laporan"
    const val BAP_GANTRY_CRANE = "paa/gantryCrane/bap"

    // --- Gondola ---
    const val LAPORAN_GONDOLA = "paa/gondola/laporan"
    const val BAP_GONDOLA = "paa/gondola/bap"

    // --- Overhead Crane ---
    const val LAPORAN_OVERHEAD_CRANE = "paa/overHeadCrane/laporan"
    const val BAP_OVERHEAD_CRANE = "paa/overHeadCrane/bap"
}

/*
// Mendapatkan semua laporan forklift
viewModelScope.launch {
    val response = apiService.getAllLaporan<LaporanForklift>(
        path = ApiPaths.LAPORAN_FORKLIFT // Menggunakan konstanta
    )
    // ... proses respons
}

// Menghapus BAP Gondola berdasarkan ID
viewModelScope.launch {
    val bapId = "some_bap_id_123"
    val response = apiService.deleteBap(
        path = ApiPaths.BAP_GONDOLA_BY_ID.replace("{id}", bapId) // Mengganti placeholder
    )
    // ... proses respons
}*/
