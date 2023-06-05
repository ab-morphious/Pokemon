package com.daabsoft.pokemon.data.remote.dto

import com.daabsoft.pokemon.data.local.entity.PokemonDetailEntity
import com.google.gson.annotations.SerializedName

data class PokemonDetailDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Float,
    @SerializedName("weight")
    val weight: Float,
    @SerializedName("stats")
    val stats: List<StatElement>
)

data class StatElement(
    @SerializedName("base_stat")
    val baseStat: Long,
    @SerializedName("effort")
    val effort: Long,
    val stat: StatStat
) {
    fun toEntity(): com.daabsoft.pokemon.data.local.entity.StatElement {
        return com.daabsoft.pokemon.data.local.entity.StatElement(baseStat, effort, stat.toEntity())
    }
}

data class StatStat(
    val name: String,
    val url: String
) {
    fun toEntity(): com.daabsoft.pokemon.data.local.entity.StatStat {
        return com.daabsoft.pokemon.data.local.entity.StatStat(name, url)
    }
}

fun PokemonDetailDto.toEntity() = PokemonDetailEntity(this.name, this.height, this.weight, stats.map { it.toEntity() })
