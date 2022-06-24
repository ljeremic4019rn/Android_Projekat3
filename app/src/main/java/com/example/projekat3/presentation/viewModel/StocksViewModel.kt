package com.example.projekat3.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.repositories.StocksRepository
import com.example.projekat3.presentation.contract.StocksContract
import com.example.projekat3.presentation.view.states.NewsState
import com.example.projekat3.presentation.view.states.StocksState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class StocksViewModel (private val stocksRepository: StocksRepository) : ViewModel(), StocksContract.ViewModel  {

    private val subscriptions = CompositeDisposable()
    override val stockState: MutableLiveData<StocksState> = MutableLiveData()
    override var detailedStock: DetailedStock? = null


    override fun fetchAllStocks(json: String) {
        val subscription = stocksRepository
            .fetchAll(json)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    stockState.value = StocksState.Success(it)
                },
                {
                    stockState.value = StocksState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun searchStock(json: String) {
        detailedStock = stocksRepository.searchStock(json)
    }

}