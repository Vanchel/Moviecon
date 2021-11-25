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
import com.vanchel.moviecon.databinding.FragmentMovieBinding
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.MovieDetails
import com.vanchel.moviecon.presentation.adapters.CastPanelAdapter
import com.vanchel.moviecon.presentation.utils.Resource
import com.vanchel.moviecon.presentation.utils.navigate
import com.vanchel.moviecon.presentation.viewmodels.MovieViewModel
import com.vanchel.moviecon.util.BASE_URL_IMAGE
import com.vanchel.moviecon.util.DATE_FORMAT_UI
import com.vanchel.moviecon.util.SIZE_BACKDROP_LARGE
import dagger.hilt.android.AndroidEntryPoint
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

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = movieViewModel
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
        binding.collapsingToolbarLayout.title = args.movieName
    }

    private fun setViewModelObservers() {
        movieViewModel.apply {
            movieDetailsResource.observe(viewLifecycleOwner) { res ->
                when (res) {
                    is Resource.Success -> res.data?.let(::bindMovieDetailsData)
                    else -> Unit
                }
            }
            navigateToPersonDetails.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { actor ->
                    navigate(MovieFragmentDirections.movieToPerson(actor.id, actor.name))
                }
            }
            navigateToCast.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    navigate(
                        MovieFragmentDirections.movieToCast(
                            args.movieId, CinematicType.MOVIE, args.movieName
                        )
                    )
                }
            }
            navigateToPosters.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    navigate(
                        MovieFragmentDirections.movieToPosters(
                            args.movieId, CinematicType.MOVIE, args.movieName
                        )
                    )
                }
            }
            navigateToPlayer.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { videoId ->
                    navigate(MovieFragmentDirections.movieToPlayer(videoId))
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            textCast.setOnClickListener { movieViewModel.viewFullCast() }
            textPosters.setOnClickListener { movieViewModel.viewMoviePosters() }
            fabTrailer.setOnClickListener { movieViewModel.viewTrailer() }
            sectionError.buttonRetry.setOnClickListener { movieViewModel.reload() }
        }
    }

    private fun setUpCastRecyclerView() {
        binding.recyclerViewCast.adapter = CastPanelAdapter(object : CastPanelAdapter.ItemCallback {
            override fun onItemSelected(item: Cast) {
                movieViewModel.selectCastPerson(item)
            }
        })
    }

    private fun bindMovieDetailsData(movieDetails: MovieDetails) {
        binding.apply {
            movieDetails.backdropPath?.let {
                val url = "$BASE_URL_IMAGE$SIZE_BACKDROP_LARGE$it"
                Glide.with(requireView())
                    .load(url).placeholder(R.drawable.img_backdrop).into(backdrop)
            }
            fabTrailer.visibility = if (movieDetails.officialTrailer != null) {
                View.VISIBLE
            } else {
                View.GONE
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