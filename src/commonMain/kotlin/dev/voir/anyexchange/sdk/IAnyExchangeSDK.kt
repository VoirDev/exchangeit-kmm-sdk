package dev.voir.anyexchange.sdk

import dev.voir.anyexchange.sdk.dto.*

interface IAnyExchangeSDK {
    suspend fun getCurrencies(crypto: Boolean?): DataArrayDto<CurrencyDto>

    suspend fun getLatestRates(base: String, codes: List<String>?): DataDto<CurrencyWithLatestRatesDto>

    suspend fun getDailyRates(base: String, date: String?, codes: List<String>?): DataDto<CurrencyWithRatesDto>

    suspend fun getHistoricalRates(
        base: String,
        start: String,
        end: String,
        codes: List<String>?
    ): DataDto<CurrencyHistoricalRatesDto>

    suspend fun getMonthlyRates(
        base: String,
        start: String,
        end: String,
        codes: List<String>?
    ): DataDto<CurrencyMonthlyRatesDto>
}
