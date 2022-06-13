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
import com.vanchel.moviecon.databinding.FragmentMovieBinding
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.MovieDetails
import com.vanchel.moviecon.presentation.adapters.CastPanelAdapter
import com.vanchel.moviecon.presentation.utils.Resource
import com.vanchel.moviecon.presentation.utils.navigate
import com.vanchel.moviecon.presentation.viewmodels.MovieViewModel
import com.vanchel.moviecon.util.DATE_FORMAT_UI
import com.vanchel.moviecon.util.SIZE_BACKDROP_LARGE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий подробную информацию о фильме.
 */
@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding: FragmentMovieBinding
        get() = _binding!!

    private val args: MovieFragmentArgs by navArgs()

    /**
     * Фабрика для создания [MovieViewModel].
     */
    @Inject
    lateinit var movieViewModelFactory: MovieViewModel.Factory
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.provideFactory(movieViewModelFactory, args.movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
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
        binding.collapsingToolbarLayout.title = args.movieName
    }

    private fun setViewModelObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.movieDetailsResource.collect { resource ->
                    if (resource is Resource.Success) {
                        resource.data?.let(::bindMovieDetailsData)
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
                    MovieFragmentDirections.movieToCast(
                        cinematicId = args.movieId,
                        type = CinematicType.MOVIE,
                        cinematicName = args.movieName
                    )
                )
            }
            textPosters.setOnClickListener {
                navigate(
                    MovieFragmentDirections.movieToPosters(
                        cinematicId = args.movieId,
                        type = CinematicType.MOVIE,
                        cinematicName = args.movieName
                    )
                )
            }
            sectionError.buttonRetry.setOnClickListener { movieViewModel.reload() }
        }
    }

    private fun setUpCastRecyclerView() {
        binding.recyclerViewCast.adapter = CastPanelAdapter(object : CastPanelAdapter.ItemCallback {
            override fun onItemSelected(item: Cast) {
                navigate(MovieFragmentDirections.movieToPerson(item.id, item.name))
            }
        })
    }

    private fun bindMovieDetailsData(movieDetails: MovieDetails) {
        binding.apply {
            movieDetails.backdropPath?.let {
                val url = "$IMAGES_URL$SIZE_BACKDROP_LARGE$it"
                Glide.with(requireView())
                    .load(url).placeholder(R.drawable.img_backdrop).into(backdrop)
            }
            fabTrailer.run {
                isVisible = movieDetails.officialTrailer != null
                movieDetails.officialTrailer?.key?.let { videoKey ->
                    setOnClickListener {
                        navigate(MovieFragmentDirections.movieToPlayer(videoKey))
                    }
                }
            }
            textDate.text = movieDetails.releaseDate?.let {
                SimpleDateFormat(DATE_FORMAT_UI, Locale.getDefault()).format(it)
            } ?: getString(R.string.date_unknown)
            textGenres.text = movieDetails.genreNames.joinToString()
            textDuration.text = movieDetails.runTime?.let {
                getString(R.string.hours_minutes, it / 60, it % 60)
            }
            textVoteAverage.text = getString(
                R.string.ratings_percentage, (movieDetails.voteAverage * 10).toInt()
            )
            textAnnotation.text = movieDetails.overview ?: getString(R.string.no_overview)

            movieDetails.getTopCast()?.let {
                (recyclerViewCast.adapter as CastPanelAdapter?)?.submitList(it)
            }
        }
    }
}