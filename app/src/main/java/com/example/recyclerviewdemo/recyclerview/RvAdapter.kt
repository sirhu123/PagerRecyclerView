package com.example.recyclerviewdemo.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemo.R

class RvAdapter(context: Context, private val itemCountPerRowOrColumn: Int) :
    RecyclerView.Adapter<RvAdapter.RvHolder>() {

    private val icons = arrayOf(
        R.color.colorPrimaryDark,
        R.color.colorPrimary,
        R.color.colorAccent
    )

    private var mScreenWidth: Int = 0

    init {
        mScreenWidth = context.resources.displayMetrics.widthPixels
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_view, parent, false)
        itemView.layoutParams = itemView.layoutParams.apply {
            width = (mScreenWidth - 10 * itemCountPerRowOrColumn) / itemCountPerRowOrColumn
            height = width * 4 / 3
        }
        return RvHolder(itemView)
    }

    override fun onBindViewHolder(holder: RvHolder, position: Int) {
        holder.bind(icons.random(), "hello world $position")
    }

    override fun getItemCount(): Int {
        return 24
    }

    class RvHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tv: TextView = itemView.findViewById(R.id.tv)

        fun bind(color: Int, text: String) {
            tv.text = text
            itemView.setBackgroundColor(itemView.resources.getColor(color))
        }
    }
}