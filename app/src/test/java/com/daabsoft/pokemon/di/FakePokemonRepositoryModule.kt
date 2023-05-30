package com.daabsoft.pokemon.di

import com.daabsoft.pokemon.data.FakePokemonRepository
import com.daabsoft.pokemon.domain.repositories.PokemonRepository
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Singleton
@InstallIn(SingletonComponent::class)
object FakePokemonRepositoryModule {

    @Singleton
    @Provides
    fun provideFakePokemonRepository(): PokemonRepository {
        return FakePokemonRepository()
    }
}
