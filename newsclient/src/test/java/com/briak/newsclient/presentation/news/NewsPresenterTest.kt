package com.briak.newsclient.presentation.news

import com.briak.newsclient.entities.mapper.ArticleMapper
import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.model.di.news.NewsRouter
import com.briak.newsclient.model.domain.news.NewsInteractor
import com.briak.newsclient.presentation.base.ErrorHandler
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.test.TestCoroutineContext
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
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
    lateinit var newsView: NewsView

    @Mock
    lateinit var newsViewState: `NewsView$$State`

    private lateinit var newsPresenter: NewsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        newsPresenter = NewsPresenter(newsInteractor, newsCicerone, errorHandler, articleMapper)
        newsPresenter.attachView(newsView)
        newsPresenter.setViewState(newsViewState)
    }


    @Test
    fun topNews() {

        val newsList = mutableListOf<Article>()
        newsList.add(Article())

        launch(TestCoroutineContext()) {
            `when`(newsInteractor.getTopNews().articles).thenReturn(newsList)
            newsPresenter.topNews(false)
            Mockito.verify(newsViewState).showTopNews(articleMapper.map(newsList))
        }
    }
}