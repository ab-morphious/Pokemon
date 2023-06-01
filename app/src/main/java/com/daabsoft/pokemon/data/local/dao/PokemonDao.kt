package com.daabsoft.pokemon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daabsoft.pokemon.data.local.entity.PokemonEntity
import io.reactivex.Flowable

@Dao
interface PokemonDao {

    /**
     * Insert pokemon to pokemon db
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemons(pokemonEntity: List<PokemonEntity>): List<Long>

    /**
     * Get list of pokemons from db
     */
    @Query("SELECT * FROM pokemon WHERE page = :page")
    fun getPokemons(page: Int): Flowable<List<PokemonEntity>>
}
