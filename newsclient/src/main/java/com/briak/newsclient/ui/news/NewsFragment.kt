package com.briak.newsclient.ui.news

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.presentation.NewsUIEntity
import com.briak.newsclient.presentation.news.NewsPresenter
import com.briak.newsclient.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment: BaseFragment(), NewsView, NewsAdapter.OnNewsClickListener {
    @InjectPresenter lateinit var presenter: NewsPresenter

    override val layoutRes: Int = R.layout.fragment_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsListView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = NewsAdapter(listOf(NewsUIEntity(), NewsUIEntity(), NewsUIEntity(), NewsUIEntity()), this@NewsFragment)
        }


    }

    override fun onNewsClick(news: NewsUIEntity) {
        presenter
    }
}