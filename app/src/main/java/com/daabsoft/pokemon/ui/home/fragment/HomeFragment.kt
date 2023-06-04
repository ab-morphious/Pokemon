package com.daabsoft.pokemon.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.daabsoft.pokemon.R
import com.daabsoft.pokemon.databinding.FragmentHomeBinding
import com.daabsoft.pokemon.ui.home.adapter.LoadingGridStateAdapter
import com.daabsoft.pokemon.ui.home.adapter.PokemonRxAdapter
import com.daabsoft.pokemon.ui.home.viewmodel.HomeRxViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // instantiate rx viewmodel by delegates
    private val homeRxViewModel: HomeRxViewModel by activityViewModels<HomeRxViewModel>()

    // private val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()
    private lateinit var navController: NavController
    private lateinit var pokemonRxAdapter: PokemonRxAdapter
    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupHomeFragment()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupHomeFragment() {
        navController = findNavController()
        pokemonRxAdapter = PokemonRxAdapter()
        binding.rvHome.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHome.adapter = pokemonRxAdapter

        binding.rvHome.adapter = pokemonRxAdapter.withLoadStateFooter(
            footer = LoadingGridStateAdapter()
        )

        pokemonRxAdapter.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.error)
                    .setMessage(it.error.localizedMessage)
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(R.string.retry) { _, _ ->
                        pokemonRxAdapter.retry()
                    }
                    .show()
            }
        }

        compositeDisposable.add(
            homeRxViewModel.getAllPokemonsRx().subscribe {
                pokemonRxAdapter.submitData(lifecycle, it.map { it.toDomain() })
            }
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
