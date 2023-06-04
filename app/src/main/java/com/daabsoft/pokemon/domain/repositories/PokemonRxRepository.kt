package com.daabsoft.pokemon.domain.repositories

import androidx.paging.PagingData
import com.daabsoft.pokemon.data.local.entity.PokemonEntity
import io.reactivex.Flowable

interface PokemonRxRepository {
    fun getPokemons(): Flowable<PagingData<PokemonEntity>>
}