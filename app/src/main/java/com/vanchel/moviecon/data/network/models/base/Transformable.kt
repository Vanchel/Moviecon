package com.vanchel.moviecon.data.network.models.base

/**
 * @author Иван Тимашов
 *
 * Интерфейс преобразования ответа с сервера в модель доменного слоя
 *
 * @param T тип результата преобразования
 */
interface Transformable<T> : BaseResponse {

    /**
     * Метод преобразования объекта в объект другого типа
     */
    fun transform(): T
}