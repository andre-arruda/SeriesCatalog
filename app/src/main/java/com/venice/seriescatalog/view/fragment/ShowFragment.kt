package com.venice.seriescatalog.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import com.venice.seriescatalog.view.activity.HomeActivity
import com.venice.seriescatalog.R
import com.venice.seriescatalog.databinding.FragmentShowBinding
import com.venice.seriescatalog.model.Episode
import com.venice.seriescatalog.model.Show
import com.venice.seriescatalog.view.adapter.ViewPagerAdapter
import com.venice.seriescatalog.view.extension.show
import com.venice.seriescatalog.view.fragment.command.FragmentShowCommand
import com.venice.seriescatalog.view.viewmodel.ShowViewModel
import kotlinx.android.synthetic.main.fragment_show.*
import org.koin.android.viewmodel.ext.android.viewModel

class ShowFragment : Fragment(R.layout.fragment_show) {

    private val showViewModel by viewModel<ShowViewModel>()
    private lateinit var binding: FragmentShowBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShowBinding.bind(view)
        //do the fetch
        setupObservables()
        showViewModel.getShowInformation(335)
        showViewModel.getShowEpisodes(335)
    }

    private fun setupObservables() {
        showViewModel.commandLiveData.observe(viewLifecycleOwner, Observer {
            when(it) {
                is FragmentShowCommand.OnLoadShowInformationSuccess -> {
                    populateView(it.show)
                }
                is FragmentShowCommand.OnLoadShowEpisodesSuccess -> {
                    populateEpisodesList(it.listOfEpisodes)
                }
                is FragmentShowCommand.OnLoadError -> {

                }
            }
        })
    }

    private fun populateEpisodesList(listOfEpisodes: List<Episode>) {

        var listOfSeasons = getListOfEpisodesBySeason(listOfEpisodes)

        val adapter = ViewPagerAdapter(listOfSeasons)
        binding.viewPagerSeasons.adapter = adapter

        TabLayoutMediator(binding.tabLayoutSeasons, binding.viewPagerSeasons) { tab, position ->
            tab.text = "Season ${position + 1}"

        }.attach()

    }

    private fun populateView(show: Show) {
        Picasso.get().load(show.image.medium).into(binding.imageViewShowImage)
        binding.textViewShowName.text = show.name
        var genres = ""
        for (i in 0 until show.genres.size) {
            genres += if(i == show.genres.size-1) {
                show.genres[i]
            } else {
                show.genres[i] + ", "
            }
        }
        binding.textViewShowGenres.text = genres
        var daysOfStreaming = ""
        for (i in 0 until show.schedule.days.size) {
            daysOfStreaming += if(i == show.schedule.days.size-1) {
                show.schedule.days[i]
            } else {
                show.schedule.days[i] + ", "
            }
        }
        binding.textViewDays.text = daysOfStreaming
        binding.textViewShowTime.text = "Show time: " + show.schedule.time
        binding.textViewShowSummary.text = HtmlCompat.fromHtml(show.summary, 0)
        binding.textViewStaticShowSummary.show(true)
        binding.textViewStaticStreamingOn.show(true)
    }

    private fun getListOfEpisodesBySeason(listOfEpisodes: List<Episode>) : MutableList<List<Episode>> {

        var listOfSeasons = mutableListOf<List<Episode>>()

        for (i in 0 until listOfEpisodes[listOfEpisodes.lastIndex].season.toInt()) {
            var season = listOfEpisodes.filterIndexed { _, episode -> episode.season.toInt() == i+1 }
            listOfSeasons.add(season)
        }

        return listOfSeasons
    }

}