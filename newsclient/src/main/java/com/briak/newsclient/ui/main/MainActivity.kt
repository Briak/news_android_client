package com.briak.newsclient.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.briak.newsclient.R
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.main.MainPresenter
import com.briak.newsclient.ui.favourites.FavouritesFragment
import com.briak.newsclient.ui.news.NewsFragment
import com.briak.newsclient.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class MainActivity : MvpAppCompatActivity(), MainView {
    @InjectPresenter
    lateinit var presenter: MainPresenter
    private var navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.mainContainerView) {
        override fun exit() {
            finish()
        }

        override fun createFragment(screenKey: String?, data: Any?): Fragment? {
            when (screenKey) {
                Screens.NEWS_SCREEN -> return NewsFragment()
                Screens.SETTINGS_SCREEN -> return SettingsFragment()
                Screens.FAVOURITES_SCREEN -> return FavouritesFragment()
            }

            return null
        }

        override fun showSystemMessage(message: String?) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator.applyCommands(arrayOf<Command>(Forward(Screens.NEWS_SCREEN, null)))

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when {
                item.itemId == R.id.action_news -> {
                    navigator.applyCommands(arrayOf<Command>(Forward(Screens.NEWS_SCREEN, null)))
                    true
                }
                item.itemId == R.id.action_settings -> {
                    navigator.applyCommands(arrayOf<Command>(Forward(Screens.SETTINGS_SCREEN, null)))
                    true
                }
                item.itemId == R.id.action_favourite -> {
                    navigator.applyCommands(arrayOf<Command>(Forward(Screens.FAVOURITES_SCREEN, null)))
                    true
                }
                else -> false
            }
        }
    }
}
