package com.daabsoft.pokemon.data.remote.services

import com.daabsoft.pokemon.data.remote.dto.AllPokemonResponse
import com.daabsoft.pokemon.data.remote.dto.PokemonDetailDto
import com.daabsoft.pokemon.domain.models.PokemonDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    fun getAllPokemon(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Single<AllPokemonResponse>

    @GET("pokemon/{name}")
    fun getPokemonInfo(
        @Path("name") name: String
    ): Single<PokemonDetailDto>
}
