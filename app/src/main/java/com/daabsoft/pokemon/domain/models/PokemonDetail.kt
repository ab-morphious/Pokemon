package com.daabsoft.pokemon.domain.models

import com.daabsoft.pokemon.data.local.entity.PokemonDetailEntity

class PokemonDetail(
    val name: String,
    val height: Float,
    val weight: Float
)

fun PokemonDetail.toEntity() = PokemonDetailEntity(this.name, this.height, this.weight)
