package com.vanchel.moviecon.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vanchel.moviecon.domain.entities.TvDetails
import com.vanchel.moviecon.domain.repositories.TvRepository
import com.vanchel.moviecon.presentation.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "TvViewModel"

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана подробной информации о сериале.
 *
 * @property tvRepository репозиторий доступа к данным о сериалах
 * @property tvId идентификатор сериала
 */
class TvViewModel @AssistedInject constructor(
    private val tvRepository: TvRepository,
    @Assisted private val tvId: Int
) : ViewModel() {

    /**
     * [Resource], содержащий состояние данных о конкретном сериале.
     */
    private val _tvDetailsResource = MutableStateFlow<Resource<TvDetails>?>(null)
    val tvDetailsResource = _tvDetailsResource.asStateFlow()

    init {
        getTvDetails()
    }

    /**
     * Метод для повторной загрузки данных
     */
    fun reload() = getTvDetails()

    private fun getTvDetails() {
        viewModelScope.launch {
            _tvDetailsResource.update { Resource.Loading() }
            try {
                val result = tvRepository.getTvDetails(tvId)
                _tvDetailsResource.update { Resource.Success(result) }
            } catch (e: Exception) {
                // TODO обрабатывать соответствующие исключения
                _tvDetailsResource.update { Resource.Error("Error: ${e.message}") }
                Log.e(TAG, "getTvDetails: ${e.message}")
            }
        }
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
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
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