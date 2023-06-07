package com.daabsoft.pokemon.ui.home.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import com.daabsoft.pokemon.core.BaseSchedulerProvider
import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.domain.repositories.PokemonRxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class HomeRxViewModel @Inject constructor(
    private val repository: PokemonRxRepository,
    private val schedulerProvider: BaseSchedulerProvider
) : ViewModel() {

    private val _pokemonPagingData: MutableLiveData<PagingData<Pokemon>> = MutableLiveData()
    val pokemonPagingData: LiveData<PagingData<Pokemon>>
        get() = _pokemonPagingData

    val compositeDisposable = CompositeDisposable()

    init {
        getAllPokemonsRx()
    }

    fun getAllPokemonsRx() {
        compositeDisposable.add(
            repository
                .getPokemons()
                .cachedIn(viewModelScope)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe {
                    _pokemonPagingData.postValue(it.map { it.toDomain() })
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
