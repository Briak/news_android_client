package com.briak.newsclient.entities.mapper

import com.briak.newsclient.entities.news.presentation.ArticleUI
import com.briak.newsclient.entities.news.server.Article
import javax.inject.Inject

open class ArticleMapper @Inject constructor() : Mapper<Article, ArticleUI>() {
    override fun map(value: Article): ArticleUI {
        val articleUI = ArticleUI()
        articleUI.author = value.author
        articleUI.title = value.title
        articleUI.description = value.description
        articleUI.urlToImage = value.urlToImage
        articleUI.publishedAt = value.publishedAt

        return articleUI
    }

    override fun reverseMap(value: ArticleUI): Article {
        val article = Article()
        article.author = value.author
        article.title = value.title
        article.description = value.description
        article.urlToImage = value.urlToImage
        article.publishedAt = value.publishedAt

        return article
    }
}