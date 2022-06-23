
import com.example.projekat3.data.datasource.remote.NewsService
import com.example.projekat3.data.repositories.NewsRepository
import com.example.projekat3.data.repositories.NewsRepositoryImpl
import com.example.projekat3.presentation.viewModel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {

    viewModel { NewsViewModel (newsRepository = get())}

    single<NewsRepository> { NewsRepositoryImpl(remoteDataSource = get()) }

    single<NewsService> { create(retrofit = get()) }
}