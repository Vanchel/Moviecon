package com.vanchel.moviecon.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vanchel.moviecon.domain.entities.PersonType
import com.vanchel.moviecon.domain.repositories.PeopleRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана, содержащего список людей.
 *
 * @constructor создает экземпляр [ViewModel] экрана списка людей
 *
 * @param repository репозиторий доступа к данным о людях
 * @param type тип людей
 */
class PeopleViewModel @AssistedInject constructor(
    repository: PeopleRepository,
    @Assisted type: PersonType
) : ViewModel() {
    /**
     * Поток людей для отображения
     */
    val people = repository.getPeopleStream(type)
        .cachedIn(viewModelScope)
        .asLiveData()

    companion object {
        /**
         * Метод, предоставляющий [ViewModelProvider.Factory] для создания [PeopleViewModel].
         *
         * @param assistedFactory фабрика для предоставления пользовательских зависимостей
         * @param type тип людей
         * @return [ViewModelProvider.Factory] для создания [PeopleViewModel]
         */
        fun provideFactory(assistedFactory: Factory, type: PersonType): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    if (modelClass.isAssignableFrom(PeopleViewModel::class.java)) {
                        return assistedFactory.create(type) as T
                    }
                    throw IllegalArgumentException("Unable to construct viewModel")
                }
            }
    }

    /**
     * Фабрика, используемая при создании [PeopleViewModel] для предоставления
     * пользовательских зависимостей.
     */
    @AssistedFactory
    interface Factory {
        /**
         * Метод, создающий [PeopleViewModel], предоставляя ему переданные параметры
         *
         * @param personType тип людей
         * @return новый экземпляр [PeopleViewModel]
         */
        fun create(@Assisted personType: PersonType): PeopleViewModel
    }
}