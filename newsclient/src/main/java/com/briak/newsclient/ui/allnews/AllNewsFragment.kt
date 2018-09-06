package com.briak.newsclient.ui.allnews

import android.os.Bundle
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
import com.briak.newsclient.extensions.isNotNullOrEmpty
import com.briak.newsclient.extensions.onClick
import com.briak.newsclient.extensions.visible
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.allnews.AllNewsPresenter
import com.briak.newsclient.presentation.allnews.AllNewsView
import com.briak.newsclient.ui.base.BaseFragment
import com.briak.newsclient.ui.base.ErrorDialogFragment
import com.briak.newsclient.ui.topnews.NewsAdapter
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_all_news.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.util.*
import javax.inject.Inject

class AllNewsFragment :
        BaseFragment(),
        AllNewsView,
        NewsAdapter.OnNewsClickListener,
        DatePickerDialog.OnDateSetListener {

    override val layoutRes: Int = R.layout.fragment_all_news

    @Inject
    @InjectPresenter
    lateinit var presenter: AllNewsPresenter

    @ProvidePresenter
    fun provideNewsPresenter(): AllNewsPresenter = presenter

    private lateinit var newsJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsClientApplication.plusAllNewsComponent().inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun startNewsJob(refresh: Boolean, query: String?, date: String?) {
        newsJob = launch(UI) {
            presenter.getAllNews(refresh, query, date)
        }
    }

    override fun onDestroy() {
        newsJob.cancel()

        super.onDestroy()
    }

    override fun onNewsClick(article: ArticleUI) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        startNewsJob(
                false,
                searchView.query?.toString(),
                formatDate(year, monthOfYear, dayOfMonth))
    }

    private fun formatDate(year: Int, monthOfYear: Int, dayOfMonth: Int): String =
            String.format("%s-0%s-0%s", year.toString(), monthOfYear.toString(), dayOfMonth.toString())

    private fun closeSearchView() {
        searchView.setQuery("", false)
        searchView.isIconified = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView.setOnSearchClickListener {
            newsToolbarTitleView.visibility = View.INVISIBLE
        }

        searchView.setOnCloseListener {
            startNewsJob(false, null, null)
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
                        startNewsJob(false, newText, null)
                    } else if (newText.isNotEmpty()) {
                        startNewsJob(false, null, null)
                    }
                }

                return true
            }

        })

        calendarView.onClick {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog.newInstance(
                    this@AllNewsFragment,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.version = DatePickerDialog.Version.VERSION_2
            datePickerDialog.show(activity!!.fragmentManager, DatePickerDialog::class.java.name)
        }
    }
}