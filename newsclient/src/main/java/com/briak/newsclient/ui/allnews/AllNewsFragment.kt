package com.briak.newsclient.ui.allnews

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
import com.briak.newsclient.extensions.onClick
import com.briak.newsclient.extensions.visible
import com.briak.newsclient.model.di.allnews.AllNewsRouter
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.allnews.AllNewsPresenter
import com.briak.newsclient.presentation.allnews.AllNewsView
import com.briak.newsclient.ui.base.BackButtonListener
import com.briak.newsclient.ui.base.BaseFragment
import com.briak.newsclient.ui.base.ErrorDialogFragment
import com.briak.newsclient.ui.base.RouterProvider
import com.briak.newsclient.ui.main.MainActivity
import com.briak.newsclient.ui.newsdetail.NewsDetailFragment
import com.briak.newsclient.ui.topnews.NewsAdapter
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_all_news.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import java.util.*
import javax.inject.Inject

class AllNewsFragment :
        BaseFragment(),
        AllNewsView,
        BackButtonListener,
        RouterProvider,
        NewsAdapter.OnNewsClickListener,
        DatePickerDialog.OnDateSetListener {

    override val layoutRes: Int = R.layout.fragment_all_news

    @Inject
    lateinit var cicerone: Cicerone<AllNewsRouter>

    @Inject
    @InjectPresenter
    lateinit var presenter: AllNewsPresenter

    @ProvidePresenter
    fun provideNewsPresenter(): AllNewsPresenter = presenter

    private var newsJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsClientApplication.plusAllNewsComponent().inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView.apply {
            setOnSearchClickListener {
                newsToolbarTitleView.visibility = View.INVISIBLE
            }

            setOnCloseListener {
                presenter.setQuery(null)
                newsToolbarTitleView.visibility = View.VISIBLE

                false
            }


            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    presenter.setSearchQuery(newText)

                    return true
                }

            })
        }

        calendarView.onClick {
            presenter.showCalendar()
        }

        refreshNewsView?.apply {
            setColorSchemeResources(R.color.colorAccent)

            setOnRefreshListener {
                presenter.refresh()
            }
        }
    }

    override fun onDestroy() {
        newsJob?.cancel()

        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()

        cicerone.navigatorHolder.setNavigator(getNavigator())

        newsToolbarTitleView.visible(searchView.isIconified)
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()

        super.onPause()
    }

    override fun showAllNews(articles: List<ArticleUI>) {
        val itemDecorator = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.decorator_listview)!!)

        launch(UI) {
            newsListView?.apply {
                layoutManager = LinearLayoutManager(activity)
                addItemDecoration(itemDecorator)
                adapter = NewsAdapter(articles, this@AllNewsFragment)
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

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()

        return true
    }

    override fun getRouter(): BaseRouter = cicerone.router

    override fun onNewsClick(article: ArticleUI) {
        presenter.onNewsClick(article)
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        presenter.setCalendar(buildCalendar(year, monthOfYear, dayOfMonth))
    }

    override fun showCalendar(calendar: Calendar) {
        val datePickerDialog = DatePickerDialog.newInstance(
                this@AllNewsFragment,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.apply {
            version = DatePickerDialog.Version.VERSION_2
            maxDate = calendar
        }

        datePickerDialog.show(activity!!.fragmentManager, DatePickerDialog::class.java.name)
    }

    override fun setTitle(title: String) {
        newsToolbarTitleView.text = title
    }

    private fun buildCalendar(year: Int, monthOfYear: Int, dayOfMonth: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        return calendar
    }

    private fun getNavigator(): Navigator {
        return object : SupportFragmentNavigator(childFragmentManager, R.id.allNewsContainerView) {
            override fun exit() {
                (activity as MainActivity).cicerone.router.exit()
            }

            override fun createFragment(screenKey: String?, data: Any?): Fragment? {
                when (screenKey) {
                    Screens.ALL_NEWS_DETAIL_SCREEN -> return NewsDetailFragment.getInstance(data as ArticleUI)
                }

                return this@AllNewsFragment
            }

            override fun showSystemMessage(message: String?) {

            }
        }
    }
}