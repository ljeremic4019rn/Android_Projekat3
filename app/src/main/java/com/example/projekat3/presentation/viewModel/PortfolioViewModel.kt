package com.example.projekat3.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.models.stocks.GroupedStock
import com.example.projekat3.data.models.stocks.LocalStock
import com.example.projekat3.data.models.stocks.LocalStockEntity
import com.example.projekat3.data.models.user.User
import com.example.projekat3.data.models.user.UserEntity
import com.example.projekat3.data.repositories.PortfolioRepository
import com.example.projekat3.presentation.contract.PortfolioContract
import com.example.projekat3.presentation.view.states.PortfolioState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PortfolioViewModel(private val portfolioRepository: PortfolioRepository) : ViewModel(), PortfolioContract.ViewModel {
    private val subscriptions = CompositeDisposable()
    override val portfolioState: MutableLiveData<PortfolioState> = MutableLiveData()
    override var userStocks: MutableLiveData<List<LocalStock>> = MutableLiveData()
    override var user: MutableLiveData<User> = MutableLiveData()
    override var detailedStock: DetailedStock? = null
    override val amountOfOwned: ArrayList<GroupedStock> = arrayListOf()



    init {//todo ovo je cisto da bi se baza otkljucala i mogli da vidimo sta je upisano - obrisi
        getUserByNameMailPass("username","asd@gmail.com","password")
        getAllStocksFromUser(1)
    }


    override fun getUserByNameMailPass(username: String, email: String, password: String) {
        val subscription = portfolioRepository
            .getUserByNameMailPass(username, email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    user.value = User(it.id, it.username, it.email, it.password, it.balance, it.portfolioValue)
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)

    }


    override fun insertUser(user: UserEntity) {
        val subscription = portfolioRepository
            .insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("COMPLETE")
                    getUserByNameMailPass(user.username, user.email, user.password)
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun updateUserBalance(userId: Long, balance: Double, portfolioValue: Double) {
        val subscription = portfolioRepository
            .updateUserBalance(userId, balance, portfolioValue)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("COMPLETE")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllStocksFromUser(userId: Long){
        val subscription = portfolioRepository
            .getAllStocksFromUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    userStocks.value = it
                },
                {
                    portfolioState.value = PortfolioState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                },
                {
                    Timber.e("ON COMPLETE")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllStocksFromUserGrouped(userId: Long) {
        val subscription = portfolioRepository
            .getAllStocksFromUserGrouped(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    portfolioState.value = PortfolioState.StockSuccessGrouped(it)
                },
                {
                    portfolioState.value = PortfolioState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                },
                {
                    Timber.e("ON COMPLETE")
                }
            )
        subscriptions.add(subscription)
    }

    override fun insertStock(localStockEntity: LocalStockEntity) {
        val subscription = portfolioRepository
            .insertStock(localStockEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("COMPLETE")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun searchStock(json: String) {
        detailedStock = portfolioRepository.searchStock(json)
    }
}