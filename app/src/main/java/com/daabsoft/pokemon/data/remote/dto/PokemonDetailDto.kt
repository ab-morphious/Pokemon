package com.daabsoft.pokemon.data.remote.dto

import com.daabsoft.pokemon.data.local.entity.PokemonDetailEntity
import com.google.gson.annotations.SerializedName

data class PokemonDetailDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Float,
    @SerializedName("weight")
    val weight: Float
)

fun PokemonDetailDto.toEntity() = PokemonDetailEntity(this.name, this.height, this.weight)
