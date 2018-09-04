package com.briak.newsclient.presentation.news

import com.briak.newsclient.entities.mapper.ArticleMapper
import com.briak.newsclient.entities.news.presentation.CategoryUI
import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.model.di.news.NewsRouter
import com.briak.newsclient.model.domain.news.NewsInteractor
import com.briak.newsclient.presentation.base.ErrorHandler
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
    lateinit var newsInteractor: NewsInteractor

    @Mock
    lateinit var articleMapper: ArticleMapper

    @Mock
    lateinit var newsCicerone: Cicerone<NewsRouter>

    @Mock
    lateinit var errorHandler: ErrorHandler

    @Mock
    lateinit var newsViewState: `NewsView$$State`

    private lateinit var newsPresenter: NewsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        newsPresenter = NewsPresenter(newsInteractor, newsCicerone, errorHandler, articleMapper)
        newsPresenter.setViewState(newsViewState)
    }

    @Test
    fun getTopNews() {
        runBlocking {
            val newsList = mutableListOf<Article>()
            newsList.add(Article())

            whenever(newsInteractor.getTopNews()).thenReturn(newsList)

            newsPresenter.getTopNews(false)

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
        verify(newsViewState).startNewsJob(false)

    }
}