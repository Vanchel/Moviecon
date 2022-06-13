package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.CinematicType
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

private const val TAG = "CastViewModel"

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана актерского состава фильма или сериала.
 *
 * @property moviesRepository репозиторий доступа к данным о фильмах
 * @property tvRepository репозиторий доступа к данным о сериалах
 * @property cinematicId идентификатор картины
 * @property cinematicType тип картины
 */
class CastViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvRepository: TvRepository,
    @Assisted private val cinematicId: Int,
    @Assisted private val cinematicType: CinematicType
) : ViewModel() {

    /**
     * [Resource], содержащий состояние списка актеров
     */
    private val _castResource = MutableStateFlow<Resource<List<Cast>>?>(null)
    val castResource = _castResource.asStateFlow()

    init {
        getCinematicCast()
    }

    /**
     * Метод для повторной загрузки данных
     */
    fun reload() = getCinematicCast()

    private fun getCinematicCast() {
        viewModelScope.launch {
            try {
                _castResource.update { Resource.Loading() }
                val result = when (cinematicType) {
                    CinematicType.MOVIE -> moviesRepository.getMovieCredits(cinematicId)
                    CinematicType.TV -> tvRepository.getTvCredits(cinematicId)
                }
                _castResource.update { Resource.Success(result) }

            } catch (e: Exception) {
                // TODO обрабатывать соответствующие исключения
                _castResource.update { Resource.Error("Error: ${e.message}") }
                Log.e(TAG, "getCinematicCast: ${e.message}")
            }
        }
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
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
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