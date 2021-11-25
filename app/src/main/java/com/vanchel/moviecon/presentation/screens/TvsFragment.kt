package com.vanchel.moviecon.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.vanchel.moviecon.R
import com.vanchel.moviecon.databinding.FragmentViewPagerBinding
import com.vanchel.moviecon.presentation.adapters.TvsViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий сериалы, разделенные по категориям.
 */
@AndroidEntryPoint
class TvsFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding: FragmentViewPagerBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        setUpViewPager()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpToolbar() {
        binding.toolbar.apply {
            setTitle(R.string.tvs)
            setNavigationIcon(R.drawable.ic_round_arrow_back_24)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpViewPager() {
        binding.apply {
            TvsViewPagerAdapter(this@TvsFragment).let {
                viewPager.adapter = it
                tabs.isVisible = it.itemCount > 1
            }
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = getTabTitle(position)
            }.attach()
        }
    }

    private fun getTabTitle(position: Int) = when (position) {
        TvsViewPagerAdapter.POPULAR_PAGE_INDEX -> getString(R.string.tvs_popular)
        TvsViewPagerAdapter.AIRING_TODAY_PAGE_INDEX -> getString(R.string.tvs_airing_today)
        TvsViewPagerAdapter.ON_THE_AIR_PAGE_INDEX -> getString(R.string.tvs_on_the_air)
        TvsViewPagerAdapter.TOP_RATED_PAGE_INDEX -> getString(R.string.tvs_top_rated)
        else -> null
    }
}