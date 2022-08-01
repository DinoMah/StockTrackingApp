// Copyright (c) 2022 Juan Carlos Herrera

package com.jch.stocktrackingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jch.stocktrackingapp.databinding.StockRowBinding

class StockAdapter(layoutInflater: LayoutInflater) : ListAdapter<StockRow, RowHolder>(RowStateDiffer) {
  val inflater = layoutInflater

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
    return RowHolder(
      StockRowBinding.inflate(inflater, parent, false)
    )
  }

  override fun onBindViewHolder(holder: RowHolder, position: Int) {
    holder.bind(getItem(position))
  }

  object RowStateDiffer : DiffUtil.ItemCallback<StockRow>() {
    override fun areItemsTheSame(oldItem: StockRow, newItem: StockRow): Boolean {
      return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: StockRow, newItem: StockRow): Boolean {
      return oldItem == newItem
    }
  }
}