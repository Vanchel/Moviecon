package com.vanchel.moviecon.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.vanchel.moviecon.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Иван Тимашов
 *
 * Основная и единственная активность приложения.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}