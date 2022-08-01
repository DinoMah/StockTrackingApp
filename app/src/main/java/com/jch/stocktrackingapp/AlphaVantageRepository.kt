// Copyright (c) 2022 Juan Carlos Herrera

package com.jch.stocktrackingapp

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object AlphaVantageRepository {
  private val moshi = Moshi.Builder().build()
  private val api = Retrofit.Builder()
    .baseUrl("https://www.alphavantage.co")
    .client(OkHttpClient())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()
    .create(AVInterface::class.java)

  suspend fun getStocks(symbol: String) = try {
    val response = api.getStocks(symbol = symbol)
    if (response.isSuccessful) {
      if (response.body()!!.stock == null) {
        StockResult.Error(Throwable("Symbol name cannot be empty, try again"))
      }
      else {
        StockResult.Content(mutableListOf(response.body()!!.stock!!) ?: mutableListOf())
      }
    }
    else {
      println("Couldn't get response from server")
      StockResult.Error(Throwable(response.errorBody().toString()))
    }
  }
  catch (t: Throwable) {
    StockResult.Error(t)
  }
}