package dev.voir.anyexchange.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateDto(
    val code: String,
    val rate: Double,
    val source: String
)
