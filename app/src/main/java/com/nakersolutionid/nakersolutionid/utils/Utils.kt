package com.nakersolutionid.nakersolutionid.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Utils {

    /**
     * Formats an ISO 8601 date string into "MM-dd-yyyy (HH:mm)" format.
     *
     * The input string is expected to be in a format that OffsetDateTime can parse,
     * such as "2024-07-26T10:00:00Z".
     *
     * The 'Z' at the end indicates that the time is in UTC. This function
     * will format the date and time based on the system's default time zone.
     *
     * @param isoDateString The date string in ISO 8601 format.
     * @return The formatted date string (e.g., "07-26-2024 (17:00)"), or an error
     * message if the input string is invalid.
     */
    fun formatIsoDate(isoDateString: String): String {
        // 1. Create a formatter that matches the input string
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)

        // 2. Create a formatter for the desired output string
        val outputFormat = SimpleDateFormat("MM-dd-yyyy (HH:mm)", Locale.US)

        return try {
            // Parse the input string into a Date object
            val date = inputFormat.parse(isoDateString)
            // Format the Date object into the new string format
            if (date != null) {
                outputFormat.format(date)
            } else {
                "Format tanggal tidak valid."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Format tanggal tidak valid."
        }
    }

    /**
     * Formats an ISO 8601 date string into "dd MMMM yyyy" format with Indonesian month names.
     *
     * The input string can be in various ISO 8601 formats, such as "2024-07-26T10:00:00Z"
     * or "2024-07-26T10:00:00.123+07:00".
     *
     * @param isoDateString The date string in ISO 8601 format.
     * @return The formatted date string (e.g., "26 Juli 2024"), or an error
     * message if the input string is invalid.
     */
    fun formatIsoDateToIndonesian(isoDateString: String): String {
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("id-ID"))

        // List of possible input formats to try, from most specific to most general
        val inputFormats = listOf(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US),
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US),
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply { timeZone = TimeZone.getTimeZone("UTC") }
        )

        for (format in inputFormats) {
            try {
                val date = format.parse(isoDateString)
                if (date != null) {
                    return outputFormat.format(date)
                }
            } catch (e: Exception) {
                // Ignore and try the next format
            }
        }

        return "Format tanggal tidak valid."
    }

    /**
     * Formats an ISO 8601 date string into "dd MMMM yyyy" format with Indonesian month names.
     *
     * The input string can be in various ISO 8601 formats, such as "2024-07-26T10:00:00Z"
     * or "2024-07-26T10:00:00.123+07:00".
     *
     * @param isoDateString The date string in ISO 8601 format.
     * @return The formatted date string (e.g., "26 Juli 2024"), or an error
     * message if the input string is invalid.
     */
    fun formatDateToIndonesian(isoDateString: String): String {
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("id-ID"))

        // List of possible input formats to try, from most specific to most general
        val inputFormats = listOf(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US),
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US),
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply { timeZone = TimeZone.getTimeZone("UTC") }
        )

        for (format in inputFormats) {
            try {
                val date = format.parse(isoDateString)
                if (date != null) {
                    return outputFormat.format(date)
                }
            } catch (e: Exception) {
                // Ignore and try the next format
            }
        }

        return "Format tanggal tidak valid."
    }

    fun getCurrentTime(): String {
        // The format XXX will produce the offset like +07:00
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
        // Set the time zone to UTC+7 (Jakarta Time)
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        return dateFormat.format(Date())
    }
}