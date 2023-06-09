package com.daabsoft.pokemon.ui.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.daabsoft.pokemon.R
import com.daabsoft.pokemon.core.Constants.keyArgsPokemon
import com.daabsoft.pokemon.core.Resource
import com.daabsoft.pokemon.databinding.FragmentDetailBinding
import com.daabsoft.pokemon.domain.models.Pokemon
import com.daabsoft.pokemon.domain.models.PokemonDetail
import com.daabsoft.pokemon.ui.detail.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    // instantiate detail view model by delegates
    private val detailViewModel: DetailViewModel by activityViewModels<DetailViewModel>()
    private lateinit var pokemonDetail: PokemonDetail

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDetailFragment()
        setupObserver()
        getPokemonDetail()
    }

    private fun setupDetailFragment() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_detail_fragment)
    }

    private fun setupObserver() {
        detailViewModel.pokemonDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> updateDetailScreen(result.data)
                is Resource.Error -> Toast.makeText(
                    context,
                    R.string.error_get_detail,
                    Toast.LENGTH_SHORT
                ).show()
                else -> {}
            }
        }
    }

    private fun updateDetailScreen(data: PokemonDetail) {
        binding.pokemonWeight.text = data.weight.toString()
        binding.pokemonHeight.text = data.height.toString()
        binding.tvStatLabel0.text = data.stats[0].stat.name
        binding.tvStatLabel1.text = data.stats[1].stat.name
        binding.tvStatLabel2.text = data.stats[2].stat.name
        binding.tvStatLabel3.text = data.stats[3].stat.name
        binding.tvStat0.text = data.stats[0].baseStat.toString()
        binding.tvStat1.text = data.stats[1].baseStat.toString()
        binding.tvStat2.text = data.stats[2].baseStat.toString()
        binding.tvStat3.text = data.stats[3].baseStat.toString()
    }

    private fun getPokemonDetail() {
        val pokemon = arguments?.getParcelable(keyArgsPokemon) as? Pokemon

        pokemon?.let {
            detailViewModel.getPokemonDetail(pokemon.name)
        }

        context?.let {
            Glide.with(it)
                .load(pokemon?.getImageMapped())
                .into(binding.pokemonImage)
        }

        binding.pokemonName.text = pokemon?.name?.capitalize()
    }
}
