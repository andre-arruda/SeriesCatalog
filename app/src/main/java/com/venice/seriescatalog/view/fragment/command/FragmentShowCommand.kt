package com.venice.seriescatalog.view.fragment.command

import com.venice.seriescatalog.model.Episode
import com.venice.seriescatalog.model.Show

/*
 * Created by Andre Arruda on 1/28/22.
 */
sealed class FragmentShowCommand {
    data class OnLoadShowInformationSuccess(val show: Show): FragmentShowCommand()
    data class OnLoadShowEpisodesSuccess(val listOfEpisodes: List<Episode>): FragmentShowCommand()
    data class OnLoadError(val msg: String?): FragmentShowCommand()
}
