package com.chargingspots.data.remote.api

import com.chargingspots.core.entities.SpotEntity
import com.chargingspots.data.remote.ApiFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ChargingSpotsAPIs {

    @GET(ApiFactory.GET_CHARGING_SPOTS)
    suspend fun loadChargingSpots(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("status") status: Boolean? = null
    ): List<SpotEntity>
}
