package com.magistor8.bonusmoneytest.data

import com.magistor8.bonusmoneytest.data.retrofit.RemoteDataSource
import com.magistor8.bonusmoneytest.data.retrofit.entires.CardsData
import com.magistor8.bonusmoneytest.domain.repos.CardsRepo
import retrofit2.Response

class CardsRepoImpl(
    private val dataSource: RemoteDataSource
) : CardsRepo {

    override suspend fun getAllCards(offset: Int): Response<List<CardsData>> = dataSource.getAllCards(offset)

}

