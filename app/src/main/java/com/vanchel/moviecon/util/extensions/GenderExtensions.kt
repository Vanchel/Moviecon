package com.vanchel.moviecon.util.extensions

import androidx.annotation.StringRes
import com.vanchel.moviecon.R
import com.vanchel.moviecon.domain.entities.Gender

val Gender.title: Int
    @StringRes
    get() = when (this) {
        Gender.NOT_SPECIFIED -> R.string.gender_not_specified
        Gender.FEMALE -> R.string.gender_female
        Gender.MALE -> R.string.gender_male
        Gender.NON_BINARY -> R.string.gender_non_binary
    }