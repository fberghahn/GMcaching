package com.example.gmcaching.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class cachenames(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)
