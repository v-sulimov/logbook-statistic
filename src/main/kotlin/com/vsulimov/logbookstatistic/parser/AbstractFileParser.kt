package com.vsulimov.logbookstatistic.parser

import com.vsulimov.logbookstatistic.exception.UnreliableResultException
import java.io.File

/**
 * An abstract [File] parser that returns only the necessary information of type [RESULT] from the entire logbook.
 *
 * Uses precision through a redundancy approach.
 *
 * Each value is calculated by two different algorithms.
 *
 * If the calculation results are different, an [UnreliableResultException] is thrown.
 *
 * @param RESULT The type of the returned result.
 * @param propertyName The name of the calculated property this parser returns.
 * @param parserName The name of the parser itself.
 * @param firstAlgorithmName The name of the first algorithm used to calculate the property value.
 * @param secondAlgorithmName The name of the second algorithm used to calculate the property value.
 */
abstract class AbstractFileParser<RESULT>(
    val propertyName: String, val parserName: String, val firstAlgorithmName: String, val secondAlgorithmName: String
) {

    /**
     * Parses the [file] using two different algorithms and returns the [RESULT] if both calculated values are the same.
     *
     * @param file Logbook file with flights information.
     * @return [RESULT].
     * @throws UnreliableResultException If the results of calculations by two algorithms differ from each other.
     */
    fun parse(file: File): RESULT {
        val firstParserResult = parseUsingFirstAlgorithm(file)
        val secondParserResult = parseUsingSecondAlgorithm(file)
        if (firstParserResult != secondParserResult) {
            throw UnreliableResultException(
                propertyName = propertyName,
                parserName = parserName,
                firstParserResult = firstAlgorithmName to firstParserResult.toString(),
                secondParserResult = secondAlgorithmName to secondParserResult.toString()
            )
        }
        return firstParserResult
    }

    /**
     * Parses the [File] using the first algorithm and returns the [RESULT].
     *
     * @param file Logbook file with flights information.
     * @return [RESULT].
     */
    abstract fun parseUsingFirstAlgorithm(file: File): RESULT

    /**
     * Parses the [File] using the second algorithm and returns the [RESULT].
     *
     * @param file Logbook file with flights information.
     * @return [RESULT].
     */
    abstract fun parseUsingSecondAlgorithm(file: File): RESULT
}
