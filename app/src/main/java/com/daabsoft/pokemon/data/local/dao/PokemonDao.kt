package com.daabsoft.pokemon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daabsoft.pokemon.data.local.entity.PokemonDetailEntity
import com.daabsoft.pokemon.domain.models.PokemonDetail
import io.reactivex.Single

@Dao
interface PokemonDao {

    /**
     * Insert pokemon details to pokemon_detail db
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonDetail(pokemonDetailEntity: PokemonDetailEntity): Long

    /**
     * Get pokemon details from db
     */
    @Query("SELECT * FROM pokemon_detail WHERE name = :name")
    fun getPokemonDetail(name: String): Single<PokemonDetail>
}
