package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.repositories.MoviesRepository
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

private const val TAG = "CastViewModel"

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана актерского состава фильма или сериала.
 *
 * @property moviesRepository репозиторий доступа к данным о фильмах
 * @property tvRepository репозиторий доступа к данным о сериалах
 * @property schedulers планировщики для выполнения асинхронных задач
 * @property cinematicId идентификатор картины
 * @property cinematicType тип картины
 */
class CastViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvRepository: TvRepository,
    private val schedulers: Schedulers,
    @Assisted private val cinematicId: Int,
    @Assisted private val cinematicType: CinematicType
) : ViewModel() {
    private val disposable = CompositeDisposable()

    /**
     * [Resource], содержащий состояние списка актеров
     */
    private val _castResource = MutableLiveData<Resource<List<Cast>>>()
    val castResource: LiveData<Resource<List<Cast>>>
        get() = _castResource

    /**
     * Флаг загрузки ресурса
     */
    val isLoading = Transformations.map(_castResource) { it is Resource.Loading }

    /**
     * Флаг ошибки загрузки ресурса
     */
    val isError = Transformations.map(_castResource) { it is Resource.Error }

    /**
     * Флаг успешности загрузки ресурса
     */
    val isSuccess = Transformations.map(_castResource) { it is Resource.Success }

    /**
     * Событие перехода на экран с подробной информацией об актере
     */
    private val _navigateToPersonDetails = MutableLiveData<Event<Cast>>()
    val navigateToPersonDetails: LiveData<Event<Cast>>
        get() = _navigateToPersonDetails

    init {
        getCinematicCast()
    }

    /**
     * Метод для повторной загрузки данных
     */
    fun reload() = getCinematicCast()

    /**
     * Метод для обработки выбора актера
     *
     * @param actor выбранный актер
     */
    fun selectCastPerson(actor: Cast) {
        _navigateToPersonDetails.value = Event(actor)
    }

    private fun getCinematicCast() {
        _castResource.value = Resource.Loading()
        val single = when (cinematicType) {
            CinematicType.MOVIE -> moviesRepository.getMovieCredits(cinematicId)
            CinematicType.TV -> tvRepository.getTvCredits(cinematicId)
        }
        single.subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
            .subscribe(object : SingleObserver<List<Cast>> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onSuccess(t: List<Cast>) {
                    _castResource.value = Resource.Success(t)
                }

                override fun onError(e: Throwable) {
                    _castResource.value = Resource.Error("Error: ${e.message}")
                    Log.e(TAG, "getCinematicCast: ${e.message}")
                }
            })
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    companion object {
        /**
         * Метод, предоставляющий [ViewModelProvider.Factory] для создания [CastViewModel].
         *
         * @param assistedFactory фабрика для предоставления пользовательских зависимостей
         * @param cinematicId идентификатор картины
         * @param cinematicType тип картины
         * @return [ViewModelProvider.Factory] для создания [CastViewModel]
         */
        fun provideFactory(
            assistedFactory: Factory, cinematicId: Int, cinematicType: CinematicType
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                if (modelClass.isAssignableFrom(CastViewModel::class.java)) {
                    return assistedFactory.create(cinematicId, cinematicType) as T
                }
                throw IllegalArgumentException("Unable to construct viewModel")
            }
        }
    }

    /**
     * Фабрика, используемая при создании [CastViewModel] для предоставления пользовательских
     * зависимостей.
     */
    @AssistedFactory
    interface Factory {
        /**
         * Метод, создающий [CastViewModel], предоставляя ему переданные параметры
         *
         * @param cinematicId идентификатор картины
         * @param cinematicType тип картины
         * @return новый экземпляр [CastViewModel]
         */
        fun create(
            @Assisted cinematicId: Int,
            @Assisted cinematicType: CinematicType
        ): CastViewModel
    }
}