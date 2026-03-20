package dev.voir.exchangeit.sdk

import io.ktor.client.engine.okhttp.*

actual object ExchangeItSDKFactory {
    actual fun create(config: ExchangeItSDKConfig): IExchangeItSDK {
        return ExchangeItSDK(
            engine = OkHttp.create(),
            config = config
        )
    }
}
