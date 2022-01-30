package com.venice.seriescatalog.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import com.venice.seriescatalog.R
import com.venice.seriescatalog.databinding.FragmentEpisodeBinding
import com.venice.seriescatalog.model.Episode
import com.venice.seriescatalog.view.extension.show
import com.venice.seriescatalog.view.fragment.command.FragmentEpisodeCommand
import com.venice.seriescatalog.view.viewmodel.EpisodeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class EpisodeFragment(
    private var episodeId: Int
) : Fragment(R.layout.fragment_episode) {

    private val episodeViewModel by viewModel<EpisodeViewModel>()
    private lateinit var binding: FragmentEpisodeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEpisodeBinding.bind(view)
        setupObservables()
        //do the fetch
        binding.progressBarEpisodeInfo.show(true)
        episodeViewModel.getEpisodeInformation(episodeId)
    }

    private fun setupObservables() {
        episodeViewModel.commandLiveData.observe(viewLifecycleOwner, Observer {
            when(it) {
                is FragmentEpisodeCommand.OnLoadEpisodeSuccess -> {
                    populateView(it.episode)
                    binding.progressBarEpisodeInfo.show(false)
                }
                is FragmentEpisodeCommand.OnLoadEpisodeError -> {
                    //do nothing
                }
            }
        })
    }

    private fun populateView(episode: Episode) {
        Picasso.get().load(episode.image.original).into(binding.imageViewEpisodeImage)
        binding.textViewEpisodeName.text = episode.name
        binding.textViewEpisodeNumber.text = getString(R.string.text_episode) + " " + episode.number
        binding.textViewEpisodeSeason.text = getString(R.string.text_season) + " " + episode.season
        binding.textViewEpisodeSummary.text = HtmlCompat.fromHtml(episode.summary, 0)
        binding.textViewStaticEpisodeSummary.show(true)
    }



}