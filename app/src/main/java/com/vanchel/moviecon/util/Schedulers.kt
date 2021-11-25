package com.vanchel.moviecon.util

import io.reactivex.Scheduler

/**
 * @author Иван Тимашов
 *
 *  Интерфейс предоставления обработчиков асинхронных операций
 */
interface Schedulers {
    /**
     * Обработчик выполнения операций ввода-вывода
     */
    val io: Scheduler

    /**
     * Основной обработчик выполнения
     */
    val ui: Scheduler
}