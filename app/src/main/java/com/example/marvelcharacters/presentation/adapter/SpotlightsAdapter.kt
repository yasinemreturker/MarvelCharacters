package com.example.marvelcharacters.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.data.model.CharacterSpotlight
import com.example.marvelcharacters.databinding.ItemSpotlightBinding

class SpotlightsAdapter  : ListAdapter<CharacterSpotlight, SpotlightsAdapter.SpotlightsViewHolder>(
    spotlightDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotlightsViewHolder {
        return SpotlightsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SpotlightsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

        class SpotlightsViewHolder(private val binding: ItemSpotlightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CharacterSpotlight) {
            binding.spotlight = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SpotlightsViewHolder {
                val binding = ItemSpotlightBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SpotlightsViewHolder(binding)
            }
        }
    }

    companion object {
        private val spotlightDiffUtil = object : DiffUtil.ItemCallback<CharacterSpotlight>() {
            override fun areItemsTheSame(
                oldItem: CharacterSpotlight,
                newItem: CharacterSpotlight
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CharacterSpotlight,
                newItem: CharacterSpotlight
            ) = oldItem == newItem
        }
    }
}