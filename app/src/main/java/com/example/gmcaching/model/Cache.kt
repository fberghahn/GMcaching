package com.example.gmcaching.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.gms.maps.model.LatLng

data class Cache(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int,
     val location : LatLng
)
