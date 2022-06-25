package com.example.projekat3.moduls

import com.example.projekat3.data.datasource.local.StocksDataBase
import com.example.projekat3.data.datasource.local.UserDatabase
import com.example.projekat3.data.repositories.UserRepository
import com.example.projekat3.data.repositories.UserRepositoryImpl
import com.example.projekat3.presentation.viewModel.UserViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val userModule = module {

    viewModel { UserViewModel(userRepository = get()) }

    single<UserRepository> { UserRepositoryImpl (userDao = get(), stockDao = get()) }

    single { get<UserDatabase>().getUserDao() }

    single { get<StocksDataBase>().getStocksDao() }

}