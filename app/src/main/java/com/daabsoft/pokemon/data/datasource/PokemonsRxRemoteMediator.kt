package com.daabsoft.pokemon.data.datasource

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.daabsoft.pokemon.core.BaseSchedulerProvider
import com.daabsoft.pokemon.data.local.PokemonDatabase
import com.daabsoft.pokemon.data.local.entity.PokemonEntity
import com.daabsoft.pokemon.data.remote.services.PokemonService
import io.reactivex.Single
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GetPokemonsRxRemoteMediator @Inject constructor(
    private val service: PokemonService,
    private val database: PokemonDatabase,
    private val schedulerProvider: BaseSchedulerProvider
) : RxRemoteMediator<Int, PokemonEntity>() {
    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(schedulerProvider.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: PokemonEntity.PokemonRemoteKeys(0, -1, 1)

                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: PokemonEntity.PokemonRemoteKeys(-1, -1, 0)

                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: PokemonEntity.PokemonRemoteKeys(-1, -1, 0)

                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    service.getAllPokemon(
                        limit = PAGE_SIZE,
                        offset = page * PAGE_SIZE
                    )
                        .map { insertToDb(page, loadType, it.pokemons.map { it.toDomain() }.map { it.toEntitiy(page) }) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = false) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }
            }
    }

    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: List<PokemonEntity>): List<PokemonEntity> {
        database.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                database.pokemonRemoteKeysRxDao().clearRemoteKeys()
                database.pokemonRxDao().clearPokemons()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (false) null else page + 1
            val keys = data.map { pokemonEntity ->
                PokemonEntity.PokemonRemoteKeys(page = page, prevKey = prevKey, nextKey = nextKey)
            }
            database.pokemonRemoteKeysRxDao().insertAll(keys)
            database.pokemonRxDao().insertAll(data)
            database.setTransactionSuccessful()
        } finally {
            database.endTransaction()
        }

        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonEntity>): PokemonEntity.PokemonRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.pokemonRemoteKeysRxDao().remoteKeysByPage(repo.page)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonEntity>): PokemonEntity.PokemonRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { pokemon ->
            database.pokemonRemoteKeysRxDao().remoteKeysByPage(pokemon.page)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PokemonEntity>): PokemonEntity.PokemonRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.page?.let { id ->
                database.pokemonRemoteKeysRxDao().remoteKeysByPage(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
        const val PAGE_SIZE = 20
    }
}
