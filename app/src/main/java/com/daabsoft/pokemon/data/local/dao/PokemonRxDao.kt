package com.daabsoft.pokemon.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daabsoft.pokemon.data.local.entity.PokemonEntity

@Dao
interface PokemonRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemons: List<PokemonEntity>): List<Long>

    @Query("SELECT * FROM pokemon")
    fun selectAll(): PagingSource<Int, PokemonEntity>

    @Query("DELETE FROM pokemon")
    fun clearPokemons()
}

@Dao
interface PokemonRemoteKeysRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<PokemonEntity.PokemonRemoteKeys>)

    @Query("SELECT * FROM movie_remote_keys WHERE page = :page")
    fun remoteKeysByPage(page: Int): PokemonEntity.PokemonRemoteKeys?

    @Query("DELETE FROM movie_remote_keys")
    fun clearRemoteKeys()
}
