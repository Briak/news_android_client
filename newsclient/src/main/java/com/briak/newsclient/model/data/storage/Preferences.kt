package com.briak.newsclient.model.data.storage

import android.content.Context
import com.briak.newsclient.entities.news.presentation.Category
import com.briak.newsclient.model.data.categories.CategoriesHolder
import javax.inject.Inject

class Preferences @Inject constructor(
        private val context: Context
) : CategoriesHolder {
    private val NEWS_DATA = "news data"
    private val CATEGORY = "category"

    private fun getSharedPreferences(prefsName: String) =
            context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    override var category: String
        get() = getSharedPreferences(NEWS_DATA).getString(CATEGORY, Category.BUSINESS.name)
        set(value) {
            getSharedPreferences(NEWS_DATA).edit().putString(CATEGORY, value).apply()
        }

}