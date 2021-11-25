package com.vanchel.moviecon.di

import com.vanchel.moviecon.util.Schedulers
import com.vanchel.moviecon.util.SchedulersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Иван Тимашов
 *
 * Модуль предоставления обработчиков асинхронных задач.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SchedulersModule {
    /**
     * Предоставляет реализацию [Schedulers].
     *
     * @param impl [SchedulersImpl], используемый в качестве реализации интерфейса
     * @return реализация [Schedulers]
     */
    @Binds
    abstract fun bindSchedulers(impl: SchedulersImpl): Schedulers
}