package dev.voir.exchangeit.sdk

import dev.voir.exchangeit.sdk.ExchangeItItSDK
import dev.voir.exchangeit.sdk.IExchangeItSDK
import io.ktor.client.engine.darwin.*

actual object ExchangeItSDKFactory {
    actual fun create(): IExchangeItSDK {
        return ExchangeItItSDK(Darwin.create {
            configureRequest {
                setAllowsExpensiveNetworkAccess(true)
                setAllowsCellularAccess(true)
            }
        })
    }
}
