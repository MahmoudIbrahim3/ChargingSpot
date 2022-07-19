package com.chargingspots.presentation.ui.settings

import androidx.lifecycle.MutableLiveData
import com.chargingspots.AppConst
import com.chargingspots.core.entities.SettingsEntity
import com.chargingspots.presentation.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsViewModel @Inject constructor() : BaseViewModel() {
    val settingsLiveData: MutableLiveData<SettingsEntity> =
        MutableLiveData<SettingsEntity>(
            SettingsEntity()
        )
}