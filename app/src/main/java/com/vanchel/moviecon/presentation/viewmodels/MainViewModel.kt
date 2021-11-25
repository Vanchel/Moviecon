package com.vanchel.moviecon.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.rxjava2.cachedIn
import com.vanchel.moviecon.domain.entities.Movie
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.Tv
import com.vanchel.moviecon.domain.repositories.TrendingRepository
import com.vanchel.moviecon.presentation.utils.Event
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
     * Событие перехода на экран с подробной информацией о фильме
     */
    private val _navigateToMovie = MutableLiveData<Event<Movie>>()
    val navigateToMovie: LiveData<Event<Movie>>
        get() = _navigateToMovie

    /**
     * Событие перехода на экран с подробной информацией о сериале
     */
    private val _navigateToTv = MutableLiveData<Event<Tv>>()
    val navigateToTv: LiveData<Event<Tv>>
        get() = _navigateToTv

    /**
     * Событие перехода на экран с подробной информацией о человеке
     */
    private val _navigateToPerson = MutableLiveData<Event<Person>>()
    val navigateToPerson: LiveData<Event<Person>>
        get() = _navigateToPerson

    /**
     * Поток трендовых фильмов для отображения
     */
    val movies = LiveDataReactiveStreams.fromPublisher(
        repository.getTrendingMoviesStream().cachedIn(viewModelScope)
    )

    /**
     * Поток трендовых сериалов для отображения
     */
    val tvs = LiveDataReactiveStreams.fromPublisher(
        repository.getTrendingTvsStream().cachedIn(viewModelScope)
    )

    /**
     * Поток трендовых людей для отображения
     */
    val people = LiveDataReactiveStreams.fromPublisher(
        repository.getTrendingPeopleStream().cachedIn(viewModelScope)
    )

    /**
     * Метод для обработки выбора фильма
     *
     * @param movie выбранный фильм
     */
    fun viewMovie(movie: Movie) {
        _navigateToMovie.value = Event(movie)
    }

    /**
     * Метод для обработки выбора сериала
     *
     * @param tv выбранный сериал
     */
    fun viewTv(tv: Tv) {
        _navigateToTv.value = Event(tv)
    }

    /**
     * Метод для обработки выбора человека
     *
     * @param person выбранный человек
     */
    fun viewPerson(person: Person) {
        _navigateToPerson.value = Event(person)
    }
}