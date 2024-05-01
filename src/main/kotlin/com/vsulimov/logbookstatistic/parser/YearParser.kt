package com.vsulimov.logbookstatistic.parser

import java.io.File

/**
 * Year parser.
 * Parses the year in which the logbook was created.
 * Uses regex and filter algorithms to calculate the values.
 */
class YearParser : AbstractFileParser<String>(
    propertyName = "year",
    parserName = YearParser::class.simpleName.orEmpty(),
    firstAlgorithmName = "regex",
    secondAlgorithmName = "filter"
) {

    override fun parseUsingFirstAlgorithm(file: File): String {
        val firstLine = file.bufferedReader().use { it.readLine() }
        val regex = Regex("\\d{4}")
        val matchResult = regex.find(firstLine)
        return matchResult?.value.orEmpty()
    }

    override fun parseUsingSecondAlgorithm(file: File): String {
        val firstLine = file.bufferedReader().use { it.readLine() }
        return firstLine.filter { it.isDigit() }
    }
}
