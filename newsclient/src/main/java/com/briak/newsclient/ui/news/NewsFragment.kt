package com.briak.newsclient.ui.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.presentation.NewsUIEntity
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.news.NewsPresenter
import com.briak.newsclient.ui.base.BackButtonListener
import com.briak.newsclient.ui.base.BaseFragment
import com.briak.newsclient.ui.main.MainActivity
import com.briak.newsclient.ui.newsdetail.NewsDetailFragment
import kotlinx.android.synthetic.main.fragment_news.*
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportFragmentNavigator

class NewsFragment :
        BaseFragment(),
        NewsView,
        NewsAdapter.OnNewsClickListener,
        BackButtonListener {
    @InjectPresenter
    lateinit var presenter: NewsPresenter

    private var cicerone = Cicerone.create()
    private var navigationHolder: NavigatorHolder = cicerone.navigatorHolder
    private var router: Router = cicerone.router

    override val layoutRes: Int = R.layout.fragment_news

    @ProvidePresenter
    fun provideNewsPresenter(): NewsPresenter = NewsPresenter(router)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NewsClientApplication.newsNavigationComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsListView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = NewsAdapter(listOf(NewsUIEntity(), NewsUIEntity(), NewsUIEntity(), NewsUIEntity()), this@NewsFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        navigationHolder.setNavigator(getNavigator())
    }

    override fun onPause() {
        navigationHolder.removeNavigator()

        super.onPause()
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

    override fun onBackPressed(): Boolean {
        router.exit()

        return true
    }

    override fun onNewsClick(id: String) {
        presenter.onNewsClick(id)
    }
}