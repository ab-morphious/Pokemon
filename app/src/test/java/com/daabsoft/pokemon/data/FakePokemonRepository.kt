package com.daabsoft.pokemon.data

import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.domain.repositories.PokemonRepository
import io.reactivex.Single

/**
 * Fake repository that uses list instead of api call.
 */
class FakePokemonRepository : PokemonRepository {

    private val fakePokemon1 = Pokemon(
        name = "Fake Pokemon 1",
        url = "Fake url 1"
    )

    private val fakePokemon2 = Pokemon(
        name = "Fake Pokemon 2",
        url = "Fake url 2"
    )

    private val fakePokemonList = listOf(fakePokemon1, fakePokemon2)

    override fun getAllPokemon(page: Int): Single<List<Pokemon>> {
        return Single.just(fakePokemonList)
    }
}
