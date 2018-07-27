package com.briak.newsclient.extensions

import android.view.View
import com.briak.newsclient.ui.base.JobHolder
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor

fun View.onClick(action: suspend (View) -> Unit) {
    val eventActor = actor<View>(UI, parent = contextJob, capacity = Channel.CONFLATED) {
        for (event in channel) action(event)
    }

    setOnClickListener {
        eventActor.offer(it)
    }
}

val View.contextJob: Job?
    get() = (context as? JobHolder)?.job