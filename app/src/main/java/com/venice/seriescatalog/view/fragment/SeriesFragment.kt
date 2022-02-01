package com.venice.seriescatalog.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.venice.seriescatalog.R
import com.venice.seriescatalog.view.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SeriesFragment : Fragment(R.layout.fragment_series) {

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //do the fetch
    }
}