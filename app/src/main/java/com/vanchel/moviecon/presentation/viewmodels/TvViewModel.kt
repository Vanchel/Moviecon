package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.TvDetails
import com.vanchel.moviecon.domain.repositories.TvRepository
import com.vanchel.moviecon.presentation.utils.Event
import com.vanchel.moviecon.presentation.utils.Resource
import com.vanchel.moviecon.util.Schedulers
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

private const val TAG = "TvViewModel"

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана подробной информации о сериале.
 *
 * @property tvRepository репозиторий доступа к данным о сериалах
 * @property schedulers планировщики для выполнения асинхронных задач
 * @property tvId идентификатор сериала
 */
class TvViewModel @AssistedInject constructor(
    private val tvRepository: TvRepository,
    private val schedulers: Schedulers,
    @Assisted private val tvId: Int
) : ViewModel() {
    private val disposable = CompositeDisposable()

    /**
     * [Resource], содержащий состояние данных о конкретном сериале.
     */
    private val _tvDetailsResource = MutableLiveData<Resource<TvDetails>>()
    val tvDetailsResource: LiveData<Resource<TvDetails>>
        get() = _tvDetailsResource

    /**
     * Флаг загрузки ресурса
     */
    val isLoading = Transformations.map(_tvDetailsResource) { it is Resource.Loading }

    /**
     * Флаг ошибки загрузки ресурса
     */
    val isError = Transformations.map(_tvDetailsResource) { it is Resource.Error }

    /**
     * Флаг успешности загрузки ресурса
     */
    val isSuccess = Transformations.map(_tvDetailsResource) { it is Resource.Success }

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
     * Событие перехода на экран с постерами сериала
     */
    private val _navigateToPosters = MutableLiveData<Event<Unit>>()
    val navigateToPosters: LiveData<Event<Unit>>
        get() = _navigateToPosters

    /**
     * Событие перехода на экран просмотра трейлера к сериалу
     */
    private val _navigateToPlayer = MutableLiveData<Event<String>>()
    val navigateToPlayer: LiveData<Event<String>>
        get() = _navigateToPlayer

    init {
        getTvDetails()
    }

    /**
     * Метод для повторной загрузки данных
     */
    fun reload() = getTvDetails()

    /**
     * Метод для обработки выбора актера, снимавшегося в сериале
     *
     * @param actor выбранный актер
     */
    fun selectCastPerson(actor: Cast) {
        _navigateToPersonDetails.value = Event(actor)
    }

    /**
     * Метод для обработки выбора актерского состава сериала
     */
    fun viewFullCast() {
        _navigateToCast.value = Event(Unit)
    }

    /**
     * Метод для обработки выбора постеров сериала
     */
    fun viewMoviePosters() {
        _navigateToPosters.value = Event(Unit)
    }

    /**
     * Метод для обработки выбора трейлера сериала
     */
    fun viewTrailer() {
        val res = _tvDetailsResource.value
        if (res is Resource.Success) {
            res.data?.officialTrailer?.key?.let { _navigateToPlayer.value = Event(it) }
        }
    }

    private fun getTvDetails() {
        _tvDetailsResource.value = Resource.Loading()
        tvRepository.getTvDetails(tvId)
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
            .subscribe(object : SingleObserver<TvDetails> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onSuccess(t: TvDetails) {
                    _tvDetailsResource.value = Resource.Success(t)
                }

                override fun onError(e: Throwable) {
                    _tvDetailsResource.value = Resource.Error("Error: ${e.message}")
                    Log.e(TAG, "getTvDetails: ${e.message}")
                }
            })
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    companion object {
        /**
         * Метод, предоставляющий [ViewModelProvider.Factory] для создания [TvViewModel].
         *
         * @param assistedFactory фабрика для предоставления пользовательских зависимостей
         * @param tvId идентификатор сериала
         * @return [ViewModelProvider.Factory] для создания [TvViewModel]
         */
        fun provideFactory(assistedFactory: Factory, tvId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    if (modelClass.isAssignableFrom(TvViewModel::class.java)) {
                        return assistedFactory.create(tvId) as T
                    }
                    throw IllegalArgumentException("Unable to construct viewModel")
                }
            }
    }

    /**
     * Фабрика, используемая при создании [TvViewModel] для предоставления
     * пользовательских зависимостей.
     */
    @AssistedFactory
    interface Factory {
        /**
         * Метод, создающий [TvViewModel], предоставляя ему переданные параметры
         *
         * @param tvId идентификатор сериала
         * @return новый экземпляр [TvViewModel]
         */
        fun create(@Assisted tvId: Int): TvViewModel
    }
}