package com.daabsoft.pokemon.data.remote.dto

import com.daabsoft.pokemon.domain.models.Pokemon
import com.google.gson.annotations.SerializedName

data class AllPokemonResponse(
    @SerializedName("results")
    val pokemons: List<PokemonDto>,
    @SerializedName("count")
    val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?
)

data class PokemonDto(
    val name: String,
    val url: String
) {
    fun toDomain() = Pokemon(
        name = name,
        url = url
    )
}
