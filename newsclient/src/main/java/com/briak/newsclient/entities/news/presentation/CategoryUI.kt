package com.briak.newsclient.entities.news.presentation

import com.briak.newsclient.R

enum class CategoryUI {
    BUSINESS {
        override fun getStringValue(): Int = R.string.category_business
    },
    ENTERTAINMENT {
        override fun getStringValue(): Int = R.string.category_entertainment
    },
    HEALTH {
        override fun getStringValue(): Int = R.string.category_health
    },
    SCIENCE {
        override fun getStringValue(): Int = R.string.category_science
    },
    TECHNOLOGY {
        override fun getStringValue(): Int = R.string.category_technology
    },
    SPORTS {
        override fun getStringValue(): Int = R.string.category_sports
    };

    abstract fun getStringValue(): Int
}