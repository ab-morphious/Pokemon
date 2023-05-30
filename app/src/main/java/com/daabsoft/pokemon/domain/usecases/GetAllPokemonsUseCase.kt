package com.daabsoft.pokemon.domain.usecases

import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.domain.repositories.PokemonRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAllPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Single<List<Pokemon>> {
        return repository.getAllPokemon()
    }
}