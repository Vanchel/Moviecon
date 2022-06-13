package com.vanchel.moviecon.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vanchel.moviecon.R
import com.vanchel.moviecon.data.network.IMAGES_URL
import com.vanchel.moviecon.databinding.FragmentTvBinding
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.TvDetails
import com.vanchel.moviecon.presentation.adapters.CastPanelAdapter
import com.vanchel.moviecon.presentation.utils.Resource
import com.vanchel.moviecon.presentation.utils.navigate
import com.vanchel.moviecon.presentation.viewmodels.TvViewModel
import com.vanchel.moviecon.util.SIZE_BACKDROP_LARGE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tvViewModel.tvDetailsResource.collect { resource ->
                    if (resource is Resource.Success) {
                        resource.data?.let(::bindTvDetailsData)
                    }
                    binding.run {
                        sectionContent.isVisible = resource is Resource.Success
                        sectionLoading.root.isVisible = resource is Resource.Loading
                        sectionError.root.isVisible = resource is Resource.Error
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            textCast.setOnClickListener {
                navigate(
                    TvFragmentDirections.tvToCast(
                        cinematicId = args.tvId,
                        type = CinematicType.TV,
                        cinematicName = args.tvName
                    )
                )
            }
            textPosters.setOnClickListener {
                navigate(
                    TvFragmentDirections.tvToPosters(
                        cinematicId = args.tvId,
                        type = CinematicType.TV,
                        cinematicName = args.tvName
                    )
                )
            }
            sectionError.buttonRetry.setOnClickListener { tvViewModel.reload() }
        }
    }

    private fun setUpCastRecyclerView() {
        binding.recyclerViewCast.adapter = CastPanelAdapter(object : CastPanelAdapter.ItemCallback {
            override fun onItemSelected(item: Cast) {
                navigate(TvFragmentDirections.tvToPerson(item.id, item.name))
            }
        })
    }

    private fun bindTvDetailsData(tvDetails: TvDetails) {
        binding.apply {
            tvDetails.backdropPath?.let {
                val url = "$IMAGES_URL$SIZE_BACKDROP_LARGE$it"
                Glide.with(requireView())
                    .load(url).placeholder(R.drawable.img_backdrop).into(backdrop)
            }
            fabTrailer.run {
                isVisible = tvDetails.officialTrailer != null
                tvDetails.officialTrailer?.key?.let { videoKey ->
                    setOnClickListener {
                        navigate(TvFragmentDirections.tvToPlayer(videoKey))
                    }
                }
            }
            textGenres.text = tvDetails.genreNames.joinToString()
            textEpisodeDuration.text = tvDetails.episodeRunTime.average().toInt().let {
                getString(R.string.minutes, it)
            }
            textVoteAverage.text = getString(
                R.string.ratings_percentage, (tvDetails.voteAverage * 10).toInt()
            )
            textAnnotation.text = tvDetails.overview

            tvDetails.getTopCast()?.let {
                (recyclerViewCast.adapter as CastPanelAdapter?)?.submitList(it)
            }
        }
    }
}