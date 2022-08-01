// Copyright (c) 2022 Juan Carlos Herrera

package com.jch.stocktrackingapp

sealed class MainViewState {
  object Loading : MainViewState()
  data class Content(var stocks: MutableList<StockRow>) : MainViewState()
  data class Error(val throwable: Throwable) : MainViewState()
}