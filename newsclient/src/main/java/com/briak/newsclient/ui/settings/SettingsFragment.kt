package com.briak.newsclient.ui.settings

import com.arellomobile.mvp.presenter.InjectPresenter
import com.briak.newsclient.R
import com.briak.newsclient.presentation.settings.SettingsPresenter
import com.briak.newsclient.presentation.settings.SettingsView
import com.briak.newsclient.ui.base.BackButtonListener
import com.briak.newsclient.ui.base.BaseFragment

class SettingsFragment :
        BaseFragment(),
        SettingsView,
        BackButtonListener {

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    override val layoutRes: Int = R.layout.fragment_settings

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()

        return true
    }

}