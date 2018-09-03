package com.briak.newsclient.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.briak.newsclient.ui.base.JobHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.wang.avi.AVLoadingIndicatorView
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

val backgroundPool: CoroutineDispatcher by lazy {
    val numProcessors = Runtime.getRuntime().availableProcessors()
    when {
        numProcessors <= 2 -> newFixedThreadPoolContext(2, "background")
        else -> CommonPool
    }
}

suspend fun <T> asyncTask(function: suspend () -> T): T {
    return withContext(backgroundPool) { function() }
}

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun Date.toShortDate(): String = SimpleDateFormat("EEE, MMMM d, yyyy", Locale("en_US")).format(this)

fun String?.isNotNullOrEmpty(): Boolean = this != null && this.isNotEmpty()

fun ImageView.loadImage(
        url: String?,
        placeHolder: Int,
        progressView: AVLoadingIndicatorView?,
        ctx: Context? = null
) {
    Glide.with(ctx ?: context)
            .asBitmap()
            .load(url)
            .into(object : BitmapImageViewTarget(this) {
                override fun onLoadStarted(placeholder: Drawable?) {
                    progressView?.visible(true)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    setImageResource(placeHolder)
                    progressView?.visible(false)
                }

                override fun setResource(resource: Bitmap?) {
                    setImageBitmap(resource)
                    progressView?.visible(false)
                }
            })
}

enum class Direction {
    START,
    END,
    TOP,
    BOTTOM
}

fun TextView.setDrawable(id: Int, direction: Direction) {
    when (direction) {
        Direction.START -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(id, 0, 0, 0)
        Direction.END -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, id, 0)
        Direction.TOP -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(0, id, 0, 0)
        Direction.BOTTOM -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, id)
    }
}

fun TextView.removeDrawable() {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
}