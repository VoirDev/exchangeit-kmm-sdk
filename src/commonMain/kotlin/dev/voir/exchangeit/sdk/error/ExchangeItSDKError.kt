package dev.voir.exchangeit.sdk.error

data class ExchangeItSDKError(
    val code: Int = 0,
    val message: String? = null,
)
