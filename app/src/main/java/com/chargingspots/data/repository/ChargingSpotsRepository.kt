package com.chargingspots.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chargingspots.core.entities.FilterEntity
import com.chargingspots.core.entities.LocationEntity
import com.chargingspots.core.entities.SettingsEntity
import com.chargingspots.core.entities.SpotEntity
import com.chargingspots.core.gateways.ChargingSpotsGateWay
import com.chargingspots.data.remote.api.ChargingSpotsAPIs
import kotlinx.coroutines.flow.Flow

class ChargingSpotsRepository(private val chargingSpotsAPIs: ChargingSpotsAPIs): ChargingSpotsGateWay {

    override fun loadChargingSpots(
        settingsEntity: SettingsEntity?,
        filterEntity: FilterEntity?,
        locationEntity: LocationEntity
    ): Flow<PagingData<SpotEntity>> {
        return Pager(
            PagingConfig(
                pageSize = settingsEntity?.pageSize!!,
                enablePlaceholders = false
            )
        ) {
            val pagerSource = ChargingSpotsPagingSource(
                chargingSpotsAPIs,
                locationEntity,
                settingsEntity,
                filterEntity
            )
            pagerSource
        }.flow
    }
}