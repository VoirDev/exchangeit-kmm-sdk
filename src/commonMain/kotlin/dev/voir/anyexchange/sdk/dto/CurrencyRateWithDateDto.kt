package dev.voir.anyexchange.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateWithDateDto(
    val code: String,
    val rate: Double,
    val source: String,
    val date: String,
    val fluctuation: Double
)
