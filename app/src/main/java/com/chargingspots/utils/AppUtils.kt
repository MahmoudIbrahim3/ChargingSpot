package com.chargingspots.utils

object AppUtils {
    fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
}