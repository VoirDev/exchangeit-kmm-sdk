# Exchange It: SDK (Kotlin MultiPlatform)

SDK for [Exchange It API](https://exchangeit.app/docs) written in Kotlin. For now supports iOS, JVM
and Android.

API supports requests to fetch list of currencies, latest/historical/average monthly rates for
selected
currency.

*No authorization needed.* Rates are collected from several *FREE* sources and provided as it is.

Right now Exchange It API is in *beta* so use with caution.

## Installation

```kotlin
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

dependencies {
    implementation("dev.voir:exchangeit-sdk:1.0.7")
}
```

## Usage

#### Configuration

You can customize the SDK behavior by providing an `ExchangeItSDKConfig`.

```kotlin
data class ExchangeItSDKConfig(
    val host: String = "api.exchangeit.app",
    val basePath: String = "",

    val requestTimeoutMillis: Long = 8_000,
    val connectTimeoutMillis: Long = 3_000,
    val socketTimeoutMillis: Long = 8_000
)
```

**Parameters:**

- `host` – API host (e.g. api.exchangeit.app)
- `basePath` – optional base path prefix for all endpoints
- `requestTimeoutMillis` – maximum duration of the whole request
- `connectTimeoutMillis` – maximum time to establish connection
- `socketTimeoutMillis` – maximum inactivity time between data packets

#### Create instance of SDK

Creating an instance of SDK is quite simple:

```kotlin
val sdk = ExchangeItSDKFactory.create()
```

You can override default settings by providing your own config:

```kotlin
val sdk = ExchangeItSDKFactory.create(
    config = ExchangeItSDKConfig(
        host = "api.exchangeit.ru",
        requestTimeoutMillis = 8_000,
        connectTimeoutMillis = 3_000,
        socketTimeoutMillis = 8_000
    )
)
```

#### Run request

```kotlin
sdk.getCurrencies() // Return list of currencies
sdk.getCurrencies(crypto = true) // Return list of cryptos
sdk.getLatestRates(alias = "USD") // Return latest rates for base currency code, additionally you can limit returned rates by codes parameter
sdk.getDailyRates(
    alias = "USD",
    date = "2002-10-04"
) // Return rates for base currency on provided date
sdk.getHistoricalRates(
    alias = "USD",
    start = "2002-10-04",
    end = "2002-10-10"
) // Return rates for base currency in provided range (not more than 365 days)
sdk.getMonthlyRates(
    alias = "USD",
    start = "2002-10",
    end = "2005-10"
) // Return average monthly rates for base currency in provided range 
```

## Testing

To run tests

```bash
./gradlew check
```

## Publish to local Maven repository

```bash
./gradlew publishToMavenLocal
```
