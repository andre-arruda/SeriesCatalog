package com.venice.seriescatalog.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.venice.seriescatalog.view.activity.HomeActivity
import com.venice.seriescatalog.R
import com.venice.seriescatalog.model.Episode
import com.venice.seriescatalog.view.fragment.EpisodeFragment
import com.venice.seriescatalog.view.fragment.HomeFragment
import kotlinx.android.synthetic.main.item_list_episode.view.*

/*
 * Created by Andre Arruda on 1/29/22.
 */
class RecyclerViewAdapter(
    private var season: List<Episode>
) : RecyclerView.Adapter<RecyclerViewAdapter.SeasonViewHolder>(){

    inner class SeasonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    var onClickListener: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_episode, parent, false)
        return SeasonViewHolder(view)

    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        holder.itemView.apply {
            Picasso.get().load(season[position].image.medium).into(this.image_view_episode_thumb)
            text_view_episode_name.text = "${position+1} - ${season[position].name}"

            setOnClickListener {
                Log.d("TAG", "ID DO EPISODE: ${season[position].id}")
                var episodeFragment = EpisodeFragment(season[position].id)
                (context as HomeActivity).changeFragmentWithBackStack(episodeFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return season.size
    }

}