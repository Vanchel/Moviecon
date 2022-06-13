package com.vanchel.moviecon.util

/**
 * @author Иван Тимашов
 *
 * Файл, содержащий все основные константы, используемые в приложении.
 */

/**
 * Размер одной страницы с данными при использовании пагинации на сервисе TMDB
 */
const val PAGE_SIZE = 20

/**
 * Изображение в исходном размере
 */
const val SIZE_ORIGINAL = "original"

/**
 * Малый размер обложки
 */
const val SIZE_BACKDROP_SMALL = "w300"

/**
 * Средний размер обложки
 */
const val SIZE_BACKDROP_MEDIUM = "w780"

/**
 * Большой размер обложки
 */
const val SIZE_BACKDROP_LARGE = "w1280"

/**
 * Малый размер изображения профиля
 */
const val SIZE_PROFILE_SMALL = "w45"

/**
 * Средний размер изображения профиля
 */
const val SIZE_PROFILE_MEDIUM = "w185"

/**
 * Большой размер изображения профиля
 */
const val SIZE_PROFILE_LARGE = "h632"

/**
 * Наименьший размер постера
 */
const val SIZE_POSTER_TINY = "w92"

/**
 * Очень маленький размер постера
 */
const val SIZE_POSTER_EXTRA_SMALL = "w154"

/**
 * Малый размер постера
 */
const val SIZE_POSTER_SMALL = "w185"

/**
 * Средний размер постера
 */
const val SIZE_POSTER_MEDIUM = "w342"

/**
 * Большой размер постера
 */
const val SIZE_POSTER_LARGE = "w500"

/**
 * Очень большой размер постера
 */
const val SIZE_POSTER_EXTRA_LARGE = "w780"

/**
 * Формат даты, ожидаемый в ответе при запросах к TMDB
 */
const val DATE_FORMAT_DEFAULT = "yyyy-MM-dd"

/**
 * Формат даты по умолчанию для отображения
 */
const val DATE_FORMAT_UI = "dd.MM.yyyy"

/**
 * Задержка перед прекращением работы StateFlow
 */
const val STOP_TIMEOUT = 5000L