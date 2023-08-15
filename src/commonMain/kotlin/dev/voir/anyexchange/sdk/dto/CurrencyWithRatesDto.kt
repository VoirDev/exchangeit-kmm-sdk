package dev.voir.anyexchange.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyWithRatesDto(
    val code: String,
    val title: String,
    val date: String,
    val rates: List<CurrencyRateDto>
)
