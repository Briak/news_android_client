package com.briak.newsclient.ui.favourites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.R
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.favourite.FavouritesPresenter
import com.briak.newsclient.presentation.favourite.FavouritesView
import com.briak.newsclient.ui.base.BackButtonListener
import com.briak.newsclient.ui.base.BaseFragment
import com.briak.newsclient.ui.favouritesdetail.FavouritesDetailFragment
import com.briak.newsclient.ui.main.MainActivity
import com.briak.newsclient.ui.news.NewsAdapter
import kotlinx.android.synthetic.main.fragment_favourites.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import javax.inject.Inject

class FavouritesFragment :
        BaseFragment(),
        FavouritesView,
        NewsAdapter.OnNewsClickListener,
        BackButtonListener {

    @InjectPresenter
    lateinit var presenter: FavouritesPresenter

    @Inject
    lateinit var favouritesNavigationHolder: NavigatorHolder

    @Inject
    lateinit var favouritesRouter: Router

    override val layoutRes: Int = R.layout.fragment_favourites

    @ProvidePresenter
    fun provideFavouritesPresenter(): FavouritesPresenter = FavouritesPresenter(favouritesRouter)

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsClientApplication.favouritesNavigationComponent.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouritesListView.apply {
            layoutManager = LinearLayoutManager(activity)
//            adapter = NewsAdapter(listOf(NewsUIEntity(), NewsUIEntity(), NewsUIEntity(), NewsUIEntity()), this@FavouritesFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        favouritesNavigationHolder.setNavigator(getNavigator())
    }

    override fun onPause() {
        favouritesNavigationHolder.removeNavigator()

        super.onPause()
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()

        return true
    }

    override fun onNewsClick(id: String) {
        presenter.onFavouritesClick(id)
    }

    private fun getNavigator(): Navigator {
        return object : SupportFragmentNavigator(childFragmentManager, R.id.favouritesContainerView) {
            override fun exit() {
                (activity as MainActivity).router.exit()
            }

            override fun createFragment(screenKey: String?, data: Any?): Fragment? {
                when (screenKey) {
                    Screens.FAVOURITES_DETAIL_SCREEN -> return FavouritesDetailFragment()
                }

                return this@FavouritesFragment
            }

            override fun showSystemMessage(message: String?) {

            }
        }
    }

}