package com.vanchel.moviecon.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.paging.LoadState
import com.vanchel.moviecon.R
import com.vanchel.moviecon.databinding.FragmentMainBinding
import com.vanchel.moviecon.domain.entities.Movie
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.Tv
import com.vanchel.moviecon.presentation.adapters.BasicLoaderStateAdapter
import com.vanchel.moviecon.presentation.adapters.MoviesTrendingAdapter
import com.vanchel.moviecon.presentation.adapters.PeopleTrendingAdapter
import com.vanchel.moviecon.presentation.adapters.TvsTrendingAdapter
import com.vanchel.moviecon.presentation.utils.navigate
import com.vanchel.moviecon.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий трендовые подборки.
 */
@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()

    private val moviesAdapter by lazy {
        MoviesTrendingAdapter(object : MoviesTrendingAdapter.ItemCallback {
            override fun onItemSelected(item: Movie) {
                mainViewModel.viewMovie(item)
            }
        })
    }

    private val tvsAdapter by lazy {
        TvsTrendingAdapter(object : TvsTrendingAdapter.ItemCallback {
            override fun onItemSelected(item: Tv) {
                mainViewModel.viewTv(item)
            }
        })
    }

    private val peopleAdapter by lazy {
        PeopleTrendingAdapter(object : PeopleTrendingAdapter.ItemCallback {
            override fun onItemSelected(item: Person) {
                mainViewModel.viewPerson(item)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpDrawer()
        setUpToolbar()
        setUpRecyclerViews()
        setViewModelObservers()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpDrawer() {
        binding.drawer.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.moviesFragment -> navigateFromDrawer(MainFragmentDirections.mainToMovies())
                R.id.showsFragment -> navigateFromDrawer(MainFragmentDirections.mainToTvs())
                R.id.peopleFragment -> navigateFromDrawer(MainFragmentDirections.mainToPeople())
                else -> false
            }
        }
    }

    private fun setUpToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_round_menu_24)
            setNavigationIcon(R.drawable.ic_round_menu_24)
            setNavigationOnClickListener {
                binding.drawerLayout.openDrawer(binding.drawer)
            }
        }
    }

    private fun setUpRecyclerViews() {
        binding.apply {
            recyclerMovies.adapter = moviesAdapter.withLoadStateFooter(BasicLoaderStateAdapter())
            moviesAdapter.addLoadStateListener { state ->
                moviesLoading.root.isVisible = state.refresh == LoadState.Loading
                moviesError.root.isVisible = state.refresh is LoadState.Error
            }
            recyclerTvs.adapter = tvsAdapter.withLoadStateFooter(BasicLoaderStateAdapter())
            tvsAdapter.addLoadStateListener { state ->
                tvsLoading.root.isVisible = state.refresh == LoadState.Loading
                tvsError.root.isVisible = state.refresh is LoadState.Error
            }
            recyclerPeople.adapter = peopleAdapter.withLoadStateFooter(BasicLoaderStateAdapter())
            peopleAdapter.addLoadStateListener { state ->
                peopleLoading.root.isVisible = state.refresh == LoadState.Loading
                peopleError.root.isVisible = state.refresh is LoadState.Error
            }
        }
    }

    private fun setViewModelObservers() {
        mainViewModel.apply {
            navigateToMovie.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { movie ->
                    navigate(MainFragmentDirections.mainToMovie(movie.id, movie.title))
                }
            }
            navigateToTv.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { tv ->
                    navigate(MainFragmentDirections.mainToTv(tv.id, tv.name))
                }
            }
            navigateToPerson.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { person ->
                    navigate(MainFragmentDirections.mainToPerson(person.id, person.name))
                }
            }
            movies.observe(viewLifecycleOwner) { pagingData ->
                moviesAdapter.submitData(lifecycle, pagingData)
            }
            tvs.observe(viewLifecycleOwner) { pagingData ->
                tvsAdapter.submitData(lifecycle, pagingData)
            }
            people.observe(viewLifecycleOwner) { pagingData ->
                peopleAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            moviesError.buttonRetry.setOnClickListener { moviesAdapter.retry() }
            tvsError.buttonRetry.setOnClickListener { tvsAdapter.retry() }
            peopleError.buttonRetry.setOnClickListener { peopleAdapter.retry() }
        }
    }

    private fun navigateFromDrawer(direction: NavDirections): Boolean {
        navigate(direction)
        binding.drawerLayout.closeDrawer(binding.drawer)
        return true
    }
}