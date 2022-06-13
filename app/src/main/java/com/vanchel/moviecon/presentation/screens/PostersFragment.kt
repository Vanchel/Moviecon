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
import com.vanchel.moviecon.databinding.FragmentPostersBinding
import com.vanchel.moviecon.domain.entities.Image
import com.vanchel.moviecon.presentation.adapters.PostersListAdapter
import com.vanchel.moviecon.presentation.utils.Resource
import com.vanchel.moviecon.presentation.utils.navigate
import com.vanchel.moviecon.presentation.viewmodels.PostersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий список постеров.
 */
@AndroidEntryPoint
class PostersFragment : Fragment() {
    private var _binding: FragmentPostersBinding? = null
    private val binding: FragmentPostersBinding
        get() = _binding!!

    private val args: PostersFragmentArgs by navArgs()

    /**
     * Фабрика для создания [PostersViewModel].
     */
    @Inject
    lateinit var postersViewModelFactory: PostersViewModel.Factory
    private val postersViewModel: PostersViewModel by viewModels {
        PostersViewModel.provideFactory(postersViewModelFactory, args.cinematicId, args.type)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostersBinding.inflate(inflater, container, false)
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
        binding.sectionError.buttonRetry.setOnClickListener { postersViewModel.reload() }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = PostersListAdapter(object : PostersListAdapter.ItemCallback {
            override fun onItemSelected(item: Image) {
                navigate(PostersFragmentDirections.postersToPoster(item.filePath))
            }
        })
    }

    private fun setViewModelObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                postersViewModel.postersResource.collect { resource ->
                    if (resource is Resource.Success) {
                        resource.data?.let(::bindPostersData)
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

    private fun bindPostersData(cast: List<Image>) {
        (binding.recyclerView.adapter as PostersListAdapter?)?.submitList(cast)
    }
}