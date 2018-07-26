package com.briak.newsclient.ui.newsdetail

import com.briak.newsclient.R
import com.briak.newsclient.ui.base.BackButtonListener
import com.briak.newsclient.ui.base.BaseFragment

class NewsDetailFragment: BaseFragment(), BackButtonListener {
    override fun onBackPressed(): Boolean {
        activity!!.supportFragmentManager.popBackStack()

        return true
    }

    override val layoutRes: Int = R.layout.fragment_news_detail

}