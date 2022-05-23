package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.Image
import com.vanchel.moviecon.domain.entities.Images
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

private const val TAG = "PostersViewModel"

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана, содержащего список постеров.
 *
 * @property moviesRepository репозиторий доступа к данным о фильмах
 * @property tvRepository репозиторий доступа к данным о сериалах
 * @property schedulers планировщики для выполнения асинхронных задач
 * @property cinematicId идентификатор картины
 * @property cinematicType тип картины
 */
class PostersViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvRepository: TvRepository,
    private val schedulers: Schedulers,
    @Assisted private val cinematicId: Int,
    @Assisted private val cinematicType: CinematicType
) : ViewModel() {
    private val disposable = CompositeDisposable()

    /**
     * [Resource], содержащий состояние данных о постерах.
     */
    private val _postersResource = MutableLiveData<Resource<List<Image>>>()
    val postersResource: LiveData<Resource<List<Image>>>
        get() = _postersResource

    /**
     * Флаг загрузки ресурса
     */
    val isLoading = Transformations.map(_postersResource) { it is Resource.Loading }

    /**
     * Флаг ошибки загрузки ресурса
     */
    val isError = Transformations.map(_postersResource) { it is Resource.Error }

    /**
     * Флаг успешности загрузки ресурса
     */
    val isSuccess = Transformations.map(_postersResource) { it is Resource.Success }

    /**
     * Событие перехода на экран с подробной информацией о постере
     */
    private val _navigateToPoster = MutableLiveData<Event<Image>>()
    val navigateToPoster: LiveData<Event<Image>>
        get() = _navigateToPoster

    init {
        getPosters()
    }

    /**
     * Метод для повторной загрузки данных
     */
    fun reload() = getPosters()

    /**
     * Метод для обработки выбора постера
     *
     * @param image выбранный постер
     */
    fun selectPoster(image: Image) {
        _navigateToPoster.value = Event(image)
    }

    private fun getPosters() {
        _postersResource.value = Resource.Loading()
        val single = when (cinematicType) {
            CinematicType.MOVIE -> moviesRepository.getMovieImages(cinematicId)
            CinematicType.TV -> tvRepository.getTvImages(cinematicId)
        }
        single.subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
            .subscribe(object : SingleObserver<Images> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onSuccess(t: Images) {
                    _postersResource.value = Resource.Success(t.posters)
                }

                override fun onError(e: Throwable) {
                    _postersResource.value = Resource.Error("Error: ${e.message}")
                    Log.e(TAG, "getPosters: ${e.message}")
                }
            })
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    companion object {
        /**
         * Метод, предоставляющий [ViewModelProvider.Factory] для создания [PostersViewModel].
         *
         * @param assistedFactory фабрика для предоставления пользовательских зависимостей
         * @param cinematicId идентификатор картины
         * @param cinematicType тип картины
         * @return [ViewModelProvider.Factory] для создания [PostersViewModel]
         */
        fun provideFactory(
            assistedFactory: Factory, cinematicId: Int, cinematicType: CinematicType
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                if (modelClass.isAssignableFrom(PostersViewModel::class.java)) {
                    return assistedFactory.create(cinematicId, cinematicType) as T
                }
                throw IllegalArgumentException("Unable to construct viewModel")
            }
        }
    }

    /**
     * Фабрика, используемая при создании [PostersViewModel] для предоставления
     * пользовательских зависимостей.
     */
    @AssistedFactory
    interface Factory {
        /**
         * Метод, создающий [PostersViewModel], предоставляя ему переданные параметры
         *
         * @param cinematicId идентификатор картины
         * @param cinematicType тип картины
         * @return новый экземпляр [PostersViewModel]
         */
        fun create(
            @Assisted cinematicId: Int,
            @Assisted cinematicType: CinematicType
        ): PostersViewModel
    }
}