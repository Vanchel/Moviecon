package com.vanchel.moviecon.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vanchel.moviecon.domain.entities.MovieType
import com.vanchel.moviecon.presentation.screens.MoviesListFragment

/**
 * @author Иван Тимашов
 *
 * [FragmentStateAdapter] для пейджера с категориями фильмов.
 *
 * @constructor создает [FragmentStateAdapter] с категориями фильмов
 *
 * @param fragment фрагмент, передаваемый родителю
 */
class MoviesViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        POPULAR_PAGE_INDEX to { MoviesListFragment.newInstance(MovieType.POPULAR) },
        NOW_PLAYING_PAGE_INDEX to { MoviesListFragment.newInstance(MovieType.NOW_PLAYING) },
        UPCOMING_PAGE_INDEX to { MoviesListFragment.newInstance(MovieType.UPCOMING) },
        TOP_RATED_PAGE_INDEX to { MoviesListFragment.newInstance(MovieType.TOP_RATED) }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    companion object {
        /**
         * Индекс страницы с популярными фильмами
         */
        const val POPULAR_PAGE_INDEX = 0

        /**
         * Индекс страницы с фильмами, которые сейчас смотрят
         */
        const val NOW_PLAYING_PAGE_INDEX = 1

        /**
         * Индекс страницы с ожидаемыми фильмами
         */
        const val UPCOMING_PAGE_INDEX = 2

        /**
         * Индекс страницы с лучшими фильмами
         */
        const val TOP_RATED_PAGE_INDEX = 3
    }
}