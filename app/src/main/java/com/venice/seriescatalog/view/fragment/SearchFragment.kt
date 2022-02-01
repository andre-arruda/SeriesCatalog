package com.venice.seriescatalog.view.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.venice.seriescatalog.R
import com.venice.seriescatalog.databinding.FragmentSearchBinding
import com.venice.seriescatalog.model.Series
import com.venice.seriescatalog.view.adapter.SeriesAdapter
import com.venice.seriescatalog.view.extension.show
import com.venice.seriescatalog.view.fragment.command.FragmentSearchCommand
import com.venice.seriescatalog.view.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

/*
 * Created by Andre Arruda on 1/31/22.
 */
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var binding: FragmentSearchBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        setupObservables()
        setupListeners()

    }

    private fun setupListeners() {
        var searchEditTextJob: Job? = null
        binding.editTextSearch.addTextChangedListener { editable ->
            searchEditTextJob?.cancel()
            searchEditTextJob = MainScope().launch {
                delay(750L)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        binding.progressBarSearchList.show(true)
                        homeViewModel.getShowsByFilter(editable.toString())
                    }
                }
            }
        }
    }


    private fun setupObservables() {
        homeViewModel.searchCommandLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is FragmentSearchCommand.OnLoadSeriesByFilterSuccess -> {
                    populatesSeriesList(it.listOfShowsByFilter)
                    binding.progressBarSearchList.show(false)
                }
                is FragmentSearchCommand.OnLoadError -> {
                    //do nothing yet
                }
            }
        })
    }

    private fun populatesSeriesList(listOfSeries: List<Series>){

        val adapter = SeriesAdapter(listOfSeries)
        binding.recyclerViewSearchSeries.apply {
            this.adapter = adapter
            //layoutManager = LinearLayoutManager(context)
            layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        }

    }



}