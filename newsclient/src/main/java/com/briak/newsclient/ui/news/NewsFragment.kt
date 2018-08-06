package com.briak.newsclient.ui.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.extensions.visible
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.news.NewsPresenter
import com.briak.newsclient.presentation.news.NewsView
import com.briak.newsclient.ui.base.BackButtonListener
import com.briak.newsclient.ui.base.BaseFragment
import com.briak.newsclient.ui.main.MainActivity
import com.briak.newsclient.ui.newsdetail.NewsDetailFragment
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import javax.inject.Inject

class NewsFragment :
        BaseFragment(),
        NewsView,
        NewsAdapter.OnNewsClickListener,
        BackButtonListener {

    @InjectPresenter
    lateinit var presenter: NewsPresenter

    @Inject
    lateinit var newsNavigationHolder: NavigatorHolder

    @Inject
    lateinit var newsRouter: Router

    override val layoutRes: Int = R.layout.fragment_news

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsClientApplication.newsNavigationComponent.inject(this)

        super.onCreate(savedInstanceState)
    }

    @ProvidePresenter
    fun provideNewsPresenter(): NewsPresenter = NewsPresenter(newsRouter)

    override fun onResume() {
        super.onResume()

        newsNavigationHolder.setNavigator(getNavigator())
    }

    override fun onPause() {
        newsNavigationHolder.removeNavigator()

        super.onPause()
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()

        return true
    }

    override fun onNewsClick(id: String) {
        presenter.onNewsClick(id)
    }

    override fun showTopNews(articles: List<Article>) {
        launch(UI) {
            newsListView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = NewsAdapter(articles, this@NewsFragment)
            }
        }
    }

    override fun showMessage(message: String) {
//        launch(UI) {
//            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
//            newsListView.visible(false)
//        }
    }

    override fun showProgress(show: Boolean) {
//        launch(UI) {
//            newsProgressView.visible(show)
//            newsListView.visible(!show)
//        }
    }

    private fun getNavigator(): Navigator {
        return object : SupportFragmentNavigator(childFragmentManager, R.id.newsContainerView) {
            override fun exit() {
                (activity as MainActivity).router.exit()
            }

            override fun createFragment(screenKey: String?, data: Any?): Fragment? {
                when (screenKey) {
                    Screens.NEWS_DETAIL_SCREEN -> return NewsDetailFragment()
                }

                return this@NewsFragment
            }

            override fun showSystemMessage(message: String?) {

            }
        }
    }

}