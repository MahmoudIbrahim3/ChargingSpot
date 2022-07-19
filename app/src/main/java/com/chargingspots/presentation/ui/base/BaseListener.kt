package com.chargingspots.presentation.ui.base

import android.view.View
import com.chargingspots.data.utils.ErrorEntity
import okhttp3.ResponseBody

interface BaseListener {
    fun startLoading(viewLoader: View)
    fun stopLoading(viewLoader: View)
    fun showSnackBar(msg: String)
    fun showShortToast(msg: String)
    fun showLongToast(msg: String)
    fun showAlertDialog(msg: String, callBack: (() -> Unit)?)
    fun onBusinessError(error: ResponseBody?)
    fun onServerError()
    fun onLoadDataFailure(errorEntity: ErrorEntity)
}