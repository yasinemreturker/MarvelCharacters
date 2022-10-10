package com.example.marvelcharacters.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.databinding.PagingLoadStateBinding

class PagingLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<PagingLoadStateAdapter.PagingLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingLoadStateViewHolder {
        return PagingLoadStateViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    class PagingLoadStateViewHolder(
        private val binding: PagingLoadStateBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState, retry: () -> Unit) {
            binding.loadingProgress.isVisible = loadState is LoadState.Loading
            binding.errorText.isVisible = loadState is LoadState.Error
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.retryButton.setOnClickListener { retry.invoke() }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PagingLoadStateViewHolder {
                val binding = PagingLoadStateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return PagingLoadStateViewHolder(binding)
            }
        }
    }
}