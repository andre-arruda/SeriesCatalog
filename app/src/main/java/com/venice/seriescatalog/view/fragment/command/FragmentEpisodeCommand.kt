package com.venice.seriescatalog.view.fragment.command

import com.venice.seriescatalog.model.Episode

/*
 * Created by Andre Arruda on 1/28/22.
 */
sealed class FragmentEpisodeCommand {
    data class OnLoadEpisodeSuccess(val episode: Episode): FragmentEpisodeCommand()
    data class OnLoadEpisodeError(val msg: String?): FragmentEpisodeCommand()
}
