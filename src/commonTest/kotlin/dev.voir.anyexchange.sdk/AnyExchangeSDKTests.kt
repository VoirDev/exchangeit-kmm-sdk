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

        assertEquals(true, currencies.meta.total > 0)
        assertEquals(true, currencies.data.isNotEmpty())

        assertEquals(true, crypto.meta.total > 0)
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

    private fun runApiTest(
        testBody: suspend TestScope.(await: suspend () -> Unit) -> Unit,
    ) = runTest(timeout = 30.seconds) {
        testLock.withLock { }
        testBody { testLock.withLock { } }
    }
}
