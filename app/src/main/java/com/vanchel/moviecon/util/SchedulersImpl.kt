package com.vanchel.moviecon.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import io.reactivex.schedulers.Schedulers as RxSchedulers

/**
 * @author Иван Тимашов
 *
 * Основная реализация [Schedulers].
 */
class SchedulersImpl @Inject constructor() : Schedulers {
    override val io: Scheduler = RxSchedulers.io()
    override val ui: Scheduler = AndroidSchedulers.mainThread()
}