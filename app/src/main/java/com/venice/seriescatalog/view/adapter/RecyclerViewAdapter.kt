package com.venice.seriescatalog.view.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.venice.seriescatalog.R
import com.venice.seriescatalog.model.Episode
import com.venice.seriescatalog.view.activity.EpisodeActivity
import kotlinx.android.synthetic.main.item_list_episode.view.*

/*
 * Created by Andre Arruda on 1/29/22.
 */
class RecyclerViewAdapter(
    private var season: List<Episode>
) : RecyclerView.Adapter<RecyclerViewAdapter.SeasonViewHolder>(){

    inner class SeasonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_episode, parent, false)
        return SeasonViewHolder(view)

    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        holder.itemView.apply {
            season[position].image?.let {
                Picasso.get().load(it.medium).into(this.image_view_episode_thumb)
            }
            text_view_episode_name.text = "${position+1} - ${season[position].name}"

            setOnClickListener {
                var intent = Intent(context, EpisodeActivity::class.java)
                var bundle = Bundle()
                bundle.putInt("idEpisode", season[position].id)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return season.size
    }

}