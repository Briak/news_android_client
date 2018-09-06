package com.briak.newsclient.presentation.news

import com.briak.newsclient.entities.mapper.ArticleMapper
import com.briak.newsclient.entities.news.presentation.CategoryUI
import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.model.di.topnews.AllNewsRouter
import com.briak.newsclient.model.domain.topnews.TopNewsInteractor
import com.briak.newsclient.presentation.base.ErrorHandler
import com.briak.newsclient.presentation.topnews.TopNewsPresenter
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.terrakok.cicerone.Cicerone


class NewsPresenterTest {

    @Mock
    lateinit var newsInteractor: TopNewsInteractor

    @Mock
    lateinit var articleMapper: ArticleMapper

    @Mock
    lateinit var newsCicerone: Cicerone<AllNewsRouter>

    @Mock
    lateinit var errorHandler: ErrorHandler

    @Mock
    lateinit var newsViewState: `NewsView$$State`

    private lateinit var newsPresenter: TopNewsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        newsPresenter = TopNewsPresenter(newsInteractor, newsCicerone, errorHandler, articleMapper)
        newsPresenter.setViewState(newsViewState)
    }

    @Test
    fun getTopNews() {
        runBlocking {
            val newsList = mutableListOf<Article>()
            newsList.add(Article())

            whenever(newsInteractor.getTopNews(null)).thenReturn(newsList)

            newsPresenter.getTopNews(false, null)

            verify(newsViewState).showTopNews(articleMapper.map(newsList))
            verify(newsViewState).showProgress(false)
        }
    }

    @Test
    fun setCategory() {
        val category = CategoryUI.BUSINESS

        whenever(newsInteractor.getCategory()).thenReturn(category.name)

        newsPresenter.setCategory(category.name)

        verify(newsViewState).setTitle(newsInteractor.getCategory())
        verify(newsViewState).startNewsJob(false, null)

    }
}