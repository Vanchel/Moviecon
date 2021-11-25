package com.vanchel.moviecon.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vanchel.moviecon.R
import com.vanchel.moviecon.databinding.FragmentTvBinding
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.TvDetails
import com.vanchel.moviecon.presentation.adapters.CastPanelAdapter
import com.vanchel.moviecon.presentation.utils.Resource
import com.vanchel.moviecon.presentation.utils.navigate
import com.vanchel.moviecon.presentation.viewmodels.TvViewModel
import com.vanchel.moviecon.util.BASE_URL_IMAGE
import com.vanchel.moviecon.util.SIZE_BACKDROP_LARGE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий подробную информацию о сериале.
 */
@AndroidEntryPoint
class TvFragment : Fragment() {
    private var _binding: FragmentTvBinding? = null
    private val binding: FragmentTvBinding
        get() = _binding!!

    private val args: TvFragmentArgs by navArgs()

    /**
     * Фабрика для создания [TvViewModel].
     */
    @Inject
    lateinit var tvViewModelFactory: TvViewModel.Factory
    private val tvViewModel: TvViewModel by viewModels {
        TvViewModel.provideFactory(tvViewModelFactory, args.tvId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = tvViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        setViewModelObservers()
        setOnClickListeners()
        setUpCastRecyclerView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_round_arrow_back_24)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }
        binding.collapsingToolbarLayout.title = args.tvName
    }

    private fun setViewModelObservers() {
        tvViewModel.apply {
            tvDetailsResource.observe(viewLifecycleOwner) { res ->
                when (res) {
                    is Resource.Success -> res.data?.let(::bindTvDetailsData)
                    else -> Unit
                }
            }
            navigateToPersonDetails.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { actor ->
                    navigate(TvFragmentDirections.tvToPerson(actor.id, actor.name))
                }
            }
            navigateToCast.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    navigate(
                        TvFragmentDirections.tvToCast(args.tvId, CinematicType.TV, args.tvName)
                    )
                }
            }
            navigateToPosters.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    navigate(
                        TvFragmentDirections.tvToPosters(args.tvId, CinematicType.TV, args.tvName)
                    )
                }
            }
            navigateToPlayer.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { videoId ->
                    navigate(TvFragmentDirections.tvToPlayer(videoId))
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            textCast.setOnClickListener { tvViewModel.viewFullCast() }
            textPosters.setOnClickListener { tvViewModel.viewMoviePosters() }
            fabTrailer.setOnClickListener { tvViewModel.viewTrailer() }
            sectionError.buttonRetry.setOnClickListener { tvViewModel.reload() }
        }
    }

    private fun setUpCastRecyclerView() {
        binding.recyclerViewCast.adapter = CastPanelAdapter(object : CastPanelAdapter.ItemCallback {
            override fun onItemSelected(item: Cast) {
                tvViewModel.selectCastPerson(item)
            }
        })
    }

    private fun bindTvDetailsData(tvDetails: TvDetails) {
        binding.apply {
            tvDetails.backdropPath?.let {
                val url = "$BASE_URL_IMAGE$SIZE_BACKDROP_LARGE$it"
                Glide.with(requireView())
                    .load(url).placeholder(R.drawable.img_backdrop).into(backdrop)
            }
            fabTrailer.visibility = if (tvDetails.officialTrailer != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
            textGenres.text = tvDetails.genreNames.joinToString()
            textEpisodeDuration.text = tvDetails.episodeRunTime.average().toInt().let {
                getString(R.string.minutes, it)
            }
            textVoteAverage.text = getString(
                R.string.ratings_percentage, (tvDetails.voteAverage * 10).toInt()
            )
            textAnnotation.text = tvDetails.overview ?: getString(R.string.no_overview)

            tvDetails.getTopCast()?.let {
                (recyclerViewCast.adapter as CastPanelAdapter?)?.submitList(it)
            }
        }
    }
}