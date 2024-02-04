package dev.voir.exchangeit.sdk

import dev.voir.exchangeit.sdk.dto.*

interface IExchangeItSDK {
    suspend fun getCurrencies(crypto: Boolean? = null, search: String? = null): ListDto<CurrencyDto>

    suspend fun getCurrencyDetailed(base: String): DataDto<CurrencyDetailedDto>

    suspend fun getLatestRates(codes: List<String>): ListDto<CurrencyWithLatestRatesDto>

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
