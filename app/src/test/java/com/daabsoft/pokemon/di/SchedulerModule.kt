package com.daabsoft.pokemon.di

import com.daabsoft.pokemon.core.BaseSchedulerProvider
import com.daabsoft.pokemon.core.TestSchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SchedulerModule {
    @Singleton
    @Provides
    @Named("testScheduler")
    fun provideScheduler(): BaseSchedulerProvider {
        return TestSchedulerProvider()
    }
}
