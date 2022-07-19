package com.chargingspots.data.repository

import androidx.paging.PagingSource
import com.chargingspots.core.entities.FilterEntity
import com.chargingspots.data.remote.api.ChargingSpotsAPIs
import com.chargingspots.core.entities.LocationEntity
import com.chargingspots.core.entities.SettingsEntity
import com.chargingspots.core.entities.SpotEntity
import com.chargingspots.utils.AppUtils.round
import com.chargingspots.utils.LocationUtils
import retrofit2.HttpException
import java.io.IOException

class ChargingSpotsPagingSource(
    private val chargingSpotsAPIs: ChargingSpotsAPIs,
    private var locationEntity: LocationEntity,
    var settingsEntity: SettingsEntity? = null,
    var filterEntity: FilterEntity? = null
):
    PagingSource<String, SpotEntity>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, SpotEntity> {
        return try {
            val page = (params.key ?: 1).toString().toInt()

            val response = chargingSpotsAPIs.loadChargingSpots(
                page = page,
                limit = params.loadSize,
                status = if (filterEntity?.showOnlyOpen!!) true else null,
            )

            val filteredResponse = ArrayList<SpotEntity>()
            response.forEach {
                val distance = LocationUtils.findTwoLocationsDistance(
                    locationEntity.lat,
                    locationEntity.lng,
                    it.lat?.toDouble() ?: 0.0,
                    it.lng?.toDouble() ?: 0.0
                )
                it.distance = distance.round(2)

                if (it.distance <= settingsEntity?.maxDistance!!)
                    filteredResponse.add(it)
            }

            LoadResult.Page(
                filteredResponse.sortedBy {
                    it.distance
                },
                prevKey = if (page == 1) null else (page - 1).toString(),
                nextKey = if (response.isEmpty()) null else (page + 1).toString()
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override val keyReuseSupported: Boolean = true
}