package com.briak.newsclient.ui.favouritesdetail

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.briak.newsclient.R
import com.briak.newsclient.extensions.onClick
import com.briak.newsclient.presentation.favouritesdetail.FavouritesDetailPresenter
import com.briak.newsclient.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_favourites_detail.*

class FavouritesDetailFragment :
        BaseFragment(),
        FavouritesDetailView {

    @InjectPresenter
    lateinit var presenter: FavouritesDetailPresenter

    override val layoutRes: Int = R.layout.fragment_favourites_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouritesDetailBackView.onClick {
            presenter.onBackPressed()
        }
    }
}

