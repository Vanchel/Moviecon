package com.vanchel.moviecon.domain.entities

import java.util.*

/**
 *  @author Иван Тимашов
 *
 *  Класс, содержащий краткие данные о сериале.
 *
 * @property id идентификатор записи о сериале
 * @property name название сериала
 * @property popularity популярность сериала
 * @property originalName исходное название сериала
 * @property originalCountry страна-производитель сериала
 * @property originalLanguage исходный язык сериала
 * @property overview краткое описание сериала
 * @property voteCount количество пользовательских оценок
 * @property firstAirDate дата выпуска пилотного эпизода сериала
 * @property backdropPath ссылка на обложку сериала
 * @property voteAverage средняя оценка сериала
 * @property genreIds список идентификаторов жанров, к которым относится сериал
 * @property posterPath ссылка на постер сериала
 */
data class Tv(
    val id: Int,
    val name: String,
    val popularity: Double,
    val originalName: String,
    val originalCountry: List<String>,
    val originalLanguage: String,
    val overview: String,
    val voteCount: Int,
    val firstAirDate: Date?,
    val backdropPath: String?,
    val voteAverage: Double,
    val genreIds: List<Int>,
    val posterPath: String?
)