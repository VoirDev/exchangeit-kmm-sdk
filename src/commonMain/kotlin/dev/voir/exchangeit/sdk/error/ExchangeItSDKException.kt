package dev.voir.exchangeit.sdk.error

class ExchangeItSDKException : Exception {
    var error: ExchangeItSDKError? = null
        private set

    constructor(error: ExchangeItSDKError?) {
        this.error = error
    }

    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?) : super(message, cause)

    override val message: String
        get() = error?.toString() ?: super.message ?: "<no message>"
}
