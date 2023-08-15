package dev.voir.anyexchange.sdk

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

@OptIn(DelicateCoroutinesApi::class)
class AnyExchangeSDKTests {
    private val sdk = AnyExchangeSDKFactory.create()
    private val testLock = Mutex()

    init {
        GlobalScope.launch {
            while (true) {
                testLock.withLock { delay(5.seconds) }
            }
        }
    }

    @Test
    fun `test get currencies`() = runApiTest {
        val currencies = sdk.getCurrencies(crypto = null)
        val crypto = sdk.getCurrencies(crypto = true)

        assertEquals(true, currencies.total > 0)
        assertEquals(true, currencies.data.isNotEmpty())

        assertEquals(true, crypto.total > 0)
        assertEquals(true, crypto.data.isNotEmpty())

        assertEquals(true, currencies.data.first().code != crypto.data.first().code)
    }

    @Test
    fun `test get latest rates`() = runApiTest {
        val latestRates = sdk.getLatestRates(base = "rub", codes = null)

        assertEquals("rub", latestRates.data.code)
        assertEquals(true, latestRates.data.rates.isNotEmpty())
    }

    @Test
    fun `test get daily rates`() = runApiTest {
        val dailyRates = sdk.getDailyRates(base = "rub", date = "2002-10-10", codes = null)

        assertEquals("rub", dailyRates.data.code)
        assertEquals("2002-10-10", dailyRates.data.date)
        assertEquals(true, dailyRates.data.rates.isNotEmpty())
    }

    @Test
    fun `test get historical rates`() = runApiTest {
        val historicalRates =
            sdk.getHistoricalRates(base = "rub", start = "2002-10-10", end = "2002-10-14", codes = null)

        assertEquals("rub", historicalRates.data.code)
        assertEquals("2002-10-10", historicalRates.data.start)
        assertEquals("2002-10-14", historicalRates.data.end)
        assertEquals("2002-10-10", historicalRates.data.historical.first().date)
        assertEquals("2002-10-14", historicalRates.data.historical.last().date)
    }

    @Test
    fun `test get monthly rates`() = runApiTest {
        val historicalRates =
            sdk.getMonthlyRates(base = "rub", start = "2002-10-10", end = "2003-10-14", codes = null)

        assertEquals("rub", historicalRates.data.code)
        assertEquals("2002-10", historicalRates.data.start)
        assertEquals("2003-10", historicalRates.data.end)
        assertEquals("2002-10", historicalRates.data.monthly.first().month)
        assertEquals("2003-10", historicalRates.data.monthly.last().month)
    }

    /*    @Test
        fun testCoin() = runApiTest { await ->
            val btc = coinGecko.getCoinById("bitcoin", localization = false)
            assertEquals("Bitcoin", btc.name)

            await()
            val eth = coinGecko.getCoinById("ethereum")
            assertEquals("Ethereum", eth.name)

            await()
            val brd = coinGecko.getCoinById("bread")
            assertEquals("Bread", brd.name)
        }

        @Test
        fun testMarketData() = runApiTest {
            val btcData = coinGecko.getCoinMarketChartById("bitcoin", "usd", 3.0)
            assertTrue(btcData.prices.isNotEmpty())
            assertTrue(btcData.prices.first().isNotEmpty())
            assertTrue(btcData.marketCaps.isNotEmpty())
            assertTrue(btcData.marketCaps.first().isNotEmpty())
            assertTrue(btcData.totalVolumes.isNotEmpty())
            assertTrue(btcData.totalVolumes.first().isNotEmpty())
        }

        @Test
        fun testGetCoinMarkets() = runApiTest {
            val ids = arrayOf("bitcoin", "ethereum", "bread", "zcash")
            val response = coinGecko.getCoinMarkets("usd", ids.joinToString(","))

            assertEquals(ids.size, response.markets.size)
            assertEquals(0, response.total)
            assertEquals(0, response.perPage)
            assertNull(response.nextPage)
            assertNull(response.previousPage)
            response.markets.forEach { market ->
                assertTrue(ids.contains(market.id))
            }
        }

        @Test
        fun testCoinPrice() = runApiTest { await ->
            val btcPrices = coinGecko.getPrice("bitcoin", "usd,cad")
            val btc = assertNotNull(btcPrices["bitcoin"])
            assertNotNull(btc.getPrice("usd"))
            assertNotNull(btc.getPrice("cad"))
            assertNull(btc.lastUpdatedAt)

            await()
            val ethPrices = coinGecko.getPrice(
                "ethereum",
                "usd,eur",
                includeMarketCap = true,
                include24hrVol = true,
                include24hrChange = true,
                includeLastUpdatedAt = true,
            )
            val eth = assertNotNull(ethPrices["ethereum"])
            assertNotNull(eth.getPrice("usd"))
            assertNotNull(eth.getPrice("eur"))
            assertNotNull(eth.get24hrChange("usd"))
            assertNotNull(eth.get24hrChange("eur"))
            assertNotNull(eth.get24hrVol("usd"))
            assertNotNull(eth.get24hrVol("eur"))
            assertNotNull(eth.getMarketCap("usd"))
            assertNotNull(eth.getMarketCap("eur"))
            assertNotNull(eth.lastUpdatedAt)
        }

        @Test
        fun testCoinTickers() = runApiTest { await ->
            val coinPage1 = coinGecko.getCoinTickerById("tether", "binance")
            assertEquals(100, coinPage1.perPage)
            assertEquals(2, coinPage1.nextPage)
            assertTrue(coinPage1.total > 100)
            assertNull(coinPage1.previousPage)

            await()
            val coinPage2 = coinGecko.getCoinTickerById("tether", "binance", page = 2)
            assertEquals(100, coinPage2.perPage)
            assertEquals(1, coinPage2.previousPage)
            assertTrue(coinPage2.total > 100)
            assertNotNull(coinPage2.nextPage)

            await()
            val coinPage500 = coinGecko.getCoinTickerById("tether", "binance", page = 500)
            assertNull(coinPage500.nextPage)
        }

        @Test
        fun testCoinHistory() = runApiTest {
            val bitcoin = coinGecko.getCoinHistoryById("bitcoin", "23-10-2018")
            val image = assertNotNull(bitcoin.image)
            assertTrue(image.small.isNotBlank())
        }

        @Test
        fun testNonExistentCoin() = runApiTest {
            val exception = assertFails {
                coinGecko.getCoinById("not-a-real-coin")
            }

            assertTrue(
                exception is CoinGeckoApiException,
                "Expected CoinGeckoApiException but was ${exception::class}",
            )
            assertEquals(404, exception.error?.code)
            assertEquals("coin not found", exception.error?.message)
        }

        @Test
        fun testCoinOhlc() = runApiTest {
            val ohlc = coinGecko.getCoinOhlc("tezos", Currency.USD, 1).firstOrNull()

            assertNotNull(ohlc?.time)
            assertNotNull(ohlc?.close)
            assertNotNull(ohlc?.high)
            assertNotNull(ohlc?.low)
            assertNotNull(ohlc?.open)
        }

        @Test
        fun testTrending() = runApiTest {
            val trending = coinGecko.getTrending()

            assertNotNull(trending)
            assertNotNull(trending.coins)
            assertTrue { trending.coins.isNotEmpty() }
            val first = trending.coins.firstOrNull()?.item
            assertNotNull(first?.id)
            assertNotNull(first?.coinId)
            assertNotNull(first?.name)
            assertNotNull(first?.symbol)
            assertNotNull(first?.marketCapRank)
            assertNotNull(first?.thumb)
            assertNotNull(first?.small)
            assertNotNull(first?.large)
            assertNotNull(first?.slug)
            assertNotNull(first?.priceBtc)
            assertNotNull(first?.score)
        }

        @Test
        fun testAssetPlatforms() = runApiTest { await ->
            val assetPlatforms = coinGecko.getAssetPlatforms()

            val withoutId = assetPlatforms.filter { it.id.isEmpty() }
            assertTrue(withoutId.isEmpty(), "All platforms should include an id, ${withoutId.size} were missing")

            val hbar = assetPlatforms.singleOrNull { it.id == "hedera-hashgraph" }
            assertEquals("Hedera Hashgraph", hbar?.name)
            assertEquals(null, hbar?.chainIdentifier)
            assertEquals("hashgraph", hbar?.shortname)

            val poly = assetPlatforms.singleOrNull { it.id == "polygon-pos" }
            assertEquals("Polygon POS", poly?.name)
            assertEquals(137, poly?.chainIdentifier)
            assertEquals("MATIC", poly?.shortname)
        }*/

    private fun runApiTest(
        testBody: suspend TestScope.(await: suspend () -> Unit) -> Unit,
    ) = runTest(timeout = 30.seconds) {
        testLock.withLock { }
        testBody { testLock.withLock { } }
    }
}