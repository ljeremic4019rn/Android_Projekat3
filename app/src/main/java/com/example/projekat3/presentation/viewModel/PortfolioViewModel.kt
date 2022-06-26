package com.example.projekat3.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.models.stocks.LocalStock
import com.example.projekat3.data.models.stocks.LocalStockEntity
import com.example.projekat3.data.models.user.User
import com.example.projekat3.data.models.user.UserEntity
import com.example.projekat3.data.repositories.UserRepository
import com.example.projekat3.presentation.contract.UserContract
import com.example.projekat3.presentation.view.states.PortfolioState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PortfolioViewModel(private val userRepository: UserRepository) : ViewModel(), UserContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val portfolioState: MutableLiveData<PortfolioState> = MutableLiveData()

    override var user: User = User(0, "XX", "XX", "XX", 0.0, 0.0)
    override var detailedStock: DetailedStock? = null

    init {//todo ovo je cisto da bi se baza otkljucala i mogli da vidimo sta je upisano - obrisi
        getUserByNameMailPass("username","asd@gmail.com","password")
        getAllStocksFromUser(1)

    }


    override val list = arrayListOf(
//todo ovo promeni
        LocalStock(0, 0, "AT&T, Inc.", -10, "T", "01/01/2001", 193.8),
        LocalStock(0, 0, "AT&T, Inc.", 10, "T", "01/02/2001", -193.8),
        LocalStock(0, 0, "AT&T, Inc.", 10, "T", "01/03/2001", -193.8),
        LocalStock(0, 0, "AT&T, Inc.", 51, "T", "01/04/2001", -988.38),
        LocalStock(0, 0, "AT&T, Inc.", -1, "T", "01/05/2001", 19.38),
        LocalStock(0, 0, "AT&T, Inc.", 5, "T", "01/06/2001", -96.9),
        LocalStock(0, 0, "AT&T, Inc.", 5, "T", "01/07/2001", -96.9),
        LocalStock(0, 0, "AT&T, Inc.", -10, "T", "01/08/2001", -193.8),
        LocalStock(0, 0, "AT&T, Inc.", 100, "T", "01/09/2001", -1938.0),
        LocalStock(0, 0, "AT&T, Inc.", 10, "T", "01/10/2001", -193.8),
    )


    override fun getUserByNameMailPass(username: String, email: String, password: String) {
        val subscription = userRepository
            .getUserByNameMailPass(username, email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {//todo ako je nepostojeci user, vuce prvog iz baze, popravi / modifikuj ovo da radi pravilno
                    user = User(it.id, it.username, it.email, it.password, it.balance, it.portfolioValue)
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }


    override fun insertUser(user: UserEntity) {
        val subscription = userRepository
            .insertUser(user)
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
        val subscription = userRepository
            .getAllStocksFromUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    portfolioState.value = PortfolioState.StockSuccess(it)
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
        val subscription = userRepository
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
        val subscription = userRepository
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
        detailedStock = userRepository.searchStock(json)
    }

}