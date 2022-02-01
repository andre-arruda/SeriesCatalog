package com.venice.seriescatalog.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venice.seriescatalog.domain.usecase.GetShowEpisodesUseCase
import com.venice.seriescatalog.domain.usecase.GetShowUseCase
import com.venice.seriescatalog.data.remote.SafeResponse
import com.venice.seriescatalog.data.remote.safeRequest
import com.venice.seriescatalog.view.fragment.command.FragmentShowCommand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/*
 * Created by Andre Arruda on 1/28/22.
 */
class ShowViewModel(
        private val getShowUseCase: GetShowUseCase,
        private val getShowEpisodesUseCase: GetShowEpisodesUseCase,
        private val dispatcher: CoroutineContext = Dispatchers.IO + SupervisorJob()
): ViewModel(){

    private val mutableCommand = MutableLiveData<FragmentShowCommand>()
    val commandLiveData: LiveData<FragmentShowCommand> = mutableCommand

    fun getShowInformation(id: Int) = viewModelScope.launch(dispatcher) {
        when(val response = safeRequest { getShowUseCase.invoke(id) }) {
            is SafeResponse.Success -> FragmentShowCommand.OnLoadShowInformationSuccess(response.value).run()
            is SafeResponse.NetworkError -> FragmentShowCommand.OnLoadError(response.errorMessage).run()
            is SafeResponse.GenericError -> FragmentShowCommand.OnLoadError(response.errorMessage).run()
        }
    }

    fun getShowEpisodes(id: Int) = viewModelScope.launch(dispatcher) {
        when(val response = safeRequest { getShowEpisodesUseCase.invoke(id) }) {
            is SafeResponse.Success -> FragmentShowCommand.OnLoadShowEpisodesSuccess(response.value).run()
        }
    }

    private fun FragmentShowCommand.run() = mutableCommand.postValue(this)

}