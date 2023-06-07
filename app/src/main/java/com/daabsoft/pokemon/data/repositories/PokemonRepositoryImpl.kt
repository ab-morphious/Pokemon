package com.daabsoft.pokemon.data.repositories

import com.daabsoft.pokemon.core.BaseSchedulerProvider
import com.daabsoft.pokemon.data.local.dao.PokemonDao
import com.daabsoft.pokemon.data.remote.dto.toEntity
import com.daabsoft.pokemon.data.remote.services.PokemonService
import com.daabsoft.pokemon.domain.models.PokemonDetail
import com.daabsoft.pokemon.domain.models.toEntity
import com.daabsoft.pokemon.domain.repositories.PokemonRepository
import io.reactivex.Single
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonService,
    private val pokemonDao: PokemonDao,
    private val schedulerProvider: BaseSchedulerProvider
) : PokemonRepository {

    override fun getPokemonDetail(name: String): Single<PokemonDetail> {
        return pokemonApi.getPokemonInfo(name)
            .subscribeOn(schedulerProvider.io())
            .flatMap { pokemonDetail ->
                pokemonDao.insertPokemonDetail(pokemonDetail.toEntity())
                pokemonDao.getPokemonDetail(name)
            }
    }
}
