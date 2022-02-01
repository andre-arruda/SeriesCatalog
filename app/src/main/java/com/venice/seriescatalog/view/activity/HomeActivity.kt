package com.venice.seriescatalog.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.WindowInsets.Type.ime
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat.toWindowInsetsCompat
import androidx.fragment.app.Fragment
import com.venice.seriescatalog.R
import com.venice.seriescatalog.databinding.ActivityHomeBinding
import com.venice.seriescatalog.view.fragment.SearchFragment
import com.venice.seriescatalog.view.fragment.SeriesFragment
import com.venice.seriescatalog.view.fragment.command.PeopleFragment
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    private val seriesFragment by inject<SeriesFragment>()
    private val searchFragment by inject<SearchFragment>()
    private val peopleFragment by inject<PeopleFragment>()
    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentFragment(seriesFragment)
        setupListener()
    }

    private fun setupListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.seriesItem -> setCurrentFragment(seriesFragment)
                R.id.searchItem -> setCurrentFragment(searchFragment)
                R.id.peopleItem -> setCurrentFragment(peopleFragment)
            }
            true
        }
    }


    private fun setCurrentFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentHome, fragment)
                commit()
            }

}