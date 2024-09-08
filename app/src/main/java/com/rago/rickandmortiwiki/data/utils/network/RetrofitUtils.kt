package com.rago.rickandmortiwiki.data.utils.network

import arrow.core.Either
import com.google.gson.Gson
import com.rago.rickandmortiwiki.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val ERROR_INTERNET = "Unable to resolve host"
const val ERROR_TIMEOUT = "timeout"


// Maneja la peticion de retrofit por callbackflow.
fun <T> retrofitArrow(api: Call<T>): Flow<Either<ErrorResponse, SuccessResponse<T>>> =
    callbackFlow {
        val callback: Callback<T> = object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                try {
                    if (response.errorBody() != null) {
                        trySend(
                            Either.Left(
                                ErrorResponse(response.code(), errorRes = R.string.error_server)
                            )
                        )
                    }

                    if (response.body() != null) {
                        trySend(
                            Either.Right(
                                SuccessResponse(
                                    code = response.code(),
                                    data = response.body()
                                )
                            )
                        )
                    } else {
                        trySend(
                            Either.Left(
                                ErrorResponse(response.code(), errorRes = R.string.error_server)
                            )
                        )
                    }
                } catch (e: Exception) {
                    trySend(
                        Either.Left(
                            ErrorResponse(
                                code = 500,
                                errorRes = R.string.error_server
                            )
                        )
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = t.message?.contains(ERROR_INTERNET) ?: false || t.message?.contains(
                    ERROR_TIMEOUT
                ) ?: false

                if (result) {
                    trySend(
                        Either.Left(
                            ErrorResponse(
                                code = 500,
                                errorRes = R.string.not_internet
                            )
                        )
                    )
                } else {
                    trySend(
                        Either.Left(
                            ErrorResponse(
                                code = 500,
                                errorRes = R.string.error_server
                            )
                        )
                    )
                }
            }
        }

        api.enqueue(callback)

        awaitClose {
            if (api.isCanceled) {
                api.cancel()
            }
        }
    }