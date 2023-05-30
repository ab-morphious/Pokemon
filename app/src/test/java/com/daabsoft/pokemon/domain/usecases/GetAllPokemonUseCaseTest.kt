package com.daabsoft.pokemon.domain.usecases

import com.daabsoft.pokemon.core.TestSchedulerProvider
import com.daabsoft.pokemon.core.utils.RxJavaUncaughtErrorRule
import com.daabsoft.pokemon.data.FakePokemonRepository
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
}
