package com.briak.newsclient.entities.news.presentation

import java.io.Serializable
import java.util.*

data class ArticleUI (
        var author: String?,
        var title: String?,
        var description: String?,
        var urlToImage: String?,
        var publishedAt: Date?
): Serializable {
    constructor() : this(null, null, null, null, Date())
}