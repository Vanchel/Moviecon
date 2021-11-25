package com.vanchel.moviecon.di

import com.vanchel.moviecon.data.repositories.*
import com.vanchel.moviecon.domain.repositories.*
import com.vanchel.moviecon.util.Schedulers
import com.vanchel.moviecon.util.SchedulersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Иван Тимашов
 *
 * Модуль предоставления реализаций репозиториев.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    /**
     * Предоставляет реализацию [MoviesRepository].
     *
     * @param impl [MoviesRepositoryImpl], используемый в качестве реализации интерфейса
     * @return реализация [MoviesRepository]
     */
    @Binds
    abstract fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

    /**
     * Предоставляет реализацию [TvRepository].
     *
     * @param impl [TvRepositoryImpl], используемый в качестве реализации интерфейса
     * @return реализация [TvRepository]
     */
    @Binds
    abstract fun bindTvRepository(impl: TvRepositoryImpl): TvRepository

    /**
     * Предоставляет реализацию [PeopleRepository].
     *
     * @param impl [PeopleRepositoryImpl], используемый в качестве реализации интерфейса
     * @return реализация [PeopleRepository]
     */
    @Binds
    abstract fun bindPeopleRepository(impl: PeopleRepositoryImpl): PeopleRepository

    /**
     * Предоставляет реализацию [TrendingRepository].
     *
     * @param impl [TrendingRepositoryImpl], используемый в качестве реализации интерфейса
     * @return реализация [TrendingRepository]
     */
    @Binds
    abstract fun bindTrendingRepository(impl: TrendingRepositoryImpl): TrendingRepository
}