package com.vsulimov.logbookstatistic.writer

/**
 * Console report writer.
 *
 * Prints the generated report to the console.
 */
class ConsoleReportWriter : AbstractReportWriter() {

    override fun handleReportText(reportText: String) {
        println(reportText)
    }
}
