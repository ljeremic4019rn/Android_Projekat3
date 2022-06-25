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
import com.example.projekat3.presentation.view.states.UserState
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UserViewModel(private val userRepository: UserRepository) : ViewModel(), UserContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val userState: MutableLiveData<UserState> = MutableLiveData()

    override var user: User = User(0, "username", "email@email.com", "password", 10000.0, 0.0)
    override var detailedStock: DetailedStock? = null

    init {
        getUserByNameMailPass("username","asd@gmail.com","password")
        println("user")
        println(user.toString())
    }



    override val list = arrayListOf(//todo ovo promeni
        LocalStock(0, 0, "name", 1, "a", "01/01/2001", 20.5),
        LocalStock(0, 1, "name1",5, "b", "01/01/2001", 25.5),
        LocalStock(0, 1, "name2",6, "c", "01/01/2001", 22.5),
        LocalStock(0, 2, "name3",9, "d", "01/01/2001", 27.5),
        LocalStock(0, 2, "name4",3, "e", "01/01/2001", 28.5)
    )


    override fun getUserByNameMailPass(username: String, email: String, password: String) {
        val subscription = userRepository
            .getUserByNameMailPass(username, email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    user = User(it.id, it.username, it.email, it.password, it.balance, it.portfolioValue)
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }




    override fun insertUser(userEntity: UserEntity) {
        val subscription = userRepository
            .insertUser(userEntity)
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
                    userState.value = UserState.StockSuccess(it)
                },
                {
                    userState.value = UserState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                },
                {
                    Timber.e("ON COMPLETE")
                }
            )
        subscriptions.add(subscription)    }

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