package com.vanchel.moviecon.domain.entities

import java.util.*

private const val DEFAULT_TOP_CAST_LIMIT = 10

/**
 * @author Иван Тимашов
 *
 * Класс, содержащий подробные данные о сериале.
 *
 * @property id идентификатор записи о сериале
 * @property name название сериала
 * @property popularity популярность сериала
 * @property originalName исходное название сериала
 * @property originalCountry страна-производитель сериала
 * @property originalLanguage исходный язык фильма
 * @property overview краткое описание сериала
 * @property voteCount количество пользовательских оценок
 * @property firstAirDate дата выпуска пилотного эпизода сериала
 * @property lastAirDate дата выпуска последнего эпизода сериала
 * @property numberOfEpisodes число эпизодов в сериале
 * @property numberOfSeasons число сезонов в сериале
 * @property backdropPath ссылка на обложку сериала
 * @property creatorNames список имен создателей сериала
 * @property episodeRunTime список хронометражей эпизодов сериала
 * @property genreNames список названий жанров, к которым относится сериал
 * @property homepage ссылка на собственную страницу сериала
 * @property inProduction флаг, показывающий, ведутся ли до сих пор съемки сериала
 * @property languages список языков
 * @property voteAverage средняя оценка сериала
 * @property genreIds список идентификаторов жанров, к которым относится сериал
 * @property status статус сериала
 * @property tagline слоган сериала
 * @property type тип сериала
 * @property posterPath ссылка на постер сериала
 * @property videos список видеозаписей, относящихся к сериалу
 * @property cast актерский состав сериала
 */
data class TvDetails(
    val id: Int,
    val name: String,
    val popularity: Double,
    val originalName: String,
    val originalCountry: List<String>,
    val originalLanguage: String,
    val overview: String,
    val voteCount: Int,
    val firstAirDate: Date?,
    val lastAirDate: Date?,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val backdropPath: String?,
    val creatorNames: List<String>,
    val episodeRunTime: List<Int>,
    val genreNames: List<String>,
    val homepage: String,
    val inProduction: Boolean,
    val languages: List<String>,
    val voteAverage: Double,
    val genreIds: List<Int>,
    val status: CinematicStatus,
    val tagline: String,
    val type: String,
    val posterPath: String?,
    val videos: List<Video>?,
    val cast: List<Cast>?
) {
    /**
     * Официальный трейлер к сериалу, если таковой есть
     */
    val officialTrailer: Video?
        get() = videos?.find(Video::isOfficial)

    /**
     * Метод получения списка актеров, отсортированный по их популярности
     *
     * @param limit максимальное количество результатов
     * @return список популярных актеров
     */
    fun getTopCast(limit: Int = DEFAULT_TOP_CAST_LIMIT): List<Cast>? {
        return cast?.sortedByDescending(Cast::popularity)?.take(limit)
    }
}