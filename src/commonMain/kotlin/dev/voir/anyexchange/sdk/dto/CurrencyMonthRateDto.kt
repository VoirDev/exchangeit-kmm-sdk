package dev.voir.anyexchange.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyMonthRateDto(
    val month: String,
    val rates: List<CurrencyRateDto>
)
