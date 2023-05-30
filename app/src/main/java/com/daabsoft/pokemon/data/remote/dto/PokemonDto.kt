package com.daabsoft.pokemon.data.remote.dto

import com.daabsoft.pokemon.domain.models.Pokemon

data class AllPokemonResponse(
    val pokemons: List<PokemonDto>
)

data class PokemonDto(
    val name: String,
    val url: String
) {
    fun toDomain() = Pokemon(
        name,
        url
    )
}
