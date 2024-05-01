package com.vsulimov.logbookstatistic.parser

import java.io.File

/**
 * Unique days with flights parser.
 * Parses information about unique days on which flights took place.
 * Uses regex-distinct and substring-set algorithms to calculate the values.
 */
class UniqueDaysWithFlightsParser : AbstractFileParser<Int>(
    propertyName = "uniqueDaysWithFlights",
    parserName = UniqueDaysWithFlightsParser::class.simpleName.orEmpty(),
    firstAlgorithmName = "regex-distinct",
    secondAlgorithmName = "substring-set"
) {

    override fun parseUsingFirstAlgorithm(file: File): Int {
        val datesList = mutableListOf<String>()
        val regex = Regex("\\d{2}\\.\\d{2}")
        file.forEachLine { line ->
            regex.find(line)?.value?.let { datesList.add(it) }
        }
        return datesList.distinct().size
    }

    override fun parseUsingSecondAlgorithm(file: File): Int {
        val datesList = mutableListOf<String>()
        file.readLines().drop(1).forEach { entry ->
            entry.substringAfter('.').substringBefore(' ').trim().also { datesList.add(it) }
        }
        return datesList.toSet().size
    }
}
