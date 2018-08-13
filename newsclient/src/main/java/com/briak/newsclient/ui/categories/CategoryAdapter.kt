package com.briak.newsclient.ui.categories

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.presentation.Category
import com.briak.newsclient.extensions.onClick
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(private val listener: OnCategoryClickListener) : RecyclerView.Adapter<CategoryAdapter.Holder>() {
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(Category.values()[position])
        holder.itemView.onClick {
            listener.onCategoryClick(Category.values()[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = Category.values().size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category) = with(itemView) {
            nameView.setText(category.getStringValue())
        }
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }
}