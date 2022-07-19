package com.chargingspots.data.utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

suspend fun <T> networkBoundResource (
    result: MutableLiveData<DataResource<T>>? = null,
    apiCall: suspend () -> T
): MutableLiveData<DataResource<T>>? = withContext(Dispatchers.IO) {
    try {
        result?.postValue(DataResource.Loading)
        val response = apiCall.invoke()
        result?.postValue(DataResource.Success(response))

    } catch (throwable: Throwable) {

        when (throwable) {
            is IOException -> result?.postValue(DataResource.Failure(ErrorEntity.Network))
            is HttpException -> {
                val errorResponse = convertErrorBody(throwable)
                when (throwable.code()) {
                    HttpURLConnection.HTTP_INTERNAL_ERROR ->
                        result?.postValue(DataResource.Failure(ErrorEntity.ServerError))
                    HttpURLConnection.HTTP_UNAUTHORIZED ->
                        result?.postValue(DataResource.Failure(ErrorEntity.UnAuthorized))
                    HttpURLConnection.HTTP_NOT_FOUND ->
                        result?.postValue(DataResource.Failure(ErrorEntity.NotFound))
                    else ->
                        if (errorResponse == null)
                            result?.postValue(DataResource.Failure(ErrorEntity.UnKnown))
                        else
                            result?.postValue(
                                DataResource.Failure(
                                    ErrorEntity.Business(
                                        errorResponse
                                    )
                                )
                            )
                }
            }
            else ->
                result?.postValue(DataResource.Failure(ErrorEntity.UnKnown))
        }
    }

    result
}

private fun convertErrorBody(throwable: HttpException): ResponseBody? {
    return try {
        throwable.response()?.errorBody()?.let {
            return throwable.response()?.errorBody()
        }
    } catch (exception: Exception) {
        exception.printStackTrace()
        null
    }
}