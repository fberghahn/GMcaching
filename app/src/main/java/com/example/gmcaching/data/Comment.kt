package com.example.gmcaching.data

class Comment {
    var commentid: String = "generated"
    var creatorname: String? = "Anonym"
    var cacheid: String? = null
    var comment: String? = null
    var cachename: String? = "null"

    constructor() {}
    constructor(
        commentid: String,
        creatorid: String?,
        cacheid: String?,
        comment: String?,
        cachename: String?
    ) {
        this.commentid = commentid
        this.creatorname = creatorid
        this.cacheid = cacheid
        this.comment = comment
        this.cachename = cachename

    }

    constructor(cachename: String?, cacheid: String?, comment: String?) {
        this.cachename = cachename
        this.cacheid = cacheid
        this.comment = comment

    }

}
