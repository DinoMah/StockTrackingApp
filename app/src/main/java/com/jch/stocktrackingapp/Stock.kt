// Copyright (c) 2022 Juan Carlos Herrera

package com.jch.stocktrackingapp

import com.squareup.moshi.Json

data class Stock(
  @field:Json(name = "01. symbol") val symbol:String,
  @field:Json(name = "05. price") val price: String,
  @field:Json(name = "03. high") val high:String,
  @field:Json(name = "04. low") val low:String
)