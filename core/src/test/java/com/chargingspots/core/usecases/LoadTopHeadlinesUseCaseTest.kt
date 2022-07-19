package com.chargingspots.core.usecases

import com.chargingspots.core.entities.Const
import com.chargingspots.core.entities.TopHeadlinesResponse
import com.chargingspots.core.gateways.ChargingSpotsGateWay
import org.junit.Test
import junit.framework.Assert.assertEquals

class LoadTopHeadlinesUseCaseTest {

    private val chargingSpotsGateWay = mock<ChargingSpotsGateWay>()

    @Test
    fun testLoadTopHeadlinesUseCase_success() {
        val topHeadlinesResponse = TopHeadlinesResponse()
        topHeadlinesResponse.status = Const.STATUS_OK

        val filterEntity = FilterEntity()
        whenever(chargingSpotsGateWay.loadChargingSpots(filterEntity)).thenReturn(topHeadlinesResponse)

        val actualTopHeadlineResponse = LoadTopHeadlinesUseCase(chargingSpotsGateWay).invoke(filterEntity)
                as TopHeadlinesResponse

        assertEquals(actualTopHeadlineResponse.status, Const.STATUS_OK)
    }
}