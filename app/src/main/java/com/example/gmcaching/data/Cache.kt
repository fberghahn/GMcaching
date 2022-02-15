package com.example.gmcaching.data

import android.graphics.Bitmap
import android.media.Image
import android.net.Uri


class Cache{
    var cacheid: String ="generated"
    var creatorid: String? =null
    var cacheName: String? =null
    var lat: Double =0.0
    var lng: Double=0.0
    var image : String?=null

    constructor(){}
    constructor(cacheid: String,creatorid: String?,cacheName: String?,lat: Double,lng: Double,image : String?){
        this.cacheid=cacheid
        this.creatorid=creatorid
        this.cacheName=cacheName
        this.lat=lat
        this.lng=lng
        this.image=image
    }
    constructor(creatorid: String?,cacheName: String?,lat: Double,lng: Double,image : String?){

        this.creatorid=creatorid
        this.cacheName=cacheName
        this.lat=lat
        this.lng=lng
        this.image=image
    }

}

