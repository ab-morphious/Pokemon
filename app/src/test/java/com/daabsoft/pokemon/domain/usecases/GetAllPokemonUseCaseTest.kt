package com.daabsoft.pokemon.domain.usecases

import com.daabsoft.pokemon.core.TestSchedulerProvider
import com.daabsoft.pokemon.core.utils.RxJavaUncaughtErrorRule
import com.daabsoft.pokemon.data.FakePokemonRepository
import com.daabsoft.pokemon.domain.models.Pokemon
import com.google.common.truth.Truth.assertThat
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class GetAllPokemonUseCaseTest {

    @get:Rule
    val uncaughtRxJavaErrors = RxJavaUncaughtErrorRule()
    private val schedulerProvider = TestSchedulerProvider()

    @Inject
    lateinit var fakePokemonRepository: FakePokemonRepository
    private lateinit var getAllPokemonUseCase: GetAllPokemonUseCase
    private val compositeDisposable = CompositeDisposable()

    @Before
    fun setup() {
        fakePokemonRepository = FakePokemonRepository()
        getAllPokemonUseCase = GetAllPokemonUseCase(fakePokemonRepository)
    }

    @Test
    fun `Get all pokemon, returns correct pokemons`() {
        val subscribe = getAllPokemonUseCase.invoke(0)
            .subscribeOn(schedulerProvider.io()).subscribe { pokemons ->
                assertThat(pokemons.size == 0).isTrue()
            }
    }

    @Test
    fun `Given correct pokemon url, image mapping works correctly`() {
        val correctImageUrl = "https://raw.githubusercontent" +
            ".com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png"
        val pokemon = Pokemon(
            "charmander",
            "https://pokeapi.co/api/v2/pokemon/4/"
        )
        assertThat(pokemon.getImageMapped().equals(correctImageUrl)).isTrue()
    }

    @Test
    fun `Given wrong pokemon url, image mapping fails`() {
        val correctImageUrl = "https://raw.githubusercontent" +
            ".com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png"
        val pokemon = Pokemon(
            "charmander",
            "https://pokeapi.co/api/v2/pokemon4/"
        )
        assertThat(pokemon.getImageMapped().equals(correctImageUrl)).isFalse()
    }
}
