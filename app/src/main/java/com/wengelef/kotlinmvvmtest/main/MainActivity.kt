package com.wengelef.kotlinmvvmtest.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.wengelef.kotlinmvvmtest.MainApplication
import com.wengelef.kotlinmvvmtest.R
import com.wengelef.kotlinmvvmtest.advanced.AdvancedFragment
import com.wengelef.kotlinmvvmtest.databinding.AcMainBinding
import com.wengelef.kotlinmvvmtest.extension.show
import com.wengelef.kotlinmvvmtest.simple.SimpleFragment
import com.wengelef.kotlinmvvmtest.util.ConnectionObserver
import kotlinx.android.synthetic.main.ac_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var mainViewModel: MainViewModel
    @Inject lateinit var connectionObserver: ConnectionObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as MainApplication).getAppComponent().inject(this)

        val snackBar = Snackbar.make(findViewById(android.R.id.content), "Disconnected", Snackbar.LENGTH_INDEFINITE)
        connectionObserver.networkChanges().subscribe { if (it) snackBar.dismiss() else snackBar.show() }

        val binding = DataBindingUtil.setContentView<AcMainBinding>(this, R.layout.ac_main)
        binding.main = mainViewModel

        mainViewModel.getSimpleFragmentEvents()
                .subscribe { supportFragmentManager.show(R.id.container, SimpleFragment()) }

        mainViewModel.getLessSimpleFragmentEvents()
                .subscribe { supportFragmentManager.show(R.id.container, AdvancedFragment()) }
    }
}
