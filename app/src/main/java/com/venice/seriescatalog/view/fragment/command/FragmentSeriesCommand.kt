package com.venice.seriescatalog.view.fragment.command

import com.venice.seriescatalog.model.Series
import com.venice.seriescatalog.model.Show

/*
 * Created by Andre Arruda on 1/28/22.
 */
sealed class FragmentSeriesCommand{
    data class OnLoadAllShowsSuccess(val listOfShow: List<Show>): FragmentSeriesCommand()
    data class OnLoadError(val msg: String?): FragmentSeriesCommand()
}
