package com.daabsoft.pokemon.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.daabsoft.pokemon.domain.models.Pokemon

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val name: String,
    val url: String,
    var imageUrl: String?
) {
    fun toDomain(): Pokemon = Pokemon(
        name,
        url
    )
}
