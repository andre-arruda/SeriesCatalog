package com.venice.seriescatalog.view.fragment

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import com.venice.seriescatalog.R
import com.venice.seriescatalog.databinding.FragmentShowBinding
import com.venice.seriescatalog.model.Episode
import com.venice.seriescatalog.model.Show
import com.venice.seriescatalog.view.adapter.ViewPagerAdapter
import com.venice.seriescatalog.view.extension.show
import com.venice.seriescatalog.view.fragment.command.FragmentShowCommand
import com.venice.seriescatalog.view.viewmodel.ShowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class ShowFragment : Fragment(R.layout.fragment_show) {

    private val showViewModel by viewModel<ShowViewModel>()
    private lateinit var binding: FragmentShowBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var showId = requireActivity().intent.extras?.get("idShow").toString().toInt()
        binding = FragmentShowBinding.bind(view)
        //do the fetch
        setupObservables()

        runBlocking {
            withContext(Dispatchers.Default) {
                showViewModel.getShowInformation(showId)
                showViewModel.getShowEpisodes(showId)
            }
        }


    }

    private fun setupObservables() {
        showViewModel.commandLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is FragmentShowCommand.OnLoadShowInformationSuccess -> {
                    populateView(it.show)
                }
                is FragmentShowCommand.OnLoadShowEpisodesSuccess -> {
                    populateEpisodesList(it.listOfEpisodes)
                    binding.progressBarShowInfo.show(false)
                }
                is FragmentShowCommand.OnLoadError -> {
                    binding.progressBarShowInfo.show(false)
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
        show.image?.let {
            Picasso.get().load(it.medium).into(binding.imageViewShowImage)
        }
        binding.textViewShowName.text = show.name
        var genres = ""
        binding.textViewShowGenres.text = if (!show.genres.isNullOrEmpty()) {
            for (i in 0 until show.genres.size) {
                genres += if(i == show.genres.size-1) {
                    show.genres[i]
                } else {
                    show.genres[i] + ", "
                }
            }
            genres
        } else {
            getString(R.string.no_information)
        }
        var daysOfStreaming = ""
        binding.textViewDays.text = if (!show.schedule.days.isNullOrEmpty()) {
            for (i in 0 until show.schedule.days.size) {
                daysOfStreaming += if (i == show.schedule.days.size - 1) {
                    show.schedule.days[i]
                } else {
                    show.schedule.days[i] + ", "
                }
            }
            daysOfStreaming
        } else {
            getString(R.string.no_information)
        }
        if(!show.schedule.time.isNullOrBlank()) {
            binding.textViewShowTime.text = getString(R.string.show_time) + show.schedule.time
        }
        binding.textViewShowSummary.text = if(!show.summary.isNullOrBlank()) {
            HtmlCompat.fromHtml(show.summary, 0)
        } else {
            getString(R.string.no_information)
        }
        binding.textViewStaticShowSummary.show(true)
        binding.textViewStaticStreamingOn.show(true)
        binding.imageViewShowImage.show(true)
    }

    private fun getListOfEpisodesBySeason(listOfEpisodes: List<Episode>) : MutableList<List<Episode>> {

        var listOfSeasons = mutableListOf<List<Episode>>()

        if(!listOfEpisodes.isEmpty()) {
            for (i in 0 until listOfEpisodes[listOfEpisodes.lastIndex].season.toInt()) {
                var season =
                    listOfEpisodes.filterIndexed { _, episode -> episode.season.toInt() == i + 1 }
                listOfSeasons.add(season)
            }
        }

        return listOfSeasons
    }

}