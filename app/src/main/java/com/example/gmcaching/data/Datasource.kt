package com.example.gmcaching.data

import com.example.gmcaching.model.Cache
import com.example.gmcaching.R
import com.google.android.gms.maps.model.LatLng


class Datasource() {

    fun loadcaches(): List<Cache> {
        return listOf<Cache>(
            Cache(R.string.cache1, R.drawable.image1, LatLng(51.030071,7.562187)),
            Cache(R.string.cache2, R.drawable.image2,LatLng(51.034146,7.561586)),
            Cache(R.string.cache3, R.drawable.image3,LatLng(51.036629,7.570690)),
            Cache(R.string.cache4, R.drawable.image3,LatLng(51.040731,7.585506))
        )
    }
}
