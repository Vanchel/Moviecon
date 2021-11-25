package com.vanchel.moviecon.presentation.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

/**
 * @author Иван Тимашов
 *
 * Файл, содержащий функции-расширения, используемые в приложении.
 */

/**
 * Метод для организации более удобной навигации на базе фрагментов. Эта реализация позволяет
 * избежать ошибок, приводящих к падению приложения. Например, при одновременном нажатии на две
 * кнопки, каждая из которых инициирует переход к другому фрагменту. Или при случайном двойном
 * нажатии на такую кнопку.
 *
 * @param directions операция перехода, которую необходимо выполнить
 */
fun Fragment.navigate(directions: NavDirections) {
    val controller = findNavController()
    val currentDestination =
        (controller.currentDestination as? FragmentNavigator.Destination)?.className
            ?: (controller.currentDestination as? DialogFragmentNavigator.Destination)?.className
    if (currentDestination == this.javaClass.name) {
        controller.navigate(directions)
    }
}