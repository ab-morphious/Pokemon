package com.daabsoft.pokemon.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daabsoft.pokemon.R
import com.daabsoft.pokemon.databinding.PokemonGridItemBinding
import com.daabsoft.pokemon.domain.models.Pokemon
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import javax.inject.Inject

class PokemonRxAdapter : PagingDataAdapter<Pokemon, HomeGridViewHolder>(
    POKEMON_COMPARATOR
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeGridViewHolder {
        return HomeGridViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeGridViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val POKEMON_COMPARATOR = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name && oldItem.url == oldItem.url
            }
        }
    }
}

class HomeGridViewHolder @Inject constructor(
    private val binding: PokemonGridItemBinding,
    private val context: Context
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon) {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(2000)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.8f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        with(pokemon) {
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }

            Glide.with(context)
                .load(pokemon.getImageMapped())
                .placeholder(shimmerDrawable)
                .into(binding.pokemonImage)
        }
    }

    companion object {
        fun create(parent: ViewGroup): HomeGridViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_grid_item, parent, false)

            val binding = PokemonGridItemBinding.bind(view)

            return HomeGridViewHolder(binding, view.context)
        }
    }
}
