package com.wengelef.kotlinmvvmtest.simple

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wengelef.kotlinmvvmtest.MainApplication
import com.wengelef.kotlinmvvmtest.R
import com.wengelef.kotlinmvvmtest.databinding.FrSimpleBinding
import javax.inject.Inject

class SimpleFragment : Fragment() {

    @Inject lateinit var simpleViewModel: SimpleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_simple, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity.application as MainApplication).getAppComponent().inject(this)

        val viewBinding: FrSimpleBinding = DataBindingUtil.bind(view)
        viewBinding.viewModel = simpleViewModel
    }
}