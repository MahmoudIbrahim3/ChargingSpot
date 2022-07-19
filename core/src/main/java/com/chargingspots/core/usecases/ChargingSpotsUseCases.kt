package com.chargingspots.core.usecases

import com.chargingspots.core.entities.FilterEntity
import com.chargingspots.core.entities.LocationEntity
import com.chargingspots.core.entities.SettingsEntity
import com.chargingspots.core.entities.SpotEntity
import com.chargingspots.core.gateways.ChargingSpotsGateWay

class LoadChargingSpotsUseCase(private val chargingSpotsGateWay: ChargingSpotsGateWay) {
    operator fun invoke(
        settingsEntity: SettingsEntity?,
        filterEntity: FilterEntity?,
        locationEntity: LocationEntity
    ): Any {
        return chargingSpotsGateWay.loadChargingSpots(
            settingsEntity,
            filterEntity,
            locationEntity
        )
    }
}
