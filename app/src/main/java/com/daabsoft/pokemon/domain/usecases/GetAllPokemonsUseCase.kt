package com.daabsoft.pokemon.domain.usecases

import androidx.paging.PagingData
import com.daabsoft.pokemon.data.local.entity.PokemonEntity
import com.daabsoft.pokemon.domain.repositories.PokemonRxRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetAllPokemonsUseCase @Inject constructor(
    private val repository: PokemonRxRepository
) {
    operator fun invoke(): Flowable<PagingData<PokemonEntity>> {
        return repository.getPokemons()
    }
}
