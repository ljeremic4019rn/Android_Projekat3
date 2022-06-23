package com.example.projekat3.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekat3.data.models.news.Resource
import com.example.projekat3.data.repositories.NewsRepository
import com.example.projekat3.presentation.contract.NewsContract
import com.example.projekat3.presentation.view.states.NewsState
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
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Success -> newsState.value = NewsState.DataFetched
                        is Resource.Error -> newsState.value = NewsState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    newsState.value = NewsState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
}