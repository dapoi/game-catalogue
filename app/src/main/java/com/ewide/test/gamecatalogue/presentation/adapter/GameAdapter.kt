package com.ewide.test.gamecatalogue.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ewide.test.gamecatalogue.data.source.remote.model.GamesResponse
import com.ewide.test.gamecatalogue.databinding.ItemListGameBinding

class GameAdapter(
    private var onClick: (GamesResponse) -> Unit
) : ListAdapter<GamesResponse, GameAdapter.GameViewHolder>(GameAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            ItemListGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = getItem(position)
        if (game != null) {
            holder.bind(game)
        }
    }

    inner class GameViewHolder(
        private val binding: ItemListGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(game: GamesResponse) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(game.thumb)
                    .into(ivGame)
                tvName.text = game.external
                tvPrices.text = game.cheapest

                itemView.setOnClickListener {
                    onClick(game)
                }
            }
        }
    }

    companion object : DiffUtil.ItemCallback<GamesResponse>() {
        override fun areItemsTheSame(oldItem: GamesResponse, newItem: GamesResponse): Boolean {
            return oldItem.gameID == newItem.gameID
        }

        override fun areContentsTheSame(oldItem: GamesResponse, newItem: GamesResponse): Boolean {
            return oldItem == newItem
        }
    }
}