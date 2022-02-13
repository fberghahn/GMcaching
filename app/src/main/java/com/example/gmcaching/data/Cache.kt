package com.example.gmcaching.data

import android.media.Image


class Cache{
    var cacheid: String ="generated"
    var creatorid: String? =null
    var cacheName: String? =null
    var lat: Double =0.0
    var lng: Double=0.0
    constructor(){}
    constructor(cacheid: String,creatorid: String?,cacheName: String?,lat: Double,lng: Double){
        this.cacheid=cacheid
        this.creatorid=creatorid
        this.cacheName=cacheName
        this.lat=lat
        this.lng=lng
    }
    constructor(creatorid: String?,cacheName: String?,lat: Double,lng: Double){

        this.creatorid=creatorid
        this.cacheName=cacheName
        this.lat=lat
        this.lng=lng
    }

}

