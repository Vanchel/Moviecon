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
import com.vanchel.moviecon.databinding.FragmentCastBinding
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.presentation.adapters.CastListAdapter
import com.vanchel.moviecon.presentation.utils.Resource
import com.vanchel.moviecon.presentation.utils.navigate
import com.vanchel.moviecon.presentation.viewmodels.CastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий актерский состав фильма или сериала.
 */
@AndroidEntryPoint
class CastFragment : Fragment() {
    private var _binding: FragmentCastBinding? = null
    private val binding: FragmentCastBinding
        get() = _binding!!

    private val args: CastFragmentArgs by navArgs()

    /**
     * Фабрика для создания [CastViewModel].
     */
    @Inject
    lateinit var castViewModelFactory: CastViewModel.Factory
    private val castViewModel: CastViewModel by viewModels {
        CastViewModel.provideFactory(castViewModelFactory, args.cinematicId, args.type)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        setOnClickListeners()
        setUpRecyclerView()
        setViewModelObservers()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpToolbar() {
        binding.toolbar.apply {
            subtitle = args.cinematicName
            setNavigationIcon(R.drawable.ic_round_arrow_back_24)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun setOnClickListeners() {
        binding.sectionError.buttonRetry.setOnClickListener { castViewModel.reload() }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = CastListAdapter(object : CastListAdapter.ItemCallback {
            override fun onItemSelected(item: Cast) {
                navigate(CastFragmentDirections.castToPerson(item.id, item.name))
            }
        })
    }

    private fun setViewModelObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                castViewModel.castResource.collect { resource ->
                    if (resource is Resource.Success) {
                        resource.data?.let(::bindCastData)
                    }
                    binding.run {
                        recyclerView.isVisible = resource is Resource.Success
                        sectionLoading.root.isVisible = resource is Resource.Loading
                        sectionError.root.isVisible = resource is Resource.Error
                    }
                }
            }
        }
    }

    private fun bindCastData(cast: List<Cast>) {
        (binding.recyclerView.adapter as CastListAdapter?)?.submitList(cast)
    }
}