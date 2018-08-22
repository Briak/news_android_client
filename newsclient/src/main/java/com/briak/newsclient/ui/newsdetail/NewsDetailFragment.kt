package com.briak.newsclient.ui.newsdetail

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.extensions.isNotNullOrEmpty
import com.briak.newsclient.extensions.loadImage
import com.briak.newsclient.extensions.onClick
import com.briak.newsclient.extensions.toShortDate
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

    private lateinit var article: Article

    companion object {
        fun getInstance(article: Article): NewsDetailFragment {
            val fragment = NewsDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable("ARTICLE", article)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsClientApplication.plusNewsComponent().inject(this)

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            article = arguments?.getSerializable("ARTICLE") as Article
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsDetailBackView.onClick {
            presenter.onBackPressed()
        }

        setArticle(article)
    }

    private fun setArticle(article: Article) {
        newsDetailToolbarTitle.text = article.title

        iconView.loadImage(article.urlToImage, R.mipmap.ic_bananya_large, progressView, activity)

        if (article.author.isNotNullOrEmpty()) {
            authorView.text = article.author
        } else {
            authorView.visibility = View.INVISIBLE
        }

        publishedAtView.text = article.publishedAt.toShortDate()

        titleView.text = article.title

        descriptionView.text = article.description
    }
}