package dev.voir.exchangeit.sdk

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration.Companion.seconds

@OptIn(DelicateCoroutinesApi::class)
class ExchangeItSDKTests {
    private val sdk = ExchangeItSDKFactory.create()
    private val testLock = Mutex()

    init {
        GlobalScope.launch {
            while (true) {
                testLock.withLock { delay(5.seconds) }
            }
        }
    }

    // TODO Disable testing for now, since iosX64 tests could not be passed
    // https://youtrack.jetbrains.com/issue/KTOR-5158/Darwin-certificate-is-invalid-error-when-making-requests-through-HTTPS-in-tests
    /*@Test
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
    fun `test get currency detailed`() = runApiTest {
        val currency = sdk.getCurrencyDetailed(base = "usd")
        assertEquals("usd", currency.data.code)
        assertEquals("United States Dollar", currency.data.title)
        assertEquals("Доллар США", currency.data.localizedTitle["ru"])

        val notFound = kotlin.runCatching { sdk.getCurrencyDetailed(base = "usdusdusd") }
        assertEquals(true, notFound.isFailure)
        assertEquals("ExchangeItSDKError(code=404, message=Not Found)", notFound.exceptionOrNull()?.message)
    }

    @Test
    fun `test get latest rates`() = runApiTest {
        val latestRates = sdk.getLatestRates(base = "rub", codes = null)

        assertEquals("rub", latestRates.data.code)
        assertEquals(true, latestRates.data.rates.isNotEmpty())

        val latestRatesByCodes = sdk.getLatestRates(codes = listOf("rub", "aed", "usd"))
        assertEquals(3, latestRatesByCodes.data.size)
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
    }*/
}
