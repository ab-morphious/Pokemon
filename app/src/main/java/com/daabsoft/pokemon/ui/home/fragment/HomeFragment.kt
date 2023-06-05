package com.daabsoft.pokemon.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.daabsoft.pokemon.R
import com.daabsoft.pokemon.core.Constants.keyArgsPokemon
import com.daabsoft.pokemon.databinding.FragmentHomeBinding
import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.ui.home.PokemonRxAdapter
import com.daabsoft.pokemon.ui.home.adapter.CellClickListener
import com.daabsoft.pokemon.ui.home.adapter.LoadingGridStateAdapter
import com.daabsoft.pokemon.ui.home.viewmodel.HomeRxViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), CellClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // instantiate rx viewmodel by delegates
    private val homeRxViewModel: HomeRxViewModel by activityViewModels<HomeRxViewModel>()
    private lateinit var navController: NavController
    private lateinit var pokemonRxAdapter: PokemonRxAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHomeFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupHomeFragment() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_home_fragment)

        navController = findNavController()
        pokemonRxAdapter = PokemonRxAdapter()
        pokemonRxAdapter.setCellClickListener(this)
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

        homeRxViewModel.pokemonPagingData.observe(viewLifecycleOwner) {
            pokemonRxAdapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCellClickListener(pokemon: Pokemon) {
        val bundle = Bundle()
        bundle.putParcelable(keyArgsPokemon, pokemon)
        navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }
}
