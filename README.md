# Exchange It: SDK (Kotlin MultiPlatform)

SDK for [Exchange It API](https://exchangeit.app/docs) written in Kotlin. For now supports iOS, JVM and Android.

API supports requests to fetch list of currencies, latest/historical/average monthly rates for selected
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
    implementation("dev.voir:exchangeit-sdk:1.0.6")
}
```

## Usage

#### Create instance of SDK

Creating an instance of SDK is quite simple, just write

```kotlin
val sdk = ExchangeItSDKFactory.create()
```

#### Run request

```kotlin
sdk.getCurrencies() // Return list of currencies
sdk.getCurrencies(crypto = true) // Return list of cryptos
sdk.getLatestRates(alias = "USD") // Return latest rates for base currency code, additionally you can limit returned rates by codes parameter
sdk.getDailyRates(alias = "USD", date = "2002-10-04") // Return rates for base currency on provided date
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
