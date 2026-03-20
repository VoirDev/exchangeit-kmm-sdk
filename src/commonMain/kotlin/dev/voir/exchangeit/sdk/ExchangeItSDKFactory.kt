package dev.voir.exchangeit.sdk

expect object ExchangeItSDKFactory {
    fun create(config: ExchangeItSDKConfig = ExchangeItSDKConfig()): IExchangeItSDK
}
