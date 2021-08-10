package com.soten.gominno.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.soten.gominno.FragmentNavigator
import com.soten.gominno.R
import com.soten.gominno.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val navigator by lazy {
        FragmentNavigator(supportFragmentManager, R.id.container)
    }

    private lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindViews(savedInstanceState)
    }

    private fun bindViews(savedInstanceState: Bundle?) {
        MobileAds.initialize(this) {}

        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tab_home ->
                    navigator.navigate(
                        "home",
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    ) { HomeFragment() }
                R.id.tab_settings ->
                    navigator.navigate(
                        "settings",
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                    ) { SettingsFragment() }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.tab_home
        } else {
            navigator.onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        navigator.onSaveInstanceState(outState)
    }
}