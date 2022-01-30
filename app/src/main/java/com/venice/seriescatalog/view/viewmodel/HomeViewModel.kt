package com.venice.seriescatalog.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venice.seriescatalog.domain.usecase.GetAllShowsUseCase
import com.venice.seriescatalog.data.remote.SafeResponse
import com.venice.seriescatalog.data.remote.safeRequest
import com.venice.seriescatalog.domain.usecase.GetShowsByFilterUseCase
import com.venice.seriescatalog.model.Show
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

    var allShowsPage = 1
    var allShowsResponse: MutableList<Show> = mutableListOf()

    fun getAllShows() = viewModelScope.launch(dispatcher) {
        when(val response = safeRequest { getAllShowsUseCase.invoke(allShowsPage) }) {
            is SafeResponse.Success -> {
                allShowsPage++
                if (allShowsResponse.isEmpty()) {
                    allShowsResponse.addAll(response.value)
                } else {
                    val oldShows = allShowsResponse
                    val newShows = response.value
                    oldShows.addAll(newShows)
                }
                //pass allShowResponse to view
            }
            else -> Log.d("RESPONSE ERROR: ", "No results available")
        }
    }

    fun getShowsByFilter(filter: String) = viewModelScope.launch(dispatcher) {
        when(val response = safeRequest { getShowsByFilterUseCase.invoke(filter) }) {
            is SafeResponse.Success -> {
                //pass list of shows response to view
            }
            else -> Log.d("RESPONSE ERROR: ", "No results available")
        }
    }


}