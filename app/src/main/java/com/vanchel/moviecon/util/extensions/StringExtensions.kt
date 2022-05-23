package com.vanchel.moviecon.util.extensions

import com.vanchel.moviecon.util.DATE_FORMAT_DEFAULT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

val String.defaultFormatDateOrNull: Date?
    get() {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault())
        return try {
            dateFormat.parse(this)
        } catch (e: ParseException) {
            null
        }
    }