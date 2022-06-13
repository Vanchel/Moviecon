package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.Image
import com.vanchel.moviecon.domain.repositories.MoviesRepository
import com.vanchel.moviecon.domain.repositories.TvRepository
import com.vanchel.moviecon.presentation.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "PostersViewModel"

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана, содержащего список постеров.
 *
 * @property moviesRepository репозиторий доступа к данным о фильмах
 * @property tvRepository репозиторий доступа к данным о сериалах
 * @property cinematicId идентификатор картины
 * @property cinematicType тип картины
 */
class PostersViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvRepository: TvRepository,
    @Assisted private val cinematicId: Int,
    @Assisted private val cinematicType: CinematicType
) : ViewModel() {
    /**
     * [Resource], содержащий состояние данных о постерах.
     */
    private val _postersResource = MutableStateFlow<Resource<List<Image>>?>(null)
    val postersResource = _postersResource.asStateFlow()

    init {
        getPosters()
    }

    /**
     * Метод для повторной загрузки данных
     */
    fun reload() = getPosters()

    private fun getPosters() {
        viewModelScope.launch {
            _postersResource.update { Resource.Loading() }
            try {
                val result = when (cinematicType) {
                    CinematicType.MOVIE -> moviesRepository.getMovieImages(cinematicId)
                    CinematicType.TV -> tvRepository.getTvImages(cinematicId)
                }
                _postersResource.update { Resource.Success(result.posters) }
            } catch (e: Exception) {
                // TODO обрабатывать соответствующие исключения
                _postersResource.update { Resource.Error("Error: ${e.message}") }
                Log.e(TAG, "getPosters: ${e.message}")
            }
        }
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