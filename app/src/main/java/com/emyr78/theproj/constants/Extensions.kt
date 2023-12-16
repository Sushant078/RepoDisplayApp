package com.emyr78.theproj.constants

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.emyr78.theproj.models.api.ApiResponse
import kotlinx.coroutines.flow.flowOn

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this) { it?.let { t -> action(t) } }
}

fun convertDateFormat(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    return try {
        val parsedDate: Date = inputFormat.parse(inputDate) ?: Date()
        outputFormat.format(parsedDate)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun <T> toResultFlow(call: suspend () -> Response<T>?): Flow<ApiResponse<T?>> {
    return flow {
        emit(ApiResponse.Loading)

        try {
            val c = call()
            c?.let {
                if (c.isSuccessful) {
                    emit(ApiResponse.Success(c.body()))
                } else {
                    c.errorBody()?.let {
                        val error = it.string()
                        it.close()
                        emit(ApiResponse.Error(error))
                    }
                }
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }

    }.flowOn(Dispatchers.IO)
}

fun <T : Any> handleApiResponse(
    apiCall: ApiResponse<T?>,
    apiCallSuccess: (T) -> Unit,
    apiCallError: (String) -> Unit,
    apiCallLoading: () -> Unit
) {
    try {
        when (apiCall) {
            is ApiResponse.Success -> {
                apiCall.data?.let { resultData ->
                    apiCallSuccess(resultData)
                }
            }

            is ApiResponse.Error -> {
                apiCallError(apiCall.message)
            }

            is ApiResponse.Loading -> {
                apiCallLoading()
            }
        }
    } catch (e: Exception) {
        apiCallError(e.message ?: "An error occurred")
    }
}

