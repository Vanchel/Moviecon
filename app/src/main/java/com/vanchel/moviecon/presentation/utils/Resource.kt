package com.vanchel.moviecon.presentation.utils

/**
 * @author Иван Тимашов
 *
 * Класс, представляющий собой обертку над данными и статус успешности их получения.
 *
 * @param T тип данных, хранымых в классе
 * @property data данные, хранимые в классе
 * @property message сообщение о статусе успешности получения данных
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    /**
     * Наследник [Resource], представляющий собой успешный результат обращения к данным.
     *
     * @param T тип данных, хранимых в классе
     * @constructor создает экземпляр успешного результата
     *
     * @param data данные, хранимые в классе
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Наследник [Resource], представляющий собой еще не полученный результат обращения к данным.
     *
     * @param T тип данных, хранимых в классе
     * @constructor создает экземпляр еще не полученного результата
     *
     * @param data данные, хранимые в классе
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)

    /**
     * Наследник [Resource], представляющий собой неудачный результат обращения к данным.
     *
     * @param T тип данных, хранимых в классе
     * @constructor создает экземпляр неудачного результата
     *
     * @param message сообщение об ошибке получения данных
     * @param data данные, хранимые в классе
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}