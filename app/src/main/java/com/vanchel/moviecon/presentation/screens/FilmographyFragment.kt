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
import com.vanchel.moviecon.R
import com.vanchel.moviecon.databinding.FragmentFilmographyBinding
import com.vanchel.moviecon.domain.entities.Credit
import com.vanchel.moviecon.presentation.adapters.CreditsListAdapter
import com.vanchel.moviecon.presentation.utils.Resource
import com.vanchel.moviecon.presentation.utils.navigate
import com.vanchel.moviecon.presentation.viewmodels.FilmographyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий фильмографию актера.
 */
@AndroidEntryPoint
class FilmographyFragment : Fragment() {
    private var _binding: FragmentFilmographyBinding? = null
    private val binding: FragmentFilmographyBinding
        get() = _binding!!

    private val args: FilmographyFragmentArgs by navArgs()

    /**
     * Фабрика для создания [FilmographyViewModel].
     */
    @Inject
    lateinit var filmographyViewModelFactory: FilmographyViewModel.Factory
    private val filmographyViewModel: FilmographyViewModel by viewModels {
        FilmographyViewModel.provideFactory(filmographyViewModelFactory, args.personId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmographyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        setOnClickListeners()
        setUpRecyclerView()
        setViewModelObservers()
    }

    private fun setUpToolbar() {
        binding.toolbar.apply {
            subtitle = args.personName
            setNavigationIcon(R.drawable.ic_round_arrow_back_24)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setOnClickListeners() {
        binding.sectionError.buttonRetry.setOnClickListener { filmographyViewModel.reload() }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = CreditsListAdapter(object : CreditsListAdapter.ItemCallback {
            override fun onItemSelected(item: Credit) {
                filmographyViewModel.viewCredit(item)
            }
        })
    }

    private fun setViewModelObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    filmographyViewModel.creditsResource.collect { resource ->
                        if (resource is Resource.Success) {
                            resource.data?.let(::bindCreditsData)
                        }
                        binding.run {
                            recyclerView.isVisible = resource is Resource.Success
                            sectionLoading.root.isVisible = resource is Resource.Loading
                            sectionError.root.isVisible = resource is Resource.Error
                        }
                    }
                }
                launch {
                    filmographyViewModel.selectedMovie.collect { movie ->
                        movie?.let {
                            navigate(
                                FilmographyFragmentDirections.filmographyToMovie(
                                    movieId = movie.id,
                                    movieName = movie.title
                                )
                            )
                            filmographyViewModel.selectedMovieProcessed()
                        }
                    }
                }
                launch {
                    filmographyViewModel.selectedTv.collect { tv ->
                        tv?.let {
                            navigate(
                                FilmographyFragmentDirections.filmographyToTv(
                                    tvId = tv.id,
                                    tvName = tv.title
                                )
                            )
                            filmographyViewModel.selectedTvProcessed()
                        }
                    }
                }
            }
        }
    }

    private fun bindCreditsData(credits: List<Credit>) {
        (binding.recyclerView.adapter as CreditsListAdapter?)?.submitList(credits)
    }
}