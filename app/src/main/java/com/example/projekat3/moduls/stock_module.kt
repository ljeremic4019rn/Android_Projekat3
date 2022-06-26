
import com.example.projekat3.data.datasource.remote.StockService
import com.example.projekat3.data.repositories.StocksRepository
import com.example.projekat3.data.repositories.StocksRepositoryImpl
import com.example.projekat3.presentation.viewModel.StocksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val stockModule = module {

    viewModel { StocksViewModel (stocksRepository = get())}

    single<StocksRepository> { StocksRepositoryImpl(remoteDataSource = get()) }

    single<StockService> { create(retrofit = get()) }
}