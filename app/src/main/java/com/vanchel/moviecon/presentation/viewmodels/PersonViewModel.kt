package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.Credit
import com.vanchel.moviecon.domain.entities.PersonDetails
import com.vanchel.moviecon.domain.repositories.PeopleRepository
import com.vanchel.moviecon.presentation.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "PersonViewModel"

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана подробной информации о человеке.
 *
 * @property peopleRepository репозиторий доступа к данным о людях
 * @property personId идентификатор человека
 */
class PersonViewModel @AssistedInject constructor(
    private val peopleRepository: PeopleRepository,
    @Assisted private val personId: Int
) : ViewModel() {

    /**
     * [Resource], содержащий состояние данных о конкретном человеке.
     */
    private val _personDetailsResource = MutableStateFlow<Resource<PersonDetails>?>(null)
    val personDetailsResource = _personDetailsResource.asStateFlow()

    init {
        getPersonDetails()
    }

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

    /**
     * Метод для повторной загрузки данных
     */
    fun reload() = getPersonDetails()

    /**
     * Метод для обработки выбора картины, в съемках которой человек принимал участие
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

    private fun getPersonDetails() {
        viewModelScope.launch {
            _personDetailsResource.update { Resource.Loading() }
            try {
                val result = peopleRepository.getPersonDetails(personId)
                _personDetailsResource.update { Resource.Success(result) }
            } catch (e: Exception) {
                // TODO обрабатывать соответствующие исключения
                _personDetailsResource.update { Resource.Error("Error: ${e.message}") }
                Log.e(TAG, "getPersonDetails")
                e.printStackTrace()
            }
        }
    }

    companion object {
        /**
         * Метод, предоставляющий [ViewModelProvider.Factory] для создания [PersonViewModel].
         *
         * @param assistedFactory фабрика для предоставления пользовательских зависимостей
         * @param personId идентификатор человека
         * @return [ViewModelProvider.Factory] для создания [PersonViewModel]
         */
        fun provideFactory(assistedFactory: Factory, personId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    if (modelClass.isAssignableFrom(PersonViewModel::class.java)) {
                        return assistedFactory.create(personId) as T
                    }
                    throw IllegalArgumentException("Unable to construct viewModel")
                }
            }
    }

    /**
     * Фабрика, используемая при создании [PersonViewModel] для предоставления
     * пользовательских зависимостей.
     */
    @AssistedFactory
    interface Factory {
        /**
         * Метод, создающий [PersonViewModel], предоставляя ему переданные параметры
         *
         * @param personId идентификатор человека
         * @return новый экземпляр [PersonViewModel]
         */
        fun create(@Assisted personId: Int): PersonViewModel
    }
}