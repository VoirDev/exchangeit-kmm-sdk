package dev.voir.anyexchange.sdk

import io.ktor.client.engine.okhttp.*

actual object AnyExchangeSDKFactory {
    actual fun create(): IAnyExchangeSDK {
        return AnyExchangeSDK(OkHttp.create())
    }
}
