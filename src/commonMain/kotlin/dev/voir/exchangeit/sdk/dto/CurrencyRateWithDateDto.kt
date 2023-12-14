package dev.voir.exchangeit.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateWithDateDto(
    val code: String,
    val rate: Double,
    val date: String,
    val fluctuation: Double
)
