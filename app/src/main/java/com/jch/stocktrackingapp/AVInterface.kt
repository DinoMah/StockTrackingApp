// Copyright (c) 2022 Juan Carlos Herrera

package com.jch.stocktrackingapp

import retrofit2.Response
import retrofit2.http.*

interface AVInterface {
  @GET("/query?&fmt=json")
  suspend fun getStocks(
    @Query("function") function: String = "GLOBAL_QUOTE",
    @Query("symbol") symbol: String,
    @Query("apikey") key: String = "VJ856XHXT0I8GR7N"
  ): Response<StockResponse>


}