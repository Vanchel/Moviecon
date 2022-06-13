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
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.vanchel.moviecon.databinding.FragmentRecyclerViewBinding
import com.vanchel.moviecon.domain.entities.Tv
import com.vanchel.moviecon.domain.entities.TvType
import com.vanchel.moviecon.presentation.adapters.BasicLoaderStateAdapter
import com.vanchel.moviecon.presentation.adapters.TvsAdapter
import com.vanchel.moviecon.presentation.viewmodels.TvsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ARG_TV_TYPE = "tvType"

private const val SPANS_COUNT = 2

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий список сериалов.
 */
@AndroidEntryPoint
class TvsListFragment : Fragment() {
    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding: FragmentRecyclerViewBinding
        get() = _binding!!

    /**
     * Фабрика для создания [TvsViewModel].
     */
    @Inject
    lateinit var tvsViewModelFactory: TvsViewModel.Factory
    private val tvsViewModel: TvsViewModel by viewModels {
        val type = (arguments?.getSerializable(ARG_TV_TYPE) as TvType)
        TvsViewModel.provideFactory(tvsViewModelFactory, type)
    }

    private val tvsAdapter by lazy {
        TvsAdapter(object : TvsAdapter.ItemCallback {
            override fun onItemSelected(item: Tv) {
                findNavController().navigate(
                    TvsFragmentDirections.tvsToTv(item.id, item.name)
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
            adapter = tvsAdapter.withLoadStateFooter(BasicLoaderStateAdapter())
            tvsAdapter.addLoadStateListener { state ->
                binding.layoutLoading.root.isVisible = state.refresh == LoadState.Loading
                binding.layoutError.root.isVisible = state.refresh is LoadState.Error
            }
        }
    }

    private fun setViewModelObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tvsViewModel.tvs.collectLatest(tvsAdapter::submitData)
            }
        }
    }

    private fun setOnClickListeners() {
        binding.layoutError.buttonRetry.setOnClickListener { tvsAdapter.retry() }
    }

    companion object {
        /**
         * Метод для создания нового экземпляра [TvsListFragment] с заданными аргументами
         *
         * @param type тип сериалов
         * @return новый экземпляр [TvsListFragment]
         */
        @JvmStatic
        fun newInstance(type: TvType) = TvsListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_TV_TYPE, type)
            }
        }
    }
}