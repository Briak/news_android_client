package com.briak.newsclient.presentation.news

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.model.domain.news.NewsInteractorImpl
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.base.ErrorHandler
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class NewsPresenter(private var router: Router) : MvpPresenter<NewsView>() {

    @Inject
    lateinit var newsInteractor: NewsInteractorImpl

    @Inject
    lateinit var errorHandler: ErrorHandler

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        getTopNews()
    }

    fun onNewsClick(news: Article) = router.navigateTo(Screens.NEWS_DETAIL_SCREEN, news)

    fun onFilterClick() = router.navigateTo(Screens.CATEGORIES_SCREEN)

    fun onBackPressed() = router.exit()

    fun setCategory(category: String) {
        newsInteractor.setCategory(category)

        getTopNews()
    }

    private fun getTopNews() {
        viewState.setTitle(newsInteractor.getCategory())

        getTopNews(false)
    }

    fun getTopNews(refresh: Boolean) {
        launch(UI) {
            viewState.showProgress(!refresh)

            val request = async { newsInteractor.getTopNews() }
            try {
                val result = request.await()
                viewState.showTopNews(result.await().articles)
                viewState.showProgress(false)
            } catch (e: Throwable) {
                e.printStackTrace()
                viewState.showMessage(errorHandler.proceed(e))
                viewState.showProgress(false)
            }
        }
    }

    init {
        NewsClientApplication.component.inject(this)
    }
}