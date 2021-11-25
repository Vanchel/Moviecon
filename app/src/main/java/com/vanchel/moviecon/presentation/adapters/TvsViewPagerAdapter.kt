package com.vanchel.moviecon.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vanchel.moviecon.domain.entities.TvType
import com.vanchel.moviecon.presentation.screens.TvsListFragment

/**
 * @author Иван Тимашов
 *
 * [FragmentStateAdapter] для пейджера с категориями сериалов.
 *
 * @constructor создает [FragmentStateAdapter] с категориями сериалов
 *
 * @param fragment фрагмент, передаваемый родителю
 */
class TvsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        POPULAR_PAGE_INDEX to { TvsListFragment.newInstance(TvType.POPULAR) },
        AIRING_TODAY_PAGE_INDEX to { TvsListFragment.newInstance(TvType.AIRING_TODAY) },
        ON_THE_AIR_PAGE_INDEX to { TvsListFragment.newInstance(TvType.ON_THE_AIR) },
        TOP_RATED_PAGE_INDEX to { TvsListFragment.newInstance(TvType.TOP_RATED) }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    companion object {
        /**
         * Индекс страницы с популярными сериалами
         */
        const val POPULAR_PAGE_INDEX = 0

        /**
         * Индекс страницы с сериалами, которые сегодня в эфире
         */
        const val AIRING_TODAY_PAGE_INDEX = 1

        /**
         * Индекс страницы с сериалами, которые идут по телевидению
         */
        const val ON_THE_AIR_PAGE_INDEX = 2

        /**
         * Индекс страницы с лучшими сериалами
         */
        const val TOP_RATED_PAGE_INDEX = 3
    }
}