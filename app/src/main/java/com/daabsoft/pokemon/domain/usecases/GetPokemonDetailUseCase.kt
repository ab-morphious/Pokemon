package com.daabsoft.pokemon.domain.usecases

import com.daabsoft.pokemon.domain.models.PokemonDetail
import com.daabsoft.pokemon.domain.repositories.PokemonRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(name: String): Single<PokemonDetail> {
        return repository.getPokemonDetail(name)
    }
}
