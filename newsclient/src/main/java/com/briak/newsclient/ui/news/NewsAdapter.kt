package com.briak.newsclient.ui.news


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.extensions.onClick
import com.briak.newsclient.extensions.toShortDate
import com.briak.newsclient.extensions.visible
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private val list: List<Article>,
                  private val listener: OnNewsClickListener) : RecyclerView.Adapter<NewsAdapter.Holder>() {
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
        holder.itemView.onClick {
            listener.onNewsClick(list[position].title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(news: Article) = with(itemView) {
            titleView.text = news.title
            authorView.text = news.author
            authorView.visible(news.author != null && news.author.isNotEmpty())
            publishedAtView.text = news.publishedAt.toShortDate()
            Glide.with(context)
                    .asBitmap()
                    .load(news.urlToImage)
                    .into(iconView)
            iconView.visible(news.urlToImage != null && news.urlToImage.isNotEmpty())
        }
    }

    interface OnNewsClickListener {
        fun onNewsClick(id: String)
    }
}