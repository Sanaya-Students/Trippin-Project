package com.example.trippin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.trippin.Domain.Trends
import com.example.trippin.R

class TrendsAdapter(var items: ArrayList<Trends>) :
    RecyclerView.Adapter<TrendsAdapter.ViewHolder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflator =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_trends, parent, false)
        return ViewHolder(inflator)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.subtitle.text = items[position].subtitle
        val drawableResourcesId = holder.itemView.resources.getIdentifier(
            items[position].picAdress.toString(),
            "drawable", holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourcesId)
            .transform(GranularRoundedCorners(30f, 30f, 0f, 0f))
            .into(holder.pic)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var subtitle: TextView
        var pic: ImageView

        init {
            title = itemView.findViewById(R.id.judul)
            subtitle = itemView.findViewById(R.id.sub_judul)
            pic = itemView.findViewById(R.id.gambar)
        }
    }
}