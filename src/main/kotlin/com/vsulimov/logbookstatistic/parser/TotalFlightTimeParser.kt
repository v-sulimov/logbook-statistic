package com.vsulimov.logbookstatistic.parser

import java.io.File
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * Total flight time parser.
 * Parses information about the total amount of time spent in air.
 * Uses regex-milliseconds-duration and regex-manual-calculations algorithms to calculate the values.
 */
class TotalFlightTimeParser : AbstractFileParser<String>(
    propertyName = "totalFlightTime",
    parserName = TotalFlightTimeParser::class.simpleName.orEmpty(),
    firstAlgorithmName = "regex-milliseconds-duration",
    secondAlgorithmName = "regex-manual-calculations"
) {

    override fun parseUsingFirstAlgorithm(file: File): String {
        val timestampList = mutableListOf<Long>()
        val regex = Regex("\\d+:\\d+:\\d+")
        file.forEachLine { line ->
            regex.find(line)?.value?.split(":")?.let { timeParts ->
                val hours = timeParts[0].toLong()
                val minutes = timeParts[1].toLong()
                val seconds = timeParts[2].toLong()
                val totalMilliseconds = (hours * 3600 + minutes * 60 + seconds) * 1000
                timestampList.add(totalMilliseconds)
            }
        }
        var duration = Duration.ZERO
        timestampList.forEach { timestamp ->
            duration = duration.plus(timestamp.toDuration(DurationUnit.MILLISECONDS))
        }
        return duration.toString()
    }

    override fun parseUsingSecondAlgorithm(file: File): String {
        val timeList = mutableListOf<String>()
        val regex = Regex("\\d+:\\d+:\\d+")
        var totalSeconds = 0L
        file.forEachLine { line ->
            regex.find(line)?.value?.let { timeList.add(it) }
        }
        timeList.forEach { time ->
            val timeParts = time.split(":")
            val hours = timeParts[0].toLong()
            val minutes = timeParts[1].toLong()
            val seconds = timeParts[2].toLong()
            totalSeconds += hours * 3600 + minutes * 60 + seconds
        }
        val days = totalSeconds / (24 * 3600)
        val hours = (totalSeconds % (24 * 3600)) / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format("%dd %dh %dm %ds", days, hours, minutes, seconds).replace("0d ", "")
    }
}
