package com.vsulimov.logbookstatistic.exception

/**
 * The name of the algorithm used to produce the result.
 */
typealias ParserAlgorithm = String

/**
 * Produced result.
 */
typealias ParserResult = String

/**
 * Thrown to indicate that values produced by two different algorithms differ from each other.
 *
 * @param propertyName The name of the property that has different calculated results.
 * @param parserName The name of the parser that produces a different results.
 * @param firstParserResult [Pair] of [ParserAlgorithm] and [ParserResult] which represents the result
 * of first algorithm calculations.
 * @param secondParserResult [Pair] of [ParserAlgorithm] and [ParserResult] which represents the result
 * of second algorithm calculations.
 */
class UnreliableResultException(
    propertyName: String,
    parserName: String,
    firstParserResult: Pair<ParserAlgorithm, ParserResult>,
    secondParserResult: Pair<ParserAlgorithm, ParserResult>
) : Exception(
    "Unreliable data received for property $propertyName.\n" +
            "Parser name: $parserName.\n" +
            "Algorithm ${firstParserResult.first} returns ${firstParserResult.second}.\n" +
            "Algorithm ${secondParserResult.first} returns ${secondParserResult.second}.\n"
)
