package com.chargingspots.presentation.ui.filter

import androidx.lifecycle.MutableLiveData
import com.chargingspots.core.entities.FilterEntity
import com.chargingspots.presentation.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterViewModel @Inject constructor() : BaseViewModel() {
    val filterLiveData: MutableLiveData<FilterEntity> =
        MutableLiveData<FilterEntity>(
            FilterEntity()
        )
}