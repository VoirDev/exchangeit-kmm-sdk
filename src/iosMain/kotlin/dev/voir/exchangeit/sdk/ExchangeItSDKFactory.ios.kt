package dev.voir.exchangeit.sdk

import io.ktor.client.engine.darwin.*

actual object ExchangeItSDKFactory {
    actual fun create(): IExchangeItSDK {
        return ExchangeItSDK(Darwin.create {
            configureRequest {
                setAllowsExpensiveNetworkAccess(true)
                setAllowsCellularAccess(true)
            }
        })
    }
}
