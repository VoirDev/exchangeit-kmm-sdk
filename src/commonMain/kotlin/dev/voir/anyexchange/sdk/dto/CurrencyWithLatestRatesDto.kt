package dev.voir.anyexchange.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyWithLatestRatesDto(
    val code: String,
    val title: String,
    val rates: List<CurrencyRateWithDateDto>
)
