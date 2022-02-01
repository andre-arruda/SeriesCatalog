package com.venice.seriescatalog.view.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.venice.seriescatalog.R
import com.venice.seriescatalog.model.Series
import com.venice.seriescatalog.view.activity.ShowActivity
import kotlinx.android.synthetic.main.item_list_series.view.*

/*
 * Created by Andre Arruda on 1/31/22.
 */
class SeriesAdapter(
    val series: List<Series>
): RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {

    inner class SeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_series, parent, false)
        return SeriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.itemView.apply {
            series[position].show.image?.let {
                Picasso.get().load(it.medium).into(this.image_view_serie)
            }
            text_view_serie_name.text = series[position].show.name

            setOnClickListener {
                var intent = Intent(context, ShowActivity::class.java)
                var bundle = Bundle()
                bundle.putInt("idShow", series[position].show.id)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return series.size
    }

}