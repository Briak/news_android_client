package com.briak.newsclient.ui.categories

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.presentation.CategoryUI
import com.briak.newsclient.extensions.Direction
import com.briak.newsclient.extensions.onClick
import com.briak.newsclient.extensions.removeDrawable
import com.briak.newsclient.extensions.setDrawable
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(
        private val listener: OnCategoryClickListener, private val selected: String
) : RecyclerView.Adapter<CategoryAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(CategoryUI.values()[position], selected)
        holder.itemView.onClick {
            listener.onCategoryClick(CategoryUI.values()[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = CategoryUI.values().size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: CategoryUI, selected: String) = with(itemView) {
            nameView.apply {
                setText(category.getStringValue())
                if (category.name.toLowerCase() == selected.toLowerCase()) {
                    setDrawable(R.mipmap.ic_done, Direction.END)
                } else {
                    removeDrawable()
                }
            }
        }!!
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(category: CategoryUI)
    }
}