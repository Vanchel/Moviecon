package com.vanchel.moviecon.di

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.converters.*
import com.vanchel.moviecon.data.network.models.*
import com.vanchel.moviecon.domain.entities.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Иван Тимашов
 *
 * Модуль предоставления конвертеров данных.
 */
@Module
@InstallIn(SingletonComponent::class)
object ConverterModule {
    /**
     * Предоставляет конвертер из типа [MovieDetailsJsonResult] в тип [MovieDetails].
     *
     * @param videoJsonConverter конвертер из типа [VideoJsonResult] в тип [Video]
     * @param cinematicCastJsonConverter конвертер из типа [CinematicCastJsonResult] в тип [Cast]
     * @return конвертированный объект типа [MovieDetails]
     */
    @Singleton
    @Provides
    fun provideMovieDetailsJsonConverter(
        videoJsonConverter: EntityConverter<Video, VideoJsonResult>,
        cinematicCastJsonConverter: EntityConverter<Cast, CinematicCastJsonResult>
    ): EntityConverter<MovieDetails, MovieDetailsJsonResult> {
        return MovieDetailsToMovieDetailsJsonResultConverter(
            videoJsonConverter,
            cinematicCastJsonConverter
        )
    }

    /**
     * Предоставляет конвертер из типа [TvDetailsJsonResult] в тип [TvDetails].
     *
     * @param videoJsonConverter конвертер из типа [VideoJsonResult] в тип [Video]
     * @param cinematicCastJsonConverter конвертер из типа [CinematicCastJsonResult] в тип [Cast]
     * @return конвертированный объект типа [TvDetails]
     */
    @Singleton
    @Provides
    fun provideTvDetailsJsonConverter(
        videoJsonConverter: EntityConverter<Video, VideoJsonResult>,
        cinematicCastJsonConverter: EntityConverter<Cast, CinematicCastJsonResult>
    ): EntityConverter<TvDetails, TvDetailsJsonResult> {
        return TvDetailsToTvDetailsJsonResultConverter(
            videoJsonConverter,
            cinematicCastJsonConverter
        )
    }

    /**
     * Предоставляет конвертер из типа [PersonDetailsJsonResult] в тип [PersonDetails].
     *
     * @param movieCreditsConverter конвертер из типа [MovieCreditsJsonResult] в тип [Credit]
     * @param tvCreditsConverter конвертер из типа [TvCreditsJsonResult] в тип [Credit]
     * @return конвертированный объект типа [PersonDetails]
     */
    @Singleton
    @Provides
    fun providePersonDetailsJsonConverter(
        movieCreditsConverter: EntityConverter<Credit, MovieCreditsJsonResult>,
        tvCreditsConverter: EntityConverter<Credit, TvCreditsJsonResult>
    ): EntityConverter<PersonDetails, PersonDetailsJsonResult> {
        return PersonDetailsToPersonDetailsJsonResultConverter(
            movieCreditsConverter, tvCreditsConverter
        )
    }

    /**
     * Предоставляет конвертер из типа [ImagesJsonResult] в тип [Images].
     *
     * @param imageConverter конвертер из типа [ImageJsonResult] в тип [Image]
     * @return конвертированный объект типа [Images]
     */
    @Singleton
    @Provides
    fun provideImagesJsonConverter(
        imageConverter: EntityConverter<Image, ImageJsonResult>
    ): EntityConverter<Images, ImagesJsonResult> {
        return ImagesToImagesJsonResultConverter(imageConverter)
    }

    /**
     * Предоставляет конвертер из типа [CinematicCreditsJsonResult] в список объектов типа [Cast].
     *
     * @param cinematicCastJsonConverter конвертер из типа [CinematicCastJsonResult] в тип [Cast]
     * @return список конвертированных объектов типа [Cast]
     */
    @Singleton
    @Provides
    fun provideCinematicCreditsJsonConverter(
        cinematicCastJsonConverter: EntityConverter<Cast, CinematicCastJsonResult>
    ): EntityConverter<List<Cast>, CinematicCreditsJsonResult> {
        return CastToCinematicCreditsJsonResultConverter(cinematicCastJsonConverter)
    }

    /**
     * Предоставляет конвертер из типа [ImageJsonResult] в тип [Image].
     *
     * @return конвертированный объект типа [Image]
     */
    @Singleton
    @Provides
    fun provideImageJsonConverter(): EntityConverter<Image, ImageJsonResult> {
        return ImageToImagesJsonResultConverter()
    }

    /**
     * Предоставляет конвертер из типа [VideoJsonResult] в тип [Video].
     *
     * @return конвертированный объект типа [Video]
     */
    @Singleton
    @Provides
    fun provideVideoJsonConverter(): EntityConverter<Video, VideoJsonResult> {
        return VideoToVideoJsonResultConverter()
    }

    /**
     * Предоставляет конвертер из типа [CinematicCastJsonResult] в тип [Cast].
     *
     * @return конвертированный объект типа [Cast]
     */
    @Singleton
    @Provides
    fun provideCinematicCastJsonConverter(): EntityConverter<Cast, CinematicCastJsonResult> {
        return CastToCinematicCastJsonResultConverter()
    }

    /**
     * Предоставляет конвертер из типа [MovieCreditsJsonResult] в тип [Credit].
     *
     * @return конвертированный объект типа [Credit]
     */
    @Singleton
    @Provides
    fun provideMovieCreditsJsonConverter(): EntityConverter<Credit, MovieCreditsJsonResult> {
        return CreditToMovieCreditsJsonResultConverter()
    }

    /**
     * Предоставляет конвертер из типа [TvCreditsJsonResult] в тип [Credit].
     *
     * @return конвертированный объект типа [Credit]
     */
    @Singleton
    @Provides
    fun provideTvCreditsJsonConverter(): EntityConverter<Credit, TvCreditsJsonResult> {
        return CreditToTvCreditsJsonResultConverter()
    }

    /**
     * Предоставляет конвертер из типа [MovieJsonResult] в тип [Movie].
     *
     * @return конвертированный объект типа [Movie]
     */
    @Singleton
    @Provides
    fun provideMovieJsonConverter(): EntityConverter<Movie, MovieJsonResult> {
        return MovieToMovieJsonResultConverter()
    }

    /**
     * Предоставляет конвертер из типа [TvJsonResult] в тип [Tv].
     *
     * @return конвертированный объект типа [Tv]
     */
    @Singleton
    @Provides
    fun provideTvJsonConverter(): EntityConverter<Tv, TvJsonResult> {
        return TvToTvJsonResultConverter()
    }

    /**
     * Предоставляет конвертер из типа [PersonJsonResult] в тип [Person].
     *
     * @return конвертированный объект типа [Person]
     */
    @Singleton
    @Provides
    fun providePersonJsonConverter() : EntityConverter<Person, PersonJsonResult> {
        return PersonToPersonJsonResultConverter()
    }
}