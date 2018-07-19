package com.briak.newsclient.ui.main

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.briak.newsclient.R
import com.briak.newsclient.model.system.Navigator
import com.briak.newsclient.presentation.main.MainPresenter
import com.briak.newsclient.ui.favourites.FavouritesFragment
import com.briak.newsclient.ui.news.NewsFragment
import com.briak.newsclient.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {
    @InjectPresenter lateinit var presenter: MainPresenter
    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator = Navigator(this, R.id.mainContainerView)
        navigator.replace(NewsFragment(), null)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when {
                item.itemId == R.id.action_news -> {
                    navigator.add(NewsFragment())
                    true
                }
                item.itemId == R.id.action_settings -> {
                    navigator.add(SettingsFragment())
                    true
                }
                item.itemId == R.id.action_favourite -> {
                    navigator.add(FavouritesFragment())
                    true
                }
                else -> false
            }
        }
    }
}
