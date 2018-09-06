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
import kotlinx.android.synthetic.main.fragment_news_detail.*
import javax.inject.Inject


class NewsDetailFragment :
        BaseFragment(),
        NewsDetailView {

    @Inject
    @InjectPresenter
    lateinit var presenter: NewsDetailPresenter

    @ProvidePresenter
    fun provideNewsDetailPresenter(): NewsDetailPresenter = presenter

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

        setArticle(article)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsDetailBackView.onClick {
            presenter.onBackPressed()
        }
    }

    private fun setArticle(article: ArticleUI) {
        newsDetailToolbarTitle.text = article.title

        iconView.loadImage(article.urlToImage, R.mipmap.ic_bananya_large, progressView, activity)

        if (article.author.isNotNullOrEmpty()) {
            authorView.text = article.author
        } else {
            authorView.visibility = View.INVISIBLE
        }

        if (article.publishedAt != null) {
            publishedAtView.text = article.publishedAt!!.toShortDate()
        } else {
            publishedAtView.visible(false)
        }

        if (article.title.isNotNullOrEmpty()) {
            titleView.text = article.title
        } else {
            titleView.visible(false)
        }

        if (article.description.isNotNullOrEmpty()) {
            descriptionView.text = article.description
        } else {
            descriptionView.visible(false)
        }
    }
}