package com.chargingspots.di.module

import com.chargingspots.core.usecases.LoadChargingSpotsUseCase
import com.chargingspots.data.Interactors
import com.chargingspots.data.repository.ChargingSpotsRepository
import com.chargingspots.data.remote.api.ChargingSpotsAPIs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object InteractorsModule {

    @Provides
    @Singleton
    fun provideInteractors(
        chargingSpotsRepository: ChargingSpotsRepository
    ) = Interactors(
        LoadChargingSpotsUseCase(chargingSpotsRepository)
    )

    @Provides
    @Singleton
    fun bindChargingSpotsRepository(chargingSpotsAPIs: ChargingSpotsAPIs) =
        ChargingSpotsRepository(chargingSpotsAPIs)
}