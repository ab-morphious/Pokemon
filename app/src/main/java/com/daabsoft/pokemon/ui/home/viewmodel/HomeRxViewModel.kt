package com.daabsoft.pokemon.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.daabsoft.pokemon.data.local.entity.PokemonEntity
import com.daabsoft.pokemon.domain.repositories.PokemonRxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import javax.inject.Inject

@HiltViewModel
class HomeRxViewModel @Inject constructor(
    private val repository: PokemonRxRepository
) : ViewModel() {
    fun getAllPokemonsRx(): Flowable<PagingData<PokemonEntity>> {
        return repository
            .getPokemons()
            .cachedIn(viewModelScope)
    }
}
