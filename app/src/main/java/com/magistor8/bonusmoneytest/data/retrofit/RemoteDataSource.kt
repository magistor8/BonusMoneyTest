package com.magistor8.bonusmoneytest.data.retrofit

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.magistor8.bonusmoneytest.App
import com.magistor8.bonusmoneytest.data.retrofit.entires.CardsData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RemoteDataSource {

    private val api = Retrofit.Builder()
        .baseUrl(App.instance.baseUri)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(Api::class.java)

    suspend fun getAllCards(offset: Int): Response<List<CardsData>> {
        val offsetJson = mapOf("offset" to offset)
        return api.getAllCards(offsetJson).await()
    }

}