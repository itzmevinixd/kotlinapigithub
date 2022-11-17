package br.com.kotlinapigithub.utilities

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {

    fun formatDate(date: String): String? {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val pattern = "dd/MM/yyyy HH:mm:ss"
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            parser.parse(date)?.let { simpleDateFormat.format(it) }
        } catch (exception: ParseException) {
            return null
        }
    }

}