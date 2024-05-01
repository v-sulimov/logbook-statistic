package com.vsulimov.logbookstatistic.parser

import java.io.File

/**
 * Days with multiple flights parser.
 * Parses information about days with more than 1 flight.
 * Uses regex-groupingBy and substring-set algorithms to calculate the values.
 */
class DaysWithMultipleFlightsParser : AbstractFileParser<Map<String, Int>>(
    propertyName = "daysWithMultipleFlights",
    parserName = DaysWithMultipleFlightsParser::class.simpleName.orEmpty(),
    firstAlgorithmName = "regex-groupingBy",
    secondAlgorithmName = "substring-set"
) {

    override fun parseUsingFirstAlgorithm(file: File): Map<String, Int> {
        val datesList = mutableListOf<String>()
        val regex = Regex("\\d{2}\\.\\d{2}")
        file.forEachLine { line ->
            regex.find(line)?.value?.let { datesList.add(it) }
        }
        return datesList.groupingBy { it }.eachCount().filter { it.value > 1 }
    }

    override fun parseUsingSecondAlgorithm(file: File): Map<String, Int> {
        val datesList = mutableListOf<String>()
        val uniqueDatesSet = mutableSetOf<String>()
        val duplicateDates = mutableMapOf<String, Int>()
        file.readLines().drop(1).forEach { entry ->
            entry.substringAfter('.').substringBefore(' ').trim().also { datesList.add(it) }
        }
        datesList.forEach { date ->
            if (!uniqueDatesSet.add(date)) {
                val currentCounter = duplicateDates[date] ?: 1
                duplicateDates[date] = currentCounter.inc()
            }
        }
        return duplicateDates
    }
}
