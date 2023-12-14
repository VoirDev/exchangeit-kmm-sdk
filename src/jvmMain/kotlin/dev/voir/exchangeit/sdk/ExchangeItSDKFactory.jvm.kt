package dev.voir.exchangeit.sdk

import io.ktor.client.engine.okhttp.*

actual object ExchangeItSDKFactory {
    actual fun create(): IExchangeItSDK {
        return ExchangeItItSDK(OkHttp.create())
    }
}
