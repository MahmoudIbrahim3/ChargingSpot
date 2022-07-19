package com.chargingspots.data.utils

import okhttp3.ResponseBody

sealed class ErrorEntity {

    object Network: ErrorEntity()

    object NotFound: ErrorEntity()

    object ServerError: ErrorEntity()

    object UnAuthorized: ErrorEntity()

    data class Business(val error: ResponseBody? = null): ErrorEntity()

    object UnKnown: ErrorEntity()
}