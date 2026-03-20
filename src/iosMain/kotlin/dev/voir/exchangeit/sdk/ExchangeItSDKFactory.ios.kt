package dev.voir.exchangeit.sdk

import io.ktor.client.engine.darwin.*

actual object ExchangeItSDKFactory {
    actual fun create(config: ExchangeItSDKConfig): IExchangeItSDK {
        return ExchangeItSDK(
            engine = Darwin.create {
                configureRequest {
                    setAllowsExpensiveNetworkAccess(true)
                    setAllowsCellularAccess(true)
                }
            },
            config = config
        )
    }
}
