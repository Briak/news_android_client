package com.briak.newsclient.ui.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.presentation.ArticleUI
import com.briak.newsclient.entities.news.presentation.CategoryUI
import com.briak.newsclient.extensions.isNotNullOrEmpty
import com.briak.newsclient.extensions.onClick
import com.briak.newsclient.extensions.visible
import com.briak.newsclient.model.di.news.NewsRouter
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.news.NewsPresenter
import com.briak.newsclient.presentation.news.NewsView
import com.briak.newsclient.ui.base.BackButtonListener
import com.briak.newsclient.ui.base.BaseFragment
import com.briak.newsclient.ui.base.ErrorDialogFragment
import com.briak.newsclient.ui.base.RouterProvider
import com.briak.newsclient.ui.categories.CategoriesFragment
import com.briak.newsclient.ui.main.MainActivity
import com.briak.newsclient.ui.newsdetail.NewsDetailFragment
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.result.ResultListener
import javax.inject.Inject


class NewsFragment :
        BaseFragment(),
        NewsView,
        NewsAdapter.OnNewsClickListener,
        BackButtonListener,
        RouterProvider,
        ResultListener {

    @Inject
    lateinit var newsCicerone: Cicerone<NewsRouter>

    @Inject
    @InjectPresenter
    lateinit var presenter: NewsPresenter

    @ProvidePresenter
    fun provideNewsPresenter(): NewsPresenter = presenter

    private lateinit var newsJob: Job

    override val layoutRes: Int = R.layout.fragment_news

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsClientApplication.plusNewsComponent().inject(this)

        super.onCreate(savedInstanceState)

        newsCicerone.router.setResultListener(1001, this)
    }

    override fun onResult(resultData: Any?) {
        presenter.setCategory(activity!!.resources.getString((resultData as CategoryUI).getStringValue()))
        closeSearchView()
    }

    private fun closeSearchView() {
        searchView.setQuery("", false)
        searchView.isIconified = true
    }

    override fun setTitle(title: String) {
        newsToolbarTitleView?.text = title
        newsToolbarTitleView.visible(searchView.isIconified)
    }

    override fun onResume() {
        super.onResume()

        newsCicerone.navigatorHolder.setNavigator(getNavigator())
    }

    override fun onPause() {
        newsCicerone.navigatorHolder.removeNavigator()

        super.onPause()
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()

        return true
    }

    override fun onNewsClick(article: ArticleUI) {
        presenter.onNewsClick(article)
    }

    override fun startNewsJob(refresh: Boolean, query: String?) {
        newsJob = launch(UI) {
            presenter.getTopNews(refresh, query)
        }
    }

    override fun onDestroy() {
        newsJob.cancel()

        super.onDestroy()
    }

    override fun getRouter(): NewsRouter = newsCicerone.router

    override fun showTopNews(articles: List<ArticleUI>) {
        val itemDecorator = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.decorator_listview)!!)

        launch(UI) {
            newsListView?.apply {
                layoutManager = LinearLayoutManager(activity)
                addItemDecoration(itemDecorator)
                adapter = NewsAdapter(articles, this@NewsFragment)
            }
        }
    }

    override fun showMessage(message: String) {
        launch(UI) {
            ErrorDialogFragment.getInstance(message).show(activity!!.supportFragmentManager, Screens.ERROR_DIALOG)
            newsListView.visible(false)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterView?.onClick {
            presenter.onFilterClick()
        }

        refreshNewsView?.apply {
            setColorSchemeResources(R.color.colorAccent)

            setOnRefreshListener {
                startNewsJob(true, null)
            }
        }

        searchView.setOnSearchClickListener {
            newsToolbarTitleView.visibility = View.INVISIBLE
        }

        searchView.setOnCloseListener {
            startNewsJob(false, null)
            newsToolbarTitleView.visibility = View.VISIBLE

            false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNotNullOrEmpty()) {
                    if (newText!!.length >= 2) {
                        startNewsJob(false, newText)
                    } else if (newText.isNotEmpty()) {
                        startNewsJob(false, null)
                    }
                }

                return true
            }

        })
    }

    override fun showEmpty(show: Boolean) {
        emptyView.visible(show)
        newsListView.visible(!show)
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

                return this@NewsFragment
            }

            override fun showSystemMessage(message: String?) {

            }
        }
    }
}