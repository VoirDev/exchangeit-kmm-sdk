package dev.voir.exchangeit.sdk

import io.ktor.client.engine.darwin.*

actual object ExchangeItSDKFactory {
    actual fun create(host: String, basePath: String): IExchangeItSDK {
        return ExchangeItSDK(
            engine = Darwin.create {
                configureRequest {
                    setAllowsExpensiveNetworkAccess(true)
                    setAllowsCellularAccess(true)
                }
            },
            apiHost = host,
            apiBasePath = basePath
        )
    }
}
