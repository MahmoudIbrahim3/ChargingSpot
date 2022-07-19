package com.chargingspots.presentation.ui.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chargingspots.core.entities.LocationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationViewModel @Inject constructor(): ViewModel() {

    var location: MutableLiveData<LocationEntity>? = MutableLiveData(null)
}