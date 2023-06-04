package com.daabsoft.pokemon.ui.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daabsoft.pokemon.core.Resource
import com.daabsoft.pokemon.core.TestSchedulerProvider
import com.daabsoft.pokemon.data.FakePokemonRepository
import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.domain.usecases.GetPokemonDetailsUseCase
import com.daabsoft.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var detailViewModel: DetailViewModel
    private val schedulerProvider = TestSchedulerProvider()

    @Before
    fun setup() {
        val fakeRepository = FakePokemonRepository()
        val allPokemonUseCae = GetPokemonDetailsUseCase(fakeRepository)
        detailViewModel = DetailViewModel(
            allPokemonUseCae,
            schedulerProvider
        )
    }

    @Test
    fun `getAll pokemons, gives livedata of pokemons`() {
        val page = 1
        detailViewModel.getAllPokemons(page)
        val pokemons = detailViewModel.pokemons.getOrAwaitValue()
        assertThat((pokemons as Resource.Success<List<Pokemon>>).data.size > 0).isTrue()
    }

    @Test
    fun `getAll pokemons with invalid page, gives error response`() {
        val page = -1
        detailViewModel.getAllPokemons(page)
        val pokemons = detailViewModel.pokemons.getOrAwaitValue()
        assertThat((pokemons is Resource.Error<List<Pokemon>>)).isTrue()
    }

    @After fun tearDown() {
    }
}
