package com.briak.newsclient.extensions

import android.view.View
import com.briak.newsclient.ui.base.JobHolder
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor
import java.text.SimpleDateFormat
import java.util.*

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

//fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job = launch(UI) { block() }
//
//suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> = async(CommonPool) { block() }
//
//suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T = async(block).await()

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun Date.toShortDate(): String = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(this)