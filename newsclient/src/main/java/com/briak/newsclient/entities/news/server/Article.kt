package com.briak.newsclient.entities.news.server

import java.util.*

data class Article (
        var source: Source?,
        var author: String?,
        var title: String?,
        var description: String?,
        var url: String?,
        var urlToImage: String?,
        var publishedAt: Date?
) {
    constructor() : this(null, null, null, null, null, null, Date())
}