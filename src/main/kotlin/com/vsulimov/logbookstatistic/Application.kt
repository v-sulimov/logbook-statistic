package com.vsulimov.logbookstatistic

import com.vsulimov.logbookstatistic.model.Report
import com.vsulimov.logbookstatistic.parser.AircraftTypesCountParser
import com.vsulimov.logbookstatistic.parser.DaysWithMultipleFlightsParser
import com.vsulimov.logbookstatistic.parser.TotalFlightTimeParser
import com.vsulimov.logbookstatistic.parser.UniqueDaysWithFlightsParser
import com.vsulimov.logbookstatistic.parser.YearParser
import com.vsulimov.logbookstatistic.writer.ConsoleReportWriter
import java.io.File

private const val KEY_LOGBOOK_FILE_PATH_VM_OPTION = "logbookFilePath"

fun main() {
    val filePath = getFilePath()
    val file = readFile(filePath)
    val report = Report()
    val yearParser = YearParser()
    val uniqueDaysWIthFlightsParser = UniqueDaysWithFlightsParser()
    val daysWithMultipleFlightsParser = DaysWithMultipleFlightsParser()
    val aircraftTypesCountParser = AircraftTypesCountParser()
    val totalFlightTimeParser = TotalFlightTimeParser()
    val reportWriter = ConsoleReportWriter()
    report.year = yearParser.parse(file)
    report.uniqueDaysWithFlights = uniqueDaysWIthFlightsParser.parse(file)
    report.daysWithMultipleFlights = daysWithMultipleFlightsParser.parse(file)
    report.aircraftTypesCount = aircraftTypesCountParser.parse(file)
    report.totalFlightTime = totalFlightTimeParser.parse(file)
    reportWriter.write(report)
}

fun getFilePath(): String {
    return System.getProperty(KEY_LOGBOOK_FILE_PATH_VM_OPTION) ?: readFilePathFromInputStream()
}

fun readFilePathFromInputStream(): String {
    println("Enter a path to the logbook file:")
    return readln()
}

private fun readFile(filePath: String): File {
    return File(filePath)
}
