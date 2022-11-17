package br.com.kotlinapigithub.utilities

import org.junit.Assert.*
import org.junit.Test

class DateFormatterTest {

    @Test
    fun formatDateFailure() {
       assertNull(DateFormatter().formatDate("2222-22-22"))
    }

    @Test
    fun formatDateSuccess() {
        val expectedDate = "14/11/2022 19:25:03"
        val dateFormatted = DateFormatter().formatDate("2022-11-14T19:25:03Z")

        assertEquals(expectedDate, dateFormatted)
    }

}