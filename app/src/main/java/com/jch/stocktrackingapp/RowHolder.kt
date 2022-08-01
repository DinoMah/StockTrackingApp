// Copyright (c) 2022 Juan Carlos Herrera

package com.jch.stocktrackingapp

import androidx.recyclerview.widget.RecyclerView
import com.jch.stocktrackingapp.databinding.StockRowBinding

class RowHolder(private val binding: StockRowBinding) :
  RecyclerView.ViewHolder(binding.root) {

    fun bind(state: StockRow) {
      binding.state = state
      binding.executePendingBindings()
    }
}