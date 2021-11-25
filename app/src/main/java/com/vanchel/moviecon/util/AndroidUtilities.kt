package com.vanchel.moviecon.util

import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * @author Иван Тимашов
 *
 * Файл содержит утилитные функции верхнего уровня, используемые в проекте.
 */

/**
 * Генерирует имя файла изображения на основании текущего системного времени.
 *
 * @param ext Расширение файла
 * @return Сгенерированное имя файла
 */
fun generatePictureFileName(ext: String = "jpg"): String {
    val date = Date().apply {
        time = System.currentTimeMillis() + Random.nextInt(1000) + 1
    }
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(date)
    return "IMG_$timeStamp.$ext"
}