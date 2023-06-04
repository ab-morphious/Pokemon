package com.daabsoft.pokemon.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_detail")
data class PokemonDetailEntity(
    @PrimaryKey
    val name: String,
    val height: Float,
    val weight: Float
)
