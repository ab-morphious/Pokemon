package com.daabsoft.pokemon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.daabsoft.pokemon.data.local.converters.ArrayListConverter
import com.daabsoft.pokemon.data.local.dao.PokemonDao
import com.daabsoft.pokemon.data.local.dao.PokemonRemoteKeysRxDao
import com.daabsoft.pokemon.data.local.dao.PokemonRxDao
import com.daabsoft.pokemon.data.local.entity.PokemonDetailEntity
import com.daabsoft.pokemon.data.local.entity.PokemonEntity

@Database(
    entities = [
        PokemonEntity::class,
        PokemonEntity.PokemonRemoteKeys::class,
        PokemonDetailEntity::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(ArrayListConverter::class)

abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    abstract fun pokemonRxDao(): PokemonRxDao

    abstract fun pokemonRemoteKeysRxDao(): PokemonRemoteKeysRxDao

    companion object {
        const val DB_NAME = "pokemon_db"
    }
}
