package com.vanchel.moviecon.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vanchel.moviecon.domain.entities.PersonType
import com.vanchel.moviecon.presentation.screens.PeopleListFragment

/**
 * @author Иван Тимашов
 *
 * [FragmentStateAdapter] для пейджера с категориями людей.
 *
 * @constructor создает [FragmentStateAdapter] с категориями людей
 *
 * @param fragment фрагмент, передаваемый родителю
 */
class PeopleViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        POPULAR_PAGE_INDEX to {
            PeopleListFragment.newInstance(PersonType.POPULAR)
        }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    companion object {
        /**
         * Индекс страницы с популярными людьми
         */
        const val POPULAR_PAGE_INDEX = 0
    }
}