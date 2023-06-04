package com.daabsoft.pokemon.di

import com.daabsoft.pokemon.core.BaseSchedulerProvider
import com.daabsoft.pokemon.data.datasource.GetPokemonsRxRemoteMediator
import com.daabsoft.pokemon.data.local.PokemonDatabase
import com.daabsoft.pokemon.data.remote.services.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PokemonRxMediatorModule {

    @Provides
    fun provideRxMediatorModule(
        service: PokemonService,
        database: PokemonDatabase,
        schedulerProvider: BaseSchedulerProvider
    ): GetPokemonsRxRemoteMediator {
        return GetPokemonsRxRemoteMediator(service, database, schedulerProvider)
    }
}
