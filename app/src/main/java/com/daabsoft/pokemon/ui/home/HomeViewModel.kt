package com.daabsoft.pokemon.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daabsoft.pokemon.core.BaseSchedulerProvider
import com.daabsoft.pokemon.core.Resource
import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.domain.usecases.GetAllPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel class HomeViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemonUseCase,
    private val schedulerProvider: BaseSchedulerProvider
) : ViewModel() {

    private val _pokemons: MutableLiveData<Resource<List<Pokemon>>> = MutableLiveData()
    val pokemons: LiveData<Resource<List<Pokemon>>>
        get() = _pokemons

    val compositeDisposable = CompositeDisposable()

    fun getAllPokemons(page: Int) {
        if (page < 0) {
            _pokemons.postValue(Resource.Error(message = "Invalid page"))
            return
        }

        compositeDisposable.add(
            getAllPokemonUseCase(page)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ pokemons ->
                    _pokemons.postValue(Resource.Success(pokemons))
                }, {
                    _pokemons.postValue(Resource.Error(message = it.localizedMessage))
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
