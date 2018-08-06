package com.briak.newsclient.presentation.news

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.model.data.server.ServerError
import com.briak.newsclient.model.domain.news.NewsInteractorImpl
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.base.ErrorHandler
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.HttpException
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

    fun onNewsClick(id: String) = router.navigateTo(Screens.NEWS_DETAIL_SCREEN)

    fun onBackPressed() = router.exit()

    private fun getTopNews() {
        viewState.showProgress(true)

        launch {
            try {
                val request = newsInteractor.getTopNews()
                val result = request.await()
                viewState.showTopNews(result.articles)
                viewState.showProgress(false)
            } catch (e: Throwable) {
                e.printStackTrace()
                errorHandler.proceed(e) {
                    viewState.showMessage(e.message!!)
                }
                viewState.showProgress(false)
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: ServerError) {
                e.printStackTrace()
            }
        }
    }

    init {
        NewsClientApplication.component.inject(this)
    }
}