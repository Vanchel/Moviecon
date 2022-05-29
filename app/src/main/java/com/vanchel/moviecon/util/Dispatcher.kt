package com.vanchel.moviecon.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author Иван Тимашов
 *
 *  Интерфейс предоставления обработчиков асинхронных операций
 */
interface Dispatcher {
    /**
     * Обработчик выполнения операций ввода-вывода
     */
    val io: CoroutineDispatcher

    /**
     * Обработчик выполнения на ui-потоке
     */
    val main: CoroutineDispatcher

    /**
     * Обработчик выполнения вычислительных операций
     */
    val default: CoroutineDispatcher

    /**
     * Обработчик выполнения прочих операций
     */
    val unconfined: CoroutineDispatcher
}