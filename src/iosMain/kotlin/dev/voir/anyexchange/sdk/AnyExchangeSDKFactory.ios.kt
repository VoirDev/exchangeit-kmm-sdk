package dev.voir.anyexchange.sdk

import io.ktor.client.engine.darwin.*

actual object AnyExchangeSDKFactory {
    actual fun create(): IAnyExchangeSDK {
        return AnyExchangeSDK(Darwin.create {
            configureRequest {
                setAllowsExpensiveNetworkAccess(true)
                setAllowsCellularAccess(true)
            }
        })
    }
}
