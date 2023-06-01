package com.daabsoft.pokemon.di

import com.daabsoft.pokemon.core.BaseSchedulerProvider
import com.daabsoft.pokemon.core.SchedulerProvider
import com.daabsoft.pokemon.data.local.dao.PokemonDao
import com.daabsoft.pokemon.data.remote.services.PokemonService
import com.daabsoft.pokemon.data.repositories.PokemonRepositoryImpl
import com.daabsoft.pokemon.domain.repositories.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        apiService: PokemonService,
        pokemonDao: PokemonDao,
        scheduler: BaseSchedulerProvider
    ): PokemonRepository {
        return PokemonRepositoryImpl(apiService, pokemonDao, scheduler)
    }
}
