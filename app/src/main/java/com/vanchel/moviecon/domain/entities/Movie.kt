package com.vanchel.moviecon.domain.entities

/**
 * @author Иван Тимашов
 *
 * Класс, содержащий краткие данные о фильме.
 *
 * @property id идентификатор записи о фильме
 * @property isAdult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property backdropPath ссылка на обложку фильма
 * @property genreIds список идентификаторов жанров, к которым относится фильм
 * @property originalLanguage исходный язык фильма
 * @property originalTitle исходное название фильма
 * @property overview краткое описание фильма
 * @property title название фильма
 * @property voteAverage средняя оценка фильма
 * @property voteCount количество пользовательских оценок
 * @property popularity популярность фильма
 */
data class Movie(
    val id: Int,
    val isAdult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double
)