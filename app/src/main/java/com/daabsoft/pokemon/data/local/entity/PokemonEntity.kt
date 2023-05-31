package com.daabsoft.pokemon.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.daabsoft.pokemon.domain.models.Pokemon

@Entity(tableName = "pokemon", indices = [Index(value = ["name"], unique = true)])
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val url: String,
    val imageUrl: String
) {
    fun toDomain(): Pokemon = Pokemon(
        id,
        name,
        url,
        imageUrl
    )
}
