package com.magistor8.bonusmoneytest.domain.contracts

import androidx.lifecycle.LiveData
import com.magistor8.bonusmoneytest.data.retrofit.entires.CardsData

interface CardFragmentContract {

    sealed interface ViewState {
        data class Error(val throwable: Throwable) : ViewState
        data class Success(val data: List<CardsData>) : ViewState
        object Loading: ViewState
    }

    sealed interface Events {
        object LoadMoreData: Events
    }

    interface ViewModelInterface {
        val viewState: LiveData<ViewState>
        fun onEvent(event: Events)
    }
}