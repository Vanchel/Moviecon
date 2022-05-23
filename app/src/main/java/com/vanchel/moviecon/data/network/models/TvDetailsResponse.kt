package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.CinematicStatus
import com.vanchel.moviecon.domain.entities.TvDetails
import com.vanchel.moviecon.util.extensions.defaultFormatDateOrNull

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
data class TvDetailsResponse(
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "created_by") val createdBy: List<TvCreatorResponse>? = null,
    @Json(name = "episode_run_time") val episodeRunTime: List<Int>? = null,
    @Json(name = "first_air_date") val firstAirDate: String? = null,
    @Json(name = "genres") val genres: List<GenreResponse>? = null,
    @Json(name = "homepage") val homepage: String? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "in_production") val inProduction: Boolean? = null,
    @Json(name = "languages") val languages: List<String>? = null,
    @Json(name = "last_air_date") val lastAirDate: String? = null,
    @Json(name = "last_episode_to_air") val lastEpisodeToAir: TvEpisodeResponse? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "next_episode_to_air") val nextEpisodeToAir: TvEpisodeResponse? = null,
    @Json(name = "networks") val networks: List<TvNetworkResponse>? = null,
    @Json(name = "number_of_episodes") val numberOfEpisodes: Int? = null,
    @Json(name = "number_of_seasons") val numberOfSeasons: Int? = null,
    @Json(name = "origin_country") val originCountry: List<String>? = null,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "original_name") val originalName: String? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompanyResponse>? = null,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountryResponse>? = null,
    @Json(name = "seasons") val seasons: List<TvSeasonResponse>? = null,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguageResponse>? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "tagline") val tagline: String? = null,
    @Json(name = "type") val type: String? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "vote_count") val voteCount: Int? = null,
    @Json(name = "videos") val videos: VideosResponse? = null,
    @Json(name = "credits") val credits: CinematicCreditsResponse? = null
) : Transformable<TvDetails> {

    override fun transform() = TvDetails(
        id = id ?: -1,
        name = name ?: "",
        popularity = popularity ?: 0.0,
        originalName = originalName ?: "",
        originalCountry = originCountry ?: listOf(),
        originalLanguage = originalLanguage ?: "",
        overview = overview ?: "",
        voteCount = voteCount ?: 0,
        firstAirDate = firstAirDate?.defaultFormatDateOrNull,
        lastAirDate = lastAirDate?.defaultFormatDateOrNull,
        numberOfEpisodes = numberOfEpisodes ?: 0,
        numberOfSeasons = numberOfSeasons ?: 0,
        backdropPath = backdropPath ?: "",
        creatorNames = createdBy?.mapNotNull(TvCreatorResponse::name) ?: listOf(),
        episodeRunTime = episodeRunTime ?: listOf(),
        genreNames = genres?.mapNotNull(GenreResponse::name) ?: listOf(),
        homepage = homepage ?: "",
        inProduction = inProduction ?: false,
        languages = languages ?: listOf(),
        voteAverage = voteAverage ?: 0.0,
        genreIds = genres?.mapNotNull(GenreResponse::id) ?: listOf(),
        status = CinematicStatus.byValue(status),
        tagline = tagline ?: "",
        type = type ?: "",
        posterPath = posterPath,
        videos = videos?.results?.map(VideoResponse::transform),
        cast = credits?.cast?.map(CinematicCastResponse::transform)
    )
}