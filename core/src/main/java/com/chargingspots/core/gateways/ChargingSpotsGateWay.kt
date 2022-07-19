package com.chargingspots.core.gateways

import com.chargingspots.core.entities.FilterEntity
import com.chargingspots.core.entities.LocationEntity
import com.chargingspots.core.entities.SettingsEntity

interface ChargingSpotsGateWay {
    fun loadChargingSpots(
        settingsEntity: SettingsEntity?,
        filterEntity: FilterEntity?,
        locationEntity: LocationEntity
    ): Any
}