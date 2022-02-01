package com.venice.seriescatalog.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.venice.seriescatalog.R
import com.venice.seriescatalog.databinding.FragmentSeriesBinding
import com.venice.seriescatalog.model.Series
import com.venice.seriescatalog.model.Show
import com.venice.seriescatalog.view.adapter.SeriesAdapter
import com.venice.seriescatalog.view.extension.show
import com.venice.seriescatalog.view.fragment.command.FragmentSeriesCommand
import com.venice.seriescatalog.view.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SeriesFragment : Fragment(R.layout.fragment_series) {

    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var binding: FragmentSeriesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSeriesBinding.bind(view)
        //do the fetch
        setupObservables()
        showProgressBar()
        homeViewModel.getAllShows()
    }

    private fun setupObservables() {
        homeViewModel.seriesCommandLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is FragmentSeriesCommand.OnLoadAllShowsSuccess -> {
                    hideProgressBar()
                    populatesSeriesList(it.listOfShow)
                    val totalPages = homeViewModel.totalResults / 250
                    isLastPage = homeViewModel.allShowsPage == totalPages
                    if(isLastPage) {
                        binding.recyclerViewSeries.setPadding(0,0,0,0)
                    }
                }
                is FragmentSeriesCommand.OnLoadError -> {
                    hideProgressBar()
                }
            }
        })
    }


    private fun populatesSeriesList(listOfShows: List<Show>){

        var listOfSeries = mutableListOf<Series>()

        listOfShows.forEach {
            listOfSeries.add(Series(score = 0.0, show = it))
        }

        val adapter = SeriesAdapter(listOfSeries)
        binding.recyclerViewSeries.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            addOnScrollListener(this@SeriesFragment.scrollListener)
            preserveFocusAfterLayout = true
        }

    }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.recyclerViewSeries.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 240
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                     isTotalMoreThanVisible && isScrolling

            if(shouldPaginate) {
                showProgressBar()
                homeViewModel.getAllShows()
                isScrolling = false
            }

        }

    }

    private fun hideProgressBar() {
        binding.progressBarSeriesList.show(false)
        isLoading = false
    }

    private fun showProgressBar() {
        binding.progressBarSeriesList.show(true)
        isLoading = true
    }


}