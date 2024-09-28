package com.devmike.issues

import androidx.compose.ui.graphics.Color
import java.time.Duration
import java.time.ZonedDateTime

/**
 * Calculates the relative time between the given date string and the current time.
 *
 * @param dateString The date string in the format "MMMM dd, yyyy, hh:mm a".
 * @return A string representing the relative time between the given date and the current time.
 */
fun relativeTime(dateString: String): String {
    val zonedDateTime = ZonedDateTime.parse(dateString)

    //  val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm a", Locale.getDefault())

    val now = ZonedDateTime.now()

    val duration = Duration.between(zonedDateTime, now)

    val relativeTime =
        when {
            duration.toMinutes() < MINUTE_THRESHOLD -> "Just now"
            duration.toMinutes() < HOUR_THRESHOLD -> "${duration.toMinutes()} min"
            duration.toHours() < DAY_THRESHOLD -> "${duration.toHours()} hr "
            duration.toDays() < WEEK_THRESHOLD -> "${duration.toDays()} day"
            duration.toDays() < MONTH_THRESHOLD -> "${duration.toDays() / WEEK_THRESHOLD} wk"
            duration.toDays() < YEAR_THRESHOLD -> "${duration.toDays() / MONTH_THRESHOLD} mon"
            else -> "${duration.toDays() / YEAR_THRESHOLD} yr"
        }

    return relativeTime
}

const val MINUTE_THRESHOLD = 1L

const val HOUR_THRESHOLD = 60L
const val DAY_THRESHOLD = 24L
const val WEEK_THRESHOLD = 7L

const val MONTH_THRESHOLD = 30L
const val YEAR_THRESHOLD = 365L

val GithubGreen = Color(0xFF006400)

const val LUMINANCE_THRESHOLD = 0.5f

fun <T> MutableSet<T>.toggleItem(item: T) {
    if (contains(item)) {
        remove(item)
    } else {
        add(item)
    }
}
