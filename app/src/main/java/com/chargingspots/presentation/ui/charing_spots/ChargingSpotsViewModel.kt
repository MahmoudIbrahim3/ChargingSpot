package com.chargingspots.presentation.ui.charing_spots

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chargingspots.core.entities.FilterEntity
import com.chargingspots.core.entities.LocationEntity
import com.chargingspots.core.entities.SettingsEntity
import com.chargingspots.core.entities.SpotEntity
import com.chargingspots.data.Interactors
import com.chargingspots.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChargingSpotsViewModel @Inject constructor(
    private val interactors: Interactors
) : BaseViewModel() {

    val chargingSpotsLiveData = MutableLiveData<PagingData<SpotEntity>>()

    fun loadChargingSpots(
        settingsEntity: SettingsEntity?,
        filterEntity: FilterEntity?,
        locationEntity: LocationEntity
    ) = viewModelScope.launch {
        val flow =
            interactors.loadChargingSpotsUseCase(settingsEntity, filterEntity, locationEntity)
                    as Flow<PagingData<SpotEntity>>
        flow.cachedIn(this).collectLatest {
            chargingSpotsLiveData.value = it
        }
    }
}