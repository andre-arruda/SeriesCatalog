package com.venice.seriescatalog.view.fragment.command

import com.venice.seriescatalog.model.Episode
import com.venice.seriescatalog.model.Series
import com.venice.seriescatalog.model.Show

/*
 * Created by Andre Arruda on 1/28/22.
 */
sealed class FragmentHomeCommand{
    data class OnLoadAllShowsSuccess(val listOfShow: List<Show>): FragmentHomeCommand()
    data class OnLoadShowsByFilterSuccess(val listOfShowsByFilter: List<Series>): FragmentHomeCommand()
    data class OnLoadError(val msg: String?): FragmentHomeCommand()
}
