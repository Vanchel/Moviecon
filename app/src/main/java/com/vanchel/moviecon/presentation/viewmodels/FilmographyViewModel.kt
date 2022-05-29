package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.Credit
import com.vanchel.moviecon.domain.repositories.PeopleRepository
import com.vanchel.moviecon.presentation.utils.Event
import com.vanchel.moviecon.presentation.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
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
    private val _creditsResource = MutableLiveData<Resource<List<Credit>>>()
    val creditsResource: LiveData<Resource<List<Credit>>>
        get() = _creditsResource

    /**
     * Флаг загрузки ресурса
     */
    val isLoading = Transformations.map(_creditsResource) { it is Resource.Loading }

    /**
     * Флаг ошибки загрузки ресурса
     */
    val isError = Transformations.map(_creditsResource) { it is Resource.Error }

    /**
     * Флаг успешности загрузки ресурса
     */
    val isSuccess = Transformations.map(_creditsResource) { it is Resource.Success }

    /**
     * Событие перехода на экран с подробной информацией о фильме
     */
    private val _navigateToMovie = MutableLiveData<Event<Credit>>()
    val navigateToMovie: LiveData<Event<Credit>>
        get() = _navigateToMovie

    /**
     * Событие перехода на экран с подробной информацией о сериале
     */
    private val _navigateToTv = MutableLiveData<Event<Credit>>()
    val navigateToTv: LiveData<Event<Credit>>
        get() = _navigateToTv

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
            CinematicType.MOVIE -> _navigateToMovie.value = Event(credit)
            CinematicType.TV -> _navigateToTv.value = Event(credit)
        }
    }

    private fun getCredits() {
        _creditsResource.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = peopleRepository.getPersonDetails(personId)
                val sortedCredits = result.credits.sortedByDescending(Credit::date)
                _creditsResource.value = Resource.Success(sortedCredits)
            } catch (e: Exception) {
                // TODO обрабатывать соответствующие исключения
                _creditsResource.value = Resource.Error("Error: ${e.message}")
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