package com.example.core.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

object ImageUrl {

    private const val BASE = "https://image.tmdb.org/t/p/"
    private const val SIZE_BIG = "w500"
    private const val SIZE_SMALL = "w185"
    private const val ADULT = "D"
    private const val CHILD = "SU"

    fun poster(path: String?): String =
        BASE + SIZE_BIG + path

    fun isAdult(adult: Boolean): String = if (adult) ADULT else CHILD

    fun toAvatarUrl(path: String): String {
        if (path.isBlank()) return ""

        return when {
            path.startsWith("/https") -> path.removePrefix("/")
            path.startsWith("http") -> path
            else -> BASE + SIZE_SMALL + path
        }
    }


}

object DateHelper {

    fun getMonth(date: String): String {
        if (date.isBlank()) return ""
        return try {
            val inputFormat = SimpleDateFormat("MMMM dd yyyy", Locale.US)
            val outputFormat = SimpleDateFormat("MMM", Locale.US)
            val parsedDate = inputFormat.parse(date)
            parsedDate?.let { outputFormat.format(it) } ?: ""
        } catch (_: Exception) {
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val outputFormat = SimpleDateFormat("MMM", Locale.US)
                val parsedDate = inputFormat.parse(date)
                parsedDate?.let { outputFormat.format(it) } ?: ""
            } catch (_: Exception) {
                ""
            }
        }
    }

    fun getFormattedDate(date: String): String {
        if (date.isBlank()) return ""

        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val outputFormat = SimpleDateFormat("MMMM dd yyyy", Locale.US)

            val date = inputFormat.parse(date)
            outputFormat.format(date!!)
        } catch (_: Exception) {
            ""
        }
    }

    fun getFormattedTime(date: String): String {
        if (date.isBlank()) return ""

        return try {
            val inputFormat = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.US
            ).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }

            val outputFormat = SimpleDateFormat(
                "MMMM d, yyyy, HH:mm",
                Locale.US
            )

            val date = inputFormat.parse(date)
            outputFormat.format(date!!)
        } catch (_: Exception) {
            ""
        }
    }

    fun getFormattedTime(time: Int): String {
        val hours = time / 60
        val remainingMinutes = time % 60

        return when {
            hours > 0 && remainingMinutes > 0 -> "${hours}h ${remainingMinutes}m"
            hours > 0 -> "${hours}h"
            else -> "${remainingMinutes}m"
        }
    }
}

object NumberHelper {

    fun roundOneDecimal(value: Double): Double {
        return (value * 10).roundToInt() / 10.0
    }

}

object YoutubeHelper {

    private const val VIDEO_URL = "https://www.youtube.com/watch?v="
    private const val THUMBNAIL_URL = "https://img.youtube.com/vi/%s/hqdefault.jpg"

    fun getVideoUrl(key: String?): String {
        return if (key.isNullOrBlank()) "" else VIDEO_URL + key
    }

    fun getThumbnailUrl(key: String?): String {
        return if (key.isNullOrBlank()) "" else THUMBNAIL_URL.format(key)
    }
}