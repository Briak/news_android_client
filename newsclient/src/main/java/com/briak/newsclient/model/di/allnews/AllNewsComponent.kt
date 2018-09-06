package com.briak.newsclient.model.di.allnews

import com.briak.newsclient.ui.allnews.AllNewsFragment
import dagger.Subcomponent

@Subcomponent(modules = [(AllNewsModule::class)])

@AllNewsScope
interface AllNewsComponent {
    fun inject(fragment: AllNewsFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): AllNewsComponent
    }
}
