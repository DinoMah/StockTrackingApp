// Copyright (c) 2022 Juan Carlos Herrera

package com.jch.stocktrackingapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
  @Test
  fun useAppContext() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("com.jch.stocktrackingapp", appContext.packageName)
  }

  /*@Test
  fun testRetroF() = runTest {
    var expectedResult = StockResult.Content(Stock("GOOG", 116.6400, 116.9000, 113.2300))

    val response = getStocks()

    when (response) {
      is StockResult.Content -> {
        assertEquals(expectedResult, response)
      }
      is StockResult.Error -> {
        assertEquals(0, 1)
      }
    }


  }

  suspend fun getStocks(): StockResult {
    return AlphaVantageRepository.getStocks("")
  }*/
}