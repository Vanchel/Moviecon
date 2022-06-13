package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.Credit
import com.vanchel.moviecon.domain.repositories.PeopleRepository
import com.vanchel.moviecon.presentation.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "FilmographyViewModel"


/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана фильмографии актера.
 *
 * @property peopleRepository репозиторий доступа к данным о людях
 * @property personId идентификатор человека
 */
class FilmographyViewModel @AssistedInject constructor(
    private val peopleRepository: PeopleRepository,
    @Assisted private val personId: Int
) : ViewModel() {

    /**
     * [Resource], содержащий состояние списка фильмов и сериалов, в съемках
     * которых человек принимал участие.
     */
    private val _creditsResource = MutableStateFlow<Resource<List<Credit>>?>(null)
    val creditsResource = _creditsResource.asStateFlow()

    /**
     * Выбранный фильм
     */
    private val _selectedMovie = MutableStateFlow<Credit?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    /**
     * Выбранный сериал
     */
    private val _selectedTv = MutableStateFlow<Credit?>(null)
    val selectedTv = _selectedTv.asStateFlow()

    init {
        getCredits()
    }

    /**
     * Метод для повторной загрузки данных
     */
    fun reload() = getCredits()

    /**
     * Метод для обработки выбора фильма или сериала из фильмографии
     *
     * @param credit выбранная картина
     */
    fun viewCredit(credit: Credit) {
        when (credit.type) {
            CinematicType.MOVIE -> _selectedMovie.update { credit }
            CinematicType.TV -> _selectedTv.update { credit }
        }
    }

    /**
     * Метод для сброса выбранного фильма
     */
    fun selectedMovieProcessed() {
        _selectedMovie.update { null }
    }

    /**
     * Метод для сброса выбранного сериала
     */
    fun selectedTvProcessed() {
        _selectedTv.update { null }
    }

    private fun getCredits() {
        viewModelScope.launch {
            try {
                _creditsResource.update { Resource.Loading() }
                val result = peopleRepository.getPersonDetails(personId)
                val sortedCredits = result.credits.sortedByDescending(Credit::date)
                _creditsResource.update { Resource.Success(sortedCredits) }
            } catch (e: Exception) {
                // TODO обрабатывать соответствующие исключения
                _creditsResource.update { Resource.Error("Error: ${e.message}") }
                Log.e(TAG, "getPersonDetails")
                e.printStackTrace()
            }
        }
    }

    companion object {
        /**
         * Метод, предоставляющий [ViewModelProvider.Factory] для создания [FilmographyViewModel].
         *
         * @param assistedFactory фабрика для предоставления пользовательских зависимостей
         * @param personId идентификатор человека
         * @return [ViewModelProvider.Factory] для создания [FilmographyViewModel]
         */
        fun provideFactory(assistedFactory: Factory, personId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    if (modelClass.isAssignableFrom(FilmographyViewModel::class.java)) {
                        return assistedFactory.create(personId) as T
                    }
                    throw IllegalArgumentException("Unable to construct viewModel")
                }
            }
    }

    /**
     * Фабрика, используемая при создании [FilmographyViewModel] для предоставления
     * пользовательских зависимостей.
     */
    @AssistedFactory
    interface Factory {
        /**
         * Метод, создающий [FilmographyViewModel], предоставляя ему переданные параметры
         *
         * @param personId идентификатор человека
         * @return новый экземпляр [FilmographyViewModel]
         */
        fun create(@Assisted personId: Int): FilmographyViewModel
    }
}