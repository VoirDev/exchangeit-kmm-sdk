package dev.voir.exchangeit.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyHistoricalRateDto(
    val date: String,
    val rates: List<CurrencyRateDto>
)
