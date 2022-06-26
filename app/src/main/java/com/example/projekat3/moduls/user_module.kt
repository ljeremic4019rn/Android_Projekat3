package com.example.projekat3.moduls

import com.example.projekat3.data.datasource.local.StocksDataBase
import com.example.projekat3.data.datasource.local.UserDatabase
import com.example.projekat3.data.repositories.PortfolioRepository
import com.example.projekat3.data.repositories.PortfolioRepositoryImpl
import com.example.projekat3.presentation.viewModel.PortfolioViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val userModule = module {

    viewModel { PortfolioViewModel(portfolioRepository = get()) }

    single<PortfolioRepository> { PortfolioRepositoryImpl (userDao = get(), stockDao = get()) }

    single { get<UserDatabase>().getUserDao() }

    single { get<StocksDataBase>().getStocksDao() }

}