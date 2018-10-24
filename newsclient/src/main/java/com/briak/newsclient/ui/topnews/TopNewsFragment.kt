package com.briak.newsclient.ui.topnews

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.presentation.ArticleUI
import com.briak.newsclient.entities.news.presentation.CategoryUI
import com.briak.newsclient.extensions.onClick
import com.briak.newsclient.extensions.visible
import com.briak.newsclient.model.di.topnews.TopNewsRouter
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.topnews.TopNewsPresenter
import com.briak.newsclient.presentation.topnews.TopNewsView
import com.briak.newsclient.ui.base.BackButtonListener
import com.briak.newsclient.ui.base.BaseFragment
import com.briak.newsclient.ui.base.ErrorDialogFragment
import com.briak.newsclient.ui.base.RouterProvider
import com.briak.newsclient.ui.categories.CategoriesFragment
import com.briak.newsclient.ui.main.MainActivity
import com.briak.newsclient.ui.newsdetail.NewsDetailFragment
import kotlinx.android.synthetic.main.fragment_top_news.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.result.ResultListener
import javax.inject.Inject


class TopNewsFragment :
        BaseFragment(),
        TopNewsView,
        NewsAdapter.OnNewsClickListener,
        BackButtonListener,
        RouterProvider,
        ResultListener {

    override val layoutRes: Int = R.layout.fragment_top_news

    @Inject
    lateinit var cicerone: Cicerone<TopNewsRouter>

    @Inject
    @InjectPresenter
    lateinit var presenter: TopNewsPresenter

    @ProvidePresenter
    fun provideNewsPresenter(): TopNewsPresenter = presenter

    private var newsJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsClientApplication.plusTopNewsComponent().inject(this)

        super.onCreate(savedInstanceState)

        cicerone.router.setResultListener(1001, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterView?.onClick {
            presenter.onFilterClick()
        }

        refreshNewsView?.apply {
            setColorSchemeResources(R.color.colorAccent)

            setOnRefreshListener {
                presenter.refresh()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        cicerone.navigatorHolder.setNavigator(getNavigator())
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()

        super.onPause()
    }

    override fun onDestroy() {
        newsJob?.cancel()

        super.onDestroy()
    }

    override fun setTitle(title: String) {
        newsToolbarTitleView?.text = title
    }

    override fun showTopNews(articles: List<ArticleUI>) {
        val itemDecorator = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.decorator_listview)!!)

        launch(UI) {
            newsListView?.apply {
                layoutManager = LinearLayoutManager(activity)
                addItemDecoration(itemDecorator)
                adapter = NewsAdapter(articles, this@TopNewsFragment)
            }
        }
    }

    override fun showProgress(show: Boolean) {
        launch(UI) {
            newsProgressView?.visible(show)
            newsListView?.visible(!show)
            refreshNewsView?.isRefreshing = false

            if (show) {
                emptyView?.visible(false)
            }
        }
    }

    override fun showEmpty(show: Boolean) {
        emptyView.visible(show)
        newsListView.visible(!show)
    }

    override fun showMessage(message: String) {
        launch(UI) {
            ErrorDialogFragment.getInstance(message).show(activity!!.supportFragmentManager, Screens.ERROR_DIALOG)
            newsListView.visible(false)
        }
    }

    override fun onNewsClick(article: ArticleUI) {
        presenter.onNewsClick(article)
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()

        return true
    }

    override fun getRouter(): BaseRouter = cicerone.router

    override fun onResult(resultData: Any?) {
        presenter.setCategory(activity!!.resources.getString((resultData as CategoryUI).getStringValue()))
    }

    private fun getNavigator(): Navigator {
        return object : SupportFragmentNavigator(childFragmentManager, R.id.newsContainerView) {
            override fun exit() {
                (activity as MainActivity).cicerone.router.exit()
            }

            override fun createFragment(screenKey: String?, data: Any?): Fragment? {
                when (screenKey) {
                    Screens.NEWS_DETAIL_SCREEN -> return NewsDetailFragment.getInstance(data as ArticleUI)
                    Screens.CATEGORIES_SCREEN -> return CategoriesFragment()
                }

                return this@TopNewsFragment
            }

            override fun showSystemMessage(message: String?) {

            }
        }
    }
}