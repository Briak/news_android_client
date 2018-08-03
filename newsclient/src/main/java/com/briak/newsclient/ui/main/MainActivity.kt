package com.briak.newsclient.ui.main

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.R
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.main.MainPresenter
import com.briak.newsclient.presentation.main.MainView
import com.briak.newsclient.ui.base.BackButtonListener
import com.briak.newsclient.ui.base.JobHolder
import com.briak.newsclient.ui.favourites.FavouritesFragment
import com.briak.newsclient.ui.news.NewsFragment
import com.briak.newsclient.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.Job
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainView, JobHolder {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private var newsFragment: NewsFragment? = null
    private var favouritesFragment: FavouritesFragment? = null
    private var settingsFragment: SettingsFragment? = null

    override val job: Job = Job()

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.mainContainerView)
        if (fragment != null
                && fragment is BackButtonListener
                && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                presenter.onBackPressed()
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()

        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NewsClientApplication.component.inject(this)

        setContentView(R.layout.activity_main)

        initFragments()

        if (savedInstanceState == null) {
            presenter.onNewsTabClick()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when {
                item.itemId == R.id.action_news -> {
                    presenter.onNewsTabClick()
                    true
                }
                item.itemId == R.id.action_favourite -> {
                    presenter.onFavouritesTabClick()
                    true
                }
                item.itemId == R.id.action_settings -> {
                    presenter.onSettingsTabClick()
                    true
                }
                else -> false
            }
        }
    }

    private var navigator = Navigator {
        for (command in it) {
            applyCommand(command)
        }
    }

    private fun applyCommand(command: Command) {
        when (command) {
            is Replace -> {
                when (command.screenKey) {
                    Screens.NEWS_SCREEN -> {
                        supportFragmentManager.beginTransaction()
                                .detach(favouritesFragment)
                                .detach(settingsFragment)
                                .attach(newsFragment)
                                .commitNow()
                    }

                    Screens.FAVOURITES_SCREEN -> {
                        supportFragmentManager.beginTransaction()
                                .detach(newsFragment)
                                .detach(settingsFragment)
                                .attach(favouritesFragment)
                                .commitNow()
                    }

                    Screens.SETTINGS_SCREEN -> {
                        supportFragmentManager.beginTransaction()
                                .detach(newsFragment)
                                .detach(favouritesFragment)
                                .attach(settingsFragment)
                                .commitNow()
                    }
                }
            }

            is Back -> finish()
        }
    }

    private fun initFragments() {
        newsFragment = supportFragmentManager.findFragmentByTag(Screens.NEWS_SCREEN) as NewsFragment?
        if (newsFragment == null) {
            newsFragment = NewsFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.mainContainerView, newsFragment, Screens.NEWS_SCREEN)
                    .detach(newsFragment)
                    .commitNow()
        }

        favouritesFragment = supportFragmentManager.findFragmentByTag(Screens.FAVOURITES_SCREEN) as FavouritesFragment?
        if (favouritesFragment == null) {
            favouritesFragment = FavouritesFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.mainContainerView, favouritesFragment, Screens.FAVOURITES_SCREEN)
                    .detach(favouritesFragment)
                    .commitNow()
        }

        settingsFragment = supportFragmentManager.findFragmentByTag(Screens.SETTINGS_SCREEN) as SettingsFragment?
        if (settingsFragment == null) {
            settingsFragment = SettingsFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.mainContainerView, settingsFragment, Screens.SETTINGS_SCREEN)
                    .detach(settingsFragment)
                    .commitNow()
        }
    }
}
