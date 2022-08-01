// Copyright (c) 2022 Juan Carlos Herrera

package com.jch.stocktrackingapp

import com.squareup.moshi.Json

class StockResponse {
  @field:Json(name = "Global Quote")
  var stock: Stock? = null

  /*class GlobalQuote {
    val stock: List<Stock>? = null
  }*/
}