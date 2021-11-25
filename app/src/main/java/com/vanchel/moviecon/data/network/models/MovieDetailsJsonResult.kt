package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB подробные сведения о фильме.
 *
 * @property adult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property backdropPath ссылка на обложку фильма
 * @property belongsToCollection коллекция, которой принадлежит фильм
 * @property budget бюджет фильма
 * @property genres список жанров, к которым относится фильм
 * @property homepage ссылка на собственную страницу фильма
 * @property id идентификатор записи о фильме
 * @property imdbId идентификатор фильма на сервисе IMDb
 * @property originalLanguage исходный язык фильма
 * @property originalTitle исходное название фильма
 * @property overview краткое описание фильма
 * @property popularity популярность фильма
 * @property posterPath ссылка на постер фильма
 * @property productionCompanies список компаний, занимающихся продюсированием фильма
 * @property productionCountries список стран, в которых продюсируется фильм
 * @property releaseDate дата выхода фильма
 * @property revenue кассовые сборы фильма
 * @property runtime продолжительность фильма
 * @property spokenLanguages языки, на которые переведен фильм
 * @property status статус фильма
 * @property tagline слоган фильма
 * @property title название фильма
 * @property video флаг, показывающий, доступен ли фильм в виде видеозаписи на сервисе TMDB
 * @property voteAverage средняя оценка фильма
 * @property voteCount количество пользовательских оценок
 * @property videos список видеозаписей, относящихся к фильму
 * @property credits съемочная группа и актерский состав фильма
 */
data class MovieDetailsJsonResult(
    val adult: Boolean,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "belongs_to_collection") val belongsToCollection: CollectionJsonResult?,
    val budget: Int,
    val genres: List<GenreJsonResult>,
    val homepage: String?,
    val id: Int,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompanyJsonResult>,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountryJsonResult>,
    @Json(name = "release_date") val releaseDate: String?,
    val revenue: Long,
    val runtime: Int?,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguageJsonResult>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    val videos: VideosJsonResult?,
    val credits: CinematicCreditsJsonResult?
)
