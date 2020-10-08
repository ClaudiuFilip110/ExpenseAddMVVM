package com.example.expenceappmvvm.domain.util

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.text.SimpleDateFormat
import java.util.*


object DateAndTimeUtils {
    const val DAY_IN_MS = 1000 * 60 * 60 * 24

    fun getDateAsString(
        dateInMillis: Long,
        addHour: Boolean,
        dateFormat: String,
        timeFormat: String = ""
    ): String {
        val date = Date(dateInMillis)
        val dateFormat = SimpleDateFormat(dateFormat)
        val timeFormat = SimpleDateFormat(timeFormat)
        return if (!addHour) {
            dateFormat.format(date)
        } else dateFormat.format(date) + " at " + timeFormat.format(date)
    }

    private fun clearData(calendar: Calendar, timeMillis: Long) {
        calendar.apply {
            timeInMillis = timeMillis
            set(Calendar.HOUR_OF_DAY, 0)
            clear(Calendar.MINUTE)
            clear(Calendar.SECOND)
            clear(Calendar.MILLISECOND)
        }
    }

    fun firstHourOfDayMillis(): Long {
        return Calendar.getInstance().apply {
            clearData(this, System.currentTimeMillis())
        }.timeInMillis
    }

    fun firstDayOfWeekMillis(): Long {
        return Calendar.getInstance().apply {
            clearData(this, System.currentTimeMillis())
            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
        }.timeInMillis
    }

    fun firstDayOfMonth(): Long {
        return Calendar.getInstance().apply {
            clearData(this, System.currentTimeMillis())
            set(Calendar.DAY_OF_MONTH, getActualMinimum(Calendar.DAY_OF_MONTH))
        }.timeInMillis
    }

    fun getOneYearAgoMillis(): Long {
        val calendar = Calendar.getInstance().apply {
            clearData(this, firstDayOfMonth())
            add(Calendar.MONTH, 1)
            add(Calendar.YEAR, -1)
        }
        return calendar.timeInMillis
    }

    fun getStartOfNextMonth(timeMillis: Long): Long {
        return Calendar.getInstance().apply {
            timeInMillis = timeMillis
            add(Calendar.MONTH, 1)
        }.timeInMillis
    }

    fun getMonthName(timeMillis: Long): String {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timeMillis
        }
        return SimpleDateFormat(Constants.APP_MONTH_FORMAT).format(calendar.timeInMillis)
    }

    fun firstDayOfYear(): Long {
        return Calendar.getInstance().apply {
            clearData(this, System.currentTimeMillis())
            set(Calendar.DAY_OF_YEAR, getActualMinimum(Calendar.DAY_OF_YEAR))
        }.timeInMillis
    }

    fun convertFromStringToDate(date: String, time: String): Date {
        try {
            return SimpleDateFormat("yyyy-MM-dd HH:mm").parse("$date $time")
        } catch (e: Exception) {
            try {
                return SimpleDateFormat("yyyy-MM-dd").parse(date)
            } catch (e: Exception) {
            }
        }
        return Date()
    }

    fun convertDate(date: Date): LocalDateTime {
        val pattern = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val d = pattern.format(date)
        return LocalDateTime.parse(d)
    }

    fun convertSimpleDate(date: Date): LocalDate {
        val pattern = SimpleDateFormat("yyyy-MM-dd")
        val d = pattern.format(date)
        return LocalDate.parse(d)
    }
}