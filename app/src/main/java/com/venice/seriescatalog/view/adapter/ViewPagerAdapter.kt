package com.venice.seriescatalog.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.venice.seriescatalog.R
import com.venice.seriescatalog.model.Episode
import kotlinx.android.synthetic.main.item_list_season.view.*


/*
 * Created by Andre Arruda on 1/29/22.
 */
class ViewPagerAdapter(
        private val seasons: MutableList<List<Episode>>
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_season, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val adapter = RecyclerViewAdapter(seasons[position])
        holder.itemView.apply {
            recycler_view_season.adapter = adapter
            recycler_view_season.layoutManager = LinearLayoutManager(context)
        }
        holder.itemView.recycler_view_season.adapter = adapter
    }

    override fun getItemCount(): Int {
        return seasons.size
    }
}