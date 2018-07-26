package com.briak.newsclient.model.di

import com.briak.newsclient.presentation.main.MainPresenter
import com.briak.newsclient.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(ApplicationModule::class), (NavigationModule::class)])
@Singleton
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(presenter: MainPresenter)
}