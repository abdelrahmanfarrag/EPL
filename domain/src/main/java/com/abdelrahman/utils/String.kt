package com.abdelrahman.utils

import com.abdelrahman.utils.DateFormat.UTC
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * Authored by Abdelrahman Ahmed on 02 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */

fun String?.convertToDesiredDateFormat(inputFormat: String, outputFormat: String): String? {
  return try {
    val dateFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getTimeZone(UTC)
    val date = dateFormat.parse(this ?: "")
    val desiredFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
    desiredFormat.timeZone = TimeZone.getDefault()
    desiredFormat.format(date)
  } catch (exception: Exception) {
    this
  }
}