// Copyright (c) 2022 Juan Carlos Herrera

package com.jch.stocktrackingapp

sealed class StockResult {
  data class Content(val stocks: MutableList<Stock>):StockResult()
  data class Error(val throwable:Throwable):StockResult()
}