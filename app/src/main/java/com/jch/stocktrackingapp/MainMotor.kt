// Copyright (c) 2022 Juan Carlos Herrera

package com.jch.stocktrackingapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class MainMotor(app: Application, val initialVals: MutableList<StockRow>) : AndroidViewModel(app){
  private val _results = MutableLiveData<MainViewState>()
  val results: LiveData<MainViewState> = _results

  fun getStocks(symbol: String) {
    _results.value = MainViewState.Content(initialVals)
    val stockList = (_results.value as? MainViewState.Content)?.stocks ?: mutableListOf()
    _results.value = MainViewState.Loading


    viewModelScope.launch(Dispatchers.Main) {
      var result = AlphaVantageRepository.getStocks(symbol)
      when (result) {
        is StockResult.Content -> {
          val symbol = result.stocks[0].symbol

          if (symbol.isNullOrEmpty()) {
            result = StockResult.Error(Throwable("Stock Not Found"))
          }
        }
        is StockResult.Error -> {}
      }

      _results.value = when (result) {
        is StockResult.Content -> {

          val row = result.stocks.map { stock -> fillRow(stock) } as MutableList<StockRow>
          stockList.add(row[0])
          MainViewState.Content(stockList)
        }
        is StockResult.Error -> MainViewState.Error(result.throwable)
      }
    }
  }

  fun fillRow(stock: Stock) : StockRow {
    var symbol = getApplication<Application>()
      .getString(R.string.symbol, stock.symbol)
    if (symbol.length >= 4) symbol = symbol.substring(0, 4)
    val price = getApplication<Application>()
      .getString(R.string.price, formatNumber(stock.price))
    val high = getApplication<Application>()
      .getString(R.string.high, formatNumber(stock.high))
    val low = getApplication<Application>()
      .getString(R.string.low, formatNumber(stock.low))
    return StockRow(symbol, price, high, low)
  }

  private fun formatNumber(number: String) : String {
    val decFormat = DecimalFormat("###,###.00")
    return decFormat.format(number.toDouble())
  }
}