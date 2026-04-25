package nz.benlawrence.splatoontracker.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.math.abs
import kotlin.time.Instant

fun formatDuration(secondsTotal: Long, hideSeconds: Boolean = false): String {
    val isNegative = secondsTotal < 0
    val absSeconds = abs(secondsTotal)

    val days = absSeconds / 86400
    val hours = (absSeconds % 86400) / 3600
    val minutes = (absSeconds % 3600) / 60
    val seconds = absSeconds % 60

    val negativeSign = if (isNegative) "-" else ""

    // Formatting numbers with leading zeros where appropriate
    val mStr = if (days > 0 || hours > 0) minutes.toString().padStart(2, '0') else minutes.toString()
    val sStr = seconds.toString().padStart(2, '0')

    return when {
        days > 0 -> {
            if (hideSeconds) "${negativeSign}${days}d ${hours}h ${mStr}m"
            else "${negativeSign}${days}d ${hours}h ${mStr}m ${sStr}s"
        }
        hours > 0 -> {
            if (hideSeconds) "${negativeSign}${hours}h ${mStr}m"
            else "${negativeSign}${hours}h ${mStr}m ${sStr}s"
        }
        else -> "${negativeSign}${mStr}m ${sStr}s"
    }
}

@Composable
fun rememberFormattedDuration(
    isoTimestamp: String,
    hideSeconds: Boolean = false
): State<String> {
    // Re-calculate whenever the timestamp or hideSeconds setting changes
    return produceState(initialValue = "", isoTimestamp, hideSeconds) {
        val targetInstant = ZonedDateTime.parse(isoTimestamp).toInstant()

        while (true) {
            val now = java.time.Instant.now()
            val remainingSeconds = targetInstant.epochSecond - now.epochSecond

            value = formatDuration(remainingSeconds, hideSeconds)

            // If we are hiding seconds and have more than an hour left,
            // we can poll less frequently (e.g., every second vs every minute),
            // but for simplicity, 1s polling keeps the UI snappy.
            delay(1000L)
        }
    }
}