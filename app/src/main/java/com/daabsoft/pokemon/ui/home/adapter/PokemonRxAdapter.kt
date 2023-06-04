package com.daabsoft.pokemon.ui.home

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.ui.home.adapter.CellClickListener
import com.daabsoft.pokemon.ui.home.adapter.HomeGridViewHolder

class PokemonRxAdapter : PagingDataAdapter<Pokemon, HomeGridViewHolder>(
    POKEMON_COMPARATOR
) {
    private lateinit var cellClickListener: CellClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeGridViewHolder {
        return HomeGridViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeGridViewHolder, position: Int) {
        getItem(position)?.run {
            holder.bind(this)
            holder.itemView.setOnClickListener { cellClickListener.onCellClickListener(this) }
        }
    }

    fun setCellClickListener(cellClickListener: CellClickListener) {
        this.cellClickListener = cellClickListener
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
