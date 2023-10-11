package dev.voir.anyexchange.sdk.error

class AnyExchangeSDKException : Exception {
    var error: AnyExchangeSDKError? = null
        private set

    constructor(error: AnyExchangeSDKError?) {
        this.error = error
    }

    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?) : super(message, cause)

    override val message: String
        get() = error?.toString() ?: super.message ?: "<no message>"
}
