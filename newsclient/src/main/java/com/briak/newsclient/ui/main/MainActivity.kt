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
import com.briak.newsclient.ui.news.NewsFragment
import com.briak.newsclient.ui.about.AboutFragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject


class MainActivity :
        MvpAppCompatActivity(),
        MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private var newsFragment: NewsFragment? = null
    private var settingsFragment: AboutFragment? = null

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
                item.itemId == R.id.action_about -> {
                    presenter.onAboutTabClick()
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
                                .detach(settingsFragment)
                                .attach(newsFragment)
                                .commitNow()
                    }

                    Screens.SETTINGS_SCREEN -> {
                        supportFragmentManager.beginTransaction()
                                .detach(newsFragment)
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

        settingsFragment = supportFragmentManager.findFragmentByTag(Screens.SETTINGS_SCREEN) as AboutFragment?
        if (settingsFragment == null) {
            settingsFragment = AboutFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.mainContainerView, settingsFragment, Screens.SETTINGS_SCREEN)
                    .detach(settingsFragment)
                    .commitNow()
        }
    }
}
