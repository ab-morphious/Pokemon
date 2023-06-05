package com.daabsoft.pokemon.domain.models

import com.daabsoft.pokemon.data.local.entity.PokemonDetailEntity
import com.daabsoft.pokemon.data.local.entity.StatElement

class PokemonDetail(
    val name: String,
    val height: Float,
    val weight: Float,
    val stats: List<StatElement>
)

fun PokemonDetail.toEntity() = PokemonDetailEntity(this.name, this.height, this.weight, stats)
