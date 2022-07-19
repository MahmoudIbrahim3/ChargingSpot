package com.chargingspots.presentation.ui.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chargingspots.AppConst
import com.google.android.material.snackbar.Snackbar
import com.chargingspots.BuildConfig
import com.chargingspots.R
import com.chargingspots.data.utils.ErrorEntity
import com.chargingspots.utils.ContextWrapper
import okhttp3.ResponseBody
import timber.log.Timber

open class BaseActivity: AppCompatActivity(), BaseListener {

    override fun attachBaseContext(newBase: Context?) {
        val base = ContextWrapper.wrap(newBase, AppConst.LANG_EN)
        super.attachBaseContext(base)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    override fun startLoading(viewLoader: View) {
        if (viewLoader is SwipeRefreshLayout)
            viewLoader.isRefreshing = true
        else if (viewLoader is ProgressBar)
            viewLoader.visibility = View.VISIBLE
    }

    override fun stopLoading(viewLoader: View) {
        if (viewLoader is SwipeRefreshLayout)
            viewLoader.isRefreshing = false
        else if (viewLoader is ProgressBar)
            viewLoader.visibility = View.GONE
    }

    override fun onLoadDataFailure(errorEntity: ErrorEntity) {
        when (errorEntity) {
            is ErrorEntity.Business -> onBusinessError(errorEntity.error)
            is ErrorEntity.Network -> showSnackBar(getString(R.string.internet_connection_error))
            is ErrorEntity.UnAuthorized -> showSnackBar("UnAuthorized")
            is ErrorEntity.ServerError -> onServerError()
            is ErrorEntity.NotFound -> showSnackBar(getString(R.string.not_found))
            is ErrorEntity.UnKnown -> showSnackBar(getString(R.string.error))
        }
    }

    override fun showSnackBar(msg: String) {
        Snackbar.make(this.findViewById(R.id.main_layout),
                msg, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    override fun showShortToast(msg: String) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun showLongToast(msg: String) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast.show()
    }

    override fun showAlertDialog(msg: String, callBack: (() -> Unit)?) {
        var alertDialog: AlertDialog? = null

        val builder1 = AlertDialog.Builder(this)
        builder1.setMessage(msg)
        builder1.setCancelable(true)

        builder1.setPositiveButton(getString(R.string.ok)) { _, _ ->
            alertDialog?.dismiss()
            if (callBack != null)
                callBack()
        }

        alertDialog = builder1.create()
        alertDialog.show()
    }

    override fun onBusinessError(error: ResponseBody?) {
        // TODO: Parse this business error and show it's message
        showSnackBar(getString(R.string.error))
    }

    override fun onServerError() {
        showSnackBar(getString(R.string.server_error))
    }
}