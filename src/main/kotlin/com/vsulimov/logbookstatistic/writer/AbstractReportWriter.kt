package com.vsulimov.logbookstatistic.writer

import com.vsulimov.logbookstatistic.model.Report

/**
 * An abstract report writer that creates a report based on a provided [Report] model.
 */
abstract class AbstractReportWriter {

    /**
     * Process the text of the report and use it
     * according to your discretion (e.g., output it to the console or write it to a file).
     */
    abstract fun handleReportText(reportText: String)

    /**
     * Writes the text of the report.
     * The details of the implementation and the further logic of working with this text
     * depend on the implementation of the method [handleReportText].
     */
    fun write(report: Report) {
        val reportText = "Logbook statistic.\n\n" +
                "Year: ${report.year}\n\n" +
                "The number of days when there were flights: ${report.uniqueDaysWithFlights}\n\n" +
                "The days when there were several flights: ${getDaysWithSeveralFlights(report)}\n" +
                "Aircraft types and the number of flights on them: ${getAircraftTypes(report)}\n" +
                "Total flight time for this year: ${report.totalFlightTime}"
        handleReportText(reportText)
    }

    private fun getAircraftTypes(report: Report): String {
        val stringBuilder = StringBuilder("")
        stringBuilder.append("\n")
        report.aircraftTypesCount?.toList()?.sortedByDescending { it.second }?.toMap()?.map { entry ->
            stringBuilder.append("${entry.key} - ${entry.value} ${getFlightsString(entry.value)}\n")
        }
        return stringBuilder.toString()
    }

    private fun getFlightsString(value: Int): String =
        if (value == 1) "flight" else "flights"

    private fun getDaysWithSeveralFlights(report: Report): String {
        val stringBuilder = StringBuilder("")
        stringBuilder.append("\n")
        report.daysWithMultipleFlights?.forEach { entry ->
            stringBuilder.append("${entry.key} - ${entry.value} flights\n")
        }
        return stringBuilder.toString()
    }
}
