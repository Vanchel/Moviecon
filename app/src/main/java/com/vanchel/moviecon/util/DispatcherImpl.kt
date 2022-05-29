package com.vanchel.moviecon.util

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основная реализация [Dispatcher].
 */
class DispatcherImpl @Inject constructor() : Dispatcher {

    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}