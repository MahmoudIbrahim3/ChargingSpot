package com.chargingspots.core.entities

data class SettingsEntity(
    var maxDistance: Int = 10000,
    var pageSize: Int = 25
)