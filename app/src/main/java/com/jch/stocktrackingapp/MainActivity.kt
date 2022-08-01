// Copyright (c) 2022 Juan Carlos Herrera

package com.jch.stocktrackingapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jch.stocktrackingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var motor : MainMotor

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val adapter = StockAdapter(layoutInflater)


    val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
    val jsonPrefs = sharedPref.getString(getString(R.string.savedStocks), "")

    val rows : MutableList<StockRow>? = Gson().fromJson(jsonPrefs, object : TypeToken<MutableList<StockRow>>(){}.type)
    motor = if (rows != null) {
      MainMotor(this.application, rows)
    } else {
      MainMotor(this.application, mutableListOf())
    }

    val stockList = (motor.results.value as? MainViewState.Content)?.stocks

    rows?.forEach { row -> stockList?.add(row) }

    adapter.submitList(rows)
    adapter.notifyDataSetChanged()

    binding.addNewStockButton.setOnClickListener { showAddScreen() }

    binding.stockList.layoutManager = LinearLayoutManager(this)
    binding.stockList.addItemDecoration(
      DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
    )
    binding.stockList.adapter = adapter

    motor.results.observe(this) { state ->
      when (state) {
        MainViewState.Loading -> {

        }
        is MainViewState.Content -> {
          adapter.submitList(state.stocks)
          adapter.notifyDataSetChanged()
          val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
          with (sharedPref.edit()) {
              val data = Gson().toJson(state.stocks)
              println(data)
              putString(getString(R.string.savedStocks), data)
              apply()
          }

        }
        is MainViewState.Error -> {
          Toast.makeText(this, state.throwable.localizedMessage, Toast.LENGTH_LONG).show()
          println(state.throwable.localizedMessage)
        }
      }
    }
  }

  private fun showAddScreen() {
    val inflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view = inflater.inflate(R.layout.add_stock_symbol, null)
    val frame = findViewById<FrameLayout>(R.id.addNewStockLayout)

    val okButton = view.findViewById(R.id.okButton) as Button
    okButton.setOnClickListener { _ ->
      // newStockVM.newStock.symbol = parent.findViewById<EditText>(R.id.symbolText).text.toString()
      val symbol = view.findViewById<EditText>(R.id.symbolText).text.toString()
      motor.getStocks(symbol)
      frame.removeView(view)
    }


    frame.addView(view, 0)
    // frame.removeView(view)


    // val addScreen = findViewById(R.layout.add_stock_symbol) as ConstraintLayout

  }
}