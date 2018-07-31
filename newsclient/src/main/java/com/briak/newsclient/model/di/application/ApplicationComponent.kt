package com.briak.newsclient.model.di.application

import com.briak.newsclient.presentation.main.MainPresenter
import com.briak.newsclient.presentation.settings.SettingsPresenter
import com.briak.newsclient.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(ApplicationModule::class), (NavigationModule::class)])
@Singleton
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(presenter: MainPresenter)
    fun inject(presenter: SettingsPresenter)
}