package com.vanchel.moviecon.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @author Иван Тимашов
 *
 * Наследник [Application], используемый для управления глобальным состаянием приложения.
 */
@HiltAndroidApp
class MovieconApp : Application()