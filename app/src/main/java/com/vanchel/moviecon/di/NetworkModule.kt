package com.vanchel.moviecon.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vanchel.moviecon.BuildConfig
import com.vanchel.moviecon.data.network.BASE_URL
import com.vanchel.moviecon.data.network.interceptors.AuthInterceptor
import com.vanchel.moviecon.data.network.services.MoviesService
import com.vanchel.moviecon.data.network.services.PeopleService
import com.vanchel.moviecon.data.network.services.TrendingService
import com.vanchel.moviecon.data.network.services.TvService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * @author Иван Тимашов
 *
 * Модуль предоставления зависимостей для взаимодействия с сетью.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Предоставляет настроенный [HttpLoggingInterceptor].
     *
     * @return настроенный экземпляр [HttpLoggingInterceptor]
     */
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * Предоставляет настроенный [AuthInterceptor].
     *
     * @return настроенный экземпляр [AuthInterceptor]
     */
    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor(BuildConfig.TMDB_API_KEY)
    }

    /**
     * Предоставляет настроенный [OkHttpClient].
     *
     * @param authInterceptor Перехватчик запросов для обработки авторизации
     * @param httpLoggingInterceptor Перехватчик запросов для обработки логирования
     * @return настроенный экземпляр [OkHttpClient]
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    /**
     * Предоставляет настроенный [Moshi].
     *
     * @return настроенный экземпляр [Moshi]
     */
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    /**
     * Предоставляет настроенный [Retrofit].
     *
     * @param moshi преобразователь json
     * @param okHttpClient клиент [OkHttpClient]
     * @return @return настроенный экземпляр [Retrofit]
     */
    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    /**
     * Предоставляет реализацию [MoviesService].
     *
     * @param retrofit Экземпляр [Retrofit] для предоставления зависимости
     * @return реализация [MoviesService]
     */
    @Singleton
    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }

    /**
     * Предоставляет реализацию [TvService].
     *
     * @param retrofit Экземпляр [Retrofit] для предоставления зависимости
     * @return реализация [TvService]
     */
    @Singleton
    @Provides
    fun provideTvService(retrofit: Retrofit): TvService {
        return retrofit.create(TvService::class.java)
    }

    /**
     * Предоставляет реализацию [PeopleService].
     *
     * @param retrofit Экземпляр [Retrofit] для предоставления зависимости
     * @return реализация [PeopleService]
     */
    @Singleton
    @Provides
    fun providePeopleService(retrofit: Retrofit): PeopleService {
        return retrofit.create(PeopleService::class.java)
    }

    /**
     * Предоставляет реализацию [TrendingService].
     *
     * @param retrofit Экземпляр [Retrofit] для предоставления зависимости
     * @return реализация [TrendingService]
     */
    @Singleton
    @Provides
    fun provideTrendingService(retrofit: Retrofit): TrendingService {
        return retrofit.create(TrendingService::class.java)
    }
}