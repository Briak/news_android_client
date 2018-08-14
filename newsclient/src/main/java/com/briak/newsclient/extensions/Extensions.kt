package com.briak.newsclient.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.briak.newsclient.ui.base.JobHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import kotlinx.coroutines.experimental.Job
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

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun Date.toShortDate(): String = SimpleDateFormat("EEE, MMMM d, yyyy", Locale("us")).format(this)

fun String?.isNotNullOrEmpty(): Boolean = this != null && this.isNotEmpty()

fun ImageView.loadImage(
        url: String?,
        placeHolder: Int,
        ctx: Context? = null
) {
    Glide.with(ctx ?: context)
            .asBitmap()
            .load(url)
            .into(object : BitmapImageViewTarget(this) {
                override fun onLoadStarted(placeholder: Drawable?) {

                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    setImageResource(placeHolder)
                }

                override fun setResource(resource: Bitmap?) {
                    setImageBitmap(resource)
                }
            })
}