package com.briak.newsclient.ui.about

import com.arellomobile.mvp.presenter.InjectPresenter
import com.briak.newsclient.R
import com.briak.newsclient.presentation.about.AboutPresenter
import com.briak.newsclient.presentation.about.AboutView
import com.briak.newsclient.ui.base.BackButtonListener
import com.briak.newsclient.ui.base.BaseFragment

class AboutFragment :
        BaseFragment(),
        AboutView,
        BackButtonListener {

    @InjectPresenter
    lateinit var presenter: AboutPresenter

    override val layoutRes: Int = R.layout.fragment_about

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()

        return true
    }

}