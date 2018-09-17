package com.briak.newsclient.presentation.news

import com.briak.newsclient.entities.mapper.ArticleMapper
import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.extensions.toUserDate
import com.briak.newsclient.model.di.allnews.AllNewsRouter
import com.briak.newsclient.model.domain.allnews.AllNewsInteractor
import com.briak.newsclient.presentation.allnews.AllNewsPresenter
import com.briak.newsclient.presentation.allnews.`AllNewsView$$State`
import com.briak.newsclient.presentation.base.ErrorHandler
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.terrakok.cicerone.Cicerone
import java.util.*

class AllNewsPresenterTest {
    @Mock
    lateinit var allNewsInteractor: AllNewsInteractor

    @Mock
    lateinit var allNewsCicerone: Cicerone<AllNewsRouter>

    @Mock
    lateinit var errorHandler: ErrorHandler

    @Mock
    lateinit var articleMapper: ArticleMapper

    @Mock
    lateinit var allNewsViewState: `AllNewsView$$State`

    private lateinit var presenter: AllNewsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = AllNewsPresenter(allNewsInteractor, allNewsCicerone, errorHandler, articleMapper)
        presenter.setViewState(allNewsViewState)
    }

    @Test
    fun getAllNews() {
        runBlocking {
            val newsList = mutableListOf<Article>()
            newsList.add(Article())

            whenever(allNewsInteractor.getAllNews(null, null)).thenReturn(newsList)

            presenter.getAllNews()

            verify(allNewsViewState).showAllNews(articleMapper.map(newsList))
            verify(allNewsViewState).showEmpty(newsList.isEmpty())
            verify(allNewsViewState).showProgress(false)

        }
    }

    @Test
    fun setCalendar() {
        val calendar = Calendar.getInstance()
        presenter.setCalendar(calendar)

        verify(allNewsViewState).setTitle(calendar.time.toUserDate())
        verify(allNewsViewState).startNewsJob()
    }

    @Test
    fun setQuery() {
        val query = ""
        presenter.setQuery(query)

        verify(allNewsViewState).startNewsJob()
    }

    @Test
    fun setNullQuery() {
        val query = null
        presenter.setQuery(query)

        verify(allNewsViewState, never()).startNewsJob()
    }

    @Test
    fun refresh() {
        presenter.refresh()

        verify(allNewsViewState).startNewsJob()
    }
}