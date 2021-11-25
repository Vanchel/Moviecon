package com.vanchel.moviecon.data

/**
 * @author Иван Тимашов
 *
 * Интерфейс конвертера объекта слоя данных в объект доменного слоя.
 *
 * @param E Тип объекта доменного слоя
 * @param O Тип объекта слоя данных
 */
interface EntityConverter<E, O> {
    /**
     * Преобразует объект типа [O] в объект типа [E].
     *
     * @param dto Data Transfer Object для преобразования
     * @return Преобразованный объект
     */
    fun toDomainModel(dto: O): E
}