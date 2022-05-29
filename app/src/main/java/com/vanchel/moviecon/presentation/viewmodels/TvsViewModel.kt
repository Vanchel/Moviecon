package com.vanchel.moviecon.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vanchel.moviecon.domain.entities.TvType
import com.vanchel.moviecon.domain.repositories.TvRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана, содержащего список сериалов.
 *
 * @constructor создает экземпляр [ViewModel] экрана списка сериалов
 *
 * @param repository репозиторий доступа к данным о сериалах
 * @param type тип сериалов
 */
class TvsViewModel @AssistedInject constructor(
    repository: TvRepository,
    @Assisted type: TvType
) : ViewModel() {
    /**
     * Поток сериалов для отображения
     */
    val tvs = repository.getTvsStream(type)
        .cachedIn(viewModelScope)
        .asLiveData()

    companion object {
        /**
         * Метод, предоставляющий [ViewModelProvider.Factory] для создания [TvsViewModel].
         *
         * @param assistedFactory фабрика для предоставления пользовательских зависимостей
         * @param type тип сериалов
         * @return [ViewModelProvider.Factory] для создания [TvsViewModel]
         */
        fun provideFactory(assistedFactory: Factory, type: TvType): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    if (modelClass.isAssignableFrom(TvsViewModel::class.java)) {
                        return assistedFactory.create(type) as T
                    }
                    throw IllegalArgumentException("Unable to construct viewModel")
                }
            }
    }

    /**
     * Фабрика, используемая при создании [TvsViewModel] для предоставления
     * пользовательских зависимостей.
     */
    @AssistedFactory
    interface Factory {
        /**
         * Метод, создающий [TvsViewModel], предоставляя ему переданные параметры
         *
         * @param tvType тип сериалов
         * @return новый экземпляр [TvsViewModel]
         */
        fun create(@Assisted tvType: TvType): TvsViewModel
    }
}