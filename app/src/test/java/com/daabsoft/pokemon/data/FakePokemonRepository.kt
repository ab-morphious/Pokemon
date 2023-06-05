package com.daabsoft.pokemon.data

import com.daabsoft.pokemon.data.local.entity.StatElement
import com.daabsoft.pokemon.data.local.entity.StatStat
import com.daabsoft.pokemon.domain.models.PokemonDetail
import com.daabsoft.pokemon.domain.repositories.PokemonRepository
import io.reactivex.Single

/**
 * Fake repository that uses list instead of api call.
 */
class FakePokemonRepository : PokemonRepository {

    private val fakePokemon = PokemonDetail(
        "Fake Pokemon",
        1.75f,
        60.5f,
        listOf(
            StatElement(
                30,
                10,
                StatStat("hp", "fakeurl.com")
            )
        )
    )

    override fun getPokemonDetail(name: String): Single<PokemonDetail> {
        return Single.just(fakePokemon)
    }
}
