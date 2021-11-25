package com.vanchel.moviecon.data.network.models

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные о жанре картины.
 *
 * @property id идентификатор жанра
 * @property name название жанра
 */
data class GenreJsonResult(
    val id: Int,
    val name: String
)