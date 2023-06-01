package com.daabsoft.pokemon.data.repositories

import com.daabsoft.pokemon.core.BaseSchedulerProvider
import com.daabsoft.pokemon.data.local.dao.PokemonDao
import com.daabsoft.pokemon.data.remote.services.PokemonService
import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.domain.repositories.PokemonRepository
import io.reactivex.Single

class PokemonRepositoryImpl constructor(
    private val pokemonApi: PokemonService,
    private val pokemonDao: PokemonDao,
    private val schedulerProvider: BaseSchedulerProvider
) : PokemonRepository {
    override fun getAllPokemon(page: Int): Single<List<Pokemon>> {
        var pokemons = listOf<Pokemon>()

        pokemonDao.getPokemons(page)
            .subscribeOn(schedulerProvider.io())
            .subscribe {
                pokemons = it.map { it.toDomain() }
            }

        if (pokemons.isNotEmpty()) {
            return Single.just(pokemons)
        }

        return pokemonApi.getAllPokemon(
            limit = PAGE_SIZE,
            offset = page * PAGE_SIZE
        ).flatMap {
            val pokemons = it.pokemons
            pokemonDao.insertPokemons(pokemons.map { it.toDomain().toEntitiy(page) })
            Single.just(pokemons.map { it.toDomain() })
        }.doOnError {
            throw (it)
        }
    }
    companion object {
        private const val PAGE_SIZE = 20
    }
}
