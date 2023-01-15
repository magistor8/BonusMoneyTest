package com.magistor8.bonusmoneytest.view.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.magistor8.bonusmoneytest.domain.BaseViewModel
import com.magistor8.bonusmoneytest.domain.contracts.CardFragmentContract
import com.magistor8.bonusmoneytest.domain.repos.CardsRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

class CardsFragmentViewModel : BaseViewModel(), CardFragmentContract.ViewModelInterface, KoinComponent {

    private var offset = 0
    private var allDataLoaded = false

    private val repository : CardsRepo by inject()
    override val viewState: LiveData<CardFragmentContract.ViewState> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewState.mutable().postValue(CardFragmentContract.ViewState.Error(throwable))
    }

    init {
        getAllCards(offset)
    }

    override fun onEvent(event: CardFragmentContract.Events) {
        when(event) {
            is CardFragmentContract.Events.LoadMoreData -> loadMoreData()
        }
    }

    private fun getAllCards(offset : Int) {
        if (allDataLoaded) return
        viewState.mutable().postValue(CardFragmentContract.ViewState.Loading)
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                val response = repository.getAllCards(offset)
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.isNotEmpty()) {
                        viewState.mutable().postValue(CardFragmentContract.ViewState.Success(response.body()!!))
                    } else {
                        viewState.mutable().postValue(CardFragmentContract.ViewState.Success(response.body()!!))
                        allDataLoaded = true
                    }
                } else {
                    viewState.mutable().postValue(CardFragmentContract.ViewState.Error(HttpException(response)))
                }
            }
        }
    }

    private fun loadMoreData() {
        offset++
        getAllCards(offset)
    }

}