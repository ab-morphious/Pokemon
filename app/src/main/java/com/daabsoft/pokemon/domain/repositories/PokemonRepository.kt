package com.daabsoft.pokemon.domain.repositories

import com.daabsoft.pokemon.domain.models.Pokemon
import io.reactivex.Single

interface PokemonRepository {
    /**
     * Fetch paginated pokemon list
     */
    fun getAllPokemon(page: Int): Single<List<Pokemon>>
}