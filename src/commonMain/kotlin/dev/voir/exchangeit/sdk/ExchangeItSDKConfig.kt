package dev.voir.exchangeit.sdk

data class ExchangeItSDKConfig(
    val host: String = "api.exchangeit.app",
    val basePath: String = "",

    val requestTimeoutMillis: Long = 8_000,
    val connectTimeoutMillis: Long = 3_000,
    val socketTimeoutMillis: Long = 8_000
)
