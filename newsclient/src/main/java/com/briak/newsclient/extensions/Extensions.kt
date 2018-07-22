package com.briak.newsclient.extensions

import android.view.View
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor

fun View.onClick(action: suspend (View) -> Unit) {
    // launch one actor
    val eventActor = actor<View>(UI, capacity = Channel.CONFLATED) {
        for (event in channel) action(event)
    }
    // install a listener to activate this actor
    setOnClickListener {
        eventActor.offer(it)
    }
}