package com.chargingspots.core.usecases

import com.chargingspots.core.entities.Const
import com.chargingspots.core.entities.LocationEntity
import com.chargingspots.core.entities.SpotEntity
import com.chargingspots.core.gateways.ChargingSpotsGateWay
import org.junit.Test
import junit.framework.Assert.assertEquals

class LoadTopHeadlinesUseCaseTest {

    private val chargingSpotsGateWay = mock<ChargingSpotsGateWay>()

    @Test
    fun testLoadTopHeadlinesUseCase_success() {
        val chargingSpotsResponse = arrayListOf(SpotEntity())

        val locationEntity = LocationEntity(10.0, 20.0)
        whenever(
            chargingSpotsGateWay.loadChargingSpots(
                null, null, locationEntity
            )
        )
            .thenReturn(chargingSpotsResponse)

        val actualChargingSpotsResponse =
            LoadChargingSpotsUseCase(chargingSpotsGateWay).invoke(null, null, locationEntity)
                    as List<SpotEntity>

        assertEquals(actualChargingSpotsResponse.size, 1)
    }
}