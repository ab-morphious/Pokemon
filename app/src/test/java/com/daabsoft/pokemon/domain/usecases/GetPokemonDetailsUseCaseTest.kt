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

class GetPokemonDetailsUseCaseTest {

    @get:Rule
    val uncaughtRxJavaErrors = RxJavaUncaughtErrorRule()
    private val schedulerProvider = TestSchedulerProvider()

    @Inject
    lateinit var fakePokemonRepository: FakePokemonRepository
    private lateinit var getPokemonDetailsUseCase: GetPokemonDetailsUseCase
    private val compositeDisposable = CompositeDisposable()

    @Before
    fun setup() {
        fakePokemonRepository = FakePokemonRepository()
        getPokemonDetailsUseCase = GetPokemonDetailsUseCase(fakePokemonRepository)
    }

    @Test
    fun `Get pokemon detail, returns correct pokemon`() {
        val subscribe = getPokemonDetailsUseCase.invoke("Fake Pokemon")
            .subscribeOn(schedulerProvider.io()).subscribe { pokemon ->
                assertThat(pokemon.name.equals("Fake Pokemon")).isTrue()
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
