package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB подробные сведения о сериале.
 *
 * @property backdropPath ссылка на обложку сериала
 * @property createdBy список создателей сериала
 * @property episodeRunTime список хронометражей эпизодов сериала
 * @property firstAirDate дата выпуска пилотного эпизода сериала
 * @property genres список жанров, к которым относится сериал
 * @property homepage ссылка на собственную страницу сериала
 * @property id идентификатор записи о сериале
 * @property inProduction флаг, показывающий, ведутся ли до сих пор съемки сериала
 * @property languages список языков
 * @property lastAirDate дата выпуска последнего эпизода сериала
 * @property lastEpisodeToAir последний показанный эпизод
 * @property name название сериала
 * @property nextEpisodeToAir следующий планируемый к показу эпизод
 * @property networks список стриминговых сервисов, на которых доступен сериал
 * @property numberOfEpisodes число эпизодов в сериале
 * @property numberOfSeasons число сезонов в сериале
 * @property originCountry страна-производитель сериала
 * @property originalLanguage исходный язык фильма
 * @property originalName исходное название сериала
 * @property overview краткое описание сериала
 * @property popularity популярность сериала
 * @property posterPath ссылка на постер сериала
 * @property productionCompanies список компаний, занимающихся продюсированием сериала
 * @property productionCountries список стран, в которых продюсируется сериал
 * @property seasons список сезонов сериала
 * @property spokenLanguages языки, на которые переведен сериал
 * @property status статус сериала
 * @property tagline слоган сериала
 * @property type тип сериала
 * @property voteAverage средняя оценка сериала
 * @property voteCount количество пользовательских оценок
 * @property videos список видеозаписей, относящихся к сериалу
 * @property credits съемочная группа и актерский состав сериала
 */
data class TvDetailsJsonResult(
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "created_by") val createdBy: List<TvCreatorJsonResult>,
    @Json(name = "episode_run_time") val episodeRunTime: List<Int>,
    @Json(name = "first_air_date") val firstAirDate: String?,
    val genres: List<GenreJsonResult>,
    val homepage: String,
    val id: Int,
    @Json(name = "in_production") val inProduction: Boolean,
    val languages: List<String>,
    @Json(name = "last_air_date") val lastAirDate: String?,
    @Json(name = "last_episode_to_air") val lastEpisodeToAir: TvEpisodeJsonResult,
    val name: String,
    @Json(name = "next_episode_to_air") val nextEpisodeToAir: TvEpisodeJsonResult?,
    val networks: List<TvNetworkJsonResult>,
    @Json(name = "number_of_episodes") val numberOfEpisodes: Int,
    @Json(name = "number_of_seasons") val numberOfSeasons: Int,
    @Json(name = "origin_country") val originCountry: List<String>,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_name") val originalName: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompanyJsonResult>,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountryJsonResult>,
    val seasons: List<TvSeasonJsonResult>,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguageJsonResult>,
    val status: String,
    val tagline: String,
    val type: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    val videos: VideosJsonResult?,
    val credits: CinematicCreditsJsonResult?
)