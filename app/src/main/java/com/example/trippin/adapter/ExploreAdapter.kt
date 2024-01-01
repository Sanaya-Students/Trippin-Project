package com.example.trippin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.trippin.R

// Dalam kelas ExploreAdapter
class ExploreAdapter(
    context: Context,
    private val data: List<Pair<Int, String>>,
    private val onDeleteClickListener: (Int) -> Unit
) : ArrayAdapter<Pair<Int, String>>(context, R.layout.explore_item, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            itemView = inflater.inflate(R.layout.explore_item, parent, false)
        }

        val historyItemText = itemView!!.findViewById<TextView>(R.id.historyItemText)
        val eraseButton = itemView.findViewById<ImageButton>(R.id.eraseButton)

        val historyItem = getItem(position)
        historyItemText.text = historyItem?.second

        eraseButton.setOnClickListener {
            historyItem?.first?.let { id -> onDeleteClickListener.invoke(id) }
        }

        return itemView
    }
}

