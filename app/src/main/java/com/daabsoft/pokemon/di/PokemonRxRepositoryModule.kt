package com.daabsoft.pokemon.di

import GetPokemonRxRemoteRepositoryImpl
import com.daabsoft.pokemon.data.datasource.GetPokemonsRxRemoteMediator
import com.daabsoft.pokemon.data.local.PokemonDatabase
import com.daabsoft.pokemon.domain.repositories.PokemonRxRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryRxModule {

    @Singleton
    @Provides
    fun providePokemonRxRepository(
        database: PokemonDatabase,
        pokemonRemoteRxMediator: GetPokemonsRxRemoteMediator
    ): PokemonRxRepository {
        return GetPokemonRxRemoteRepositoryImpl(database, pokemonRemoteRxMediator)
    }
}
