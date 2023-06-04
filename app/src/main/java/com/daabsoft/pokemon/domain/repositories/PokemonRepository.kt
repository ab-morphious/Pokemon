package com.daabsoft.pokemon.domain.repositories

import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.domain.models.PokemonDetail
import io.reactivex.Single

interface PokemonRepository {
    /**
     * get pokemon detail information
     * @param id
     */
    fun getPokemonDetail(name: String): Single<PokemonDetail>
}