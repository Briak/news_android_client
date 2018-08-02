package com.briak.newsclient.entities.news.server

data class RSS (
        val status: String,
        val totalResults: Int,
        val articles: List<Article>
)