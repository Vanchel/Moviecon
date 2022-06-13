package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vanchel.moviecon.domain.entities.MovieDetails
import com.vanchel.moviecon.domain.repositories.MoviesRepository
import com.vanchel.moviecon.presentation.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "MovieViewModel"

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана подробной информации о фильме.
 *
 * @property moviesRepository репозиторий доступа к данным о фильмах
 * @property movieId идентификатор фильма
 */
class MovieViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val movieId: Int
) : ViewModel() {

    /**
     * [Resource], содержащий состояние данных о конкретном фильме.
     */
    private val _movieDetailsResource = MutableStateFlow<Resource<MovieDetails>?>(null)
    val movieDetailsResource = _movieDetailsResource.asStateFlow()

    init {
        getMovieDetails()
    }

    /**
     * Метод для повторной загрузки данных
     */
    fun reload() = getMovieDetails()

    private fun getMovieDetails() {
        viewModelScope.launch {
            _movieDetailsResource.update { Resource.Loading() }
            try {
                val result = moviesRepository.getMovieDetails(movieId)
                _movieDetailsResource.update { Resource.Success(result) }
            } catch (e: Exception) {
                // TODO обрабатывать соответствующие исключения
                _movieDetailsResource.update { Resource.Error("Error: ${e.message}") }
                Log.e(TAG, "getMovieDetails: ${e.message}")
            }
        }
    }

    companion object {
        /**
         * Метод, предоставляющий [ViewModelProvider.Factory] для создания [MovieViewModel].
         *
         * @param assistedFactory фабрика для предоставления пользовательских зависимостей
         * @param movieId идентификатор фильма
         * @return [ViewModelProvider.Factory] для создания [MovieViewModel]
         */
        fun provideFactory(assistedFactory: Factory, movieId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                        return assistedFactory.create(movieId) as T
                    }
                    throw IllegalArgumentException("Unable to construct viewModel")
                }
            }
    }

    /**
     * Фабрика, используемая при создании [MovieViewModel] для предоставления
     * пользовательских зависимостей.
     */
    @AssistedFactory
    interface Factory {
        /**
         * Метод, создающий [MovieViewModel], предоставляя ему переданные параметры
         *
         * @param movieId идентификатор фильма
         * @return новый экземпляр [MovieViewModel]
         */
        fun create(@Assisted movieId: Int): MovieViewModel
    }
}