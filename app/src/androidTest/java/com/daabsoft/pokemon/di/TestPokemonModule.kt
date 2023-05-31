package com.daabsoft.pokemon.di

import android.content.Context
import androidx.room.Room
import com.daabsoft.pokemon.data.local.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestPokemonModule {

    @Provides
    @Named("test_db_pokemon")
    fun providesTestPokemonDb(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(context, PokemonDatabase::class.java)
        .allowMainThreadQueries()
        .build()
}
