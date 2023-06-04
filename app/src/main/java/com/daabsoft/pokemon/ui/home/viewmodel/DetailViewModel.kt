package com.daabsoft.pokemon.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daabsoft.pokemon.core.BaseSchedulerProvider
import com.daabsoft.pokemon.core.Resource
import com.daabsoft.pokemon.domain.models.PokemonDetail
import com.daabsoft.pokemon.domain.usecases.GetPokemonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel class DetailViewModel @Inject constructor(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
    private val schedulerProvider: BaseSchedulerProvider
) : ViewModel() {

    private val _pokemonDetail: MutableLiveData<Resource<PokemonDetail>> = MutableLiveData()
    val pokemonDetail: LiveData<Resource<PokemonDetail>>
        get() = _pokemonDetail

    val compositeDisposable = CompositeDisposable()

    fun getPokemonDetail(name: String) {
        compositeDisposable.add(
            getPokemonDetailsUseCase(name)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ pokemonDetail ->
                    _pokemonDetail.postValue(Resource.Success(pokemonDetail))
                }, {
                    _pokemonDetail.postValue(Resource.Error(message = it.localizedMessage))
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
