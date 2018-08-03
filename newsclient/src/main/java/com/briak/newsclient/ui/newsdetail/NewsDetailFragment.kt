package com.briak.newsclient.ui.newsdetail

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.briak.newsclient.R
import com.briak.newsclient.extensions.onClick
import com.briak.newsclient.presentation.newsdetail.NewsDetailPresenter
import com.briak.newsclient.presentation.newsdetail.NewsDetailView
import com.briak.newsclient.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_news_detail.*

class NewsDetailFragment :
        BaseFragment(),
        NewsDetailView {

    @InjectPresenter
    lateinit var presenter: NewsDetailPresenter

    override val layoutRes: Int = R.layout.fragment_news_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsDetailBackView.onClick {
            presenter.onBackPressed()
        }
    }
}