package com.ewide.test.gamecatalogue.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ewide.test.gamecatalogue.data.source.local.model.DetailEntity
import com.ewide.test.gamecatalogue.databinding.ItemListGameBinding

class FavoriteAdapter(
    private val onClick: (DetailEntity) -> Unit
) : ListAdapter<DetailEntity, FavoriteAdapter.FavoriteViewHolder>(FavoriteAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemListGameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteViewHolder(
        private val binding: ItemListGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailEntity) {
            binding.apply {
                Glide.with(itemView.context).load(data.image).into(ivGame)
                tvName.text = data.gameName
                tvPrices.text = data.price
            }
        }

        init {
            binding.root.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }
    }

    companion object : DiffUtil.ItemCallback<DetailEntity>() {
        override fun areItemsTheSame(oldItem: DetailEntity, newItem: DetailEntity): Boolean {
            return oldItem.gameID == newItem.gameID
        }

        override fun areContentsTheSame(oldItem: DetailEntity, newItem: DetailEntity): Boolean {
            return oldItem == newItem
        }
    }
}