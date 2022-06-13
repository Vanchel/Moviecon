package com.vanchel.moviecon.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vanchel.moviecon.domain.repositories.TrendingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана приложения с трендовыми подборками.
 *
 * @constructor создает экземпляр [ViewModel] главного экрана приложения
 *
 * @param repository репозиторий доступа к данным о трендах
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    repository: TrendingRepository
) : ViewModel() {
    /**
     * Поток трендовых фильмов для отображения
     */
    val movies = repository.getTrendingMoviesStream()
        .cachedIn(viewModelScope)

    /**
     * Поток трендовых сериалов для отображения
     */
    val tvs = repository.getTrendingTvsStream()
        .cachedIn(viewModelScope)

    /**
     * Поток трендовых людей для отображения
     */
    val people = repository.getTrendingPeopleStream()
        .cachedIn(viewModelScope)
}