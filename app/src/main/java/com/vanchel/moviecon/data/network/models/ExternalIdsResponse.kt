package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные о социальных сетях человека.
 *
 * @property id идентификатор записи о социальных сетях
 * @property imdbId идентификатор аккаунта человека на сервисе IMDb
 * @property facebookId идентификатор аккаунта человека в Facebook
 * @property freebaseMid mid аккаунта человека на сервисе Freebase
 * @property freebaseId идентификатор аккаунта человека на сервисе Freebase
 * @property tvRageId идентификатор аккаунта человека на сервисе TVRage
 * @property twitterId идентификатор аккаунта человека в Twitter
 * @property instagramId идентификатор аккаунта человека в Instagram
 */
data class ExternalIdsResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "imdb_id") val imdbId: String? = null,
    @Json(name = "facebook_id") val facebookId: String? = null,
    @Json(name = "freebase_mid") val freebaseMid: String? = null,
    @Json(name = "freebase_id") val freebaseId: String? = null,
    @Json(name = "tvrage_id") val tvRageId: String? = null,
    @Json(name = "twitter_id") val twitterId: String? = null,
    @Json(name = "instagram_id") val instagramId: String? = null,
)