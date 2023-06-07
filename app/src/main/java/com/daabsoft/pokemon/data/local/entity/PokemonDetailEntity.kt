package com.daabsoft.pokemon.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.daabsoft.pokemon.domain.models.PokemonDetail

@Entity(tableName = "pokemon_detail")
data class PokemonDetailEntity(
    @PrimaryKey
    val name: String,
    val height: Float,
    val weight: Float,
    val stats: List<StatElement>
) {
    fun toDomain() = PokemonDetail(name, height, weight, stats)
}

data class StatElement(
    val baseStat: Long,
    val effort: Long,
    val stat: StatStat
)

data class StatStat(
    val name: String,
    val url: String
)
