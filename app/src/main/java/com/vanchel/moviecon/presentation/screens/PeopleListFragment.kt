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
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.PersonType
import com.vanchel.moviecon.presentation.adapters.BasicLoaderStateAdapter
import com.vanchel.moviecon.presentation.adapters.PeopleAdapter
import com.vanchel.moviecon.presentation.viewmodels.PeopleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ARG_PERSON_TYPE = "personType"

private const val SPANS_COUNT = 2

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий список людей.
 */
@AndroidEntryPoint
class PeopleListFragment : Fragment() {
    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding: FragmentRecyclerViewBinding
        get() = _binding!!

    /**
     * Фабрика для создания [PeopleViewModel].
     */
    @Inject
    lateinit var peopleViewModelFactory: PeopleViewModel.Factory
    private val peopleViewModel: PeopleViewModel by viewModels {
        val type = (arguments?.getSerializable(ARG_PERSON_TYPE) as PersonType)
        PeopleViewModel.provideFactory(peopleViewModelFactory, type)
    }

    private val peopleAdapter by lazy {
        PeopleAdapter(object : PeopleAdapter.ItemCallback {
            override fun onItemSelected(item: Person) {
                findNavController().navigate(
                    PeopleFragmentDirections.peopleToPerson(item.id, item.name)
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
            adapter = peopleAdapter.withLoadStateFooter(BasicLoaderStateAdapter())
            peopleAdapter.addLoadStateListener { state ->
                binding.layoutLoading.root.isVisible = state.refresh == LoadState.Loading
                binding.layoutError.root.isVisible = state.refresh is LoadState.Error
            }
        }
    }

    private fun setViewModelObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                peopleViewModel.people.collectLatest(peopleAdapter::submitData)
            }
        }
    }

    private fun setOnClickListeners() {
        binding.layoutError.buttonRetry.setOnClickListener { peopleAdapter.retry() }
    }

    companion object {
        /**
         * Метод для создания нового экземпляра [PeopleListFragment] с заданными аргументами
         *
         * @param type тип людей
         * @return новый экземпляр [PeopleListFragment]
         */
        @JvmStatic
        fun newInstance(type: PersonType) = PeopleListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_PERSON_TYPE, type)
            }
        }
    }
}