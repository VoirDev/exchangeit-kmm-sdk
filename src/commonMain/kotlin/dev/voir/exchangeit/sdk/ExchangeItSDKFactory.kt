package dev.voir.exchangeit.sdk

expect object ExchangeItSDKFactory {
    fun create(host: String = "api.exchangeit.app", basePath: String = ""): IExchangeItSDK
}
