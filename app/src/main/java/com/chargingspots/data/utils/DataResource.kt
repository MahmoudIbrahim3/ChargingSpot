package com.chargingspots.data.utils

sealed class DataResource<out T> {

    data class Success<out T>(val value: T): DataResource<T>()

    object Loading: DataResource<Nothing>()

    data class Failure<out T>(val errorEntity: ErrorEntity): DataResource<T>()

    object NoThing: DataResource<Nothing>()
}