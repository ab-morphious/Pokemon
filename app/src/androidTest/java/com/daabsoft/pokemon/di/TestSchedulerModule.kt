package com.daabsoft.pokemon.di

import com.daabsoft.pokemon.core.BaseSchedulerProvider
import com.daabsoft.pokemon.core.SchedulerProvider
import com.daabsoft.pokemon.core.TestSchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestSchedulerModule {
    @Singleton
    @Provides
    @Named("test_scheduler_provider")
    fun provideScheduler(): BaseSchedulerProvider {
        return TestSchedulerProvider()
    }
}