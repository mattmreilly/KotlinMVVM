package com.wengelef.kotlinmvvmtest.advanced

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wengelef.kotlinmvvmtest.MainApplication
import com.wengelef.kotlinmvvmtest.R
import com.wengelef.kotlinmvvmtest.databinding.FrAdvancedBinding
import kotlinx.android.synthetic.main.fr_advanced.*
import javax.inject.Inject

class AdvancedFragment : Fragment() {

    @Inject lateinit var advancedViewModel: AdvancedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_advanced, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity.application as MainApplication).getAppComponent().inject(this)

        advancedViewModel.getUserClicks().asObservable()
                .subscribe { Snackbar.make(coordinator, it.login, Snackbar.LENGTH_SHORT).show() }

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context)

        val viewBinding: FrAdvancedBinding = DataBindingUtil.bind(view)
        viewBinding.viewModel = advancedViewModel
    }
}