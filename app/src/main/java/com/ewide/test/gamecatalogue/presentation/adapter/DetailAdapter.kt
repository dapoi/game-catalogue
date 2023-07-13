package com.ewide.test.gamecatalogue.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ewide.test.gamecatalogue.data.source.remote.model.DealsItem
import com.ewide.test.gamecatalogue.databinding.ItemListPriceBinding
import java.text.DecimalFormat

class DetailAdapter : ListAdapter<DealsItem, DetailAdapter.PriceViewHolder>(DetailAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        return PriceViewHolder(
            ItemListPriceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class PriceViewHolder(
        private val binding: ItemListPriceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: DealsItem) {
            binding.apply {
                tvStore.text = "Store: ${item.storeID}"
                tvPrice.text = "Price: ${item.price}"
                tvRetailPrice.text = "Retail Price: ${item.retailPrice}"
                val floatValue = item.savings?.toFloat()
                val percent = floatValue?.times(100)
                val format = DecimalFormat("##.##")
                val formattedPercent = format.format(percent)
                tvSaving.text = "Savings: $formattedPercent%"
            }
        }
    }

    companion object : DiffUtil.ItemCallback<DealsItem>() {
        override fun areItemsTheSame(oldItem: DealsItem, newItem: DealsItem): Boolean {
            return oldItem.storeID == newItem.storeID
        }

        override fun areContentsTheSame(oldItem: DealsItem, newItem: DealsItem): Boolean {
            return oldItem == newItem
        }
    }
}