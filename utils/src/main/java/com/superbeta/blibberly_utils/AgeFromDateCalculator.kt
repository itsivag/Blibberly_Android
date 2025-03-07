package com.superbeta.blibberly_utils

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Suppress("NewApi")
fun ageFromDateCalculator(dateStr: String): Int? {
    return try {
        val date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy"))
        Period.between(date, LocalDate.now()).years
    } catch (e: Exception) {
        println("AgeFromDate : $e")
        null
    }
}