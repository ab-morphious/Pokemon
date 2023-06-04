package com.daabsoft.pokemon.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daabsoft.pokemon.R
import com.daabsoft.pokemon.databinding.PokemonGridItemBinding
import com.daabsoft.pokemon.domain.models.Pokemon
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import java.util.*
import javax.inject.Inject

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

        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

        Glide.with(context)
            .load(pokemon.getImageMapped())
            .placeholder(shimmerDrawable)
            .into(binding.pokemonImage)

        binding.pokemonName.setText(pokemon.name.capitalize())
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

interface CellClickListener {
    fun onCellClickListener(pokemon: Pokemon)
}
