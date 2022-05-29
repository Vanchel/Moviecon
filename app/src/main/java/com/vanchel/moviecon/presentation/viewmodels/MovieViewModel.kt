package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.MovieDetails
import com.vanchel.moviecon.domain.repositories.MoviesRepository
import com.vanchel.moviecon.presentation.utils.Event
import com.vanchel.moviecon.presentation.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
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
    private val _movieDetailsResource = MutableLiveData<Resource<MovieDetails>>()
    val movieDetailsResource: LiveData<Resource<MovieDetails>>
        get() = _movieDetailsResource

    /**
     * Флаг загрузки ресурса
     */
    val isLoading = Transformations.map(_movieDetailsResource) { it is Resource.Loading }

    /**
     * Флаг ошибки загрузки ресурса
     */
    val isError = Transformations.map(_movieDetailsResource) { it is Resource.Error }

    /**
     * Флаг успешности загрузки ресурса
     */
    val isSuccess = Transformations.map(_movieDetailsResource) { it is Resource.Success }

    /**
     * Событие перехода на экран с подробной информацией о человеке
     */
    private val _navigateToPersonDetails = MutableLiveData<Event<Cast>>()
    val navigateToPersonDetails: LiveData<Event<Cast>>
        get() = _navigateToPersonDetails

    /**
     * Событие перехода на экран с полным актерским составом
     */
    private val _navigateToCast = MutableLiveData<Event<Unit>>()
    val navigateToCast: LiveData<Event<Unit>>
        get() = _navigateToCast

    /**
     * Событие перехода на экран с постерами фильма
     */
    private val _navigateToPosters = MutableLiveData<Event<Unit>>()
    val navigateToPosters: LiveData<Event<Unit>>
        get() = _navigateToPosters

    /**
     * Событие перехода на экран просмотра трейлера к фильму
     */
    private val _navigateToPlayer = MutableLiveData<Event<String>>()
    val navigateToPlayer: LiveData<Event<String>>
        get() = _navigateToPlayer

    init {
        getMovieDetails()
    }

    /**
     * Метод для повторной загрузки данных
     */
    fun reload() = getMovieDetails()

    /**
     * Метод для обработки выбора актера, снимавшегося в фильме
     *
     * @param actor выбранный актер
     */
    fun selectCastPerson(actor: Cast) {
        _navigateToPersonDetails.value = Event(actor)
    }

    /**
     * Метод для обработки выбора актерского состава фильма
     */
    fun viewFullCast() {
        _navigateToCast.value = Event(Unit)
    }

    /**
     * Метод для обработки выбора постеров фильма
     */
    fun viewMoviePosters() {
        _navigateToPosters.value = Event(Unit)
    }

    /**
     * Метод для обработки выбора трейлера фильма
     */
    fun viewTrailer() {
        val res = _movieDetailsResource.value
        if (res is Resource.Success) {
            res.data?.officialTrailer?.key?.let { _navigateToPlayer.value = Event(it) }
        }
    }

    private fun getMovieDetails() {
        _movieDetailsResource.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = moviesRepository.getMovieDetails(movieId)
                _movieDetailsResource.value = Resource.Success(result)
            } catch (e: Exception) {
                // TODO обрабатывать соответствующие исключения
                _movieDetailsResource.value = Resource.Error("Error: ${e.message}")
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