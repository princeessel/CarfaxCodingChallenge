package com.carfax.android.api

import com.carfax.android.api.models.GetCarsResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface WebService {

    @GET("assignment.json")
    fun getCars(): Observable<GetCarsResponse>
}