package com.daabsoft.pokemon.data.remote.services

import com.daabsoft.pokemon.domain.models.Pokemon
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    fun getAllPokemon(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Single<List<Pokemon>>
}