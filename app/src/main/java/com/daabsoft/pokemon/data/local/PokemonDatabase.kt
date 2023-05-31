package com.daabsoft.pokemon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daabsoft.pokemon.data.local.dao.PokemonDao
import com.daabsoft.pokemon.data.local.entity.PokemonEntity

@Database(
    entities = [
        PokemonEntity::class
    ],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        const val DB_NAME = "pokemon_db"
    }
}
