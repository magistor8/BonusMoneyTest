package com.magistor8.bonusmoneytest.domain.repos

import com.magistor8.bonusmoneytest.data.retrofit.entires.CardsData
import retrofit2.Response

interface CardsRepo {
    suspend fun getAllCards(offset: Int) : Response<List<CardsData>>
}