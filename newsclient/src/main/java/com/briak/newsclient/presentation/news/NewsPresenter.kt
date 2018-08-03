package com.briak.newsclient.presentation.news

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.model.domain.news.NewsInteractorImpl
import com.briak.newsclient.model.system.Screens
import kotlinx.coroutines.experimental.async
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class NewsPresenter(private var router: Router) : MvpPresenter<NewsView>() {

    @Inject lateinit var newsInteractor: NewsInteractorImpl

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        getTopNews()
    }

    fun onNewsClick(id: String) = router.navigateTo(Screens.NEWS_DETAIL_SCREEN)

    fun onBackPressed() = router.exit()

    private fun getTopNews() {
        async {
            viewState.showTopNews(newsInteractor.getTopNews().articles)
        }.onAwait
    }

    init {
        NewsClientApplication.component.inject(this)
    }
}