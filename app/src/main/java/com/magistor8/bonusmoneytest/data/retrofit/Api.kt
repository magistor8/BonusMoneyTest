package com.magistor8.bonusmoneytest.data.retrofit

import com.magistor8.bonusmoneytest.data.retrofit.entires.CardsData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {
    @POST("getAllCompanies")
    @Headers("TOKEN: 123")
    fun getAllCards(
        @Body offset: Map<String, Int>
    ): Deferred<Response<List<CardsData>>>
}
