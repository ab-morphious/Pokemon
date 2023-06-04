package com.daabsoft.pokemon.ui.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.daabsoft.pokemon.R
import com.daabsoft.pokemon.databinding.LoadingItemBinding

class LoadingGridStateViewHolder(
    private val binding: LoadingItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup): LoadingGridStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_item, parent, false)

            val binding = LoadingItemBinding.bind(view)

            return LoadingGridStateViewHolder(
                binding
            )
        }
    }
}
