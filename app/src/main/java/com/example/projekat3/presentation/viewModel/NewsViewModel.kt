package com.example.projekat3.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekat3.data.models.news.News
import com.example.projekat3.data.models.news.NewsResponse
import com.example.projekat3.data.models.news.Resource
import com.example.projekat3.data.repositories.NewsRepository
import com.example.projekat3.presentation.contract.NewsContract
import com.example.projekat3.presentation.view.states.NewsState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class NewsViewModel (private val newsRepository: NewsRepository) : ViewModel(), NewsContract.ViewModel  {

    private val subscriptions = CompositeDisposable()
    override val newsState: MutableLiveData<NewsState> = MutableLiveData()


    override fun fetchAllNews() {
        val subscription = newsRepository
            .fetchAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    newsState.value = NewsState.Success(it)
                },
                {
                    newsState.value = NewsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

}