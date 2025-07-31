package com.nakersolutionid.nakersolutionid.utils

import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.TemporalAccessor
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Utils {

    fun formatIsoDate(isoDateString: String): String {
        // 1. This single formatter handles all three of your specified formats.
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]XXX")

        // 2. This is the desired output format.
        val outputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy (HH:mm)")

        return try {
            // 3. Parse the string using the flexible formatter.
            val temporalAccessor: TemporalAccessor = inputFormatter.parse(isoDateString)

            // 4. Format the parsed object into the new string.
            outputFormatter.format(temporalAccessor)
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
            "Invalid date format"
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

    /**
     * Returns the current system date and time in ISO 8601 format.
     *
     * This function uses the device's default time zone to format the date.
     * For a device in Jakarta, the output will include the "+07:00" offset.
     *
     * @return The formatted date string, e.g., "2025-07-29T15:18:00.123+07:00".
     */
    fun getCurrentTime(): String {
        // The format XXX will produce the offset like +07:00 or -05:00
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
        // By not setting a specific time zone, SimpleDateFormat uses the system's default.
        return dateFormat.format(Date())
    }
}