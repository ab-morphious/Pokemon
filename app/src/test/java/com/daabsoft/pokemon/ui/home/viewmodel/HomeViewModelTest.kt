package com.daabsoft.pokemon.ui.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daabsoft.pokemon.core.Resource
import com.daabsoft.pokemon.core.TestSchedulerProvider
import com.daabsoft.pokemon.data.FakePokemonRepository
import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.domain.usecases.GetAllPokemonUseCase
import com.daabsoft.pokemon.ui.home.HomeViewModel
import com.daabsoft.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var homeViewModel: HomeViewModel
    private val schedulerProvider = TestSchedulerProvider()

    @Before
    fun setup() {
        val fakeRepository = FakePokemonRepository()
        val allPokemonUseCae = GetAllPokemonUseCase(fakeRepository)
        homeViewModel = HomeViewModel(
            allPokemonUseCae,
            schedulerProvider
        )
    }

    @Test
    fun `getAll pokemons, gives livedata of pokemons`() {
        val page = 1
        homeViewModel.getAllPokemons(page)
        val pokemons = homeViewModel.pokemons.getOrAwaitValue()
        assertThat((pokemons as Resource.Success<List<Pokemon>>).data.size > 0).isTrue()
    }

    @Test
    fun `getAll pokemons with invalid page, gives error response`() {
        val page = -1
        homeViewModel.getAllPokemons(page)
        val pokemons = homeViewModel.pokemons.getOrAwaitValue()
        assertThat((pokemons is Resource.Error<List<Pokemon>>)).isTrue()
    }

    @After fun tearDown() {
    }
}
