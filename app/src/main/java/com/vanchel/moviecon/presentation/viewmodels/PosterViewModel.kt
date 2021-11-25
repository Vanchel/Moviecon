package com.vanchel.moviecon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vanchel.moviecon.presentation.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * [ViewModel] экрана приложения с постером.
 */
@HiltViewModel
class PosterViewModel @Inject constructor() : ViewModel() {
    /**
     * Событие инициации сохранения постера в галерею
     */
    private val _saveToGallery = MutableLiveData<Event<Unit>>()
    val saveToGallery: LiveData<Event<Unit>>
        get() = _saveToGallery

    /**
     * Событие завершения сохранения постера в галерею
     */
    private val _savedToGallery = MutableLiveData<Event<Unit>>()
    val savedToGallery: LiveData<Event<Unit>>
        get() = _savedToGallery

    /**
     * Флаг доступности изображения для сохранения
     */
    private val _isImageAvailable = MutableLiveData<Boolean>()
    val isImageAvailable: LiveData<Boolean>
        get() = _isImageAvailable

    /**
     * Метод для обработки выбора опции сохранения постера
     */
    fun saveToGallery() {
        _saveToGallery.value = Event(Unit)
    }

    /**
     * Метод для обработки успешной загрузки постера
     */
    fun imageLoadSuccess() {
        _isImageAvailable.value = true
    }

    /**
     * Метод для обработки успешного сохранения постера
     */
    fun imageSaveSuccess() {
        _savedToGallery.value = Event(Unit)
    }
}