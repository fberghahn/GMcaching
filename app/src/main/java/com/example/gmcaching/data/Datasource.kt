package com.example.gmcaching.data

import com.example.gmcaching.model.cachenames
import com.example.gmcaching.R


class Datasource() {

    fun loadcaches(): List<cachenames> {
        return listOf<cachenames>(
            cachenames(R.string.cache1, R.drawable.image1),
            cachenames(R.string.cache2, R.drawable.image2),
            cachenames(R.string.cache3, R.drawable.image3),
            cachenames(R.string.cache4, R.drawable.image3)
        )
    }
}
