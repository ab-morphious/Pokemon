package com.daabsoft.pokemon.di

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
        apiService: PokemonService
    ): PokemonRepository {
        return PokemonRepositoryImpl(apiService)
    }
}
