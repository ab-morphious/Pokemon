

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.daabsoft.pokemon.data.datasource.GetPokemonsRxRemoteMediator
import com.daabsoft.pokemon.data.datasource.GetPokemonsRxRemoteMediator.Companion.PAGE_SIZE
import com.daabsoft.pokemon.data.local.PokemonDatabase
import com.daabsoft.pokemon.data.local.entity.PokemonEntity
import com.daabsoft.pokemon.domain.repositories.PokemonRxRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetPokemonRxRemoteRepositoryImpl @Inject constructor(
    private val database: PokemonDatabase,
    private val remoteMediator: GetPokemonsRxRemoteMediator
) : PokemonRxRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemons(): Flowable<PagingData<PokemonEntity>> {
        return Pager(
            config = PagingConfig(
                PAGE_SIZE,
                enablePlaceholders = true,
                maxSize = PAGE_SIZE + 2 * 5,
                prefetchDistance = 5,
                initialLoadSize = PAGE_SIZE
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.pokemonRxDao().selectAll() }
        ).flowable
    }
}
