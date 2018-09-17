package com.briak.newsclient.ui.newsdetail

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.presentation.ArticleUI
import com.briak.newsclient.extensions.*
import com.briak.newsclient.model.system.Parameters
import com.briak.newsclient.presentation.newsdetail.NewsDetailPresenter
import com.briak.newsclient.presentation.newsdetail.NewsDetailView
import com.briak.newsclient.ui.base.BaseFragment
import com.briak.newsclient.ui.base.RouterProvider
import kotlinx.android.synthetic.main.fragment_news_detail.*


class NewsDetailFragment :
        BaseFragment(),
        NewsDetailView {

    @InjectPresenter
    lateinit var presenter: NewsDetailPresenter

    @ProvidePresenter
    fun provideNewsDetailPresenter(): NewsDetailPresenter = NewsDetailPresenter()

    override val layoutRes: Int = R.layout.fragment_news_detail

    private lateinit var article: ArticleUI

    companion object {
        fun getInstance(article: ArticleUI): NewsDetailFragment {
            val fragment = NewsDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable(Parameters.ARTICLE, article)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsClientApplication.plusTopNewsComponent().inject(this)

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            article = arguments?.getSerializable(Parameters.ARTICLE) as ArticleUI
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(Parameters.ARTICLE, article)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (savedInstanceState != null) {
            article = savedInstanceState.getSerializable(Parameters.ARTICLE) as ArticleUI
        }

        presenter.setArticle(article)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setRouter((parentFragment as RouterProvider).getRouter())

        newsDetailBackView.onClick {
            presenter.onBackPressed()
        }
    }

    override fun setNewsDetailToolbarTitle(value: String) {
        newsDetailToolbarTitle.text = value
    }

    override fun loadImage(urlToImage: String?, iconId: Int) {
        iconView.loadImage(urlToImage, iconId, progressView, activity)
    }

    override fun setAuthor(value: String) {
        authorView.text = value
    }

    override fun hideAuthor() {
        authorView.visibility = View.INVISIBLE
    }

    override fun setPublishedAt(date: String) {
        publishedAtView.text = date
    }

    override fun hidePublishedAt() {
        publishedAtView.visible(false)
    }

    override fun setTitle(title: String) {
        titleView.text = title
    }

    override fun hideTitle() {
        titleView.visible(false)
    }

    override fun setDescription(description: String) {
        descriptionView.text = description
    }

    override fun hideDescription() {
        descriptionView.visible(false)
    }
}