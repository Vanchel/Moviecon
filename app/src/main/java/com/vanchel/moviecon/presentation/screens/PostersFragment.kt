package com.vanchel.moviecon.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = postersViewModel
        }

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
        binding.sectionError.buttonRetry.setOnClickListener { v -> postersViewModel.reload() }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = PostersListAdapter(object : PostersListAdapter.ItemCallback {
            override fun onItemSelected(item: Image) {
                postersViewModel.selectPoster(item)
            }
        })
    }

    private fun setViewModelObservers() {
        postersViewModel.apply {
            postersResource.observe(viewLifecycleOwner) { res ->
                when(res) {
                    is Resource.Success -> res.data?.let(::bindPostersData)
                    else -> Unit
                }
            }
            navigateToPoster.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { image ->
                    navigate(PostersFragmentDirections.postersToPoster(image.filePath))
                }
            }
        }
    }

    private fun bindPostersData(cast: List<Image>) {
        (binding.recyclerView.adapter as PostersListAdapter?)?.submitList(cast)
    }
}