package com.briak.newsclient.presentation.newsdetail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.presentation.ArticleUI
import com.briak.newsclient.extensions.isNotNullOrEmpty
import com.briak.newsclient.extensions.toShortDate
import com.briak.newsclient.model.di.allnews.AllNewsRouter
import com.briak.newsclient.model.di.topnews.TopNewsRouter
import ru.terrakok.cicerone.BaseRouter

@InjectViewState
class NewsDetailPresenter : MvpPresenter<NewsDetailView>() {
    private lateinit var router: BaseRouter

    fun setRouter(router: BaseRouter) {
        this.router = router
    }

    fun onBackPressed() {
        when (router) {
            is TopNewsRouter -> (router as TopNewsRouter).exit()
            is AllNewsRouter -> (router as AllNewsRouter).exit()
        }
    }

    fun setArticle(article: ArticleUI) {
        viewState.setNewsDetailToolbarTitle(
                if (article.title != null) {
                    article.title!!
                } else {
                    ""
                })

        viewState.loadImage(article.urlToImage, R.mipmap.ic_bananya_large)

        if (article.author.isNotNullOrEmpty()) {
            viewState.setAuthor(article.author!!)
        } else {
            viewState.hideAuthor()
        }

        if (article.publishedAt != null) {
            viewState.setPublishedAt(article.publishedAt!!.toShortDate())
        } else {
            viewState.hidePublishedAt()
        }

        if (article.title.isNotNullOrEmpty()) {
            viewState.setTitle(article.title!!)
        } else {
            viewState.hideTitle()
        }

        if (article.description.isNotNullOrEmpty()) {
            viewState.setDescription(article.description!!)
        } else {
            viewState.hideDescription()
        }
    }
}