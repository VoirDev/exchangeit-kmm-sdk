package dev.voir.exchangeit.sdk

import dev.voir.exchangeit.sdk.dto.*
import dev.voir.exchangeit.sdk.error.ExchangeItSDKError
import dev.voir.exchangeit.sdk.error.ExchangeItSDKException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json


private val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
    prettyPrint = true
}

class ExchangeItSDK(
    engine: HttpClientEngine,
    private val config: ExchangeItSDKConfig
) : IExchangeItSDK {
    private val client = HttpClient(engine) {
        install(HttpTimeout) {
            requestTimeoutMillis = config.requestTimeoutMillis
            connectTimeoutMillis = config.connectTimeoutMillis
            socketTimeoutMillis = config.socketTimeoutMillis
        }

        defaultRequest {
            url.protocol = URLProtocol.HTTPS
            url.host = config.host
            url.path(config.basePath, url.encodedPath)
        }
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }.apply {
        sendPipeline.intercept(HttpSendPipeline.Before) {
            try {
                proceed()
            } catch (e: Throwable) {
                throw ExchangeItSDKException(e)
            }
        }
    }

    override suspend fun getCurrencies(
        crypto: Boolean?,
        search: String?,
        withObsolete: Boolean?,
        withNoRates: Boolean?
    ): ListDto<CurrencyDto> {
        return client.get("v1/currencies") {
            parameter("crypto", crypto)
            parameter("search", search)
            parameter("withObsolete", withObsolete)
            parameter("withNoRates", withNoRates)
        }.bodyOrThrow()
    }

    override suspend fun getCurrencyDetailed(alias: String): DataDto<CurrencyDetailedDto> {
        return client.get("v1/currencies/${alias}").bodyOrThrow()
    }

    override suspend fun getLatestRates(
        alias: String,
        forAliases: List<String>?,
        forPopular: Int?
    ): DataDto<CurrencyLatestRatesDto> {
        return client.get("v2/currencies/${alias}/latest") {
            parameter("for", forAliases)
            parameter("forPopular", forPopular)
        }.bodyOrThrow()
    }

    override suspend fun getLatestRates(aliases: List<String>): DataDto<RatesDto> {
        return client.get("v2/rates/latest") {
            parameter("aliases", aliases.joinToString(","))
        }.bodyOrThrow()
    }

    override suspend fun getHistoricalRates(
        alias: String,
        date: String,
        forAliases: List<String>?,
        forPopular: Int?,
    ): DataDto<CurrencyRateByDateDto> {
        return client.get("v2/currencies/${alias}/historical") {
            parameter("date", date)
            parameter("for", forAliases)
            parameter("forPopular", forPopular)
        }.bodyOrThrow()
    }

    override suspend fun getHistoricalRates(
        alias: String,
        start: String,
        end: String,
        forAliases: List<String>?,
        forPopular: Int?,
    ): DataDto<CurrencyHistoricalRatesDto> {
        return client.get("v2/currencies/${alias}/range") {
            parameter("start", start)
            parameter("end", end)
            parameter("for", forAliases)
            parameter("forPopular", forPopular)
        }.bodyOrThrow()
    }

    override suspend fun getMonthlyRates(
        alias: String,
        start: String,
        end: String,
        forAliases: List<String>?,
        forPopular: Int?,
    ): DataDto<CurrencyMonthlyRatesDto> {
        return client.get("v2/currencies/${alias}/monthly") {
            parameter("start", start)
            parameter("end", end)
            parameter("for", forAliases)
            parameter("forPopular", forPopular)
        }.bodyOrThrow()
    }

    override suspend fun getSources(): ListDto<SourceDto> {
        return client.get("v1/sources") { }.bodyOrThrow()
    }

    override suspend fun getSource(alias: String): DataDto<SourceDto> {
        return client.get("v1/sources/${alias}") { }.bodyOrThrow()
    }

    override suspend fun convert(
        amount: Double,
        from: String,
        to: String
    ): DataDto<ConvertResultDto> {
        return client.get("v1/converter") {
            parameter("amount", amount)
            parameter("from", from)
            parameter("to", to)
        }.bodyOrThrow()
    }

    private suspend inline fun <reified T> HttpResponse.bodyOrThrow(): T {
        return if (status == HttpStatusCode.OK) {
            body()
        } else {
            val bodyText = bodyAsText()
            val bodyError = try {
                json.decodeFromString<ErrorDto>(bodyText).error
            } catch (e: SerializationException) {
                null
            }
            throw ExchangeItSDKException(ExchangeItSDKError(status.value, bodyError))
        }
    }
}
