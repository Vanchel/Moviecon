package com.vanchel.moviecon.data.network.models.base

/**
 * @author Иван Тимашов
 *
 * Интерфейс преобразования ответа с сервера в модель доменного слоя, позволяющий использовать
 * дополнительные зависимости для преобразования
 *
 * @param D объект-контейнер, содержащий зависимости для преобразования
 * @param T тип результата преобразования
 */
interface DependantTransformable<D, T> : BaseResponse {

    /**
     * Метод преобразования объекта в объект другого типа
     *
     * @param dependency контейнер с зависимостями для преобразования
     */
    fun transform(dependency: D): T
}