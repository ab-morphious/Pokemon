package com.daabsoft.pokemon.data.repositories

import com.daabsoft.pokemon.data.remote.services.PokemonService
import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.domain.repositories.PokemonRepository
import io.reactivex.Single

class PokemonRepositoryImpl constructor(
    private val pokemonApi: PokemonService
) : PokemonRepository {
    override fun getAllPokemon(page: Int): Single<List<Pokemon>> {
        return pokemonApi.getAllPokemon(
            limit = PAGE_SIZE,
            offset = page * PAGE_SIZE
        )
    }
    companion object {
        private const val PAGE_SIZE = 20
    }
}
