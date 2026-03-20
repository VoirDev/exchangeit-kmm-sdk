package dev.voir.exchangeit.sdk

import io.ktor.client.engine.okhttp.*

actual object ExchangeItSDKFactory {
    actual fun create(host: String, basePath: String): IExchangeItSDK {
        return ExchangeItSDK(
            engine = OkHttp.create(),
            apiHost = host,
            apiBasePath = basePath,
        )
    }
}
