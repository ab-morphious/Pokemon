package com.daabsoft.pokemon.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.daabsoft.pokemon.core.BaseSchedulerProvider
import com.daabsoft.pokemon.data.local.dao.PokemonDao
import com.daabsoft.pokemon.data.local.dao.PokemonRxDao
import com.daabsoft.pokemon.data.local.entity.PokemonEntity
import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.domain.models.PokemonDetail
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class PokemonDaoTest {

    @get:Rule
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db_pokemon")
    lateinit var pokemonDatabase: PokemonDatabase

    @Inject
    @Named("test_scheduler_provider")
    lateinit var testSchedulerProvider: BaseSchedulerProvider

    private lateinit var pokemonDao: PokemonDao
    private lateinit var pokemonRxDao: PokemonRxDao
    private val disposable = CompositeDisposable()

    @Before
    fun setup() {
        hiltTestRule.inject()
        pokemonDao = pokemonDatabase.pokemonDao()
        pokemonRxDao = pokemonDatabase.pokemonRxDao()
    }

    @After
    fun tearDown() {
        pokemonDatabase.close()
        disposable.dispose()
    }

    @Test
    fun insertNewPokemonShouldWork() {
        val pokemon1 = PokemonEntity(
            1,
            name = "Pokeee",
            url = "test url"
        )
        val pokemon2 = PokemonEntity(
            1,
            name = "Pokeeemon 2",
            url = "test url"
        )

        assertThat(pokemonRxDao.insertAll(listOf(pokemon1, pokemon2)).size > 0).isTrue()
    }
}
