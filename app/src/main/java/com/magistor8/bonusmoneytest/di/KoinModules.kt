package com.magistor8.bonusmoneytest.di

import com.magistor8.bonusmoneytest.data.CardsRepoImpl
import com.magistor8.bonusmoneytest.data.retrofit.RemoteDataSource
import com.magistor8.bonusmoneytest.domain.repos.CardsRepo
import com.magistor8.bonusmoneytest.view.cards.CardsAdapter
import com.magistor8.bonusmoneytest.view.cards.CardsFragment
import com.magistor8.bonusmoneytest.view.cards.CardsFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {

    single<CardsRepo> { CardsRepoImpl(RemoteDataSource()) }

    scope<CardsFragment> {
        viewModel { CardsFragmentViewModel() }
        scoped { CardsAdapter() }
    }
}