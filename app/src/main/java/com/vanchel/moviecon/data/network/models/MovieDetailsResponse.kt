package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.CinematicStatus
import com.vanchel.moviecon.domain.entities.MovieDetails
import com.vanchel.moviecon.util.extensions.defaultFormatDateOrNull

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
data class MovieDetailsResponse(
    @Json(name = "adult") val adult: Boolean? = null,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "belongs_to_collection") val belongsToCollection: CollectionResponse? = null,
    @Json(name = "budget") val budget: Int? = null,
    @Json(name = "genres") val genres: List<GenreResponse>? = null,
    @Json(name = "homepage") val homepage: String? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "imdb_id") val imdbId: String? = null,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "original_title") val originalTitle: String? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompanyResponse>? = null,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountryResponse>? = null,
    @Json(name = "release_date") val releaseDate: String? = null,
    @Json(name = "revenue") val revenue: Long? = null,
    @Json(name = "runtime") val runtime: Int? = null,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguageResponse>? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "tagline") val tagline: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "video") val video: Boolean? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "vote_count") val voteCount: Int? = null,
    @Json(name = "videos") val videos: VideosResponse? = null,
    @Json(name = "credits") val credits: CinematicCreditsResponse? = null
) : Transformable<MovieDetails> {

    override fun transform() = MovieDetails(
        id = id ?: -1,
        isAdult = adult ?: false,
        backdropPath = backdropPath,
        budget = budget ?: 0,
        genreNames = genres?.mapNotNull(GenreResponse::name) ?: listOf(),
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalTitle ?: "",
        overview = overview,
        popularity = popularity ?: 0.0,
        posterPath = posterPath,
        releaseDate = releaseDate?.defaultFormatDateOrNull,
        revenue = revenue ?: 0,
        runTime = runtime,
        status = CinematicStatus.byValue(status),
        tagline = tagline,
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        videos = videos?.results?.map(VideoResponse::transform),
        cast = credits?.cast?.map(CinematicCastResponse::transform)
    )
}
