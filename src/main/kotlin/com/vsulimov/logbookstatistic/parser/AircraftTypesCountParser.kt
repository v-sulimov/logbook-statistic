package com.vsulimov.logbookstatistic.parser

import java.io.File

/**
 * Aircraft types count parser.
 * Parses information about the types of aircraft that flights were made on
 * and the number of flights for each type.
 * Uses regex-groupingBy and substring-set algorithms to calculate the values.
 */
class AircraftTypesCountParser : AbstractFileParser<Map<String, Int>>(
    propertyName = "aircraftTypesCount",
    parserName = AircraftTypesCountParser::class.simpleName.orEmpty(),
    firstAlgorithmName = "regex-groupingBy",
    secondAlgorithmName = "substring-set"
) {

    override fun parseUsingFirstAlgorithm(file: File): Map<String, Int> {
        val aircraftList = mutableListOf<String>()
        val regex = Regex("(?<=-> [A-Z]{4} ).*(?= - )")
        file.forEachLine { line ->
            regex.find(line)?.value?.let { aircraftList.add(it) }
        }
        return aircraftList.groupingBy { it }.eachCount()
    }

    override fun parseUsingSecondAlgorithm(file: File): Map<String, Int> {
        val aircraftList = mutableListOf<String>()
        val uniqueAircraftSet = mutableSetOf<String>()
        val aircraftTypesCountMap = mutableMapOf<String, Int>()
        file.readLines().drop(1).forEach { entry ->
            entry.substringAfter("->").drop(5).substringBefore(" -")
                .trim()
                .also { aircraftList.add(it) }
        }
        aircraftList.forEach { aircraft ->
            if (!uniqueAircraftSet.add(aircraft)) {
                val currentCounter = aircraftTypesCountMap[aircraft] ?: 1
                aircraftTypesCountMap[aircraft] = currentCounter.inc()
            } else {
                aircraftTypesCountMap[aircraft] = 1
            }
        }
        return aircraftTypesCountMap
    }
}
