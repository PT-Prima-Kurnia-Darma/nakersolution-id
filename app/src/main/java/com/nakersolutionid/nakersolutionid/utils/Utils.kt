package com.nakersolutionid.nakersolutionid.utils

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

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
        return try {
            // 1. Parse the input string into an OffsetDateTime object.
            // This object understands time zones and offsets.
            val parsedDateTime = OffsetDateTime.parse(isoDateString)

            // 2. Define the desired output format.
            // MM: Month
            // dd: Day
            // yyyy: Year
            // HH: Hour (00-23)
            // mm: Minute
            val outputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy (HH:mm)", Locale.getDefault())

            // 3. Format the parsed date-time object into the target string format.
            parsedDateTime.format(outputFormatter)

        } catch (e: DateTimeParseException) {
            // Handle cases where the input string is not a valid date format.
            "Invalid date format: ${e.message}"
        }
    }
}