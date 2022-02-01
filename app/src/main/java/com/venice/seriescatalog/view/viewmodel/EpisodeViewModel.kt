package com.venice.seriescatalog.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venice.seriescatalog.domain.usecase.GetEpisodeUseCase
import com.venice.seriescatalog.data.remote.SafeResponse
import com.venice.seriescatalog.data.remote.safeRequest
import com.venice.seriescatalog.view.fragment.command.FragmentEpisodeCommand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/*
 * Created by Andre Arruda on 1/28/22.
 */
class EpisodeViewModel(
        private val getEpisodeUseCase: GetEpisodeUseCase,
        private val dispatcher: CoroutineContext = Dispatchers.IO + SupervisorJob()
): ViewModel() {

    private val mutableCommand = MutableLiveData<FragmentEpisodeCommand>()
    val commandLiveData: LiveData<FragmentEpisodeCommand> = mutableCommand

    fun getEpisodeInformation(id: Int) = viewModelScope.launch(dispatcher) {
        when(val response = safeRequest { getEpisodeUseCase.invoke(id) }) {
            is SafeResponse.Success -> FragmentEpisodeCommand.OnLoadEpisodeSuccess(response.value).run()
            is SafeResponse.NetworkError -> FragmentEpisodeCommand.OnLoadEpisodeError(response.errorMessage).run()
            is SafeResponse.GenericError -> FragmentEpisodeCommand.OnLoadEpisodeError(response.errorMessage).run()
        }
    }

    private fun FragmentEpisodeCommand.run() = mutableCommand.postValue(this)

}