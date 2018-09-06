package com.briak.newsclient.ui.main

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.R
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.main.MainPresenter
import com.briak.newsclient.presentation.main.MainView
import com.briak.newsclient.ui.about.AboutFragment
import com.briak.newsclient.ui.allnews.AllNewsFragment
import com.briak.newsclient.ui.base.BackButtonListener
import com.briak.newsclient.ui.base.JobHolder
import com.briak.newsclient.ui.topnews.TopNewsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.Job
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject


class MainActivity :
        MvpAppCompatActivity(),
        JobHolder,
        MainView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var cicerone: Cicerone<Router>

    @ProvidePresenter
    fun provideMainPresenter(): MainPresenter = presenter

    private var topNewsFragment: TopNewsFragment? = null
    private var allNewsFragment: AllNewsFragment? = null
    private var settingsFragment: AboutFragment? = null

    override val job: Job = Job()

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

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }

    override fun onResume() {
        super.onResume()

        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()

        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsClientApplication.component.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initFragments()

        if (savedInstanceState == null) {
            presenter.onTopNewsTabClick()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when {
                item.itemId == R.id.action_top_news -> {
                    presenter.onTopNewsTabClick()
                    true
                }
                item.itemId == R.id.action_all_news -> {
                    presenter.onAllNewsTabClick()
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
                    Screens.TOP_NEWS_SCREEN -> {
                        supportFragmentManager.beginTransaction()
                                .detach(settingsFragment)
                                .detach(allNewsFragment)
                                .attach(topNewsFragment)
                                .commitNow()
                    }

                    Screens.ALL_NEWS_SCREEN -> {
                        supportFragmentManager.beginTransaction()
                                .detach(topNewsFragment)
                                .detach(settingsFragment)
                                .attach(allNewsFragment)
                                .commitNow()
                    }

                    Screens.SETTINGS_SCREEN -> {
                        supportFragmentManager.beginTransaction()
                                .detach(topNewsFragment)
                                .detach(allNewsFragment)
                                .attach(settingsFragment)
                                .commitNow()
                    }
                }
            }

            is Back -> finish()
        }
    }

    private fun initFragments() {
        topNewsFragment = supportFragmentManager.findFragmentByTag(Screens.TOP_NEWS_SCREEN) as TopNewsFragment?
        if (topNewsFragment == null) {
            topNewsFragment = TopNewsFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.mainContainerView, topNewsFragment, Screens.TOP_NEWS_SCREEN)
                    .detach(topNewsFragment)
                    .commitNow()
        }

        allNewsFragment = supportFragmentManager.findFragmentByTag(Screens.ALL_NEWS_SCREEN) as AllNewsFragment?
        if (allNewsFragment == null) {
            allNewsFragment = AllNewsFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.mainContainerView, allNewsFragment, Screens.ALL_NEWS_SCREEN)
                    .detach(allNewsFragment)
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
