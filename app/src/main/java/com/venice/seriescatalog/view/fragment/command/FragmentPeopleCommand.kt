package com.venice.seriescatalog.view.fragment.command

import com.venice.seriescatalog.model.Show

/*
 * Created by Andre Arruda on 1/31/22.
 */
sealed class FragmentPeopleCommand {
    data class OnLoadPeopleSuccess(val string: String?): FragmentPeopleCommand()
    data class OnLoadError(val msg: String?): FragmentPeopleCommand()
}
