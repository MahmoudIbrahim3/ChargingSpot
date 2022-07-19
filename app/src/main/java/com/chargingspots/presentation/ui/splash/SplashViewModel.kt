package com.chargingspots.presentation.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chargingspots.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(): BaseViewModel() {

    val splashLiveData = MutableLiveData<Boolean>()

    init {
        delayOnSplash()
    }

    private fun delayOnSplash() {
        viewModelScope.launch {
            delay(2000)
            splashLiveData.postValue(true)
        }
    }
}