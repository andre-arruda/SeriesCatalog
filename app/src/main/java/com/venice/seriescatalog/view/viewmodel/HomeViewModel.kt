package com.venice.seriescatalog.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venice.seriescatalog.domain.usecase.GetAllShowsUseCase
import com.venice.seriescatalog.data.remote.SafeResponse
import com.venice.seriescatalog.data.remote.safeRequest
import com.venice.seriescatalog.domain.usecase.GetShowsByFilterUseCase
import com.venice.seriescatalog.model.Show
import com.venice.seriescatalog.view.fragment.command.FragmentSearchCommand
import com.venice.seriescatalog.view.fragment.command.FragmentSeriesCommand
import com.venice.seriescatalog.view.fragment.command.FragmentShowCommand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/*
 * Created by Andre Arruda on 1/27/22.
 */
class HomeViewModel(
    private val getAllShowsUseCase: GetAllShowsUseCase,
    private val getShowsByFilterUseCase: GetShowsByFilterUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.IO + SupervisorJob()
) : ViewModel() {

    var allShowsPage = 0
    var allShowsResponse: MutableList<Show> = mutableListOf()
    var totalResults = 0

    private val searchMutableCommand = MutableLiveData<FragmentSearchCommand>()
    val searchCommandLiveData: LiveData<FragmentSearchCommand> = searchMutableCommand

    private val seriesMutableCommand = MutableLiveData<FragmentSeriesCommand>()
    val seriesCommandLiveData: LiveData<FragmentSeriesCommand> = seriesMutableCommand

    fun getAllShows() = viewModelScope.launch(dispatcher) {
        when(val response = safeRequest { getAllShowsUseCase.invoke(allShowsPage) }) {
            is SafeResponse.Success -> {
                allShowsPage++
                if (allShowsResponse.isEmpty()) {
                    allShowsResponse.addAll(response.value)
                    totalResults = allShowsResponse.size
                    FragmentSeriesCommand.OnLoadAllShowsSuccess(allShowsResponse.toList()).run()
                } else {
                    val oldShows = allShowsResponse
                    val newShows = response.value
                    oldShows.addAll(newShows)
                    totalResults = oldShows.size
                    FragmentSeriesCommand.OnLoadAllShowsSuccess(oldShows.toList()).run()
                }
            }
            else -> Log.d("RESPONSE ERROR: ", "No results available")
        }
    }

    fun getShowsByFilter(filter: String) = viewModelScope.launch(dispatcher) {
        when(val response = safeRequest { getShowsByFilterUseCase.invoke(filter) }) {
            is SafeResponse.Success -> {
                FragmentSearchCommand.OnLoadSeriesByFilterSuccess(response.value).run()
            }
            else -> Log.d("RESPONSE ERROR: ", "No results available")
        }
    }


    private fun FragmentSeriesCommand.run() = seriesMutableCommand.postValue(this)
    private fun FragmentSearchCommand.run() = searchMutableCommand.postValue(this)


}