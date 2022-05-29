package com.vanchel.moviecon.di

import com.vanchel.moviecon.util.Dispatcher
import com.vanchel.moviecon.util.DispatcherImpl
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
abstract class DispatcherModule {
    /**
     * Предоставляет реализацию [Dispatcher].
     *
     * @param impl [DispatcherImpl], используемый в качестве реализации интерфейса
     * @return реализация [Dispatcher]
     */
    @Binds
    abstract fun bindDispatcher(impl: DispatcherImpl): Dispatcher
}