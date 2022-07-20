package com.chargingspots.presentation.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chargingspots.presentation.ui.main.MainActivity
import com.chargingspots.R
import com.chargingspots.di.ViewModelFactory
import com.chargingspots.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        (activity as MainActivity).hideActionBar()
        initSplashLiveData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]
    }

    private fun initSplashLiveData() {
        viewModel.splashLiveData.observe(viewLifecycleOwner) {
            navigateToSpotFragment()
        }
    }

    private fun navigateToSpotFragment() =
        findNavController().navigate(R.id.action_splashFragment_to_spotsFragment)
}