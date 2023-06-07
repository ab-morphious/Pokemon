package com.daabsoft.pokemon.domain.repositories

import com.daabsoft.pokemon.domain.models.PokemonDetail
import io.reactivex.Single

interface PokemonRepository {
    /**
     * get pokemon detail information
     * @param name
     */
    fun getPokemonDetail(name: String): Single<PokemonDetail>
}
