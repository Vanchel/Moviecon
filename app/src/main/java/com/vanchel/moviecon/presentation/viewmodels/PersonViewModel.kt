package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.Credit
import com.vanchel.moviecon.domain.entities.PersonDetails
import com.vanchel.moviecon.domain.repositories.PeopleRepository
import com.vanchel.moviecon.presentation.utils.Event
import com.vanchel.moviecon.presentation.utils.Resource
import com.vanchel.moviecon.util.Schedulers
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

private const val TAG = "PersonViewModel"

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана подробной информации о человеке.
 *
 * @property peopleRepository репозиторий доступа к данным о людях
 * @property schedulers планировщики для выполнения асинхронных задач
 * @property personId идентификатор человека
 */
class PersonViewModel @AssistedInject constructor(
    private val peopleRepository: PeopleRepository,
    private val schedulers: Schedulers,
    @Assisted private val personId: Int
) : ViewModel() {
    private val disposable = CompositeDisposable()

    /**
     * [Resource], содержащий состояние данных о конкретном человеке.
     */
    private val _personDetailsResource = MutableLiveData<Resource<PersonDetails>>()
    val personDetailsResource: LiveData<Resource<PersonDetails>>
        get() = _personDetailsResource

    /**
     * Флаг загрузки ресурса
     */
    val isLoading = Transformations.map(_personDetailsResource) { it is Resource.Loading }

    /**
     * Флаг ошибки загрузки ресурса
     */
    val isError = Transformations.map(_personDetailsResource) { it is Resource.Error }

    /**
     * Флаг успешности загрузки ресурса
     */
    val isSuccess = Transformations.map(_personDetailsResource) { it is Resource.Success }

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

    /**
     * Событие перехода в instagram человека
     */
    private val _navigateToInstagram = MutableLiveData<Event<String>>()
    val navigateToInstagram: LiveData<Event<String>>
        get() = _navigateToInstagram

    /**
     * Событие перехода к фильмографии человека
     */
    private val _navigateToFilmography = MutableLiveData<Event<Unit>>()
    val navigateToFilmography: LiveData<Event<Unit>>
        get() = _navigateToFilmography

    init {
        getPersonDetails()
    }

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
            CinematicType.MOVIE -> _navigateToMovie.value = Event(credit)
            CinematicType.TV -> _navigateToTv.value = Event(credit)
        }
    }

    /**
     * Метод для обработки выбора профиля instagram
     */
    fun viewInstagramProfile() {
        val res = _personDetailsResource.value
        if (res is Resource.Success) {
            res.data?.instagramId?.let { _navigateToInstagram.value = Event(it) }
        }
    }

    /**
     * Метод для обработки выбора фильмографии человека
     */
    fun viewFilmography() {
        _navigateToFilmography.value = Event(Unit)
    }

    private fun getPersonDetails() {
        _personDetailsResource.value = Resource.Loading()
        peopleRepository.getPersonDetails(personId)
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
            .subscribe(object : SingleObserver<PersonDetails> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onSuccess(t: PersonDetails) {
                    _personDetailsResource.value = Resource.Success(t)
                }

                override fun onError(e: Throwable) {
                    _personDetailsResource.value = Resource.Error("Error: ${e.message}")
                    Log.e(TAG, "getPersonDetails")
                    e.printStackTrace()
                }
            })
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
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
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
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