package com.vanchel.moviecon.presentation.viewmodels

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava2.cachedIn
import com.vanchel.moviecon.domain.entities.MovieType
import com.vanchel.moviecon.domain.repositories.MoviesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана, содержащего список фильмов.
 *
 * @constructor создает экземпляр [ViewModel] экрана списка фильмов
 *
 * @param repository репозиторий доступа к данным о фильмах
 * @param type тип фильмов
 */
class MoviesViewModel @AssistedInject constructor(
    repository: MoviesRepository,
    @Assisted type: MovieType
) : ViewModel() {
    /**
     * Поток фильмов для отображения
     */
    val movies = LiveDataReactiveStreams.fromPublisher(
        repository.getMoviesStream(type).cachedIn(viewModelScope)
    )

    companion object {
        /**
         * Метод, предоставляющий [ViewModelProvider.Factory] для создания [MoviesViewModel].
         *
         * @param assistedFactory фабрика для предоставления пользовательских зависимостей
         * @param type тип фильмов
         * @return [ViewModelProvider.Factory] для создания [MoviesViewModel]
         */
        fun provideFactory(assistedFactory: Factory, type: MovieType): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
                        return assistedFactory.create(type) as T
                    }
                    throw IllegalArgumentException("Unable to construct viewModel")
                }
            }
    }

    /**
     * Фабрика, используемая при создании [MoviesViewModel] для предоставления
     * пользовательских зависимостей.
     */
    @AssistedFactory
    interface Factory {
        /**
         * Метод, создающий [MoviesViewModel], предоставляя ему переданные параметры
         *
         * @param movieType тип фильмов
         * @return новый экземпляр [MoviesViewModel]
         */
        fun create(@Assisted movieType: MovieType): MoviesViewModel
    }
}