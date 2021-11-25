package com.vanchel.moviecon.presentation.utils

import androidx.lifecycle.LiveData

/**
 * @author Иван Тимашов
 *
 * Используется как оболочка для данных, представляющих событие и предоставляемых через
 * [LiveData].
 *
 * @param T Тип данных, хранимых в [LiveData]
 * @constructor Создает новый экземпляр [Event]
 * @property content Данные, представляющие собой событие
 */
open class Event<out T>(private val content: T) {

    /**
     * Флаг, позволяющий узнать, было ли данное событие обработано.
     */
    var hasBeenHandled = false
        private set

    /**
     * Метод, предоставлящий данные о событии всего один раз.
     *
     * @return Данные о событии или null, если собыие обработано
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Метод, предоставляющий данные о событии и позволяющий получить их снова в будущем.
     *
     * @return Данные о событии
     */
    fun peekContent(): T = content
}