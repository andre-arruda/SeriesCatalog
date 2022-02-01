package com.venice.seriescatalog.view.fragment.command

import com.venice.seriescatalog.model.Series

/*
 * Created by Andre Arruda on 1/31/22.
 */
sealed class FragmentSearchCommand {
    data class OnLoadSeriesByFilterSuccess(val listOfShowsByFilter: List<Series>): FragmentSearchCommand()
    data class OnLoadError(val msg: String?): FragmentSearchCommand()

}
