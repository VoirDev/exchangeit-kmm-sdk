package dev.voir.anyexchange.sdk

import dev.voir.anyexchange.sdk.dto.*

interface IAnyExchangeSDK {
    suspend fun getCurrencies(crypto: Boolean? = null): DataArrayDto<CurrencyDto>

    suspend fun getLatestRates(base: String, codes: List<String>? = null): DataDto<CurrencyWithLatestRatesDto>

    suspend fun getDailyRates(
        base: String,
        date: String? = null,
        codes: List<String>? = null
    ): DataDto<CurrencyWithRatesDto>

    suspend fun getHistoricalRates(
        base: String,
        start: String,
        end: String,
        codes: List<String>? = null
    ): DataDto<CurrencyHistoricalRatesDto>

    suspend fun getMonthlyRates(
        base: String,
        start: String,
        end: String,
        codes: List<String>? = null
    ): DataDto<CurrencyMonthlyRatesDto>
}
