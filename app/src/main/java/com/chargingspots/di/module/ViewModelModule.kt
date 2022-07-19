package com.chargingspots.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chargingspots.di.ViewModelFactory
import com.chargingspots.di.ViewModelKey
import com.chargingspots.presentation.ui.settings.SettingsViewModel
import com.chargingspots.presentation.ui.charging_spot_details.CharingSpotDetailsViewModel
import com.chargingspots.presentation.ui.charing_spots.ChargingSpotsViewModel
import com.chargingspots.presentation.ui.filter.FilterViewModel
import com.chargingspots.presentation.ui.location.LocationViewModel
import com.chargingspots.presentation.ui.main.MainViewModel
import com.chargingspots.presentation.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@InstallIn(SingletonComponent::class)
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChargingSpotsViewModel::class)
    abstract fun bindChargingSpotsViewModel(chargingSpotsViewModel: ChargingSpotsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharingSpotDetailsViewModel::class)
    abstract fun bindChargingSpotDetailsViewModel(chargingSpotDetailsViewModel: CharingSpotDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun bindLocationViewModel(locationViewModel: LocationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterViewModel::class)
    abstract fun bindFilterViewModel(filterViewModel: FilterViewModel): ViewModel
}
