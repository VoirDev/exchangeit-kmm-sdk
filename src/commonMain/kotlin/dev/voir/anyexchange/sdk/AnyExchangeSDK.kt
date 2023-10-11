package dev.voir.anyexchange.sdk

import dev.voir.anyexchange.sdk.dto.*
import dev.voir.anyexchange.sdk.error.AnyExchangeSDKError
import dev.voir.anyexchange.sdk.error.AnyExchangeSDKException
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

class AnyExchangeSDK(engine: HttpClientEngine) : IAnyExchangeSDK {
    private val client = HttpClient(engine) {
        defaultRequest {
            url.protocol = URLProtocol.HTTPS
            url.host = API_HOST
            url.path(API_BASE_PATH, url.encodedPath)
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
                throw AnyExchangeSDKException(e)
            }
        }
    }

    override suspend fun getCurrencies(crypto: Boolean?): DataArrayDto<CurrencyDto> {
        return client.get("currencies") {
            parameter("crypto", crypto)
        }.bodyOrThrow()
    }

    override suspend fun getLatestRates(base: String, codes: List<String>?): DataDto<CurrencyWithLatestRatesDto> {
        return client.get("currencies/${base}/latest") {
            parameter("codes", codes)
        }.bodyOrThrow()
    }

    override suspend fun getDailyRates(
        base: String,
        date: String?,
        codes: List<String>?
    ): DataDto<CurrencyWithRatesDto> {
        return client.get("currencies/${base}/daily") {
            parameter("date", date)
            parameter("codes", codes)
        }.bodyOrThrow()
    }

    override suspend fun getHistoricalRates(
        base: String,
        start: String,
        end: String,
        codes: List<String>?
    ): DataDto<CurrencyHistoricalRatesDto> {
        return client.get("currencies/${base}/historical") {
            parameter("start", start)
            parameter("end", end)
            parameter("codes", codes)
        }.bodyOrThrow()
    }

    override suspend fun getMonthlyRates(
        base: String,
        start: String,
        end: String,
        codes: List<String>?
    ): DataDto<CurrencyMonthlyRatesDto> {
        return client.get("currencies/${base}/monthly") {
            parameter("start", start)
            parameter("end", end)
            parameter("codes", codes)
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
            throw AnyExchangeSDKException(AnyExchangeSDKError(status.value, bodyError))
        }
    }

    companion object {
        private const val API_HOST = "anyexchange-api.voir.dev"
        private const val API_BASE_PATH = ""
    }
}
