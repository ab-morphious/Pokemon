package com.daabsoft.pokemon.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.daabsoft.pokemon.domain.models.Pokemon

@Entity(tableName = "pokemon")
data class PokemonEntity(
    val page: Int,
    @PrimaryKey
    val name: String,
    val url: String
) {

    @Entity(tableName = "movie_remote_keys")
    data class PokemonRemoteKeys(
        @PrimaryKey val page: Int,
        val prevKey: Int?,
        val nextKey: Int?
    )

    fun toDomain(): Pokemon = Pokemon(
        name,
        url
    )
}
