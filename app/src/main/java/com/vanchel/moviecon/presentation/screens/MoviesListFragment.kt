package com.vanchel.moviecon.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.vanchel.moviecon.databinding.FragmentRecyclerViewBinding
import com.vanchel.moviecon.domain.entities.Movie
import com.vanchel.moviecon.domain.entities.MovieType
import com.vanchel.moviecon.presentation.adapters.BasicLoaderStateAdapter
import com.vanchel.moviecon.presentation.adapters.MoviesAdapter
import com.vanchel.moviecon.presentation.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ARG_MOVIE_TYPE = "movieType"

private const val SPANS_COUNT = 2

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий список фильмов.
 */
@AndroidEntryPoint
class MoviesListFragment : Fragment() {
    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding: FragmentRecyclerViewBinding
        get() = _binding!!

    /**
     * Фабрика для создания [MoviesViewModel].
     */
    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModel.Factory
    private val moviesViewModel: MoviesViewModel by viewModels {
        val type = (arguments?.getSerializable(ARG_MOVIE_TYPE) as MovieType)
        MoviesViewModel.provideFactory(moviesViewModelFactory, type)
    }

    private val moviesAdapter by lazy {
        MoviesAdapter(object : MoviesAdapter.ItemCallback {
            override fun onItemSelected(item: Movie) {
                findNavController().navigate(
                    MoviesFragmentDirections.moviesToMovie(item.id, item.title)
                )
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setViewModelObservers()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), SPANS_COUNT)
            adapter = moviesAdapter.withLoadStateFooter(BasicLoaderStateAdapter())
            moviesAdapter.addLoadStateListener { state ->
                binding.layoutLoading.root.isVisible = state.refresh == LoadState.Loading
                binding.layoutError.root.isVisible = state.refresh is LoadState.Error
            }
        }
    }

    private fun setViewModelObservers() {
        moviesViewModel.movies.observe(viewLifecycleOwner) { pagingData ->
            moviesAdapter.submitData(lifecycle, pagingData)
        }
    }

    private fun setOnClickListeners() {
        binding.layoutError.buttonRetry.setOnClickListener { moviesAdapter.retry() }
    }

    companion object {
        /**
         * Метод для создания нового экземпляра [MoviesListFragment] с заданными аргументами
         *
         * @param type тип фильмов
         * @return новый экземпляр [MoviesListFragment]
         */
        @JvmStatic
        fun newInstance(type: MovieType) = MoviesListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_MOVIE_TYPE, type)
            }
        }
    }
}